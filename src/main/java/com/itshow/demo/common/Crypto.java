package com.itshow.demo.common;

import lombok.Data;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Data
public class Crypto {
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "inab@itshow.secretkey".getBytes();

    public static String encrypt(String valueToEnc, String salt) throws Exception {
        String saltedVal = valueToEnc + salt;
        Key key = generateKey(salt);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(saltedVal.getBytes());
        return Base64.getEncoder().encodeToString(encValue);
    }

    public static String decrypt(String encryptedValue, String salt) throws Exception {
        Key key = generateKey(salt);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedValue);
        byte[] decValue = c.doFinal(decordedValue);
        String decStr = new String(decValue);
        return decStr.substring(0, decStr.length() - salt.length());
    }

    private static Key generateKey(String salt) throws Exception {
        Key key = new SecretKeySpec(hashPassword(salt.toCharArray(), keyValue, 1000, 256), ALGORITHM);
        return key;
    }

    private static byte[] hashPassword(char[] password, byte[] salt, int iterations, int keyLength) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
