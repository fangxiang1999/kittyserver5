package com.yc.http.server;

import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;

import com.yc.http.javax.servlet.Servlet;
import com.yc.http.javax.servlet.ServletContext;
import com.yc.http.javax.servlet.ServletRequest;
import com.yc.http.javax.servlet.ServletResponse;
import com.yc.http.javax.servlet.http.HttpServlet;
import com.yc.http.javax.servlet.http.HttpServletRequest;
import com.yc.http.javax.servlet.http.HttpServletResponse;
import com.yc.http.server.YcConstants;

public class DynamicProcessor implements Processor {

	@Override // request requestURI=> /res/Hello.do
	public void process(ServletRequest request, ServletResponse response) {
		// 1 取出uri,从uri中取出请求的servlet名字
		String uri = ((HttpServletRequest) request).getRequestURI();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf(".")); // Hello
		Servlet servlet = null;
		// System.out.println("================"+servletName);

		// 从application中判断是否有这个servletName
		ServletContext application = YcServletContext.getInstance();// 单例
		if (application.getServlet(servletName) != null) {
			servlet = application.getServlet(servletName);
		} else {
			// 2.动态字节码加载 到 wowotuan/找.class文件
			URL[] urls = new URL[1];
			try {
				// file://d:\workspace\Kittyserver\ Hello.class
				urls[0] = new URL("file", null, YcConstants.KITTYSERVER_BASEPATH);
				URLClassLoader url = new URLClassLoader(urls);// classloader类加载器会自动扫描 urls数组中指定的路径
				// url地址 =》 file:\\\
				// 4.Class urlclassloader.loadClass(类的名字);
				Class c = url.loadClass(servletName);

				// 5.以反射的形式 newInstance()创建 servlet实例
				// Object o=c.newInstance();
				// 6.再以生命周期的方式，采用servlet中的各方法
				// if(o!=null&& o instanceof Servlet) {
				// Servlet servlet=(Servlet) o;
				servlet = (Servlet) c.newInstance();
				application.setServelt(servletName, servlet);
				// 生命周期方法的调用
				servlet.init();
				// 父类引用只能调用子类重写了父类的方法 而不腻调用子类特有的方法
				((HttpServlet) servlet).service((HttpServletRequest) request, (HttpServletResponse) response);
				// }

			} catch (Exception e) {
				e.printStackTrace();
				String bodyentity = e.toString();
				String protocal = gen500(bodyentity.getBytes().length);
				PrintWriter pw = response.getWriter();
				pw.println(protocal);
				pw.println(bodyentity);
				pw.flush();
			}
		}
	}

	private String gen500(long bodylength) {
		String protocal500 = "HTTP/1.1 500 Internal Server Error\r\nContent-Type: text/html;charset=utf-8\r\nContent-Length: "
				+ (bodylength + 2) + "\r\n\r\n";
		return protocal500;
	}

}
