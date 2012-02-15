package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class ImageUploader {

	public static String upload(String urlServer, String fileName){
		
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		Bitmap bm = BitmapFactory.decodeFile(fileName);
//		bm.compress(CompressFormat.JPEG, 100, bos);

		StringBuilder output = new StringBuilder();
		File file = new File(fileName);
		try {
			HttpClient client = new DefaultHttpClient(); 
			HttpPost post = new HttpPost(urlServer); 
			ContentBody bin = new FileBody(file);
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
			reqEntity.addPart("file", bin);
			post.setEntity(reqEntity);  
			HttpResponse response = client.execute(post);  

			InputStream is = response.getEntity().getContent();

			BufferedReader br = new BufferedReader(new InputStreamReader(is,"euc-kr"));
			String str = null;
			while( (str = br.readLine()) != null) {
				output.append(str + "\n");
			}
			br.close();
		} catch (Exception e) {
			Log.e("myDebug", "sendFile", e);
		}
		return output.toString().trim();
	}
	
}
