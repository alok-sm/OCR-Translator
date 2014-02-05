package com.alok.OcrTranslator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

//import com.example.test1.R;

import abbyy.ocrsdk.android.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.RecognizerIntent;
public class ResultsActivity extends Activity 
{
	protected static final int RESULT_SPEECH = 1;
	String outputPath;
	TextView tv2;
	String result;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_result);
		//tv = new TextView(this);
		//setContentView(tv);
		tv2 = (TextView)findViewById(R.id.tv);
		String imageUrl = "unknown";
		
		Bundle extras = getIntent().getExtras();
		if( extras != null) 
		{
			imageUrl = extras.getString("IMAGE_PATH" );
			outputPath = extras.getString( "RESULT_PATH" );
		
		// Starting recognition process
		new AsyncProcessTask(this).execute(imageUrl, outputPath);
		}
		else
		{
			getspeech();
		}
	           
       
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        switch (requestCode) {
        case RESULT_SPEECH: {
            if (resultCode == RESULT_OK && null != data) {
 
                ArrayList<String> text = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
 
               // tv.setText(text.get(0));
            AsyncGetTask a=new AsyncGetTask();
            String resultEng = text.get(0);
            a.execute(resultEng,"hi");
            
            result= "";
            try{result=a.get();}
            catch(Exception e){}
            tv2.setText(result);
            }
            break;
        }
 
        }
    }
	 public void getspeech(View v) {
		 
         getspeech();
     }
	 public void getspeech() {
         Intent intent = new Intent(
                 RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

         intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

         try {
             startActivityForResult(intent, RESULT_SPEECH);
             tv2.setText("");
         } catch (ActivityNotFoundException a) {
             Toast t = Toast.makeText(getApplicationContext(),
                     "Opps! Your device doesn't support Speech to Text",
                     Toast.LENGTH_SHORT);
             t.show();
         }
     }

	public void updateResults() 
	{
		try 
		{
			StringBuffer contents = new StringBuffer();

			FileInputStream fis = openFileInput(outputPath);
			Reader reader = new InputStreamReader(fis, "UTF-8");
			BufferedReader bufReader = new BufferedReader(reader);
			String text = null;
			while ((text = bufReader.readLine()) != null) 
			{
				contents.append(text).append(System.getProperty("line.separator"));
			}
//			result;
			String res = contents.toString();
			result = res.replaceAll("\n", "$");
			AsyncGetTask tast = new AsyncGetTask();
			tast.execute(result, "hi");
			result = tast.get();
			res = result.replaceAll("$","\n");
			result = res;
			tv2.setText(result);
//			speak(result,"hi");
		} 
		catch (Exception e) 
		{
			tv2.setText("Error");
		}
	}
	public void callCam(View v)
	{
		Intent i = new Intent(this,MainActivity.class);
		i.putExtra("call cam", true);
		startActivity(i);
	}
	public void sound(View v)
	{
		speak(result,"hi");
	}
	public void speak(String text,String language)
	{
		try
      {
			MediaPlayer mp = new MediaPlayer();
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			String url1 = "http://translate.google.com/translate_tts?ie=UTF-8&q=";
			String url2 = "&tl=";
			mp.setDataSource(url1+text+url2+language);
			mp.prepare();
			mp.start();
		}
		catch(Exception e){}
	}
}
