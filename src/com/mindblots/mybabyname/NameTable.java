package com.mindblots.mybabyname;

import java.util.Date;

public class NameTable {
	public static final String ID = "_id";
	public static final String NAME = "Baby_Name";
	public static final String GENDER = "Gender";
	public static final String MEANING = "Meaning";
	public static final String VIEW_COUNT = "View_Count";
	public static final String IS_FAVORITE = "isFavorite";
	public static final String FAVORITE_DATE = "Favorite_Date";
	public static final String LAST_VIEW_DATE = "Last_View_Date";
	public static final String TABLE_NAME = "BabyNames";

	public String BabyName;
	public char Gender;
	public String Meaning;
	public int ViewCount;
	public boolean isFavorite;
	public Date FavoriteDate;
	public Date LastViewDate;

}
