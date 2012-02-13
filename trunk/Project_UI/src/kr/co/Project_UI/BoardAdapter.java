package kr.co.Project_UI;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardAdapter extends ArrayAdapter<BoardVO> {

	ArrayList<BoardVO> items;
	int textViewResourceId;
	LayoutInflater vi;
	
	public BoardAdapter(Context context, int textViewResourceId,
			ArrayList<BoardVO> items) {
		super(context, textViewResourceId, items);
		Log.d("mydebug", "생성자");
		this.items = items;
		this.textViewResourceId = textViewResourceId;
		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	//하나의 항목이 들어갈때 마다 호출됨
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View v = null;
		if(convertView != null) {
			v = convertView;
		}
		else {
			v = vi.inflate(R.layout.boardui, null);
		}
		
		BoardVO boardVO = items.get(position);
		
		ImageView img 	 = (ImageView)v.findViewById(R.id.img);
		TextView title = (TextView)v.findViewById(R.id.title);
		TextView writer  = (TextView)v.findViewById(R.id.writer);
		TextView likeCnt  = (TextView)v.findViewById(R.id.likeCnt);
		img.setImageResource( R.drawable.icon );
		title.setText(boardVO.getTitle());
		writer.setText(boardVO.getWriter());
		likeCnt.setText(boardVO.getLikeCnt()+"");
		return v;
	}
}
