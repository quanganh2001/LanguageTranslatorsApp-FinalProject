package com.example.englishhindidictionary.langauge_translator.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;
import com.example.englishhindidictionary.langauge_translator.utildata.GetSetClass;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "thesaurus_en_hi_en.db";
    private static String DB_NAME = "thesaurus_en_hi_en.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String ENG_MEANING = "meanings";
    private static final String ENG_TABLE = "eng_table";
    private static final String ENG_WORD = "word";
    private static final String ENG_WORD1 = "englishword";
    private static final String FAVOURITE = "favourite";
    private static final String HIN_MEANING = "meanings_hindi";
    private static final String HIN_WORD = "trans_hindi";
    private static final String HIS_EnWORD = "his_enWord";
    private static final String HIS_HinWORD = "his_hWord";
    private static final String HIS_ID = "his_id";
    private static final String HIS_TABLE = "his_table";

    static Context context;
    private String DB_PATH = "/data/data/com.example.englishhindidictionary/databases/";


    private SQLiteDatabase f206db;

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public Database(Context context2) {
        super(context2, DB_NAME, (SQLiteDatabase.CursorFactory) null, 33);
        context = context2;
    }

    public boolean createDataBase() throws IOException {
        boolean checkDataBase = checkDataBase();
        if (!checkDataBase) {
            getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException unused) {
                throw new Error("Error copying Database");
            }
        }
        return checkDataBase;
    }

    private boolean checkDataBase() {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = SQLiteDatabase.openDatabase(this.DB_PATH + DB_NAME, (SQLiteDatabase.CursorFactory) null, 0);
        } catch (SQLiteException unused) {
        }
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        if (sQLiteDatabase != null) {
            return true;
        }
        return false;
    }

    private void copyDataBase() throws IOException {
        InputStream open = context.getAssets().open(DB_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(this.DB_PATH + DB_NAME);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
        }
    }

    public void openDataBase() throws SQLException {
        try {
            createDataBase();
        } catch (IOException e) {

            e.printStackTrace();
        }
        this.f206db = SQLiteDatabase.openDatabase(this.DB_PATH + DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public ArrayList<GetSetClass> SearchEnglishWord(String str) {
        ArrayList<GetSetClass> arrayList = new ArrayList<>();
        Cursor rawQuery = this.f206db.rawQuery("Select englishword,id FROM eng_table WHERE englishword LIKE '" + str.toLowerCase(Locale.getDefault()) + "%'", (String[]) null);
        if (rawQuery.moveToFirst()) {
            do {
                GetSetClass getSetClass = new GetSetClass();
                getSetClass.setId(rawQuery.getInt(rawQuery.getColumnIndex("id")));
                getSetClass.setEngWord(rawQuery.getString(rawQuery.getColumnIndex(ENG_WORD1)));
                arrayList.add(getSetClass);
            } while (rawQuery.moveToNext());
        } else {
            rawQuery.close();
        }
        return arrayList;
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
        if (Build.VERSION.SDK_INT >= 16) {
            sQLiteDatabase.disableWriteAheadLogging();
        }
    }

    public ArrayList<GetSetClass> GetMeaningsByEngWord(int i) {
        try {
            openDataBase();
        } catch (SQLException e) {
            Toast.makeText(context, "Data Base are not opened" + e, 0).show();
        }
        ArrayList<GetSetClass> arrayList = new ArrayList<>();
        Cursor rawQuery = this.f206db.rawQuery("Select word,meanings,favourite FROM tbl_thesaurus WHERE _id = '" + i + "'", (String[]) null);
        if (rawQuery.moveToFirst()) {
            do {
                GetSetClass getSetClass = new GetSetClass();
                getSetClass.setEngWord(rawQuery.getString(rawQuery.getColumnIndex(ENG_WORD)));
                getSetClass.setEngMeaning(rawQuery.getString(rawQuery.getColumnIndex(ENG_MEANING)));
                getSetClass.setFavourite(rawQuery.getInt(rawQuery.getColumnIndex(FAVOURITE)));
                arrayList.add(getSetClass);
            } while (rawQuery.moveToNext());
        } else {
            rawQuery.close();
        }
        this.f206db.close();
        return arrayList;
    }

    public ArrayList<GetSetClass> GetMeaningsByHinWord(int i) {
        try {
            openDataBase();
        } catch (SQLException e) {
            Toast.makeText(context, "Data Base are not opened" + e, 0).show();
        }
        ArrayList<GetSetClass> arrayList = new ArrayList<>();
        Cursor rawQuery = this.f206db.rawQuery("Select trans_hindi,meanings_hindi,favourite FROM tbl_thesaurus WHERE _id = '" + i + "'", (String[]) null);
        if (rawQuery.moveToFirst()) {
            do {
                GetSetClass getSetClass = new GetSetClass();
                getSetClass.setHinWord(rawQuery.getString(rawQuery.getColumnIndex(HIN_WORD)));
                getSetClass.setHinMeaning(rawQuery.getString(rawQuery.getColumnIndex(HIN_MEANING)));
                getSetClass.setFavourite(rawQuery.getInt(rawQuery.getColumnIndex(FAVOURITE)));
                arrayList.add(getSetClass);
            } while (rawQuery.moveToNext());
        } else {
            rawQuery.close();
        }
        this.f206db.close();
        return arrayList;
    }

    public long InsertHis(int i, String str, String str2) {
        this.f206db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HIS_ID, Integer.valueOf(i));
        contentValues.put(HIS_EnWORD, str);
        contentValues.put(HIS_HinWORD, str2);
        return this.f206db.insert(HIS_TABLE, (String) null, contentValues);
    }

    public void closeDB() {
        this.f206db.close();
    }
}
