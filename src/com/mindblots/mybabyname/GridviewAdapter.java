package com.mindblots.mybabyname;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridviewAdapter extends BaseAdapter {
	private ArrayList<String> listAlphabet;
	private Activity activity;

	public GridviewAdapter(Activity activity, ArrayList<String> listAlphabet) {
		super();
		this.listAlphabet = listAlphabet;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listAlphabet.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return listAlphabet.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class ViewHolder {
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.alphabet_row, null);

			view.txtViewTitle = (TextView) convertView
					.findViewById(R.id.alphabetTxt);

			Typeface font = Typeface.createFromAsset(activity.getAssets(),
					"babybloc.ttf");
			view.txtViewTitle.setTypeface(font);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		view.txtViewTitle.setText(listAlphabet.get(position));

		return convertView;
	}
}