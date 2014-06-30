package com.mindblots.mybabyname;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SearchActivity extends Activity {
	private nameAdapter nAdapter;
	private List<NameTable> namesList;
	ListView list;
	private SchemaHelper dbHelper;
	private EditText inputSearch;
	private RadioGroup radioSexGroup;

	private static final String TAG = "SearchBabyNames";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_list);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
		dbHelper = new SchemaHelper(getBaseContext());

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				String selected = ((RadioButton) findViewById(radioSexGroup
						.getCheckedRadioButtonId())).getText() + "";
				char sex = selected.equals("Girl") ? 'F' : 'M';
				Toast.makeText(SearchActivity.this, selected,
						Toast.LENGTH_SHORT).show();
				namesList = dbHelper.getBabyNamesBy(inputSearch.getText()
						.toString(), sex);
				list = (ListView) findViewById(R.id.search_list_view);

				nAdapter = new nameAdapter(SearchActivity.this, namesList);

				try {

					list.setAdapter(nAdapter);

				} catch (Exception e) {
					Log.i(TAG, "Exception " + e.getMessage());
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
