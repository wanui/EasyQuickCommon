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
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
	
	private CloseableHttpClient httpClient = HttpClientBuilder
			.create()
			.setUserAgent(USER_AGENT)
			.build();
	private HttpClientContext httpContext = HttpClientContext.create();  
	private HttpGet httpGet = new HttpGet();
	private HttpPost httpPost = new HttpPost();
	
	
	/**
	 * 用post方式发送请求,返回字符串
	 * @param url 请求的url
	 * @param data 提交的数据
	 * @return String 请求返回字符串
	 * @throws Exception
	 */
	public String requestStringByPost(String url, List<? extends NameValuePair> data) throws Exception{
		
		CloseableHttpResponse response = request(url, HttpMethod.POST, data, null);
		
		return EntityUtils.toString(response.getEntity(), DEFAUT_CHARSET);
		
	}
	
	/**
	 * 用post方式发送请求,返回xml字符串
	 * @param url 请求的url
	 * @param data 提交的数据
	 * @return String 请求返回字符串
	 * @throws Exception
	 */
	public String requestXmlStringByPost(String url, List<? extends NameValuePair> data) throws Exception{
		HashMap<String, String> header = new HashMap<>();
		header.put("Content-Type", CONTENT_TYPE_XML);
		CloseableHttpResponse response = request(url, HttpMethod.POST, data, header);
		
		return EntityUtils.toString(response.getEntity(), DEFAUT_CHARSET);
		
	}
	
	/**
	 * 用get方式发送请求,返回字符串
	 * @param url 请求的url
	 * @return String 请求返回字符串
	 * @throws Exception
	 */
	public String requestStringByGet(String url) throws Exception{
		
		CloseableHttpResponse response = request(url, HttpMethod.GET, null, null);
		
		return EntityUtils.toString(response.getEntity(), DEFAUT_CHARSET);
		
	}
	
	/**
	 * 用get方式发送请求,返回xml字符串
	 * @param url 请求的url
	 * @return String 请求返回字符串
	 * @throws Exception
	 */
	public String requestXmlStringByGet(String url) throws Exception{
		HashMap<String, String> header = new HashMap<>();
		header.put("Content-Type", CONTENT_TYPE_XML);
		CloseableHttpResponse response = request(url, HttpMethod.POST, null, header);
		
		return EntityUtils.toString(response.getEntity(), DEFAUT_CHARSET);
		
	}
	
	/**
	 * 用post方式发送请求
	 * @param url 请求的url
	 * @param data 提交的数据
	 * @return CloseableHttpResponse 请求回应
	 * @throws Exception
	 */
	public CloseableHttpResponse requestByPost(String url, List<? extends NameValuePair> data) throws Exception{
		
		return request(url, HttpMethod.POST, data, null);
		
	}
	
	/**
	 * 用get方式发送请求
	 * @param url 请求的url
	 * @return CloseableHttpResponse 请求回应
	 * @throws Exception
	 */
	public CloseableHttpResponse requestByGet(String url) throws Exception{
		
		return request(url, HttpMethod.GET, null, null);
		
	}
	
	
	/**
	 * 发送请求
	 * @param url 请求的url
	 * @param data 提交的数据
	 * @param httpMethod 请求的方法
	 * @return CloseableHttpResponse 请求回应
	 * @throws Exception
	 */
	public CloseableHttpResponse request(String url, HttpMethod httpMethod, List<? extends NameValuePair> data, HashMap<String, String> headers) throws Exception{
		URI uri = this.buildURI(url);
		CloseableHttpResponse response = null;
		
		switch(httpMethod){
			case GET:
				this.addHeaders(httpGet, headers);
				httpGet.setURI(uri);
				response = httpClient.execute(httpGet, httpContext);
				break;
			case POST:
				this.addHeaders(httpPost, headers);
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
	 * @throws Exception
	 */
	public void close() throws Exception{
		httpClient.close();
	}
	
	
	private void addHeaders(HttpUriRequest request, HashMap<String, String> headers) {
		if (headers != null) {
			for (String key : headers.keySet()) {
				request.addHeader(key, headers.get(key));
			}
		}
	}
	
	private void saveFile(HttpResponse response, String fileName) throws Exception {
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
