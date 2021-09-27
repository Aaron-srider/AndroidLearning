package com.example.sqllite;

import android.content.ContentValues;

import java.util.List;

public interface WordDao {

    public List<WordContent.WordItem> getAll();

    public List<WordContent.WordItem> like(String likeStr);

    public void insertOne(String word, String meaning, String sample);

    public void updateOneById(String id, String column, String value);
    
    public void deleteOneById(String id);
}
