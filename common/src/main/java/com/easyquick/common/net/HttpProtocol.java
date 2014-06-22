package com.easyquick.common.net;

public enum HttpProtocol {
	
	HTTP("http"), HTTPS("https");
      
    private final String value;

    HttpProtocol(String value) {
    	this.value = value;
    }
      
    public String getValue() {
    	return value;
    }
}
