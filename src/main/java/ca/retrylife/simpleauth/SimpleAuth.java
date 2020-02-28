package ca.retrylife.simpleauth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import ca.retrylife.simplelogger.SimpleLogger;
import ca.retrylife.util.MathUtils;

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

	public boolean isValid(String data, String hash) {
		return false;
	}

}