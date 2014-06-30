package com.mindblots.mybabyname;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {

	private GridviewAdapter mAdapter;
	private ArrayList<String> listAlphabet;
	private GridView gridView;
	private static final String NAMES_DB = "names_db";
	private SchemaHelper dbHelper;
	private ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbHelper = new SchemaHelper(getBaseContext());
		try {
			ReadPref();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prepareList();

		mAdapter = new GridviewAdapter(this, listAlphabet);

		// Set custom adapter to gridview
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(mAdapter);

		// Implement On Item click listener
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {

				Intent myIntent = new Intent(MainActivity.this, babyNames.class);
				myIntent.setType("text/plain");
				myIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						mAdapter.getItem(position));
				startActivity(myIntent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void ReadPref() throws IOException {
		SharedPreferences sp = getSharedPreferences(NAMES_DB,
				Context.MODE_PRIVATE);
		boolean hasVisited = sp.getBoolean("hasVisited", false);
		if (!hasVisited) {
			progressBarHandler = new Handler();
			progressBar = new ProgressDialog(this);
			progressBar.setCancelable(true);
			progressBar.setTitle("Loading names...1 time job");
			progressBar.setMessage("One time loading,\nplease wait...");
			progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressBar.setProgress(0);
			CSVReader reader = new CSVReader(getBaseContext());
			final List<NameTable> names = reader.GetNamesAndMeanings();
			progressBar.setMax(names.size());
			progressBar.show();
			new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						for (NameTable n : names) {
							dbHelper.InsertNames(n);
							++progressBarStatus;
							progressBarHandler.post(new Runnable() {
								public void run() {
									progressBar.setProgress(progressBarStatus);
								}
							});
						}
						Thread.sleep(1000);

						progressBar.dismiss();

					} catch (Exception e) {
					}

				}
			}).start();
			Editor e = sp.edit();
			e.putBoolean("hasVisited", true);
			e.commit();

		}
	}

	public void prepareList() {
		listAlphabet = new ArrayList<String>();

		char[] alphabets = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z' };
		for (char c : alphabets) {
			listAlphabet.add(c + "");
		}

	}

	public boolean onCreateOptionsMenu1(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		startActivity(new Intent(this, SearchActivity.class));
		return false;
	}
}
