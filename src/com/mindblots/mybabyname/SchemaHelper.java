package com.mindblots.mybabyname;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SchemaHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "babyNames_data.db";
	private static final int DATABASE_VERSION = 1;

	final String TAG = "SchemaHelper";

	public SchemaHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// CREATE BABY NAMES TABLE
		db.execSQL("CREATE TABLE " + NameTable.TABLE_NAME + " (" + NameTable.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + NameTable.NAME
				+ " TEXT," + NameTable.GENDER + " TEXT," + NameTable.MEANING
				+ " TEXT," + NameTable.VIEW_COUNT + " INTEGER,"
				+ NameTable.LAST_VIEW_DATE + " TEXT," + NameTable.IS_FAVORITE
				+ " INTEGER," + NameTable.FAVORITE_DATE + " TEXT);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(";LOG_TAG", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + " , which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + NameTable.TABLE_NAME);
	}

	// WRAPPER METHOD FOR ADDING A NAME
	public long addName(String name, boolean gender, String meaning) {
		ContentValues cv = new ContentValues();
		cv.put(NameTable.NAME, name);
		cv.put(NameTable.GENDER, gender);
		cv.put(NameTable.MEANING, meaning);

		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.insert(NameTable.TABLE_NAME, NameTable.NAME, cv);
		return result;
	}

	public long InsertNames(NameTable n) {
		long result = -1;

		ContentValues cv = new ContentValues();
		cv.put(NameTable.NAME, n.BabyName);
		cv.put(NameTable.GENDER, n.Gender + "");
		cv.put(NameTable.MEANING, n.Meaning);
		cv.put(NameTable.VIEW_COUNT, 0);
		cv.put(NameTable.IS_FAVORITE, false);
		cv.put(NameTable.LAST_VIEW_DATE, "");
		SQLiteDatabase sd = getWritableDatabase();
		result = sd.insert(NameTable.TABLE_NAME, NameTable.NAME, cv);
		return result;

	}

	// GET ALL NAMES
	public Cursor getBabyName(int nameId) {
		SQLiteDatabase sd = getWritableDatabase();

		// WE ONLY NEED TO RETURN IDs
		String[] cols = new String[] { NameTable.ID };

		String[] selectionArgs = new String[] { String.valueOf(nameId) };

		Cursor c = sd.query(NameTable.TABLE_NAME, cols, NameTable.ID + "= ?",
				selectionArgs, null, null, null);
		return c;
	}

	public List<NameTable> getBabyNamesBy(String name, char gender) {
		SQLiteDatabase sd = getWritableDatabase();
		ArrayList<NameTable> list = new ArrayList<NameTable>();

		Cursor c;
		if (gender == 'M' || gender == 'F') {

			c = sd.rawQuery(
					"SELECT Baby_Name,Gender,Meaning FROM BabyNames WHERE  Baby_Name LIKE '"
							+ name + "%'" + " AND Gender LIKE '" + gender + "'",
					null);
		} else {
			c = sd.rawQuery(
					"SELECT Baby_Name,Gender,Meaning FROM BabyNames WHERE  Baby_Name LIKE '"
							+ name + "%'", null);
		}

		if (c != null) {
			if (c.moveToFirst()) {
				while (c.moveToNext()) {

					list.add(Parse(c.getString(0), c.getString(1),
							c.getString(2), 0, 0));
				}
			}

		}
		if (c != null && !c.isClosed()) {
			c.close();
		}
		return list;
	}

	public List<NameTable> getFavoriteBabyNamesBy(String name, boolean gender) {
		SQLiteDatabase sd = getWritableDatabase();
		ArrayList<NameTable> list = new ArrayList<NameTable>();

		Cursor c;

		c = sd.rawQuery(
				"SELECT Baby_Name,Gender,Meaning FROM BabyNames WHERE  isFavorite = 1  ORDER BY Favorite_Date",
				null);

		if (c != null)
			if (c.moveToFirst()) {
				while (c.moveToNext()) {

					list.add(Parse(c.getString(0), c.getString(1),
							c.getString(2), 0, 0));
				}
			}
		if (c != null && !c.isClosed()) {
			c.close();
		}
		return list;
	}

	public int AddBabyNameToFavorite(int nameId) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NameTable.IS_FAVORITE, 1);
		values.put(NameTable.FAVORITE_DATE, Calendar.getInstance().getTime()
				.toString());
		return db.update(NameTable.TABLE_NAME, values, NameTable.ID + " = ?",
				null);
	}

	public int RemoveBabyNameFromFavorite(int nameId) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NameTable.IS_FAVORITE, 0);
		values.put(NameTable.FAVORITE_DATE, Calendar.getInstance().getTime()
				.toString());
		return db.update(NameTable.TABLE_NAME, values, NameTable.ID + " = ?",
				null);
	}

	public int updateBabyNameViewCount(int nameId) {
		SQLiteDatabase db = this.getWritableDatabase();

		String[] cols = new String[] { NameTable.VIEW_COUNT };
		String[] selectionArgs = new String[] { String.valueOf(nameId) };

		Cursor c = db.query(NameTable.TABLE_NAME, cols, NameTable.ID + "= ?",
				selectionArgs, null, null, null);
		int viewCount = Integer.parseInt(c.getString(0));
		viewCount = viewCount + 1;

		ContentValues values = new ContentValues();
		values.put(NameTable.VIEW_COUNT, viewCount);
		values.put(NameTable.LAST_VIEW_DATE, Calendar.getInstance().getTime()
				.toString());
		return db.update(NameTable.TABLE_NAME, values, NameTable.ID + " = ?",
				null);
	}

	private NameTable Parse(String name, String gender, String meaning,
			int view_count, int isFavorite) {

		NameTable nt = new NameTable();

		nt.BabyName = name;
		nt.Gender = gender.equals("M") ? 'M' : 'F';

		nt.Meaning = meaning;
		nt.ViewCount = view_count;
		nt.isFavorite = isFavorite == 1 ? true : isFavorite == 0 ? false : null;

		return nt;
	}

}
