package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;



public class GetAPITest extends TestBase {

	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {

		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("ServiceURL");
		url = serviceUrl + apiUrl;


	}

	@Test(priority=1)
	public void getAPTestWithoutHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);

		// a. status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("status Code--->" + statusCode);

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200,"Status code is not 200");
		
		// b. Json String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API--->" + responseJson);
		
		
		//single value assertion
		//per page:
        String perPageValue=TestUtil.getValueByJPath(responseJson,"/per_page");
        System.out.println("Value of per page is--> "+perPageValue);
        Assert.assertEquals(perPageValue, "3");
        
        //total:
        String totalValue=TestUtil.getValueByJPath(responseJson,"/total");
        System.out.println("total is--> "+totalValue);
        Assert.assertEquals(totalValue, "12");
        
        String lastName=TestUtil.getValueByJPath(responseJson,"data[0]/last_name");
        String id=TestUtil.getValueByJPath(responseJson,"data[0]/id");
        String avatar=TestUtil.getValueByJPath(responseJson,"data[0]/avatar");
        String firstName=TestUtil.getValueByJPath(responseJson,"data[0]/first_name");
        
        System.out.println(lastName);
        System.out.println(id);
        System.out.println(avatar);
        System.out.println(firstName);
        
        
		// c. All headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();

		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}

		System.out.println("Haeders Array---->" + allHeaders);

	}
	
	@Test(priority=2)
	public void getAPTestWithHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		HashMap<String,String> headerMap=new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		closeableHttpResponse = restClient.get(url,headerMap);

		// a. status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("status Code--->" + statusCode);

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200,"Status code is not 200");
		
		// b. Json String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API--->" + responseJson);
		
		
		//single value assertion
		//per page:
        String perPageValue=TestUtil.getValueByJPath(responseJson,"/per_page");
        System.out.println("Value of per page is--> "+perPageValue);
        Assert.assertEquals(perPageValue, "3");
        
        //total:
        String totalValue=TestUtil.getValueByJPath(responseJson,"/total");
        System.out.println("total is--> "+totalValue);
        Assert.assertEquals(totalValue, "12");
        
        String lastName=TestUtil.getValueByJPath(responseJson,"data[0]/last_name");
        String id=TestUtil.getValueByJPath(responseJson,"data[0]/id");
        String avatar=TestUtil.getValueByJPath(responseJson,"data[0]/avatar");
        String firstName=TestUtil.getValueByJPath(responseJson,"data[0]/first_name");
        
        System.out.println(lastName);
        System.out.println(id);
        System.out.println(avatar);
        System.out.println(firstName);
        
        
		// c. All headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();

		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}

		System.out.println("Haeders Array---->" + allHeaders);

	}
}
