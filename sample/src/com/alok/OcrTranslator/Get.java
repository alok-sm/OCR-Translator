package com.alok.OcrTranslator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Get 
{
	public String getJSONFromUrl(String url)
	{
	    String resultString="";
	    HttpResponse response;
	    try 
	    {
	        HttpClient httpclient = new DefaultHttpClient();
	        response = httpclient.execute(new HttpGet(url));
	        HttpEntity entity = response.getEntity();
	        resultString = EntityUtils.toString(entity);
	    } 
	    catch (Exception e) 
	    {
	    }
	    return resultString;
	}
}