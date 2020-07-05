package com.yc.http.javax.servlet.http;


import com.yc.http.javax.servlet.ServletContext;
import com.yc.http.javax.servlet.ServletRequest;


public interface HttpServletRequest extends ServletRequest {

	//请求的方法
	
	public String getMethod();
	
	public String getRequestURI();
	
	/**
	 * 新增一个获取application的方法
	 * @return
	 */
	public ServletContext getServletContext();
}
