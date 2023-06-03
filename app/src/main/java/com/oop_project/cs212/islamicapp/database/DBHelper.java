package com.oop_project.cs212.islamicapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "aafwathakkir";

    public static final String TABLE_ARABIC = "arabic";
    public static final String TABLE_ENGLISH = "english";
    public static final String TABLE_URDU = "urdu";

    public static final String COL_ID = "id";
    public static final String COL_ATHKAR = "athkar";
    public static final String COL_TAG = "tag";
    public static final String COL_ATHKAR_ID = "athkar_id";

//    public static final String AMHARIC_QUERY = "Create Table "
//            +TABLE_AMHARIC+" ( "
//            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
//            +COL_ATHKAR_ID+" Integer not null, "
//            +COL_ATHKAR+" TEXT, "
//            +COL_TAG+" TEXT );";

    public static final String ARABIC_QUERY = "Create Table "
            +TABLE_ARABIC+" ( "
            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
            +COL_ATHKAR_ID+" Integer not null, "
            +COL_ATHKAR+" TEXT, "
            +COL_TAG+" TEXT );";

//    public static final String AZERBAIJANI_QUERY = "Create Table "
//            +TABLE_AZERBAIJANI+" ( "
//            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
//            +COL_ATHKAR_ID+" Integer not null, "
//            +COL_ATHKAR+" TEXT, "
//            +COL_TAG+" TEXT );";
//
//    public static final String AZERI_QUERY = "Create Table "
//            +TABLE_AZERI+" ( "
//            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
//            +COL_ATHKAR_ID+" Integer not null, "
//            +COL_ATHKAR+" TEXT, "
//            +COL_TAG+" TEXT );";
//
//    public static final String BEHASA_MALAYU_QUERY = "Create Table "
//            +TABLE_BEHASA_MALAYU+" ( "
//            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
//            +COL_ATHKAR_ID+" Integer not null, "
//            +COL_ATHKAR+" TEXT, "
//            +COL_TAG+" TEXT );";
//
//    public static final String BENGALI_QUERY = "Create Table "
//            +TABLE_BENGALI+" ( "
//            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
//            +COL_ATHKAR_ID+" Integer not null, "
//            +COL_ATHKAR+" TEXT, "
//            +COL_TAG+" TEXT );";
//
//    public static final String CHINESE_SIMPLIFIED_QUERY = "Create Table "
//            +TABLE_CHINESE_SIMPLIFIED+" ( "
//            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
//            +COL_ATHKAR_ID+" Integer not null, "
//            +COL_ATHKAR+" TEXT, "
//            +COL_TAG+" TEXT );";
//
//    public static final String CHINESE_TRADITIONAL_QUERY = "Create Table "
//            +TABLE_CHINESE_TRADITIONAL+" ( "
//            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
//            +COL_ATHKAR_ID+" Integer not null, "
//            +COL_ATHKAR+" TEXT, "
//            +COL_TAG+" TEXT );";

    public static final String ENGLISH_QUERY = "Create Table "
            +TABLE_ENGLISH+" ( "
            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
            +COL_ATHKAR_ID+" Integer not null, "
            +COL_ATHKAR+" TEXT, "
            +COL_TAG+" TEXT );";


    public static final String URDU_QUERY = "Create Table "
            +TABLE_URDU+" ( "
            +COL_ID+" Integer not null PRIMARY KEY AUTOINCREMENT, "
            +COL_ATHKAR_ID+" Integer not null, "
            +COL_ATHKAR+" TEXT, "
            +COL_TAG+" TEXT );";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(AMHARIC_QUERY);
       db.execSQL(ARABIC_QUERY);
//        db.execSQL(AZERBAIJANI_QUERY);
//        db.execSQL(AZERI_QUERY);
//        db.execSQL(BEHASA_MALAYU_QUERY);
//        db.execSQL(BENGALI_QUERY);
//        db.execSQL(CHINESE_SIMPLIFIED_QUERY);
//        db.execSQL(CHINESE_TRADITIONAL_QUERY);
        db.execSQL(ENGLISH_QUERY);
//        db.execSQL(FRENCH_QUERY);
//        db.execSQL(GERMAN_QUERY);
//        db.execSQL(HINDI_QUERY);
//        db.execSQL(INDONESIAN_QUERY);
//        db.execSQL(MALAY_QUERY);
//        db.execSQL(PASHTO_QUERY);
//        db.execSQL(PERSIAN_QUERY);
//        db.execSQL(RUSSIAN_QUERY);
//        db.execSQL(SPANISH_QUERY);
//        db.execSQL(TURKISH_QUERY);
        db.execSQL(URDU_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        db.execSQL("Drop table if exists '" +AMHARIC_QUERY+"' ");
          db.execSQL("Drop table if exists '" +ARABIC_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +AZERBAIJANI_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +AZERI_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +BEHASA_MALAYU_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +BENGALI_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +CHINESE_SIMPLIFIED_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +CHINESE_TRADITIONAL_QUERY+"' ");
          db.execSQL("Drop table if exists '" +ENGLISH_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +FRENCH_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +GERMAN_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +HINDI_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +INDONESIAN_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +MALAY_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +PASHTO_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +PERSIAN_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +RUSSIAN_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +SPANISH_QUERY+"' ");
//        db.execSQL("Drop table if exists '" +TURKISH_QUERY+"' ");
          db.execSQL("Drop table if exists '" +URDU_QUERY+"' ");
    }
}
