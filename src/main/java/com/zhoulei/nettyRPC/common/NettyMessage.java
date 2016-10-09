package com.zhoulei.nettyRPC.common;

/**
 * RPC协议
 * @author acer
 *
 */
public class NettyMessage implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Header header;//协议头
	private Object body;//协议体
	/**
	 * @return the header
	 */
	public Header getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(Header header) {
		this.header = header;
	}
	/**
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(Object body) {
		this.body = body;
	}
	
	
	

}
