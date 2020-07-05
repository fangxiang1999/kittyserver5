package com.yc.http.javax.servlet;

import java.io.PrintWriter;

import com.yc.http.javax.servlet.http.HttpServlet;
import com.yc.http.javax.servlet.http.HttpServletRequest;
import com.yc.http.javax.servlet.http.HttpServletResponse;

/**
 * 用户开发的业务程序
 */
//@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	//实现了一个网页访问计数
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		System.out.println("doPost被调用了...");
		System.out.println("计数器开始运行");
		ServletContext application=request.getServletContext();
		Integer count=new Integer(0);
		if(application.getAttribute("count")!=null) {
			count=(Integer) application.getAttribute("count");
		}
		count++;
		application.setAttribute("count", count);
		
		System.out.println("访问次数"+count);
		
		String result="<html><head></head><body><hr />visited count:"+count+"</body></html>";
		PrintWriter out=response.getWriter();
		
		StringBuffer sb=new StringBuffer();
		sb.append("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\nContent-Length: "
				+ result.getBytes().length + "\r\n\r\n");
		out.println(sb.toString());
		out.println(result);
		out.flush();
		out.close();
	}
	
	
	public HelloServlet() {
        super();
        System.out.println("HelloServlet的构造方法...");
    }

	
	@Override
	public void init() {
		super.init();
		System.out.println("init方法");
	}

	public void service(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("service被调用了...");
		super.service(request, response);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		System.out.println("doGet()...");
		doPost(request, response);
	}

	

}
