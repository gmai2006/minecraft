<%@page import="com.hosting.middle.DataCacheManager"%>
<%@page import="java.util.*"%>
<%@page import="com.hosting.spring.paypal.std.*"%>

<%
String test = PaypalOrderController.getPath(request, "paypalcallback.asp");
String path = request.getServletPath();
path = path.substring(0, path.lastIndexOf("/"));
%>  
<p><%=request.isSecure()%></p>
<p>protocol: <%=request.getProtocol()%></p>
<p>auth type<%=request.getAuthType()%></p>
<p>request url <%=request.getRequestURL()%></p>
<p>servlet path <%=request.getServletPath()%></p>
<p>request URI <%=request.getRequestURI()%></p>
<p>server name <%=request.getServerName()%></p>
<p>real path:<%=getServletContext().getRealPath("/") %>
<p><%=getServletContext().getRealPath("/images/index.jpeg") %>
<p><%=request.getContextPath() + "account" %>
<p><%=test%></p>

