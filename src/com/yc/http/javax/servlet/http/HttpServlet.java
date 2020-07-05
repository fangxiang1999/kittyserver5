package com.yc.http.javax.servlet.http;

import com.yc.http.javax.servlet.Servlet;
import com.yc.http.javax.servlet.ServletRequest;
import com.yc.http.javax.servlet.ServletResponse;

public abstract class HttpServlet implements Servlet {

	@Override
	public void init() {
	}

	@Override
	public void destory() {
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) {}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) {}
	protected void doHead(HttpServletRequest request,HttpServletResponse response) {}
	protected void doDelete(HttpServletRequest request,HttpServletResponse response) {}
	public void service(HttpServletRequest request,HttpServletResponse response) {	
	//取出request的method
		String method=((HttpServletRequest)request).getMethod();
		//判断是get/post
		if("get".equals(method)) {
			doGet((HttpServletRequest)request, (HttpServletResponse)response);
		}else if("post".equals(method)) {
			doPost((HttpServletRequest)request, (HttpServletResponse)response);
		}else if("head".equals(method)) {
			doHead((HttpServletRequest)request, (HttpServletResponse)response);
		}else if("delete".equals(method)) {
			doDelete((HttpServletRequest)request, (HttpServletResponse)response);
		}
	}
	
	@Override
	public void service(ServletRequest request,ServletResponse response) {
		this.service((HttpServletRequest)request, (HttpServletResponse)response);
	}

}
