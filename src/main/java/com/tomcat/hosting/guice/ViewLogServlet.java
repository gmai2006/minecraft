package com.tomcat.hosting.guice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.MinecraftServerInfo;
import com.tomcat.hosting.utils.ServerUtils;

public class ViewLogServlet extends BaseServlet {
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
		String serverId = req.getParameter("id");
		ST st = getTemplate(getContainer(), pageName);
		ST menu = getTemplate(getContainer(), "menu-manageserver");
		st.add("menu", menu);
		
		String userId = ServerUtils.getUserid();
		File home = new File("/home");
		File userHome = new File(home, userId);
		File target = new File(userHome, serverId);
		File log = new File(target, "logs");
		File logfile = new File(log, "latest.log");
		
		if (logfile.exists()) {
			BufferedReader reader = null;
			List<String> list = new ArrayList<String>();
			String line = null;
			try {
				reader = new BufferedReader(new FileReader(logfile));
				while ((line = reader.readLine()) != null) {
					list.add(line);
				}
				st.add("logs", list);
			} catch (Exception e) {
				catchException(e, this.getClass().getName());
			} finally {
				try {
					if (null != reader) { reader.close(); reader = null; }
				} catch (Exception e) {}
			}
		} 
		MinecraftServerInfo server = new MinecraftServerInfo();
		server.setId(serverId);
		st.add("server", server);
		st.add("log", "active");
		return st;
	}
	
}
