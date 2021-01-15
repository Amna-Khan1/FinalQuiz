package com.example.finalquiz;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "quizApp.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase database;

    public static String table_question = "question";

    public static String[] tables_List = {table_question};

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        database = getWritableDatabase();
        createTables();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void createTables() {
        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS " + table_question + " (\n" +
                    "  `c_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    " `question` TEXT,\n" +
                    " `answer` TEXT,\n" +
                    " `option1` TEXT,\n" +
                    " `option2` TEXT,\n" +
                    " `option3` TEXT,\n" +
                    " `option4` TEXT\n" +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTable(String tableName) {
        database.execSQL("DROP TABLE " + tableName + ";");
        createTables();
    }


}
