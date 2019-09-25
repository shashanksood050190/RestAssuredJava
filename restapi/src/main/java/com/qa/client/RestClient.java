package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {
	
	//without headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException
	{
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url); //http get request
		CloseableHttpResponse closeableHttpResponse=httpClient.execute(httpGet); //hit the GET URL
		
		return closeableHttpResponse;
	}
	
	
	//with headers
	public CloseableHttpResponse get(String url,HashMap<String,String> headerMap) throws ClientProtocolException, IOException
	{
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url); //http get request
		for(Map.Entry<String, String> entry:headerMap.entrySet())
		{
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		
		//hit the GET URL
		CloseableHttpResponse closeableHttpResponse=httpClient.execute(httpGet); 
		return closeableHttpResponse;
	}
	
	//POST call
	public CloseableHttpResponse Post(String url,String entitystring,HashMap<String,String> headerMap) throws ClientProtocolException, IOException
	{
	  CloseableHttpClient httpClient=HttpClients.createDefault();	
	  HttpPost httppost=new HttpPost(url);
	  httppost.setEntity(new StringEntity(entitystring)); //for payload
	  
	  for(Map.Entry<String,String> entry: headerMap.entrySet())
	  {
		  httppost.addHeader(entry.getKey(), entry.getValue());
	  }
	  
	  CloseableHttpResponse closeableHttpResponse=httpClient.execute(httppost);
	  return closeableHttpResponse;
	}

}
