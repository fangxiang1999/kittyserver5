package com.yc.http.javax.servlet.http;

import com.yc.http.javax.servlet.ServletResponse;

public interface HttpServletResponse extends ServletResponse {

	//输出重定向方法
	public void sendRedirect();
}
