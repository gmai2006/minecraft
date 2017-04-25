package com.tomcat.hosting.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UUIDFetcher implements Callable<Map<String, UUID>> {
	private static final double PROFILES_PER_REQUEST = 100;
	private static final String PROFILE_URL = "https://api.mojang.com/users/profiles/minecraft/";
	private final JSONParser jsonParser = new JSONParser();
	private final List<String> names = new ArrayList<String>();
	private final boolean rateLimiting;

	public UUIDFetcher(List<String> names, boolean rateLimiting) {
		this.names.addAll(names);
		this.rateLimiting = rateLimiting;
	}

	public UUIDFetcher(List<String> names) {
		this(names, true);
	}

	public Map<String, UUID> call() throws Exception {
		Map<String, UUID> uuidMap = new HashMap<String, UUID>();
		int requests = (int) Math.ceil(names.size() / PROFILES_PER_REQUEST);
		for (int i = 0; i < requests; i++) {
			HttpURLConnection connection = createConnection();
			String body = JSONArray.toJSONString(names.subList(i * 100,
					Math.min((i + 1) * 100, names.size())));
			writeBody(connection, body);
			JSONArray array = (JSONArray) jsonParser
					.parse(new InputStreamReader(connection.getInputStream()));
			for (Object profile : array) {
				JSONObject jsonProfile = (JSONObject) profile;
				String id = (String) jsonProfile.get("id");
				String name = (String) jsonProfile.get("name");
				UUID uuid = UUIDFetcher.getUUID(id);
				uuidMap.put(name, uuid);
			}
			if (rateLimiting && i != requests - 1) {
				Thread.sleep(100L);
			}
		}
		return uuidMap;
	}

	private static void writeBody(HttpURLConnection connection, String body)
			throws Exception {
		OutputStream stream = connection.getOutputStream();
		stream.write(body.getBytes());
		stream.flush();
		stream.close();
	}

	private static HttpURLConnection createConnection() throws Exception {
		URL url = new URL(PROFILE_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}

	private static UUID getUUID(String id) {
		return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12)
				+ "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-"
				+ id.substring(20, 32));
	}

	public static byte[] toBytes(UUID uuid) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return byteBuffer.array();
	}

	public static UUID fromBytes(byte[] array) {
		if (array.length != 16) {
			throw new IllegalArgumentException("Illegal byte array length: "
					+ array.length);
		}
		ByteBuffer byteBuffer = ByteBuffer.wrap(array);
		long mostSignificant = byteBuffer.getLong();
		long leastSignificant = byteBuffer.getLong();
		return new UUID(mostSignificant, leastSignificant);
	}

	public static UUID getUUIDOf(String name) throws Exception {
//		return new UUIDFetcher(Arrays.asList(name)).call().get(name);
		String result = doHttpUrlConnectionAction(PROFILE_URL + name);
		System.out.println(result);
		JSONParser jsonParser = new JSONParser();
		JSONObject o = (JSONObject) jsonParser.parse(result);
		String id = (String) o.get("id");
		UUID uuid = UUIDFetcher.getUUID(id);
		return uuid;
	}
	
	/**
	   * Returns the output from the given URL.
	   * 
	   * I tried to hide some of the ugliness of the exception-handling
	   * in this method, and just return a high level Exception from here.
	   * Modify this behavior as desired.
	   * 
	   * @param desiredUrl
	   * @return
	   * @throws Exception
	   */
	  private static String doHttpUrlConnectionAction(String desiredUrl)
	  throws Exception
	  {
	    URL url = null;
	    BufferedReader reader = null;
	    StringBuilder stringBuilder;
	 
	    try
	    {
	      // create the HttpURLConnection
	      url = new URL(desiredUrl);
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	       
	      // just want to do an HTTP GET here
	      connection.setRequestMethod("GET");
	       
	      // uncomment this if you want to write output to this url
	      //connection.setDoOutput(true);
	       
	      // give it 15 seconds to respond
	      connection.setReadTimeout(15*1000);
	      connection.connect();
	 
	      // read the output from the server
	      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      stringBuilder = new StringBuilder();
	 
	      String line = null;
	      while ((line = reader.readLine()) != null)
	      {
	        stringBuilder.append(line + "\n");
	      }
	      return stringBuilder.toString();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      throw e;
	    }
	    finally
	    {
	      // close the reader; this can throw an exception too, so
	      // wrap it in another try/catch block.
	      if (reader != null)
	      {
	        try
	        {
	          reader.close();
	        }
	        catch (IOException ioe)
	        {
	          ioe.printStackTrace();
	        }
	      }
	    }
	  }
}
