package com.chen.imbot.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptUtil {
	public static String encryptedMethod = "AES";
	public static IvParameterSpec iv = new IvParameterSpec(new byte[16]);
	public static SecretKey key;
	public static int GEN_KEY_INT = 0;
	static {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptedMethod);
			keyGenerator.init(GEN_KEY_INT);
			key = keyGenerator.generateKey();
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
			key = null;
		}
		
	}
	public static String encrypted(String input) {
		try {
			Cipher cipher = Cipher.getInstance(encryptedMethod);
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			byte[] cipherText = cipher.doFinal(input.getBytes());
			return Base64.getEncoder().encodeToString(cipherText).toString();
 		} catch (Exception e) {
 			log.error("error: {}", e.getMessage());
 			return null;
		}
	}
	public static String decrypted(String cipherText) {
		try {
			Cipher cipher = Cipher.getInstance(encryptedMethod);
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
			return new String(plainText);
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
			return null;
		}
	}
}
