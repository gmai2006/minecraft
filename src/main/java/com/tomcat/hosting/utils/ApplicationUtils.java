package com.tomcat.hosting.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Properties;


public class ApplicationUtils {
	public static final SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");
	public static final SimpleDateFormat anotherformater = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy");
	public static final SimpleDateFormat timeformater = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	public static final SimpleDateFormat displayformater = new SimpleDateFormat("MMM dd, yyyy");
	public static final DecimalFormat PRICE = new DecimalFormat("$0.00");
	static final String SALT = "*%&$^#($)%^|FFTRE$#$%++_";
	
	public static void main(String[] s) throws Exception {
		System.out.println(encrypt("test123$$"));
	}
	public static String displayDate(java.util.Date date)
    {
        if(null == date)
            return "";
        else
            return displayformater.format(date);
    }
	
	public static String displayDate(long value)
	{
		return displayFormat.format(value);
	}
	public static String getDate(long value)
	{
		return formater.format(value);
	}
	public static boolean isStringNull(String s)
	{
		return (null == s || 0 == s.length() || "null".equalsIgnoreCase(s));
	}
	
	public static Hashtable<String, String> loadProperties(String name) throws Exception
	{
		Properties prop = new Properties();
		Hashtable<String, String> table = new Hashtable<String, String>();
		try {
			InputStream in = ApplicationUtils.class.getResourceAsStream("/" + name);
			prop.load(in);
			for (Object o : prop.keySet())
			{
				String key = (String)o;
				String value = prop.getProperty(key);
				table.put(key, value);
			}
		}
		
		catch (Exception e)
		{
			throw new Exception(e);
		}
		return table;
	}
	
	public static String encrypt(String x)   throws Exception
	  {
	     java.security.MessageDigest d =null;
	     d = java.security.MessageDigest.getInstance("SHA-1");
	     d.reset();
	     d.update(x.getBytes());
	     byte[] bytes =  d.digest();
	     return byteArrayToHexString(bytes);
	  }

	public static String byteArrayToHexString(byte[] b){
	    StringBuffer sb = new StringBuffer(b.length * 2);
	    for (int i = 0; i < b.length; i++){
	      int v = b[i] & 0xff;
	      if (v < 16) {
	        sb.append('0');
	      }
	      sb.append(Integer.toHexString(v));
	    }
	    return sb.toString().toUpperCase();
	}

	public static byte[] hexStringToByteArray(String s) {
	    byte[] b = new byte[s.length() / 2];
	    for (int i = 0; i < b.length; i++){
	      int index = i * 2;
	      int v = Integer.parseInt(s.substring(index, index + 2), 16);
	      b[i] = (byte)v;
	    }
	    return b;
	  }

	
	public static String encode(String value)
	{
		StringBuilder encode = new StringBuilder(value + ":" + SALT);

		if (encode.length()%4 != 0)
		{
			int remainder = encode.length()%4;
			for (int i = 0; i < 4-remainder; i++)
			{
				encode.append("^");
			}
		}
		
		return Base64Coder.encodeString(encode.toString());
	}
	
	public static String decode(String value)
	{
		String temp = Base64Coder.decodeString(value);
		String[] tokens = temp.split(":");
		return tokens[0];
	}
	
	public static String getUserIdWithoutEmail(String email)
	{
		if (email.indexOf("@") == -1) return email;
		return email.substring(0, email.indexOf("@"));
	}
	
	public static String getNameWithoutExtension(String value)
	{
		return value.substring(0, value.lastIndexOf("."));
	}
	public static String truncate(String value, int limit)
	{
		if (value.length() >= limit)
		{
			return value.substring(0, limit-4) + "...";
		}
		else
		{
			return value;
		}
	}
	
	public static String truncateWithoutDot(String value, int limit)
	{
		if (value.length() >= limit)
		{
			return value.substring(0, limit);
		}
		else
		{
			return value;
		}
	}
	
	
	public static String getSystemInfo()
	{
		String os = System.getProperty("os.name");
		String temp = os.toLowerCase();
		if (temp.indexOf("windows") == -1)
		{
			return os + " CPU:" + getCPUInfo() + " Memory:" + getMemInfo();
		}
		return os + ": No hardware information available";
	}
	
	public static String getCPUInfo()
	{
		return getDeviceInfo("/proc/cpuinfo", "model name", "CPU Info Not Available");
	}
	
	public static String getMemInfo()
	{
		return getDeviceInfo("/proc/meminfo", "MemTotal", "Memory Info Not Available");
	}
	
	
	private static String getDeviceInfo(String devicename, String key, String errormsg)
	{
		try
        {            
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(new String[] {"/bin/cat", devicename});// | /bin/grep 'model name'"});
            InputStream stderr = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ( (line = br.readLine()) != null) 
        	{
        		if (line.startsWith(key)) 
        		{
        			String[] tokens = line.split(":");
        			if (tokens.length > 1)
        			{
        				return tokens[1].trim();
        			}
        			else return errormsg;
        		}
        				
        	}
           
            int exitVal = proc.waitFor();
        } catch (Throwable t)
          {
            t.printStackTrace();
          }
        return errormsg;
	}
	
	public static String displayPrice(float value)
    {
        return PRICE.format(value);
    }
}
