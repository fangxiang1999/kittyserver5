package com.yc.http.server;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.yc.http.javax.servlet.ServletContext;
import com.yc.http.javax.servlet.http.HttpServletRequest;

public class YcHttpServletRequest implements HttpServletRequest {
	private String method; //请求方法
	private String protocal;//协议版本
	private String serverName;//服务器名
	private int serverPort;//端口
	private String requestURI;//资源的地址
	private String requestURL;//绝对路径
	private String contextPath;//项目上下文路径
	private String realPath=System.getProperty("user.dir")+File.separatorChar+"webapps";
	//user.dir  -> 取到是当前的class路径
	
	private InputStream iis;

	public YcHttpServletRequest(InputStream iis) {
		this.iis=iis;
		parseRequest();
	}

	private void parseRequest() {
		String requestInfoString=readFromInputStream();// 从输入流中读取请求头
		if(requestInfoString==null||"".equals(requestInfoString)) {
			return;
		}
		//解析requestInfo字符串:method, URI , 键:值  参数
		parseRequestInfoString(requestInfoString);
	}

	private void parseRequestInfoString(String requestInfoString) {
		StringTokenizer st = new StringTokenizer(requestInfoString);
		if(st.hasMoreElements()) {
			this.method=st.nextToken();
			this.requestURI=st.nextToken();  //  /wowotuan/index.html
			this.protocal=st.nextToken();
			this.contextPath="/"+this.requestURI.split("/")[1]; //contextpath应用上下文路径   => /wowotuan
		}
		//TODO:后面的请求  键值对  请求实体 暂时不管
	}

	// 从输入流中读取请求头
	private String readFromInputStream() {
		// 1.从input中读取所有的内容(http请求协议=>> protocal)
		String protocal=null;
		//TODO:从流中取protocal
		StringBuffer sb=new StringBuffer(1024*10); //10K ->>避免循环读取
		int length=-1;
		byte []bs=new byte[1024*10];
		try {
			length=this.iis.read(bs);
		} catch (Exception e) {
			e.printStackTrace();
			length=-1;
		}
		for(int j=0;j<length;j++) {
			sb.append((char)bs[j]);
		}
		protocal=sb.toString();
		return protocal;
	}

	
	public String getProtocal() {
		return protocal;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public String getContextPath() {
		return contextPath;
	}

	public InputStream getIis() {
		return iis;
	}


	//父接口中的参数
	private Map<String,Object> attributes=new HashMap<String,Object>();
	
	@Override
	public String getRealPath() {
		return realPath;
	}

	@Override
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		attributes.put(key,value);
		
	}

	@Override
	public String getParameter(String key) {
		return parameters.get(key);
	}

	
	//请求参数
	private Map<String,String>parameters=new HashMap<String,String>();
	@Override
	public Map<String, String> getParameterMap() {
		return this.parameters;
	}

	@Override
	public void parse() {
		String requestInfoString=readFromInputStream();//从输入流中获取请求头
		if(requestInfoString==null||"".equals(requestInfoString)) {
			return;
		}
		//解析requestInfo字符串
		parseRequestInfoString(requestInfoString);
	}

	@Override
	public String getServerName() {
		return serverName;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return serverPort;
	}

	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return method;
	}

	@Override
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return requestURI;
	}

	@Override
	public ServletContext getServletContext() {
		return YcServletContext.getInstance();
	}

	

}
