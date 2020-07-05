package com.yc.http.javax.servlet;

/**
 * Servlet接口用来定义生命周期的
 * @author fangxiang
 *
 */
public interface Servlet {

	public void init();
	
	public void destory();
	
	public void service(ServletRequest request,ServletResponse response);
}
