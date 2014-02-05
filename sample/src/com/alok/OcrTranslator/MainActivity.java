package com.alok.OcrTranslator;
import abbyy.ocrsdk.android.R;
import android.os.Bundle;
import android.app.Activity;
import android.net.*;
import java.io.*;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
public class MainActivity extends Activity {
	private final int TAKE_PICTURE = 0;
	private final int SELECT_FILE = 1; 	
	private String resultUrl = "result.txt";	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bundle extras = getIntent().getExtras();
		if(extras!=null) captureImageFromCamera();
	}
	public void captureImageFromSdCard( View view ) {
    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    	intent.setType("image/*");

    	startActivityForResult(intent, SELECT_FILE);
    }
	
	public static final int MEDIA_TYPE_IMAGE = 1;

	private static Uri getOutputMediaFileUri(){
	      return Uri.fromFile(getOutputMediaFile());
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "test");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            return null;
	        }
	    }
	    File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "image.jpg" );
	    return mediaFile;
	}
	public void camera(View v)
	{
		captureImageFromCamera();
	}
    public void textspeech(View v)
    {
//    	ResultsActivity a=new ResultsActivity();
//    	a.getspeech();
    	Intent i = new Intent(this,ResultsActivity.class);
    	startActivity(i);
    }
    
    public void captureImageFromCamera() {
    	Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    	Uri fileUri = getOutputMediaFileUri(); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
        
        startActivityForResult(intent, TAKE_PICTURE);
    } 
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK)
			return;
		
		String imageFilePath = null;

		switch (requestCode) {
		case TAKE_PICTURE:
			imageFilePath = getOutputMediaFileUri().getPath();
			break;
		case SELECT_FILE: {
			Uri imageUri = data.getData();

			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cur = managedQuery(imageUri, projection, null, null, null);
			cur.moveToFirst();
			imageFilePath = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DATA));
			}
			break;
		}

		//Remove output file
		deleteFile(resultUrl);
		
        Intent results = new Intent( this, ResultsActivity.class);
    	results.putExtra("IMAGE_PATH", imageFilePath);
    	results.putExtra("RESULT_PATH", resultUrl);
    	startActivity(results);
    }    
}