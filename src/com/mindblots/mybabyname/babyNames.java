package com.mindblots.mybabyname;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class babyNames extends Activity {
	private nameAdapter nAdapter;
	private List<NameTable> namesList;
	ListView list;
	private SchemaHelper dbHelper;
	private static final String TAG = "babyNames";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.name_list);
		dbHelper = new SchemaHelper(getBaseContext());
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		// Get data via the key
		String value1 = extras.getString(Intent.EXTRA_TEXT);
		if (value1 != null) {
			this.setTitle("Names starting from " + value1);
			Toast.makeText(babyNames.this, value1, Toast.LENGTH_SHORT).show();
			namesList = dbHelper.getBabyNamesBy(value1, 'A');
			list = (ListView) findViewById(R.id.namelist);

			nAdapter = new nameAdapter(this, namesList);

			try {

				list.setAdapter(nAdapter);
				list.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
					}
				});
			} catch (Exception e) {
				Log.i(TAG, "Exception " + e.getMessage());
			}
		}
	}

}
