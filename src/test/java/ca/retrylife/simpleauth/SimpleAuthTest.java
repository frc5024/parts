package ca.retrylife.simpleauth;

import java.util.regex.Pattern;

import org.junit.Test;

public class SimpleAuthTest {

    private SimpleAuth auth = new SimpleAuth();
    private Pattern pattern = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");

    @Test
    public void testHashGen() {

        // Hash a password
        String hash = auth.hash("ThisIsMyT0T4LLYSecureP@55W0RD");

        // Ensure hash is nonnull
        assert hash != null;

        // Check hash
        System.out.println("Generated Hash: " + hash);
        assert !hash.equals("");

        // Check hash against generated hash
        assert pattern.matcher(hash).matches();
    }

    @Test
    public void testHashMatch() {

        // Ensure the password is valid
        assert auth.isValid("ThisIsMyT0T4LLYSecureP@55W0RD", "$31$16$GBnDfR8OF_4EV-JTXPdXxv3JvULMwgAIcs1Fsx2Erzo");
    }

    @Test
    public void testHashMatchIncorrect() {

        // Ensure the password is not valid
        assert !auth.isValid("ThisIsNotMyPasswordAtAll", "$31$16$GBnDfR8OF_4EV-JTXPdXxv3JvULMwgAIcs1Fsx2Erzo");
    }

    @Test
    public void testHashMatchNoHash() {

        // Ensure blank hash is functional
        assert !auth.isValid("ThisIsMyT0T4LLYSecureP@55W0RD", "");
    }

    @Test
    public void testHashMatchNoPass() {

        // Ensure blank password is functional
        assert !auth.isValid("", "$31$16$GBnDfR8OF_4EV-JTXPdXxv3JvULMwgAIcs1Fsx2Erzo");
    }

}