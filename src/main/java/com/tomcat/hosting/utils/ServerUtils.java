package com.tomcat.hosting.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ServerUtils {

	public static final String SERVER_NAME = "minecraftserver";
	static final String STARTUP_NAME = "mincraftstartupscript.sh";
	static final String CHECKUP_NAME = "checkuptime.sh";
//	static final String JAR_FILE_BUKKIT = "/home/minecraft/craftbukkit.jar";
//	static final String JAR_FILE_MINECRAFT = "/home/minecraft/minecraft_server.1.8.8.jar";
	static final DecimalFormat FORMAT = new DecimalFormat("##.00");
	public static void main(String[] args) throws Exception {

		// Map map = new HashMap();
		// map.put("userId", "paul");
		// map.put("serverName", "server8");
		// map.put("memory", "2048M");
		// CommandLine cmdLine = new CommandLine("python");
		// cmdLine.addArgument("/home/tomcat/apps/minecraft/src/main/shells/create-minecraft-server.py");
		//
		// cmdLine.addArgument("${userId}");
		// cmdLine.addArgument("${serverName}");
		// cmdLine.addArgument("${memory}");
		// cmdLine.setSubstitutionMap(map);
		//
		// System.out.println(cmdLine.toString());
		// DefaultExecutor executor = new DefaultExecutor();
		// executor.setExitValue(0);
		// ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		// executor.setWatchdog(watchdog);
		// int exitValue = executor.execute(cmdLine);

//		deleteMinecraftServer("server7");
//		buildNewMineCraftServer("/home/minecraft/", "craftbukkit", "2048M");
//		System.out.println("status:" + checkMinecraftServer("minecraftserver0"));
//		startMinecraftServer("minecraftserver0");
//		stopMinecraftServer("server7");
//		addUsertoWhiteList("curiosgeorge", "minecraftserver0");
//		String uuid = addUsertoWhiteList("curiousgeorge", "minecraftserver0");
//		addOperator("curiousgeorge", "minecraftserver0", uuid);
//		System.out.println(UUIDFetcher.getUUIDOf("curiousgeorge").toString());
//		System.out.println(displayCpuLoad());
//		System.out.println(displayOS());
//		System.out.println(displayUptimes());
//		System.out.println(displayMeminfo());
//		System.out.println(displayProcessUptime("minecraftserver1"));
//		System.out.println(displayProcessUptime("minecraftserver0"));
		
//		System.out.println(UUID.nameUUIDFromBytes("curiousgeorge".getBytes()).toString());
		
		Properties prop = ServerUtils.loadApplicationProperties();
		System.out.println(prop.getProperty("admin"));
		System.out.println(prop.getProperty("password"));
	}
	public static String getUserid() {
		try {
			return execToString("whoami").trim();
		}
		catch (Exception e) {
			return "";
		}
	}
	public static void buildNewMineCraftServer(String location, String serverType, String memory) 
	throws Exception {
		try {
			// display userId
			String userId = execToString("whoami").trim();

			File home = new File("/home");
			File userHome = new File(home, userId);
			File[] servers = userHome.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					return pathname.isDirectory() && pathname.getName().startsWith(SERVER_NAME);
				}
			});
			
			int max = servers.length;
			String serverName = SERVER_NAME + max;
			File target = new File(userHome, serverName);
			while (target.exists()) {
				max++;
				serverName = SERVER_NAME + max;
				target = new File(userHome, serverName);
			}
			target.mkdir();
						
			File source = new File(location, serverType);
			//copy all the files to the user dir
			StreamCopier.copyDir(source, target);
			
			//copy common script files like startup and chekuptime
			File startup = new File(location, STARTUP_NAME);
			File checkuptime = new File(location, CHECKUP_NAME);
			
			StreamCopier.copyNonRecursive(startup, target);
			StreamCopier.copyNonRecursive(checkuptime, target);
			// replace tokens with real values
			/**
			 * SERVICE="@@SERVICE@@" USERNAME="@@USERID@@" MCPATH="@@MCPATH@@"
			 * SESSIONID="@@SESSIONID@@" MEMORY="@@MEMORY@@"
			 */
			File jarFile = new File(source, serverType + ".jar");
			if (!jarFile.exists()) {
				throw new Exception("jar file NOT FOUND:" + jarFile.getAbsolutePath());
			}
			Map<String, String> replacement = new HashMap<String, String>();
			
			replacement.put("@@SERVICE@@", jarFile.getAbsolutePath());
			replacement.put("@@USERID@@", userId);
			replacement.put("@@MCPATH@@", target.getAbsolutePath());
			replacement.put("@@SESSIONID@@", serverName);
			replacement.put("@@MEMORY@@", memory);
			File startupscript = new File(target, STARTUP_NAME);
			File checkuptimescript = new File(target, CHECKUP_NAME);
			replace(startupscript, replacement);
			replace(checkuptimescript, replacement);
			
			execToString("chmod 755 " + startupscript.getAbsolutePath());
			execToString("chmod 755 " + checkuptimescript.getAbsolutePath());
			
			//update port number - get latest port from the minecraft server properties file
			int port = 25565;
			int rconport = 25575;
			if (servers.length > 0) {
				Arrays.sort(servers);
				File server = servers[servers.length-1];
				Properties prop = loadServerProperties(server.getName());
				int lastPort = Integer.valueOf(prop.getProperty("server-port"));
				int lastCronPort = Integer.valueOf(prop.getProperty("rcon.port"));
				port = lastPort + 1;
				rconport = lastCronPort + 1;
				File newprop = new File(target, "server.properties");
				replacement = new HashMap<String, String>();
				replacement.put("25565", String.valueOf(port));
				replacement.put("25575", String.valueOf(rconport));
				replace(newprop, replacement);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	public static File[] getMaps(String serverId) {
		File home = new File("/home/", getUserid());
		File server = new File (home, serverId);
		File[] maps = server.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.isDirectory() 
						&& !pathname.getName().equals("logs")
						&& !pathname.getName().equals("plugins")
						&& !pathname.getName().endsWith("nether")
						&& !pathname.getName().endsWith("end");
			}
		});
		return maps;
	}
	
	public static File[] getPlugins(String serverId) {
		File home = new File("/home/", getUserid());
		File server = new File (home, serverId);
		File pluginsDir = new File(server, "plugins");
		File[] maps = pluginsDir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.isDirectory();
			}
		});
		return maps;
	}
	
	public static File getServerDir(String serverId) {
		File home = new File("/home/", getUserid());
		File server = new File (home, serverId);
		return server;
	}
	
	public static File getServerDir(String serverId, String type) {
		File home = new File("/home/", getUserid());
		File server = new File (home, serverId);
		File dir = new File(server, type);
		return dir;
	}
	public static void deleteMinecraftServer(String servername) {
		try {
			// display userId
			String userId = execToString("whoami").trim();
//			System.out.println("UserId:" + userId);
			// copy configuration files
			File home = new File("/home");
			File userHome = new File(home, userId);
			File target = new File(userHome, servername);
//			System.out.println(target.getAbsolutePath());
			System.out.println(execToString("rm -R " + target));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String checkMinecraftServer(String servername) {
		return handleMinecraftServer(servername, "status");
	}

	public static String stopMinecraftServer(String servername) {
		return handleMinecraftServer(servername, "stop");
	}

	public static String startMinecraftServer(String servername) {
		return handleMinecraftServer(servername, "start");
	}

	/*
	 * `grep 'cpu ' /proc/stat | awk '{usage=($2+$4)*100/($2+$4+$5)} END {print usage "%"}'`
	 */
	public static String displayCpuLoad()  throws Exception {
		String result = execToString("grep 'cpu ' /proc/stat");
		String[] lines = result.split("\n");
		String[] tokens = lines[0].split(" ");
		//skip first empty string tokkens[1]
		Integer userProc = Integer.valueOf(tokens[2]);
		Integer sysProc = Integer.valueOf(tokens[4]);
		Integer sysIdle = Integer.valueOf(tokens[5]);
		
		Double cpuLoad = ((double)(userProc + sysProc) * 100) / (userProc + sysProc + sysIdle);
		return FORMAT.format(cpuLoad) + "%";
	}
	
	public static String displayUptimes() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(execToString("uptime -p").trim());
		return builder.toString();
	}
	public static String displayProcessUptime(String serverId) throws Exception {
		String status = checkMinecraftServer(serverId);
		if (!status.equals("0")) return "The server is NOT running";
		String userId = execToString("whoami").trim();
		File home = new File("/home");
		File userHome = new File(home, userId);
		File server = new File(userHome, serverId);
		File target = new File(server, CHECKUP_NAME);
		String result = execToString(target.getAbsolutePath());
		String[] tokens = result.split(" ");
		return tokens[0];
	}
	
	public static String displayOS() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(execToString("uname -os").trim());
		builder.append(" ");
		builder.append(execToString("lsb_release -d").trim());
		return builder.toString();
	}
	
	public static String displayMeminfo() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(execToString("free -mht").trim());
		return builder.toString();
	}
	
	public static void addOperator(String userId, String serverId, String uuid) {
		try {
			JsonObject user = new JsonObject();
			user.addProperty("uuid", uuid.toString());
			user.addProperty("name", userId);
			user.addProperty("level", 4);
			JsonArray ops = loadJsonFiletoArray(serverId, "ops.json");
			ops.add(user);
			
			Gson gson = 
					new GsonBuilder().setPrettyPrinting().serializeNulls()
					.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
			String outout = gson.toJson(ops);
			System.out.println(outout);
			saveJsonFileFromArray(ops, serverId, "ops.json");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addOperator(String userId, String serverId) {
		try {
			String uuid = null;
			JsonArray array = loadJsonFiletoArray(serverId, "whitelist.json");
			for (JsonElement e : array) {
				JsonObject o = (JsonObject)e;
				JsonElement item = o.get("name");
				String name = item.getAsString();
				System.out.println(name + ":" + userId + ":");
				if (userId.equals(name)) {
					item = o.get("uuid");
					uuid = item.getAsString();
					System.out.println(name + ":" + uuid);
					break;
				}
			}
			
			if (null == uuid) return;
			
			addOperator(userId, serverId, uuid);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getUUIDfromWhiteList(String userId, String serverId) {
		try {
			JsonArray array = ServerUtils.loadJsonFiletoArray(serverId, "whitelist.json");
			for (JsonElement e : array)  {
				JsonObject o = (JsonObject)e;
				String name = o.get("name").getAsString();
				if (userId.equals(name)) { 
					return o.get("uuid").getAsString();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
	public static String addUsertoWhiteList(String userId, String serverId) {
		String uuidStr = null;
		try {
			boolean existed = false;
			JsonArray array = ServerUtils.loadJsonFiletoArray(serverId, "whitelist.json");
			for (JsonElement e : array)  {
				JsonObject o = (JsonObject)e;
				String name = o.get("name").getAsString();
				if (userId.equals(name)) { 
					existed = true;
					return o.get("uuid").getAsString();
				}
			}
			if (!existed) {
				UUID uuid = UUIDFetcher.getUUIDOf(userId);
				JsonObject user = new JsonObject();
				user.addProperty("uuid", uuid.toString());
				user.addProperty("name", userId);
				
				array.add(user);
				System.out.println(array.toString());
				
				saveJsonFileFromArray(array, serverId, "whitelist.json");
				uuidStr = uuid.toString();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return uuidStr;
	}
	public static JsonArray loadJsonFiletoArray(String serverId, String fileName) throws Exception {
		
		Gson gson = new Gson();
		BufferedReader br = null;
		try {
			String userId = execToString("whoami").trim();
			File home = new File("/home");
			File userHome = new File(home, userId);
			File server = new File(userHome, serverId);
			File file = new File(server, fileName);
			br = new BufferedReader(
					new java.io.FileReader(file));
			JsonArray obj = gson.fromJson(br, JsonArray.class);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static void saveJsonFileFromArray(JsonArray array, String serverId, String fileName) throws Exception {
		Gson gson = 
				new GsonBuilder().setPrettyPrinting().serializeNulls()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		BufferedWriter writer = null;
		try {
			String userId = execToString("whoami").trim();
			File home = new File("/home");
			File userHome = new File(home, userId);
			File server = new File(userHome, serverId);
			File file = new File(server, fileName);
			writer = new BufferedWriter(
					new FileWriter(file));
			
			writer.write(gson.toJson(array));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != writer) writer.flush(); writer.close();
			} catch (Exception e) {
				
			}
		}
	}
	
	public static Properties loadApplicationProperties() {
		BufferedInputStream bis = null;
		Properties prop = new Properties();
		try {
			bis = 
				new BufferedInputStream(
						ServerUtils.class.getResourceAsStream("/application.properties"));
		
			prop.load(bis);
		} catch (Exception e) {
			
		} finally {
			try {
				if (null != bis) bis.close();
			} catch (Exception e) {}
		}
		return prop;
	}
	
	public static Properties loadServerProperties(String serverId) {
		String userId = ServerUtils.getUserid();
		File home = new File("/home");
		File userHome = new File(home, userId);
		File serverHome = new File(userHome, serverId);
		BufferedInputStream bis = null;
		Properties prop = new Properties();
		try {
			bis = 
				new BufferedInputStream(
						new FileInputStream(
								new File(serverHome, "server.properties")));
		
			prop.load(bis);
		} catch (Exception e) {
			
		} finally {
			try {
				if (null != bis) bis.close();
			} catch (Exception e) {}
		}
		return prop;
	}
	
	public static void saveServerProperties(String serverId, Properties prop) {
		String userId = ServerUtils.getUserid();
		File home = new File("/home");
		File userHome = new File(home, userId);
		File serverHome = new File(userHome, serverId);
		BufferedOutputStream bos = null;
		try {
			bos = 
				new BufferedOutputStream(
						new FileOutputStream(
								new File(serverHome, "server.properties")));
		
			prop.store(bos, "");
		} catch (Exception e) {
			
		} finally {
			try {
				if (null != bos) bos.close();
			} catch (Exception e) {}
		}
	}
	
	private static String handleMinecraftServer(String servername, String action) {
		try {
			// display userId
			String userId = execToString("whoami").trim();
//			System.out.println("UserId:" + userId);
			// copy configuration files
			File home = new File("/home");
			File userHome = new File(home, userId);
			File server = new File(userHome, servername);
			File target = new File(server, "mincraftstartupscript.sh");
			return execToString(target.getAbsolutePath() + " " + action);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Fail to run minecraft action";
		}

	}

	private static void replace(File file, final Map<String, String> replacement) {
		try {
			Path path = Paths.get(file.getAbsolutePath());
			Charset charset = StandardCharsets.UTF_8;

			String content = new String(Files.readAllBytes(path), charset);
			// System.out.println("BEFORE:" + content);
			Set<String> keys = replacement.keySet();
			for (String k : keys) {
				content = content.replaceAll(k, replacement.get(k));
			}
			// System.out.println(content);
			Files.write(path, content.getBytes(charset));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String execToString(String command) throws Exception {
//		System.out.println(command);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		CommandLine commandline = CommandLine.parse(command);
		DefaultExecutor exec = new DefaultExecutor();
		PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
		exec.setStreamHandler(streamHandler);
		exec.execute(commandline);
		return (outputStream.toString().trim());
	}

}
