package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase {

	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	public void setUP() {
		testBase = new TestBase();
		apiUrl = prop.getProperty("URL");
		serviceUrl = prop.getProperty("ServiceURL");
		url = apiUrl+serviceUrl;
		
	}

	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException
	{
		restClient=new RestClient();
		HashMap<String,String> headerMap=new HashMap<String,String>();
		headerMap.put("content-Type", "application/json");
		
		//jackson API
		ObjectMapper mapper=new ObjectMapper();
		Users users=new Users("Morpheus","Leader"); //expected Users object
		mapper.writeValue(new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\data\\users.json"), users);
	
		
		//Object to json string: {Marshalling}
		String userJsonString= mapper.writeValueAsString(users);
		System.out.println(userJsonString);
		
		closeableHttpResponse=restClient.Post(url, userJsonString, headerMap);
		
		//validate response from API
		//1. statusCode:
		int satusCode=closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(satusCode,RESPONSE_STATUS_CODE_201);
		
		//JsonString
		String responseString=EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		System.out.println("The response of API is: "+responseString);
		JSONObject responseJson=new JSONObject(responseString);
		System.out.println("The response of API is: "+responseJson.toString());
		
		//Json to java (UNMARSHALLING)
		Users usersResObj=mapper.readValue(responseString, Users.class); //Actual users obj
		System.out.println(usersResObj);
		
		//print
		Assert.assertTrue(users.getName().equals(usersResObj.getName()));
		Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));
		System.out.println(usersResObj.getId());
		System.out.println(usersResObj.getCreatedAt());
	}
	
}
