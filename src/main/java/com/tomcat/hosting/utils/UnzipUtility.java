package com.tomcat.hosting.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnzipUtility {
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipFile zipFile = new ZipFile(zipFilePath);
        Enumeration en = zipFile.entries(); 
        ZipEntry entry;
        while (en.hasMoreElements()) {
        	entry = (ZipEntry)en.nextElement();
        	if (entry.isDirectory()) {
        		String filePath = entry.getName();
                filePath = filePath.replaceAll("'", "").replaceAll(" ", "");
                //fix the zip file bug
                //does not read the root dir
                int index = filePath.indexOf("/");
                File parent = destDir;
                while (index > 0) {
                	File dir = new File(parent, filePath.substring(0, index));
                	if (!dir.exists()) dir.mkdir();
//                	System.out.println("mkdir:" + dir.getAbsolutePath());
                	parent = dir;
                	filePath = filePath.substring(index + 1);
                	index = filePath.indexOf("/");
                }
        		File dir = new File(parent, filePath);
        		if (!dir.exists()) dir.mkdir();
        		
        	} else {
        		String filePath = entry.getName().replaceAll("'", "").replaceAll(" ", "");
        		File file = new File(destDirectory, filePath);
                
        		BufferedInputStream input =
        				new BufferedInputStream(zipFile.getInputStream(entry));
        		extractFile(input, file.getAbsolutePath());
        	}
        }
        
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(InputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}