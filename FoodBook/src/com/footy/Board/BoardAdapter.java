package com.footy.Board;

import java.util.ArrayList;

import com.footy.Facebook.FacebookInfo;
import com.footy.FoodBook.R;
import com.footy.Util.ImageDownloader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardAdapter extends ArrayAdapter<BoardVO> {
	ImageDownloader imageDownloader = new ImageDownloader();
	ArrayList<BoardVO> items;
	int textViewResourceId;
	LayoutInflater vi;
	
	public BoardAdapter(Context context, int textViewResourceId,
			ArrayList<BoardVO> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.textViewResourceId = textViewResourceId;
		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	//�ϳ��� �׸��� ���� ���� ȣ���
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
		imageDownloader.download("http://graph.facebook.com/" + boardVO.getWriterId() + "/picture?type=normal", img);
		title.setText(boardVO.getTitle());
		writer.setText(boardVO.getWriter());
		likeCnt.setText(boardVO.getLikeCnt()+"");
		return v;
	}
	
}
