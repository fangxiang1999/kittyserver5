package com.yc.http.server;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.yc.http.javax.servlet.Servlet;
import com.yc.http.javax.servlet.ServletContext;

public class YcServletContext implements ServletContext {

	private Map<String,Servlet> servlets=new Hashtable<String,Servlet>();//线程安全 修改方法synchronized
	private Map<String,Object> attributes=new HashMap<String,Object>();
	
	
	private static YcServletContext ycServletContext;
	
	
	//构造方法私有化
	public YcServletContext() {}
	
	//对外提供访问方法，获取唯一实例
	public synchronized static YcServletContext getInstance() {
		if(ycServletContext==null) {
			ycServletContext=new YcServletContext();
		}
		return ycServletContext;
	}

	@Override
	public Map<String, Servlet> getServlets() {
		// TODO Auto-generated method stub
		return this.servlets;
	}

	@Override
	public Servlet getServlet(String servletName) {
		// TODO Auto-generated method stub
		return this.servlets.get(servletName);
	}

	@Override
	public void setServelt(String key, Servlet servlet) {
		this.servlets.put(key, servlet);
	}

	@Override
	public void setAttribute(String key, Object obj) {
		this.attributes.put(key, obj);
	}

	@Override
	public Object getAttribute(String key) {
		return this.attributes.get(key);
	}

}
