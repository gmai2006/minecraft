package com.tomcat.hosting.guice;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import com.google.rconclient.rcon.AuthenticationException;
import com.google.rconclient.rcon.GameMode;
import com.google.rconclient.rcon.RCon;
import com.tomcat.hosting.dao.MinecraftServerInfo;
import com.tomcat.hosting.utils.ServerUtils;

public class RConServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private RCon connection;
	private String[] response;
	
	@Override
	protected String getHtmlTitle(String pageName)
	{
		return "Tomcat Hosting Service";
	}
	@Override
	protected String gethtmlDescr(String pageName)
	{
		return "Tomcat Hosting Service";
	}
	
	@Override
	public ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception {
		String serverId = req.getParameter("id");
		ST st = getTemplate(getContainer(), pageName);
		ST menu = getTemplate(getContainer(), "menu-manageserver");
		st.add("menu", menu);
		String command = req.getParameter("command");
		if (StringUtils.isNotEmpty(command)) {
			try {
				String status = ServerUtils.checkMinecraftServer(serverId);
				if (!"0".equals(status)) {
					setMessage("Server is NOT running.  Please start the server first");
					setCssId("alert-warning");
				} else {
					connection = getConnection(serverId);
					int result = execute(command.trim());
					if (0 == result) {
						setMessage("Successfully execute command: " + command.trim());
						setCssId("alert-success");
						
						if (null != response) st.add("response", response);
					} else {
						setMessage("Invalid command please try again:" + command.trim());
						setCssId("alert-warning");
					}
				}
			} catch (Exception e) {
				setMessage("Unable to execute command : " + e.getMessage());
				setCssId("alert-warning");
				catchException(e, this.getClass().getName());
			} finally {
				if (null != connection) {
					connection.close();
					connection = null;
				}
			}
				
			st.add("action", true);
			st.add("message", getMessage());
			st.add("cssId", getCssId());
			if (null != response) st.add("response", response);
		}
		MinecraftServerInfo server = new MinecraftServerInfo();
		server.setId(serverId);
		st.add("server", server);
		st.add("rcon", "active");
		return st;
	}
	private int execute(String command) throws Exception {
		int result = -1;
		if (command.startsWith("tell")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 3) {
				connection.tell(tokens[1], tokens[2]);
				return 0;
			}
		} else if (command.startsWith("kick")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 2) {
				connection.kick(tokens[1]);
				return 0;
			}
		} else if (command.startsWith("ban ")) { //but not banlist
			String[] tokens = command.split(" ");
			if (tokens.length == 2) {
				connection.ban(tokens[1]);
				return 0;
			}
		} else if ("banlist".equals(command)) {
			response = connection.banList();
			System.out.println("banlist was executed");
			return 0;
			
		} else if ("list".equals(command)) {
			response = connection.list();
			return 0;
			
		} else if ("save-all".equals(command)) {
			connection.saveAll();
			return 0;
		} else if ("save-on".equals(command)) {
			connection.saveOn();
			return 0;
			
		} else if ("save-off".equals(command)) {
			connection.saveOff();
			return 0;
			
		} else if ("stop".equals(command)) {
			connection.stop();
			return 0;
			
		} else if ("toggledownfall".equals(command)) {
			connection.toggleDownfall();
			return 0;
			
		} else if ("whitelist on".equals(command)) {
			connection.whitelistOn();
			return 0;
			
		} else if ("whitelist off".equals(command)) {
			connection.whitelistOff();
			return 0;
			
		} else if ("whitelist reload".equals(command)) {
			connection.whitelistReload();
			return 0;
			
		} else if (command.startsWith("ban-ip")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 2) {
				connection.banIp(tokens[1]);
				return 0;
			} 
		} else if (command.startsWith("op")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 2) {
				connection.op(tokens[1]);
				return 0;
			}
		} else if (command.startsWith("deop")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 2) {
				connection.deOp(tokens[1]);
				return 0;
			}
		} else if (command.startsWith("gamemode")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 3) {
				int gamemode = Integer.valueOf(tokens[2]);
				GameMode mode = (0 == gamemode) ? GameMode.Survival : GameMode.Creative;
				connection.gameMode(tokens[1], mode);
				return 0;
			}
		} else if (command.startsWith("give")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 3) {
				int datavalue = Integer.valueOf(tokens[2]);
				connection.give(tokens[1], datavalue);
				return 0;
				
			} else if (tokens.length == 4) {
				int datavalue = Integer.valueOf(tokens[2]);
				int amount = Integer.valueOf(tokens[3]);
				connection.give(tokens[1], datavalue, amount);
				return 0;
				
			} else if (tokens.length == 5) {
				int datavalue = Integer.valueOf(tokens[2]);
				int amount = Integer.valueOf(tokens[3]);
				int damage = Integer.valueOf(tokens[4]);
				connection.give(tokens[1], datavalue, amount, damage);
				return 0;
			}
		} else if (command.startsWith("pardon")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 2) {
				connection.pardon(tokens[1]);
				return 0;
			}
		} else if (command.startsWith("pardon-ip")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 2) {
				connection.pardonIp(tokens[1]);
				return 0;
			}
		} else if (command.startsWith("time add")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 3) {
				int amount = Integer.valueOf(tokens[2]);
				connection.timeAdd(amount);
				return 0;
			}
		} else if (command.startsWith("time set")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 3) {
				int amount = Integer.valueOf(tokens[2]);
				connection.timeSet(amount);
				return 0;
			}
		} else if (command.startsWith("tp")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 3) {
				connection.tp(tokens[1], tokens[2]);
				return 0;
			}
		} else if (command.startsWith("xp")) {
			String[] tokens = command.split(" ");
			if (tokens.length == 3) {
				int amount = Integer.valueOf(tokens[2]);
				connection.xp(tokens[1], amount);
				return 0;
			}
		} 
		return result;
	}
	private RCon getConnection(String serverId) {
		if (null == connection) {
			Properties prop = ServerUtils.loadServerProperties(serverId);
			int port = Integer.valueOf(prop.getProperty("rcon.port"));
			String password = prop.getProperty("rcon.password");
			try {
				connection = new RCon("127.0.0.1", port, password.toCharArray());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}
	
}
