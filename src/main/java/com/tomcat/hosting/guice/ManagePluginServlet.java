package com.tomcat.hosting.guice;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.FileWrapper;
import com.tomcat.hosting.dao.MinecraftServerInfo;
import com.tomcat.hosting.utils.ServerUtils;

public class ManagePluginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String getHtmlTitle(String pageName)
	{
		return "Manage Plugin";
	}
	@Override
	protected String gethtmlDescr(String pageName)
	{
		return "Minecraft Server - Manage Plugin";
	}
	
	@Override
	public ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception {
		String serverId = req.getParameter("id");
		ST st = getTemplate(getContainer(), pageName);
		ST menu = getTemplate(getContainer(), "menu-manageserver");
		st.add("menu", menu);
		Properties prop = ServerUtils.loadServerProperties(serverId);
		String command = req.getParameter("action");
		
		if (StringUtils.isNotEmpty(command)) {
			try {
				String mapName = req.getParameter("mapName");
				prop.setProperty("level-name", mapName);
				ServerUtils.saveServerProperties(serverId, prop);
				setMessage("Successfully update map to: " + mapName 
						+ " Please make sure to stop and start the server in order to play in the new map");
				setCssId("alert-success");

			} catch (Exception e) {
				setMessage("Unable to switch map due to : " + e.getMessage());
				setCssId("alert-warning");
				catchException(e, this.getClass().getName());
			} 
			
			st.add("action", true);
			st.add("message", getMessage());
			st.add("cssId", getCssId());
			
		}
		try {
			MinecraftServerInfo server = new MinecraftServerInfo();
			
			server.setId(serverId);
			File[] files = ServerUtils.getPlugins(serverId);	
			FileWrapper[] maps = new FileWrapper[files.length];
			for (int i = 0; i < maps.length; i++) {
//				if (files[i].getName().equals(server.getMapName())) continue; //remove the selected map
				maps[i] = new FileWrapper();
				maps[i].setName(files[i].getName());
				maps[i].setDate(new java.util.Date(files[i].lastModified()));
				maps[i].setSize(files[i].length());
			}
			
			st.add("server", server);
			st.add("plugins", maps);
			st.add("manageplugin", "active");
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception (e);
		}
		return st;
	}
}
