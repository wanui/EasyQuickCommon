package com.easyquick.common.net;

public class HttpComException extends Exception {

	private int statusCode;
	
	public HttpComException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
	
}
