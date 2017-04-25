package com.tomcat.hosting.guice;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.MinecraftServerInfo;
import com.tomcat.hosting.utils.ServerUtils;

public class ManageServerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	
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
		ST st = getTemplate(getContainer(), pageName);
		
		String serverId = req.getParameter("id");
		String action = req.getParameter("action");
		Properties prop = ServerUtils.loadServerProperties(serverId);
		if (StringUtils.isNotEmpty(action)) {
			try {
				if ("stop".equalsIgnoreCase(action)) ServerUtils.stopMinecraftServer(serverId);
				else if ("start".equalsIgnoreCase(action)) ServerUtils.startMinecraftServer(serverId);
//				else if ("update".equals(action)) {
//					updateServer(req, serverId, prop);
//					prop = ServerUtils.loadServerProperties(serverId);
//				}
//				else if ("addUser".equals(action)) {
//					addUser(req);
//				}
				setMessage("Successfully " + action + " " + serverId);
				setCssId("alert-success");
			} catch (Exception e) {
				setMessage("Unable to " + action + " server due to: " + e.getMessage());
				setCssId("alert-warning");
				catchException(e, this.getClass().getName());
			}
					
			st.add("action", true);
			st.add("message", getMessage());
			st.add("cssId", getCssId());		
		}
		
		MinecraftServerInfo server = new MinecraftServerInfo();
		server.setId(serverId);
		server.setMapName((String)prop.get("level-name"));
		server.setGameMode(Integer.valueOf((String)prop.get("gamemode")));
		server.setGameModeDescr(MinecraftServerInfo.GAMEMODES[server.getGameMode()]);
		server.setDifficulty(Integer.valueOf((String)prop.get("difficulty")));
		server.setDifficultyDescr(MinecraftServerInfo.DIFFICULTIES[server.getDifficulty()]);
		server.setLevelType((String)prop.get("level-type"));
		server.setSpawnAnimal(Boolean.valueOf((String)prop.get("spawn-animals")));
		server.setSpawnMonsters(Boolean.valueOf((String)prop.get("spawn-monsters")));
		server.setMaxPlayers(Integer.valueOf((String)prop.get("max-players")));
		server.setWhitelist(Boolean.valueOf((String)prop.get("white-list")));
		server.setServerPort(Integer.valueOf((String)prop.get("server-port")));
		server.setMotd((String)prop.get("motd"));
		server.setViewDistance(Integer.valueOf((String)prop.get("view-distance")));
		server.setResourcePack((String)prop.get("resource-pack"));
		
		String status = ServerUtils.checkMinecraftServer(serverId);
		if ("0".equals(status)) { //is running
			server.setActive(1);
			server.setAction("Stop");
			server.setDescr("Server is running...");
		} else {
			server.setActive(0);
			server.setAction("Start");
			server.setDescr("Server is NOT running...");
		}
		st.add("server", server);
		ST menu = getTemplate(getContainer(), "menu-manageserver");
		st.add("manage", "active");
		st.add("uptime", ServerUtils.displayProcessUptime(serverId));
		st.add("menu", menu);
		return st;
	}
	
//	private void addUser(HttpServletRequest req) {
//		String userId = req.getParameter("userId");
//		String serverId = req.getParameter("id");
//		String op = req.getParameter("userType");
//		try {
//			String uuid = ServerUtils.addUsertoWhiteList(userId, serverId);
//			if ("op".equals(op)) {
//				ServerUtils.addOperator(userId, serverId, uuid);
//			}
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
}
