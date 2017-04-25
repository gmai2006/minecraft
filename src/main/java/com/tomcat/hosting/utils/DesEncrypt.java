package com.tomcat.hosting.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class DesEncrypt implements Serializable {
	private transient Cipher ecipher;
	private SecretKey key;
	// Buffer used to transport the bytes from one stream to another
	private transient byte[] buf = new byte[1024];

	public DesEncrypt() {
		try {
			// Initializing and Serializing key object.
			key = KeyGenerator.getInstance("DES").generateKey();
			FileOutputStream out = new FileOutputStream("./key.object");
			ObjectOutputStream s = new ObjectOutputStream(out);
			s.writeObject(key);
			s.flush();
			s.close();
		} catch (NoSuchAlgorithmException exc) {
			System.out.println(exc.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		// Create an 8-byte initialization vector
		byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07,
				0x72, 0x6F, 0x5A };
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		try {
			ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// CBC requires an initialization vector
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		} catch (java.security.InvalidAlgorithmParameterException e) {
			System.out.println(e.getMessage());
		} catch (javax.crypto.NoSuchPaddingException e) {
			System.out.println(e.getMessage());
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		} catch (java.security.InvalidKeyException e) {
			System.out.println(e.getMessage());
		}
	}

	private void encrypt(InputStream in, OutputStream out) {
		try {
			// Bytes written to out will be encrypted
			out = new CipherOutputStream(out, ecipher);
			// Read in the cleartext bytes and write to out to encrypt
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			out.flush();
			out.close();
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void doEncryption(String source, String dest) {
		try {
			DesEncrypt encrypter = new DesEncrypt();
			// Do the Encryption here...
			encrypter.encrypt(new FileInputStream(source),
					new FileOutputStream("./" + dest));
			System.out
					.println("Encryption completed. Please see the BPMS config dir.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
