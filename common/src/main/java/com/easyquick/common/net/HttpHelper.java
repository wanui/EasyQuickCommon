package com.easyquick.common.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import javax.xml.crypto.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.HeaderGroup;
import org.apache.http.util.EntityUtils;

/**
 * @author 王辉
 * http通信工具类
 */
public class HttpHelper {

	private final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0)";
	private final String URL_SEPARATOR = "/";
	private final String DEFAUT_CHARSET = "UTF-8";
	private final String CONTENT_TYPE_XML = "text/xml; charset=UTF-8";
	private final int HTTP_STATUS_SUCCESS = 200;
	
	private String urlPrefix;
	private CloseableHttpClient httpClient;
	private HttpClientBuilder httpClientBuilder;
	private HttpClientContext httpContext;
	private HttpGet httpGet;
	private HttpPost httpPost;
	
	
	public HttpHelper() {
		init();
		httpClient =httpClientBuilder
				.setUserAgent(USER_AGENT)
				.build();
	}
	
	public HttpHelper(String urlPrefix) {
		init();
		httpClient =httpClientBuilder
				.setUserAgent(USER_AGENT)
				.build();
		this.urlPrefix = urlPrefix;
	}
	
	public HttpHelper(String urlPrefix, RequestConfig requestConfig) {
		init();
		httpClient =httpClientBuilder
				.setUserAgent(USER_AGENT)
				.setDefaultRequestConfig(requestConfig)
				.build();
		this.urlPrefix = urlPrefix;
	}
	
	private void init(){
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClientContext httpContext = HttpClientContext.create();  
		HttpGet httpGet = new HttpGet();
		HttpPost httpPost = new HttpPost();
	}
	
	/**
	 * 用post方式发送请求,返回字符串
	 * @param url 请求的url
	 * @param data 提交的数据
	 * @return String 请求返回字符串
	 * @throws Exception
	 */
	public String requestStringByPost(String url, List<? extends NameValuePair> data) throws Exception{
		String result;
		CloseableHttpResponse response = request(url, HttpMethod.POST, data);
		
		try {
			result = EntityUtils.toString(response.getEntity(), DEFAUT_CHARSET);
			return result;
		} catch (Exception e) {
			throw e;
		}
		finally{
			response.close();
		}
		
	}
	
	/**
	 * 用post方式发送请求,返回xml字符串
	 * @param url 请求的url
	 * @param data 提交的数据
	 * @return String 请求返回字符串
	 * @throws Exception
	 */
	public String requestXmlStringByPost(String url, List<? extends NameValuePair> data) throws Exception{
		String result;
		this.addHeader("Content-Type", CONTENT_TYPE_XML);
		CloseableHttpResponse response = request(url, HttpMethod.POST, data);
		
		try {
			result = EntityUtils.toString(response.getEntity(), DEFAUT_CHARSET);
			return result;
		} catch (Exception e) {
			throw e;
		}
		finally{
			this.removeHeader("Content-Type", CONTENT_TYPE_XML);
			response.close();
		}
	}
	
	/**
	 * 用get方式发送请求,返回字符串
	 * @param url 请求的url
	 * @return String 请求返回字符串
	 * @throws Exception
	 */
	public String requestStringByGet(String url) throws Exception{
		String result;
		CloseableHttpResponse response = request(url, HttpMethod.GET, null);
		
		try {
			result = EntityUtils.toString(response.getEntity(), DEFAUT_CHARSET);
			return result;
		} catch (Exception e) {
			throw e;
		}
		finally{
			response.close();
		}
		
	}
	
	/**
	 * 用get方式发送请求,返回xml字符串
	 * @param url 请求的url
	 * @return String 请求返回字符串
	 * @throws Exception
	 */
	public String requestXmlStringByGet(String url) throws Exception{		
		String result;
		this.addHeader("Content-Type", CONTENT_TYPE_XML);
		CloseableHttpResponse response = request(url, HttpMethod.GET, null);
		
		try {
			result = EntityUtils.toString(response.getEntity(), DEFAUT_CHARSET);
			return result;
		} catch (Exception e) {
			throw e;
		}
		finally{
			this.removeHeader("Content-Type", CONTENT_TYPE_XML);
			response.close();
		}
		
	}
	
	
	/**
	 * 用get方式下载文件
	 * @param url 请求的url
	 * @param localFileName 保存的文件名
	 * @throws Exception
	 */
	public void downloadFileByGet(String url, String localFileName) throws Exception {
		CloseableHttpResponse response = this.requestByGet(url);
		try {
			this.saveFile(response, localFileName);
		} catch (Exception e) {
			throw e;
		}
		finally{
			response.close();
		}
		
	}
	
	/**
	 * 用post方式下载文件
	 * @param url 请求的url
	 * @param localFileName 保存的文件名
	 * @param data 提交的数据
	 * @throws Exception
	 */
	public void downloadFileByPost(String url, String localFileName, List<? extends NameValuePair> data) throws Exception {
		CloseableHttpResponse response = this.requestByPost(url, data);
		try {
			this.saveFile(response, localFileName);
		} catch (Exception e) {
			throw e;
		}
		finally{
			response.close();
		}
	}
	
	/**
	 * 用post方式发送请求
	 * @param url 请求的url
	 * @param data 提交的数据
	 * @return CloseableHttpResponse 请求回应
	 * @throws Exception
	 */
	public CloseableHttpResponse requestByPost(String url, List<? extends NameValuePair> data) throws Exception{	
		return request(url, HttpMethod.POST, data);
	}
	
	/**
	 * 用get方式发送请求
	 * @param url 请求的url
	 * @return CloseableHttpResponse 请求回应
	 * @throws Exception
	 */
	public CloseableHttpResponse requestByGet(String url) throws Exception{
		return request(url, HttpMethod.GET, null);
	}
	
	/**
	 * 发送请求
	 * @param url 请求的url
	 * @param httpMethod 请求的方法
	 * @param data 提交的数据
	 * @return CloseableHttpResponse 请求回应
	 * @throws Exception
	 */
	public CloseableHttpResponse request(String url, HttpMethod httpMethod, List<? extends NameValuePair> data) throws Exception{
		URI uri = this.buildURI(url);
		CloseableHttpResponse response = null;
		
		switch(httpMethod){
			case GET:
				httpGet.setURI(uri);
				response = httpClient.execute(httpGet, httpContext);
				break;
			case POST:
				httpPost.setURI(uri);
				httpPost.setEntity(new UrlEncodedFormEntity(data, DEFAUT_CHARSET));
				response = httpClient.execute(httpPost, httpContext);
				break;
			default:
				throw new Exception("request only support method GET or POST");
		}
		
		if(requestSuccess(response)){
			return response;
		}
		
		return null;
	}
	
	/**
	 * 关闭HttpHelper释放资源
	 * @throws Exceptionddddd
	 */
	public void close() throws Exception{
		httpClient.close();
	}
	
	/**
	 * 增加http协议头
	 * @param name http协议头名称
	 * @param value http协议头值
	 */
	public void addHeader(String name, String value) {
		this.httpGet.addHeader(name, value);
		this.httpPost.addHeader(name, value);
	}
	
	/**
	 * 增加http协议头
	 * @param header http协议头
	 */
	public void addHeader(Header header) {
		this.httpGet.addHeader(header);
		this.httpPost.addHeader(header);
	}
	
	/**
	 * 增加一组http协议头
	 * @param headers 一组http协议头
	 */
	public void addHeaders(Header[] headers) {
		if (headers != null) {
			for (Header header : headers) {
				this.httpGet.addHeader(header);
				this.httpPost.addHeader(header);
			}
		}
	}
	
	/**
	 * 增加一组http协议头
	 * @param headers 一组http协议头
	 */
	public void addHeaders(HashMap<String, String> headers) {
		if (headers != null) {
			for (String key : headers.keySet()) {
				this.httpGet.addHeader(key, headers.get(key));
				this.httpPost.addHeader(key, headers.get(key));
			}
		}
	}
	
	/**
	 * 移除http协议头
	 * @param name http协议头名称
	 */
	public void removeHeader(String name) {
		this.httpGet.removeHeaders(name);
		this.httpPost.removeHeaders(name);
	}
	
	/**
	 * 移除http协议头
	 * @param header http协议头
	 */
	public void removeHeader(Header header) {
		this.httpGet.removeHeader(header);
		this.httpPost.removeHeader(header);
	}
	
	/**
	 * 移除http协议头
	 * @param name http协议头名称
	 * @param value http协议头值
	 */
	public void removeHeader(String name, String value) {
		Header[] headers = this.getAllHeaders();
		for (Header header : headers) {
			if(header.getName().equals(name) && header.getValue().equals(value))
				this.removeHeader(header);
		}
	}
	
	/**
	 * 清除所有http协议头
	 */
	public void clearAllHeaders() {
		Header[] headers = this.getAllHeaders();
		for (Header header : headers) {
			this.removeHeader(header);
		}
	}
	
	/**
	 * 获取所有http协议头
	 * @return Header[] http协议数组
	 */
	public Header[] getAllHeaders() {
		return this.httpPost.getAllHeaders();
	}
	
	/**
	 * 将响应内容保存为文件
	 * @param response 请求的响应
	 * @param fileName 保存的文件名
	 * @return String 保存的文件名
	 * @throws Exception
	 */
	private String saveFile(HttpResponse response, String fileName) throws Exception {
		InputStream inStream = response.getEntity().getContent();
		File file = new File(fileName);
		FileOutputStream outputStream = new FileOutputStream(file);
		byte[] bytes = new byte[1024];
		int j = 0;
		
		try {
			while  ((j=inStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, j);
			}
			outputStream.flush();
		}
		finally{
			if (inStream != null) {
				inStream.close();
			}
			if (outputStream != null){
				outputStream.close();
			}
		}
		
		return fileName;
	}
	
	/**
	 * 判断是否http请求成功
	 * @param response 请求的响应
	 * @return boolean true为成功
	 * @throws Exception
	 */
	private boolean requestSuccess(HttpResponse response) throws Exception {
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode == HTTP_STATUS_SUCCESS){
			return true;
		}
		else {
			throw new Exception("http request fail status code " + String.valueOf(statusCode));
		}
	}
	
	/**
	 * 重置Http请求
	 */
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
					StringUtils.removeEnd(this.urlPrefix, URL_SEPARATOR)
					.concat(URL_SEPARATOR)
					.concat(StringUtils.removeStart(url, URL_SEPARATOR)));
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
