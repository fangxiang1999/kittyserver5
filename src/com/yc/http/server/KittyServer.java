package com.yc.http.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import threadpool.ThreadPoolManger;

public class KittyServer {
	private ThreadPoolManger tpm;//线程池管理器

	public static void main(String[] args) throws Exception {
		KittyServer ks=new KittyServer();
		ks.startServer();
	}

	boolean flag=false;
	private void startServer() throws Exception {
		ServerSocket ss=null;
		//TODO:此处课修改为解析xml  为一个map 参数都可以在map中取
		int port =parseServerXml();
		//TODO:此处课修改为解析xml中读取  线程池配置
		tpm=new ThreadPoolManger(10, null );
		try {
			ss=new ServerSocket(port);
			YcConstants.logger.debug("kitty server is starting,and listening to port"+ss.getLocalPort());
		} catch (Exception e) {
			YcConstants.logger.error("kitty server's port"+port+" is already in user...");
			return;
		}
		while(!flag) {
			try {
				Socket s = ss.accept();
				YcConstants.logger.debug("a client "+s.getInetAddress()+" is connecting to kitty server...");
				TaskService ts=new TaskService(s);
				
				//TODO:可以改写成  判断是否采用了线程池，如果是则使用线程池执行任务
				tpm.process(ts);
				//TODO:不是则采用原生的  Thread来操作
//				Thread t = new Thread(ts);
//				t.start();
			} catch (Exception e) {
				YcConstants.logger.debug("client is down,cause "+e.getMessage());
			}
		}
		
	}
	
	
	/**
	 * TODO:可以改写成解析xml  返回一个Map<String,Object>的结构  object代表可能是一个string 也有可能是一个map
	 * 比如: {"port":"8989","connectionPool":{"num":"10","closable":"true"}}
	 * 解析xml 得到port
	 * @return
	 * @throws Exception 
	 */
	private int parseServerXml() throws Exception {
		List<Integer> list=new ArrayList<Integer>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//通过DocumentBuilderFactory 来创建xml解析容器
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();//通过解析器创建一个可以加载并生成xml的DocumentBuilder
			Document doc=builder.parse(YcConstants.SERVERCONFIG);//通过DocumentBuilder加载并生成一颗xml树  Document对象的实例
			NodeList nl = doc.getElementsByTagName("Connector");//通过Document可以遍历这棵树,并读取相应节点中的内容
			for(int i=0;i<nl.getLength();i++) {
				Element node = (Element) nl.item(i);
				list.add(Integer.parseInt(node.getAttribute("port")));
			}
		
		} catch (Exception e) {
			throw e;
		}
		return list.get(0);
	}
}
