package com.konkr.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyClass {

	public static void HideKeyboard(Context context, View view) {
		if (view != null) {
//            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			view.clearFocus();
			InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	
	public String getCurrentTimeStamp() {
		  String currentTime = null;
		  try {
	      SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	      Date now = new Date();
	      String strDate = sdfDate.format(now);
	      Date date;
		  date = (Date)sdfDate.parse(strDate);
		  currentTime = String.valueOf(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		} 

	      return currentTime;
	  }
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}

	public void clearCache(Context context)
	{
		try {
			String root = Environment.getExternalStorageDirectory().toString();
			File files = new File(root + "/" + GlobalData.APP_NAME, "Chunks");
		    for (File file : files.listFiles())
		    {
		        file.delete();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public File makeDir(Context context){
   	 File cacheDir;
//		    if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		String root = Environment.getExternalStorageDirectory().toString();
	            cacheDir=new File(root + "/" + GlobalData.APP_NAME, "Chunks");
//	        else
//	            cacheDir=context.getCacheDir();
		    if(!cacheDir.exists())
		    	cacheDir.mkdirs();
		return cacheDir;
   }
	
	public Bitmap getRoundBitmap(Bitmap myBitmap){
		Bitmap circleBitmap = null;
		try{
			circleBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.ARGB_8888);
			BitmapShader shader = new BitmapShader(myBitmap,  TileMode.CLAMP, TileMode.CLAMP);
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			final int color = 0xff424242;
			 paint.setColor(color);
			paint.setShader(shader);
//			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			Canvas c = new Canvas(circleBitmap);
			c.drawCircle(myBitmap.getWidth()/2, myBitmap.getHeight()/2, myBitmap.getWidth()/2, paint);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return circleBitmap;
  }
	
}
