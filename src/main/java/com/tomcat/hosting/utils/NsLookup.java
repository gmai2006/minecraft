package com.tomcat.hosting.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NsLookup {

	public static final String ValidIpAddressRegex = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
  public static final String DOMAIN_REGEX = "^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$";
  static Pattern pattern = Pattern.compile(DOMAIN_REGEX);
  public static String performNSLookup(String name) {
      
      try {
    	  Matcher matcher = pattern.matcher(name.subSequence(0, name.length()));
    	  if (matcher.matches())
    	  {
	          InetAddress inetHost = InetAddress.getByName(name);
	          return inetHost.getHostAddress();
    	  }
    	  else 
		  {
    		  System.out.println("not matched " + name);
		  	return "Unrecognized host";
		  }
          
      } catch(UnknownHostException ex) {
          
    	  System.out.println(ex.getMessage());
          return "Unrecognized host";
      }
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      System.out.println(performNSLookup("tomcathostingservice.com"));
  }
  
} // end javalookup

