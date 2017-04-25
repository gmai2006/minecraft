package com.tomcat.hosting.utils;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class StringEncrypterTest
{

	@Test
	public void testEncryptsUsingDes() throws Exception
	{
		String stringToEncrypt = "test123$$";
		String encryptionScheme = StringEncrypter.DES_ENCRYPTION_SCHEME;

		StringEncrypter encrypter = new StringEncrypter( encryptionScheme );
		String encryptedString = encrypter.encrypt( stringToEncrypt );
		
		System.out.println(encryptedString);
		System.out.println(encrypter.decrypt(encryptedString));	

	}

//	@Test
	public void testDecryptsUsingDes() throws Exception
	{
		String string = "hosting";
		String encryptionScheme = StringEncrypter.DES_ENCRYPTION_SCHEME;

		StringEncrypter encrypter = new StringEncrypter( encryptionScheme, StringEncrypter.DEFAULT_ENCRYPTION_KEY );
		String decryptedString = encrypter.encrypt( string );

System.out.println("decrypted string:" + decryptedString);
//		Assert.assertEquals( "test", decryptedString );
	}
}