package com.yc.http.javax.servlet;

import java.util.Map;

/**
 * 处理请求的接口
 * @author fangxiang
 *
 */
public interface ServletRequest {
	
	//获取真实路径
	public String getRealPath();
	
	//获取参数
	public Object getAttribute(String key);
	
	//设置键值对 参数
	public void setAttribute(String key,Object value);
	
	//获取传递过来的参数
	public String getParameter(String key);
	
	public Map<String,String> getParameterMap();
	
	//解析出uri
	public void parse();
	
	//解析出参数name age
	public String getServerName();
	
	//解析出请求的方式
	public int getServerPort();
}
