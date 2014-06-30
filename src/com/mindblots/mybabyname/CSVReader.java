package com.mindblots.mybabyname;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class CSVReader {

	// private static final String TAG = "CSVReader";
	InputStream is;

	public CSVReader(Context context) throws IOException {

		is = context.getAssets().open("a.csv");
	}

	public List<NameTable> GetNamesAndMeanings() throws IOException {
		if (is != null) {

			InputStreamReader inputStreamReader = new InputStreamReader(is);

			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String receiveString = "";

			List<NameTable> names = new ArrayList<NameTable>();
			bufferedReader.readLine();
			while ((receiveString = bufferedReader.readLine()) != null) {
				String[] vals = receiveString.split(",");
				// Log.i(TAG, "CSVReader buffer " + receiveString);
				NameTable n = new NameTable();
				n.BabyName = vals[0];
				n.Meaning = vals[1].trim();
				n.Gender = vals[2].isEmpty() ? null : vals[2].equals("F") ? 'F'
						: 'M';
				names.add(n);

			}

			is.close();
			return names;
		}
		return null;
	}
}
