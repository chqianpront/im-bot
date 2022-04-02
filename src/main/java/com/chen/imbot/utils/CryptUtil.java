package com.chen.imbot.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptUtil {
	public static String ENCREYPTED_METHOD = "AES";
	public static String CIPHER_METHOD = "AES/CBC/PKCS5Padding";
	public static String C_KEY = "jk2l;34_=-+dw3bf";
	public static String IV = "aabbccddeeffgghh";
	public static SecretKey KEY;
	public static int GEN_KEY_INT = 128;
	public static String encrypted(String input) {
		try {
			IvParameterSpec ivParameterSpec = new IvParameterSpec(CryptUtil.IV.getBytes());
			byte[] rawKey = CryptUtil.C_KEY.getBytes("utf-8");
			SecretKeySpec keySpec = new SecretKeySpec(rawKey, CryptUtil.ENCREYPTED_METHOD);
			Cipher cipher = Cipher.getInstance(CryptUtil.CIPHER_METHOD);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
			byte[] cipherText = cipher.doFinal(input.getBytes("utf-8"));
			byte[] base64 = org.apache.tomcat.util.codec.binary.Base64.encodeBase64(cipherText);
			return new String(base64);
 		} catch (Exception e) {
 			log.error("error: {}", e.getMessage());
 			return null;
		}
	}
	public static String decrypted(String cipherText) {
		try {
			byte[] rawKey = CryptUtil.C_KEY.getBytes("utf-8");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(CryptUtil.IV.getBytes());
			byte[] base64 = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(cipherText.getBytes());
			SecretKeySpec keySpec = new SecretKeySpec(rawKey, CryptUtil.ENCREYPTED_METHOD);
			Cipher cipher = Cipher.getInstance(CryptUtil.CIPHER_METHOD);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
			byte[] plainText = cipher.doFinal(base64);
			return new String(plainText);
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
			return null;
		}
	}
}
