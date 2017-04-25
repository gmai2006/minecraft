package com.tomcat.hosting;

import com.tomcat.hosting.utils.UnzipUtility;

public class TestMics {

	public static void main(String[] args) {
//		System.out.println("minecraftserver0".matches(BaseServlet.REGEX));
		try {
			testZipUtility();
		} catch (Exception e) { e.printStackTrace(); };
	}
	
	public static void testZipUtility() throws Exception {
		UnzipUtility engine = new UnzipUtility();
		engine.unzip("/home/paul/minecraftserver1/Satoshis_Treasure-Episode_3.zip", 
				"/home/paul/minecraftserver1/");
	}

}
