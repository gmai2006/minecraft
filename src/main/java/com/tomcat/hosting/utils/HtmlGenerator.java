package com.tomcat.hosting.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
public class HtmlGenerator {

	public static void main(String[] args) {
		File folder = new File("./WEB-INF/template/");
		String url = "http://127.0.0.1:9080/hosting3";
		File outputdir = new File("./");
		String extension = ".xhtml";
		
		if (args.length > 3) {
			folder = new File(args[0]);
			url = args[1];
			outputdir = new File(args[2]);
			extension = args[3];
		}
		
		File[] files = folder.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.isFile() && -1 == pathname.getName().indexOf("layout");
			}
		});
		
		for (int i = 0; i < files.length; i++) {
			System.out.println("generate html for " + files[i].getName());
			try {
			generateHTMLPage(files[i].getName(), extension, outputdir, url);
			} catch (Exception e) {
				System.err.println("unable to generate html for " + files[i].getName());
			}
		}
	}
	
	public static void generateHTMLPage(String name, String extension,
			File outputdir, String url) throws Exception
	{	 
		String actualname = name.substring(0, name.lastIndexOf("."));
		File f = new File(outputdir, actualname + ".html");
				
		FileDownloader download = new FileDownloader();
		download.downloadFile(url + "/" + actualname +  extension, f.getAbsolutePath());
			System.out.println("start compresing:" + f.getName());

			
			StringWriter writer = new StringWriter();
			BufferedReader reader = new BufferedReader(new FileReader(f));
			StreamCopier.copy(reader, writer);
			
			HtmlCompressor compressor = new HtmlCompressor();
//			compressor.setRemoveComments(true);  
			String temp = writer.toString();
			temp = temp.replaceAll(".xhtml", ".html");
			String compressedHtml = compressor.compress(temp);

			StringReader anotherreader = new StringReader(compressedHtml);
			BufferedWriter anowriter = new BufferedWriter(new FileWriter(f));
			StreamCopier.copy(anotherreader, anowriter);
			anowriter.flush();
			anowriter.close();
	}

}
