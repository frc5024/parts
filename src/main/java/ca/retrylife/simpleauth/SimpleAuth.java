package ca.retrylife.simpleauth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import ca.retrylife.simplelogger.SimpleLogger;
import ca.retrylife.util.MathUtils;

/**
 * A simple authentication tool using HMAC
 */
public class SimpleAuth {

	/* Constants */
	private static final String HASH_PREFIX = "$31$";
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final int HASH_SIZE = 128;
	private static final SecureRandom RANDOM = new SecureRandom();
	private static final Pattern HASH_LAYOUT = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");

	// Computational Cost
	private int cost;

	/**
	 * Create a SimpleAuth object
	 */
	public SimpleAuth() {
		this(16);
	}

	/**
	 * Create a SimpleAuth object
	 * 
	 * @param cost Hashing computational cost
	 */
	public SimpleAuth(int cost) {

		// Set cost
		this.cost = (int) MathUtils.clamp(cost, 0, 30);

	}

	/**
	 * Hash a data string
	 * 
	 * @param data Data
	 * @return Hash
	 */
	public String hash(String data) {

		// Handle null data
		if (data == null) {
			data = "";
		}

		// Derived from: https://stackoverflow.com/a/2861125/12481555
		byte[] salt = new byte[HASH_SIZE / 8];
		RANDOM.nextBytes(salt);

		byte[] dk = null;
		try {
			dk = pbkdf2(data.toCharArray(), salt, 1 << cost);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			SimpleLogger.log("SimpleAuth", "Failed to hash a string");
			return null;
		}

		byte[] hash = new byte[salt.length + dk.length];
		System.arraycopy(salt, 0, hash, 0, salt.length);
		System.arraycopy(dk, 0, hash, salt.length, dk.length);
		Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
		return HASH_PREFIX + cost + '$' + enc.encodeToString(hash);

	}

	private byte[] pbkdf2(char[] password, byte[] salt, int iterations)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		// Derived from: https://stackoverflow.com/a/2861125/12481555
		KeySpec spec = new PBEKeySpec(password, salt, iterations, HASH_SIZE);
		SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
		return f.generateSecret(spec).getEncoded();

	}

	/**
	 * Check if a password matches it's hash
	 * @param data Password
	 * @param hash Hash
	 * @return Matches?
	 */
	public boolean isValid(String data, String hash) {
		Matcher m = HASH_LAYOUT.matcher(hash);

		if (!m.matches()) {
			SimpleLogger.log("SimpleAuth", "Hash does not match scheme: " + ALGORITHM);
			return false;
		}

		int iterations = 1 << (Integer.parseInt(m.group(1)));
		byte[] h = Base64.getUrlDecoder().decode(m.group(2));
		byte[] salt = Arrays.copyOfRange(h, 0, HASH_SIZE / 8);

		byte[] check = null;
		try {
			check = pbkdf2(data.toCharArray(), salt, iterations);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			SimpleLogger.log("SimpleAuth", "Failed to hash a string");
			return false;
		}

		int zero = 0;
		for (int idx = 0; idx < check.length; ++idx)
			zero |= h[salt.length + idx] ^ check[idx];
		return zero == 0;
	}

}