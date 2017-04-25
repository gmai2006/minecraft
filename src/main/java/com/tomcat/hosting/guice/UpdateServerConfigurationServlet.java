package com.tomcat.hosting.guice;

import java.io.File;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.MinecraftServerInfo;
import com.tomcat.hosting.utils.ServerUtils;

public class UpdateServerConfigurationServlet extends BaseServlet {
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
				if ("update".equals(action)) {
					updateServer(req, serverId, prop);
					prop = ServerUtils.loadServerProperties(serverId);
				}
				
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
		server.setViewDistance(Integer.valueOf((String)prop.get("view-distance")));
		server.setResourcePack((String)prop.get("resource-pack"));
		server.setCommandBlock((String)prop.get("enable-command-block"));
		
		String status = ServerUtils.checkMinecraftServer(serverId);
		if ("0".equals(status)) { //is running
			server.setActive(1);
			server.setAction("Stop");
		} else {
			server.setActive(0);
			server.setAction("Start");
		}
		File[] maps = ServerUtils.getMaps(serverId);
		st.add("server", server);
		st.add("maps", maps);
		ST menu = getTemplate(getContainer(), "menu-manageserver");
		st.add("update", "active");
		st.add("menu", menu);
		return st;
	}
	
	
	private void updateServer(HttpServletRequest req, String serverId, Properties prop) {
		String gameMode = req.getParameter("gameMode");
		String difficulty = req.getParameter("difficulty");
		String levelType = req.getParameter("levelType");
		String maxPlayers = req.getParameter("maxPlayers");
		String spawnMonsters = req.getParameter("spawnMonsters");
		String spawnAnimal = req.getParameter("spawnAnimal");
		String whitelist = req.getParameter("whitelist");
		String mapName = req.getParameter("mapName");
		String viewDist = req.getParameter("viewDistance");
		String resourcePack = req.getParameter("resourcePack");
		String commandBlock = req.getParameter("commandBlock");
		
		if (StringUtils.isNotEmpty(mapName)) prop.setProperty("level-name", mapName);
		if (StringUtils.isNotEmpty(gameMode)) prop.setProperty("gamemode", gameMode);
		if (StringUtils.isNotEmpty(difficulty)) prop.setProperty("difficulty", difficulty);
		if (StringUtils.isNotEmpty(levelType)) prop.setProperty("level-type", levelType);
		if (StringUtils.isNotEmpty(maxPlayers)) prop.setProperty("max-players", maxPlayers);
		if (StringUtils.isNotEmpty(spawnMonsters)) prop.setProperty("spawn-monsters", spawnMonsters);
		if (StringUtils.isNotEmpty(spawnAnimal)) prop.setProperty("spawn-animals", spawnAnimal);
		if (StringUtils.isNotEmpty(whitelist)) prop.setProperty("white-list", whitelist);
		if (StringUtils.isNotEmpty(viewDist)) prop.setProperty("view-distance", viewDist);
		if ("clear".equalsIgnoreCase(resourcePack)) prop.setProperty("resource-pack", "");
		else if (StringUtils.isNotEmpty(resourcePack)) prop.setProperty("view-distance", resourcePack);
		if (StringUtils.isNotEmpty(commandBlock)) prop.setProperty("enable-command-block", commandBlock);
		
		System.out.println(mapName + ":" + prop.getProperty("level-name"));
		
		ServerUtils.saveServerProperties(serverId, prop);
	}
	
}
