package com.yc.http.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.yc.http.server.YcConstants;

import threadpool.Taskable;

/**
 * 处理一次请求的任务类
 * @author fangxiang
 *
 */
public class TaskService implements Taskable {
	private Socket socket;
	private InputStream iis;
	private OutputStream oos;
	private boolean flag;
	
	public TaskService(Socket socket) {
		this.socket=socket;
		try {
			this.iis=this.socket.getInputStream();
			this.oos=this.socket.getOutputStream();
			flag=true;
		} catch (Exception e) {
			YcConstants.logger.error("failed to get stream",e);
			flag=false;
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Object doTask() {
		if(flag) {
			//包装一个httpservletrequest对象  功能是从iis中取数据 解析请求信息  保存信息
			YcHttpServletRequest request = new YcHttpServletRequest(this.iis);
			//包装一个httpservletResponse对象 从request中取信息(文件的资源)  读取资源 构建响应头 回给客户端
			YcHttpServletResponse response=new YcHttpServletResponse(request,this.oos);
			//response.sendRedirect();
			
			//判断是静态资源还是动态资源
			Processor processor=null;
			if(request.getRequestURI().endsWith(".do")) {
				processor=new DynamicProcessor();
			}else {
				processor=new StaticProcessor();
			}
			processor.process(request, response);
		}
		try {
			this.socket.close();//http协议是基于请求/响应无状态的
		} catch (Exception e) {
			YcConstants.logger.error(" failed to close connection to client",e);
		}
		return null;
	}

}
