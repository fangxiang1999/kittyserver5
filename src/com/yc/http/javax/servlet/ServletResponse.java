package com.yc.http.javax.servlet;

import java.io.PrintWriter;

public interface ServletResponse {

	//获取输出字符流
	public PrintWriter getWriter();
	
	//获取输出资源的类型
	public String getContentType();
}
