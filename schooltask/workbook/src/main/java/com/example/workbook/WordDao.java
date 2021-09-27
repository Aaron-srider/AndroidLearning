package com.example.workbook;

import android.content.ContentValues;

import java.util.List;

public interface WordDao {

    public void createDataBase();

    public List<WordContent.WordItem> getAll();

    public List<WordContent.WordItem> like(String likeStr);

    public WordContent.WordItem  getOneById(String id);

    public long insertOne(String word, String meaning, String sample);

    public int updateOneById(String id, ContentValues contentValues);
    
    public long deleteOneById(String id);
}
