package com.alok.OcrTranslator;


import java.net.URLEncoder;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class AsyncGetTask extends AsyncTask<String, Void, String> 
{
	private String url1 = "http://translator-api.herokuapp.com/translate?from=en&to=";
	private String url2 = "&text%5B%5D=";
	private String url = "";
	String result = "";
	@Override
	protected String doInBackground(String... params) 
	{
		try{url = url1+params[1]+url2+URLEncoder.encode(params[0], "UTF-8");}
		catch(Exception e){}
//		MainActivity.t.setText(url);
		Get j = new Get();
		try 
		{
			result = j.getJSONFromUrl(url);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		int start = result.indexOf(':')+2;
		int end;
		for(end = start;end<result.length();end++)
		{
			if(result.charAt(end)=='\"')break;
		}
		result = result.substring(start,end);
		result = result.replaceAll("/[^0-9a-zA-Z ,.]+/", "");
		return result;
	}
	

}
