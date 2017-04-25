package com.tomcat.hosting.utils;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.FileChannel;

public class StreamCopier {

  public static void main(String[] args) {

    try {
      copy(System.in, System.out);
    }
    catch (IOException e) {
      System.err.println(e);
    }

  }

  public static void copy(InputStream in, OutputStream out)
   throws IOException {

    // do not allow other threads to read from the
    // input or write to the output while copying is
    // taking place

    synchronized (in) {
      synchronized (out) {

        byte[] buffer = new byte[4*1024];
        while (true) {
          int bytesRead = in.read(buffer);
          if (bytesRead == -1) break;
          out.write(buffer, 0, bytesRead);
        }
      }
    }
  }
  
  public static void copy(Reader reader, Writer writer)
  throws IOException {

   // do not allow other threads to read from the
   // input or write to the output while copying is
   // taking place

   synchronized (reader) {
     synchronized (writer) {

       byte[] buffer = new byte[4*1024];
       while (true) {
         int value = reader.read();
         if (-1 == value) break;
         writer.write(value);
       }
     }
   }
 }
  
  public static void copyDir(File source, File target) throws IOException
  { 
	  if (source.isDirectory())
		{
			if(target.exists()!=true)
			{
				boolean created = target.mkdir();
			}
			String list[] = source.list();

			for (int i = 0; i < list.length; i++)
			{
				File dest1 = new File(target, list[i]);
				File src1 = new File(source, list[i]);
				copyDir(src1 , dest1);
			}
		}
		else
		{
		     FileChannel sourceChannel = new FileInputStream(source).getChannel();
		     FileChannel targetChannel = new FileOutputStream(target).getChannel();
		     sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
		     sourceChannel.close();
		     targetChannel.close();
		}
  }

  public static void copyNonRecursive(File source, File target) throws IOException
  { 
	  if (source.isDirectory())
		{
			if(target.exists()!=true)
			{
				target.mkdirs();
			}
			File[] files = source.listFiles(new FileFilter()
			{
				public boolean accept(File f)
				{
					return (f.isFile());
				}
			});

			for (int i = 0; i < files.length; i++)
			{
				File dest1 = new File(target, files[i].getName());
				File src1 = new File(source, files[i].getName());
				FileChannel sourceChannel = new FileInputStream(src1).getChannel();
			     FileChannel targetChannel = new FileOutputStream(dest1).getChannel();
			     sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
			     sourceChannel.close();
			     targetChannel.close();
			}
		}
		else
		{
			File dest1 = target;
			if (target.isDirectory())
			{
				dest1 = new File(target, source.getName());
			}
		     FileChannel sourceChannel = new FileInputStream(source).getChannel();
		     FileChannel targetChannel = new FileOutputStream(dest1).getChannel();
		     sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
		     sourceChannel.close();
		     targetChannel.close();
		}
  }

}
