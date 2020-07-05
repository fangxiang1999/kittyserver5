package com.yc.http.server;

import com.yc.http.javax.servlet.ServletRequest;
import com.yc.http.javax.servlet.ServletResponse;

/**
 * 资源处理器:处理静态或动态的资源
 * @author fangxiang
 */
public interface Processor {

	/**
	 * 处理资源(动、静)的方法
	 * @param request  请求对象
	 * @param response 响应对象 
	 */
	public void process(ServletRequest request,ServletResponse response);
}
