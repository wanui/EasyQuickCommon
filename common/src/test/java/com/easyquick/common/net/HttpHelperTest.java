package com.easyquick.common.net;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.omg.DynamicAny.NameValuePair;

public class HttpHelperTest {

	@Test
	public void requestStringByGetTest() throws Exception {
		HttpHelper httpHelper=new HttpHelper("ju.taobao.com", 80);
		System.out.println(httpHelper.requestStringByGet("/?spm=1.7274553.1997517361.2.TBl1Mo"));
	}
	
	@Test
	public void requestStringByPostTest() throws Exception {
		HttpHelper httpHelper=new HttpHelper("www.booksky.me", 80);
		List<BasicNameValuePair> data = new ArrayList<>();
		data.add(new BasicNameValuePair("UserName", "wanui"));
		data.add(new BasicNameValuePair("Password", "wanui"));
		data.add(new BasicNameValuePair("CookieSave", "Never"));
		data.add(new BasicNameValuePair("postcheck", "true"));
		//System.out.println(httpHelper.requestStringByPost("checklogin.ashx",data));
		System.out.println(httpHelper.requestStringByGet("BookDetail.aspx?BookID=3554713"));
		httpHelper.setProxyHost("BookDetail.aspx?BookID=3554713")
	}

}
