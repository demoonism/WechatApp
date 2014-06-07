package com.Daniel.CSSA;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class BaiduMapService {
	Logger logger = Logger. getLogger("BaiduMapService");
	
	public String getPalace(String query,String lat,String lng) throws ClientProtocolException, IOException{
		HttpClient httpClient = new DefaultHttpClient();
		String url = palceRequestUrl(query,lat,lng);
		logger.log(Level.INFO, url);
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpClient.execute(httpget, responseHandler);
		logger.log(Level.INFO,"baidu response:"+responseBody);
		return responseBody;
	}
	
	public String palceRequestUrl(String query,String lat,String lng) throws UnsupportedEncodingException {
		String url = WeChatConstant.BASEURL + "maps/api/place/textsearch/" + WeChatConstant.OUTPUTFORMAT + "?query=" + URLEncoder.encode(query,"UTF-8") + "&key="
				+ WeChatConstant.MAPKEY +"&location="+lat+","+lng +"&radius=" + WeChatConstant.RADIUS + "&sensor=" + WeChatConstant.SENSOR + "&language=" + WeChatConstant.LANGUAGE;
		return url;
	}
	
//	public String getGeoCode(String query) throws ClientProtocolException, IOException{
//		HttpClient httpClient = new DefaultHttpClient();
//		String url = geoCodeRequestUrl(query);
//		logger.log(Level.INFO, url);
//		HttpGet httpget = new HttpGet(url);
//		ResponseHandler<String> responseHandler = new BasicResponseHandler();
//		String responseBody = httpClient.execute(httpget, responseHandler);
//		logger.log(Level.INFO,"baidu response:"+responseBody);
//		return responseBody;
//	}
//	
//	public String geoCodeRequestUrl(String query) throws UnsupportedEncodingException{
//		String url = WeChatConstant.BASEURL + "geocoder?address=" + URLEncoder.encode(query,"UTF-8") + "&key="
//				+ WeChatConstant.MAPKEY + "&output=" + WeChatConstant.OUTPUTFORMAT;
//		return url;
//	}
}
