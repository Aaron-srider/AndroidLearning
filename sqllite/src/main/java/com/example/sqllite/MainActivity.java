package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="tag";

    WordsDBHelper mDbHelper;

    SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView) findViewById(R.id.listview);
        registerForContextMenu(list);
        mDbHelper = new WordsDBHelper(this);
        writableDatabase = mDbHelper.getWritableDatabase();
        //ArrayList<Map<String, String>> items = getAll();
        //setWordsListView(items);

        Log.v(TAG, like().toString());

        List<WordContent.WordItem> all = getAll();
        System.out.println(all);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
    }

    public void insertOne() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", "apple");
        contentValues.put("meaning", "苹果");
        contentValues.put("sample", "i like apple");
        writableDatabase.insert("words", null, contentValues);
    }

    public List<WordContent.WordItem> getAll() {
        Cursor words = writableDatabase.query("words", null, null, null, null, null, null);
        List<WordContent.WordItem> wordList = new ArrayList<>();
        while (words.moveToNext()) {
            String id = words.getString(words.getColumnIndex("_id"));
            String word = words.getString(words.getColumnIndex("word"));
            String meaning = words.getString(words.getColumnIndex("meaning"));
            String sample = words.getString(words.getColumnIndex("sample"));
            wordList.add(new WordContent.WordItem(id, word, meaning, sample));
        }
        return wordList;
    }

    public void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sample", "i don't like apple");
        writableDatabase.update("words", contentValues, "_id=?", new String[]{"1"});
    }

    public void del() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sample", "i don't like apple");
        writableDatabase.delete("words", "_id=?", new String[]{"3"});
    }

    public List<WordContent.WordItem> like() {
        String sql ="select * from words where word like ?";
        Cursor words = writableDatabase.rawQuery(sql, new String[]{'%' + "ap" + '%'});
        List<WordContent.WordItem> wordList = new ArrayList<>();
        while (words.moveToNext()) {
            String id = words.getString(words.getColumnIndex("_id"));
            String word = words.getString(words.getColumnIndex("word"));
            String meaning = words.getString(words.getColumnIndex("meaning"));
            String sample = words.getString(words.getColumnIndex("sample"));
            wordList.add(new WordContent.WordItem(id, word, meaning, sample));
        }
        return wordList;
    }

}