package com.mindblots.mybabyname;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class nameAdapter extends BaseAdapter {

	private Activity activity;
	private List<NameTable> namesList;

	public nameAdapter(Activity a, List<NameTable> listNames) {
		activity = a;
		namesList = listNames;

	}

	public int getCount() {
		return namesList.size();
	}

	public NameTable getItem(int position) {
		return namesList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView txtViewBabyName;
		public TextView txtViewMeaning;
		public ImageView imgIcon;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {

			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.name_list_item, null);

			view.txtViewBabyName = (TextView) convertView
					.findViewById(R.id.babyName);
			view.txtViewMeaning = (TextView) convertView
					.findViewById(R.id.meaning);
			view.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		view.txtViewBabyName.setText(namesList.get(position).BabyName
				.toString());
		view.txtViewMeaning.setText(namesList.get(position).Meaning);
		if (namesList.get(position).Gender == 'M') {
			view.imgIcon.setImageResource(R.drawable.m);
			view.txtViewBabyName.setTextColor(-16776961);
		} else {
			view.imgIcon.setImageResource(R.drawable.f);
			view.txtViewBabyName.setTextColor(Color.parseColor("#DC143C"));
		}
		return convertView;
	}
}
