package com.yc.http.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.yc.http.javax.servlet.http.HttpServletRequest;
import com.yc.http.javax.servlet.http.HttpServletResponse;

public class YcHttpServletResponse implements HttpServletResponse {

	private OutputStream oos;
	private HttpServletRequest request;
	private String contentType;
	
	public YcHttpServletResponse(HttpServletRequest request,OutputStream oos) {
		this.oos = oos;
		this.request = request;
	}
	
	/**
	 * 1.从request中取出uri  2.判断是佛在本地存在这个uri指代的文件 没有 404  有3.咦输入流读取这个文件
	 * 4.以输出流将文件写到客户端  要加入响应的协议
	 */
	public void sendRedirect() {
		String responseprotocal=null;//响应协议头
		byte[] fileContext=null;//响应的内容
		String uri=request.getRequestURI();//请求资源的地址  /wowotuan/index.html
		File f=new File(request.getRealPath(),uri);//请求的文件的绝对路径
		if(!f.exists()) {
			fileContext=readFile(new File(request.getRealPath(),"/404.html"));
			responseprotocal=gen200(fileContext.length);
		}else {
			fileContext=readFile(f);
			responseprotocal=gen200(fileContext.length);
		}
		try {
			oos.write(responseprotocal.getBytes());//写协议
			oos.flush();
			oos.write(fileContext);//写出文件
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(oos!=null) {
				try {
					oos.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	private byte[] readFile(File f) {
		FileInputStream fis=null;
		//字节数组输出流: 读取到字节数组后  存到内存
		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		
		try {
			//读取这个文件
			fis=new FileInputStream(f);
			byte []bs=new byte[10*1024];
			int length=-1;
			while((length=fis.read(bs,0,bs.length))!=-1) {
				boas.write(bs,0,length); //写到内存缓存
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return boas.toByteArray();//一次性的从内存中读取所有字节数组返回
	}
	
	/**
	 * 要考虑静态文件的类型
	 * @param bodylength
	 *            , 内容的长度
	 * @return
	 */
	private String gen200(long bodylength) {
		String uri = this.request.getRequestURI(); // 取出要访问的文件的地址
		int index = uri.lastIndexOf(".");
		if (index >= 0) {
			index = index + 1;
		}
		String fileExtension = uri.substring(index); // 文件的后缀名
		String protocal200 = "";
		String contentType="";
		if ("JPG".equalsIgnoreCase(fileExtension)
				|| "JPEG".equalsIgnoreCase(fileExtension)) {
			contentType="image/JPEG";
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: "+contentType+"\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else if ("PNG".equalsIgnoreCase(fileExtension)) {
			contentType="image/PNG";
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: "+ contentType+"\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else if ("json".equalsIgnoreCase(fileExtension)) {
			contentType="application/json";
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: "+contentType+"\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else if ("css".equalsIgnoreCase(fileExtension)) {
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: text/css\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else if ("js".equalsIgnoreCase(fileExtension)) {
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: text/javascript\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		}else {
			contentType="text/html";
			protocal200 = "HTTP/1.0 200 OK\r\nContent-Type: "+contentType+"\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		}
		return protocal200;
	}

	/**
	 * 产生404响应
	 * 
	 * @return
	 */
	private String gen404(long bodylength) {
		String protocal404 = "HTTP/1.1 404 File Not Found\r\nContent-Type: text/html\r\nContent-Length: "
				+ bodylength + "\r\n\r\n";
		return protocal404;
	}

	@Override
	public PrintWriter getWriter() {
		// oos字节流 writer字符流
		PrintWriter pw=new PrintWriter(this.oos);
		return pw;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}
	
}
