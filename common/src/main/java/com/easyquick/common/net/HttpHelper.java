package com.easyquick.common.net;

import java.io.File;
import java.lang.invoke.ConstantCallSite;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author 王辉
 * http通信工具类
 */
public class HttpHelper {

	private final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0)";
	private final String URL_Separator = "/";
	private String urlPrefix;
	
	private CloseableHttpClient httpClient = HttpClientBuilder
			.create()
			.setUserAgent(USER_AGENT)
			.build();
	private HttpClientContext httpContext = HttpClientContext.create();  
	private HttpGet httpGet = new HttpGet();
	private HttpPost httpPost = new HttpPost();
	
	/**
	 * 发送请求
	 * @param url 请求的url
	 * @param httpMethod 请求的方法
	 * @return CloseableHttpResponse 请求回应
	 * @throws Exception
	 */
	public CloseableHttpResponse request(String url, HttpMethod httpMethod) throws Exception{
		URI uri = this.buildURI(url);
		
		switch(httpMethod){
			case GET:
				httpGet.setURI(uri);
				return httpClient.execute(httpGet, httpContext);
			case POST:
				httpPost.setURI(uri);
				return httpClient.execute(httpPost, httpContext);
			default:
				throw new Exception("request only support method GET or POST");
		}
		
	}
	
	private void resetRequest() {
		httpGet.releaseConnection();
		httpGet.reset();
		httpPost.releaseConnection();
		httpPost.reset();
	}
	
	/**
	 * 构造请求URI
	 * @param url
	 * @return 加上urlPrefix后的URI
	 * @throws URISyntaxException 
	 */
	private URI buildURI(String url) throws URISyntaxException{
		if(!StringUtils.startsWithIgnoreCase(url, HttpProtocol.HTTP.getValue()) 
				&& !StringUtils.startsWithIgnoreCase(url, HttpProtocol.HTTPS.getValue())){
			return new URI(
					StringUtils.removeEnd(this.urlPrefix, URL_Separator)
					.concat(URL_Separator)
					.concat(StringUtils.removeStart(url, URL_Separator)));
		}
		
		return new URI(url);
	}

	/**
	 * 获取URL的前缀
	 * @return String 列如：http://www.xxx.com:8080/app
	 */
	public String getUrlPrefix() {
		return urlPrefix;
	}

	/**
	 * 设置URL的前缀
	 * @param urlPrefix 列如：http://www.xxx.com:8080/app
	 */
	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}
	
}
