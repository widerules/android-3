package kr.co.Project_UI;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class BoardHelper {
	
	
	public BoardHelper() {
		// nothing to do yet
	}

	public ArrayList<BoardVO> getList() {
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		String urlStr = "http://115.145.172.123:8000/android-3/getList.jsp";
		String jsonStr = call(urlStr);
		try {
			JSONObject jobj = new JSONObject(jsonStr);
			JSONArray jarr = jobj.getJSONArray("data");
			for(int i=0; i<jarr.length(); i++) {
				jobj = (JSONObject)jarr.get(i);
				int postNo = jobj.getInt("post_no");
				String writer = jobj.getString("writer");
				String writerId = jobj.getString("writer_id");
				String title = jobj.getString("title");
				int likeCnt = jobj.getInt("like_cnt");
				String regDate = jobj.getString("reg_date");
				list.add(new BoardVO(postNo, writer, writerId, title, null, likeCnt, regDate));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private String call(String urlStr) {
    	StringBuilder output = new StringBuilder();
    	try {
    		HttpClient client = new DefaultHttpClient();
    		HttpPost post = new HttpPost(urlStr);
    		HttpResponse response = client.execute(post);
    		InputStream is = response.getEntity().getContent();
    		
    		BufferedReader br = new BufferedReader(new InputStreamReader(is,"euc-kr"));
    		String str = null;
    		while( (str = br.readLine()) != null) {
    			output.append(str + "\n");
    		}
    		br.close();
    		
		} catch (Exception e) {
			Log.e("myDebug", e.toString());
		}
    	return output.toString();
    }
    
    private void parse(String str) {
    	
    }
}
