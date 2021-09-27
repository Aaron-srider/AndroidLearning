package com.example.workbook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WordDaoImpl implements WordDao {

    SQLiteDatabase writableDatabase;

    WordsDBHelper mDbHelper;

    public WordDaoImpl(WordsDBHelper mDbHelper) {
        if(mDbHelper == null) {
            this.mDbHelper = mDbHelper;
        }
    }

    public void createDataBase() {
        if(mDbHelper == null) {
            throw new IllegalStateException("DBHelper not exists");
        }
        if(writableDatabase != null) {
            writableDatabase = mDbHelper.getWritableDatabase();
        }
    }

    @Override
    public List<WordContent.WordItem> getAll() {
        Cursor cursor = writableDatabase.query("cursor", null, null, null, null, null, null);
        List<WordContent.WordItem> wordList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String word = cursor.getString(cursor.getColumnIndex("word"));
            String meaning = cursor.getString(cursor.getColumnIndex("meaning"));
            String sample = cursor.getString(cursor.getColumnIndex("sample"));
            wordList.add(new WordContent.WordItem(id, word, meaning, sample));
        }
        return wordList;
    }

    @Override
    public List<WordContent.WordItem> like(String likeStr) {
        String sql ="select * from words where word like ?";
        Cursor words = writableDatabase.rawQuery(sql, new String[]{'%' + likeStr + '%'});
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

    @Override
    public WordContent.WordItem getOneById(String id) {
        String sql ="select * from words where _id = ?";
        Cursor words = writableDatabase.rawQuery(sql, new String[]{id});
        String word = words.getString(words.getColumnIndex("word"));
        String meaning = words.getString(words.getColumnIndex("meaning"));
        String sample = words.getString(words.getColumnIndex("sample"));
        WordContent.WordItem wordItem = new WordContent.WordItem(id, word, meaning, sample);
        return wordItem;
    }

    @Override
    public long insertOne(String word, String meaning, String sample) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("meaning", meaning);
        contentValues.put("sample", sample);
        long id = writableDatabase.insert("words", null, contentValues);
        return id;
    }

    @Override
    public int updateOneById(String id, ContentValues contentValues) {
        int count = writableDatabase.update("words", contentValues, "_id=?", new String[]{id});
        return count;
    }

    @Override
    public long deleteOneById(String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sample", "i don't like apple");
        long l = writableDatabase.delete("words", "_id=?", new String[]{id});
        return l;
    }
}
