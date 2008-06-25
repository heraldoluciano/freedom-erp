package org.freedom.infra.util.crypt;

import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class CryptUtil {

	// public static final String DEFAULT_CHARSET = "ISO-8859-1";
	public static final String DEFAULT_CHARSET = "UTF-16BE";
	
	public static byte[] generateKey() {
		return generateKey(null);
	}
	public static byte[] generateKey(String key) {
		
		byte[] encKey = new byte[] {107, 101, 121, 64, 101, 110, 99, 114, 121, 112, 116, 105, 111, 110};
		if (key!=null) encKey = key.getBytes();
		
		return encKey;
	}
	
	public static String encrypt(String source, byte[] encKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException {
		
		SecretKeySpec skeySpec = new SecretKeySpec(encKey, "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		
		byte[] sourceBytes = source.getBytes(DEFAULT_CHARSET);
		byte[] encryptedSource = cipher.doFinal(sourceBytes);
		
		return new String(encryptedSource, DEFAULT_CHARSET);		
	}

	public static String decrypt(String encryptedSource, byte[] encKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException {
		
		SecretKeySpec skeySpec = new SecretKeySpec(encKey, "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		byte[] encryptedSourceBytes = encryptedSource.getBytes(DEFAULT_CHARSET);
		byte[] source = cipher.doFinal(encryptedSourceBytes);
		
		return new String(source, DEFAULT_CHARSET);	
	}
	
	public static void encrypt(String source, String destination, byte[] encKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException {
		
		SecretKeySpec skeySpec = new SecretKeySpec(encKey, "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		
		File inputFile = new File(source);
		File outputFile = new File(destination);
		
		if(inputFile.isFile()) { 
			if(outputFile.exists()) outputFile.delete();
	
			FileInputStream in = new FileInputStream(inputFile);
			FileOutputStream out = new FileOutputStream(outputFile);
			CipherOutputStream cout = new CipherOutputStream(out, cipher);
		
			int length = 0;
			byte[] buffer = new byte[8];
		
			while ((length = in.read(buffer)) != -1) {
				cout.write(buffer, 0, length);
			}
		
			cout.flush();
			cout.close();
			out.close();
			in.close();
		}
	}

	public static void decrypt(String source, String destination, byte[] encKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException {
		
		SecretKeySpec skeySpec = new SecretKeySpec(encKey, "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		
		File inputFile = new File(source);
		File outputFile = new File(destination);
		
		if(inputFile.isFile()) { 
			if(outputFile.exists()) outputFile.delete();
	
			FileInputStream in = new FileInputStream(inputFile);
			FileOutputStream out = new FileOutputStream(outputFile);
			CipherInputStream cin = new CipherInputStream(in, cipher);
		
			int length = 0;
			byte[] buffer = new byte[8];
			while ((length = cin.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			out.flush();
			out.close();
			cin.close();
			in.close();
		}
	}
}