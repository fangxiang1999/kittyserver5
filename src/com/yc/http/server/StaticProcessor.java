package com.yc.http.server;

import com.yc.http.javax.servlet.ServletRequest;
import com.yc.http.javax.servlet.ServletResponse;
import com.yc.http.server.YcConstants;

public class StaticProcessor implements Processor {

	@Override
	public void process(ServletRequest request, ServletResponse response) {
		((YcHttpServletResponse)response).sendRedirect();
	}

}
