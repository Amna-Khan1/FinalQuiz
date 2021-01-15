package com.example.finalquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseContract {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseContract(Context context) {
        this.context = context;
        open();
    }

    public DatabaseContract open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    //Insert
    public void insertQuestionList(List<QuestionModel> authoritiesAndGuardiansModels) {
        String tableName = DatabaseHelper.table_question;
        try {
            database.delete(tableName, "", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        for (QuestionModel itemModel : authoritiesAndGuardiansModels) {
            ContentValues contentValues = new ContentValues();
            String json = new Gson().toJson(itemModel);
            Map<String, Object> retMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {
            }.getType());
            for (String key : retMap.keySet()) {
                contentValues.put(key, retMap.get(key).toString());
            }
            try {
                long result=database.insert(tableName, null, contentValues);
                //Toast.makeText(context, "Result: "+result, Toast.LENGTH_SHORT).show();
            } catch (SQLiteException e) {

            }
        }
    }
    public void removeDataFromTable(String tableName)
    {
        try {
            database.delete(tableName, "", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<QuestionModel> showAllQuestion() {
        List<QuestionModel> quickCallModels = new ArrayList<>();
        String query = "Select * from "+ DatabaseHelper.table_question;
        try {
            Cursor cursors = database.rawQuery(query, null);
            cursors.moveToFirst();
            while (cursors != null) {
                JSONObject json = new JSONObject();
                for (int i = 0; i < cursors.getColumnCount(); i++) {
                    json.put(cursors.getColumnName(i), JSONObject.wrap(cursors.getString(i)));
                }
                QuestionModel questionModel=new Gson().fromJson(json.toString(),QuestionModel.class);;
                quickCallModels.add(questionModel);
                cursors.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quickCallModels;
    }

    public void deleteAllTables()
    {
        try {
            String[] tables= DatabaseHelper.tables_List;
            for (String table : tables) {
                String dropQuery = "DROP TABLE IF EXISTS '" + table+ "'";
                database.execSQL(dropQuery);
            }
        } catch (Exception e)
        {
            Log.e("SQL Delete all table",e.getMessage());
        }
    }

}
