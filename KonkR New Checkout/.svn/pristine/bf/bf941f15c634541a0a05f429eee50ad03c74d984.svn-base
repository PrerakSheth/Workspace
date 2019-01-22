package com.konkr.Utils;

import android.content.Context;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FileHandeling {

	Context context;
	
	public FileHandeling(Context context_){
		context = context_;
	}
	
//	public String getRealPathFromURI(Uri uri) {
//		Cursor cursor = null;
//		int idx = 0;
//		try{
//			cursor = context.getContentResolver().query(uri, null, null, null, null);
//		    cursor.moveToFirst();
//		    idx = cursor.getColumnIndex(Images.ImageColumns.DATA);
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}
//	    return cursor.getString(idx);
//	}
//
//	public void splitFile(File f) throws Exception {
//		try{
//			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
//	        FileOutputStream out;
//	        int partCounter = 1;
//	        int sizeOfFiles = 1024 * 1024;// 1MB
//	        byte[] buffer = new byte[sizeOfFiles];
//	        int tmp = 0;
//	        while ((tmp = bis.read(buffer)) > 0)
//			{
//	        	MyClass myClass = new MyClass();
//	            File newFile=new File(myClass.makeDir(context), "Part_"+partCounter++);
//	            newFile.createNewFile();
//	            out = new FileOutputStream(newFile);
//	            out.write(buffer,0,tmp);
//	            out.close();
//	        }
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//    }
//
//	public String getBase64FromBitmap(Bitmap bitmap){
//		String base64 = "";
//		try{
//			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//			bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//			byte[] byteArray = byteArrayOutputStream .toByteArray();
//			base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}
//		return base64;
//	}
	
	public String getBase64FromByteArray(byte[] byteArray){
		String base64 = "";
		try{
			base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return base64;
	}
	
	public byte[] convertFileToByteArray(File file) throws Exception
	{
		ByteArrayOutputStream baos = null;
		try
		{
			FileInputStream fis = new FileInputStream(file);
		    baos = new ByteArrayOutputStream();
		    byte[] buff = new byte[1024*1024];
		    int i = Integer.MAX_VALUE;
		    while ((i = fis.read(buff, 0, buff.length)) > 0)
			{
		        baos.write(buff, 0, i);
		    }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	    return baos.toByteArray(); // be sure to close InputStream in calling function
	}
	
//	public static byte[] convertStreamToByteArray(InputStream is) throws Exception {
//		ByteArrayOutputStream baos = null;
//		try{
//			baos = new ByteArrayOutputStream();
//		    byte[] buff = new byte[10240];
//		    int i = Integer.MAX_VALUE;
//		    while ((i = is.read(buff, 0, buff.length)) > 0) {
//		        baos.write(buff, 0, i);
//		    }
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}
//
//	    return baos.toByteArray(); // be sure to close InputStream in calling function
//	}
	
	public ArrayList<String> splitFileTobase64(File f) throws Exception
	{
		ArrayList<String> base64 = null;
		try{
			base64 = new ArrayList<String>();
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
	        FileOutputStream out;
	        int partCounter = 0;
	        int sizeOfFiles = 1024 * 1024;// 1MB
//			int sizeOfFiles = 512 * 512;// 0.5MB
	        byte[] buffer = new byte[sizeOfFiles];
	        int tmp = 0;
	        while ((tmp = bis.read(buffer)) > 0) {
	        	MyClass myClass = new MyClass();
	        	int temp = partCounter++;
	            File newFile=new File(myClass.makeDir(context), "ChunkPart_"+temp);
	            base64.add("ChunkPart_"+temp);
	            newFile.createNewFile();
	            out = new FileOutputStream(newFile);
	            out.write(buffer,0,tmp);
	            out.close();
	        }

//			byte[] bytesArray = new byte[(int) f.length()];
//			String value = Base64.encodeToString(bytesArray, Base64.DEFAULT).trim();
//			LogM.LogV("Full Base64 " + value.length());

		}catch(Exception e){
			e.printStackTrace();
		}
		return base64;
    }
//	public ArrayList<String> wholeFileBase64(String filePath) {
//		ArrayList<String> chunk = new ArrayList<String>();
//		try {
//			File file = new File(filePath);
//			if (file.exists()) {
//				System.gc();
//				String base64_ = getBase64FromByteArray(convertFileToByteArray(file));
////				writeToFile("main", base64_);
//
//				int fileLength = (int) file.length();
//				Log.v("FileSplittTag", "fileLength ::::: " + fileLength + " :::: " + base64_.length());
//
//				Log.v("FileSplittTag", "fileLength ::::: " + filePath.contains(".mp4"));
//
//				Log.v("FileSplittTag", "fileLength ::::: " + filePath.contains(".jpg"));
//
//				Log.v("FileSplittTag", "fileLength ::::: " + filePath.contains(".png"));
//
//				int fileSplit = 1;
//
//				if (filePath.contains(".mp4")) {
//					if (fileLength > 10000000) {
//						fileSplit = (int) fileLength / 10;
//					} else if (fileLength > 1000000) {
//						fileSplit = (int) fileLength / 8;
//					} else {
//						fileSplit = (int) fileLength / 5;
//					}
//				} else {
//					fileSplit = (int) fileLength / 5;
//				}
//
//				Log.v("FileSplittTag", "fileSplit ::::: " + fileSplit);
//
//				String[] tokens = Iterables.toArray(Splitter.fixedLength(fileSplit).split(base64_), String.class);
//				chunk = new ArrayList<String>(Arrays.asList(tokens));
//
//				Log.v("FileSplittTag", "chunk ::::: " + chunk.size());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return chunk;
//	}

//	public Bitmap getCompressBitmapFromPath(String filePathForOriginal){
//
//    	Bitmap temp = null;
//        try {
//
//        // Decode image size
//        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse("file://"+filePathForOriginal)), null, o);
//
//        // The new size we want to scale to
//        final int REQUIRED_SIZE = 200;
//
//        // Find the correct scale value. It should be the power of 2.
//        int width_tmp = o.outWidth, height_tmp = o.outHeight;
//        int scale = 1;
//        while (true) {
//            if (width_tmp / 2 < REQUIRED_SIZE
//                    || height_tmp / 2 < REQUIRED_SIZE) {
//                break;
//            }
//            width_tmp /= 2;
//            height_tmp /= 2;
//            scale *= 2;
//        }
//
//        // Decode with inSampleSize
//        BitmapFactory.Options o2 = new BitmapFactory.Options();
//        o2.inSampleSize = scale;
//        o2.inPreferredConfig = Config.RGB_565;
//        temp =  BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse("file://"+filePathForOriginal)), null, o2);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return temp;
//    }
//
//	public Bitmap getOriginalBitmapFromPath(String photoPath){
//		Bitmap bitmap = null;
//		try{
//			BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inPreferredConfig = Config.ARGB_8888;
//			bitmap = BitmapFactory.decodeFile(photoPath, options);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return bitmap;
//	}
//	public static Bitmap loadBitmapFromView(View  drawingCanvas) {
//		  /*drawingCanvas.setLayoutParams(new LinearLayout.LayoutParams(350, 300));
//		  drawingCanvas.measure(MeasureSpec.makeMeasureSpec(drawingCanvas.getLayoutParams().width, MeasureSpec.EXACTLY),
//		    MeasureSpec.makeMeasureSpec(drawingCanvas.getLayoutParams().height, MeasureSpec.EXACTLY));
//		  drawingCanvas.layout(10, 100, drawingCanvas.getMeasuredWidth() + 100, drawingCanvas.getMeasuredHeight() + 100);*/
//		  Bitmap overAllDrawing = Bitmap.createBitmap(drawingCanvas.getWidth(), drawingCanvas.getHeight(), Config.ARGB_8888);
//		  Canvas canvas = new Canvas(overAllDrawing);
//		  drawingCanvas.setBackgroundColor(Color.WHITE);
//		 // canvas.drawColor(Color.WHITE);
//
//		/* Drawable bgDrawable =drawingCanvas.getBackground();
//		    if (bgDrawable!=null)
//		    {
//		        bgDrawable.draw(canvas);
//		    Log.e("drawing","drawing");
//		    }
//		    else
//		        canvas.drawColor(Color.WHITE);*/
//
//		  drawingCanvas.draw(canvas);
//		  return overAllDrawing;
//		}
//	public Uri getImageUri(Activity inContext, Bitmap inImage) {
//		  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//		  inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//		  String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//		  return Uri.parse(path);
//		}
}
