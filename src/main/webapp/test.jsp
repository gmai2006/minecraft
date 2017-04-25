<%
String uri = request.getRequestURI();
String path = request.getContextPath();
int index = uri.indexOf(path);
String pagename = uri.substring(path.length() + 1, uri.length());
%>
<p>test again</p>
<%=request.getServletContext().getRealPath("/")%>
<br/>
<%=request.getServletContext().getRealPath("/WEB-INF/cert/paypal_cert_pem.txt")%>
<br/>
<%=request.getRequestURI()%>
<br/>
<%=request.getContextPath()%>
<br/>
<%=pagename%>