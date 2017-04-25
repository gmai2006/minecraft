package com.tomcat.hosting.guice;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.tomcat.hosting.dao.MinecraftServerInfo;
import com.tomcat.hosting.utils.ServerUtils;

public class IndexServlet extends BaseServlet {
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
		ST menu = getTemplate(getContainer(), "menu-index");
		st.add("menu", menu);
		st.add("os", ServerUtils.displayOS());
		st.add("mem", ServerUtils.displayMeminfo());
		st.add("uptime", ServerUtils.displayUptimes());
		st.add("cpuload", ServerUtils.displayCpuLoad());
		
		String action = req.getParameter("action");
		if (StringUtils.isNotEmpty(action)) {
			String serverId = req.getParameter("id");
			dropServer(serverId);
			setCssId("alert-success");
			setMessage("Successfully remove minecraft server:" + serverId);
			st.add("action", true);
			st.add("message", getMessage());
			st.add("cssId", getCssId());		
		}
		String userId = ServerUtils.getUserid();
		File homeDir = new File("/home/" + userId);
		File[] servers = homeDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.isDirectory() && pathname.getName().startsWith(ServerUtils.SERVER_NAME);
			}
		});
		if (servers.length > 0) {
			List<MinecraftServerInfo> mcservers = new ArrayList<MinecraftServerInfo>(servers.length*2);
			for (File f:  servers) {
				MinecraftServerInfo mcserver = new MinecraftServerInfo();
				mcserver.setId(f.getName());
				String status = ServerUtils.checkMinecraftServer(mcserver.getId());
				System.out.println("status:" + status + " - " + mcserver.getId());
				if ("0".equals(status)) mcserver.setDescr("is running...");
				else mcserver.setDescr("is currently not running...");
				mcservers.add(mcserver);
			}
			Collections.sort(mcservers);
			st.add("servers", mcservers);
		}
		return st;
	}
	
	private void dropServer(String serverId) {
		String status = ServerUtils.checkMinecraftServer(serverId);
		if ("0".equals(status)) ServerUtils.stopMinecraftServer(serverId);
		ServerUtils.deleteMinecraftServer(serverId);
	}
	
}
