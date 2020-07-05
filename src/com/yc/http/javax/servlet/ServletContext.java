package com.yc.http.javax.servlet;

import java.util.Map;

/**
 * application容器
 * @author fangxiang
 *
 */
public interface ServletContext {
	
	//获取所有的servlet
	public Map<String,Servlet> getServlets();
	
	public Servlet getServlet(String servletName);
	
	public void setServelt(String key,Servlet servlet);
	
	public void setAttribute(String key,Object obj);
	
	public Object getAttribute(String key);

}
