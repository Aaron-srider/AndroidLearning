package com.example.workbook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class WordsProvider extends ContentProvider {
    private static final int MULTIPLE_WORDS = 1;
    private static final int SINGLE_WORD = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private WordDao wordDao;

    static {
        uriMatcher.addURI(Words.AUTHORITY, Words.Word.PATH_SINGLE, SINGLE_WORD);
        uriMatcher.addURI(Words.AUTHORITY, Words.Word.PATH_MULTIPLE, MULTIPLE_WORDS);
    }

    public WordsProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        long count = wordDao.deleteOneById(uri.getPathSegments().get(1));
        getContext().getContentResolver().notifyChange(uri, null);
        return ((Long)count).intValue();
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS:
                return Words.Word.MINE_TYPE_MULTIPLE;
            case SINGLE_WORD:
                return Words.Word.MINE_TYPE_SINGLE;
            default:
                throw new IllegalArgumentException("Unkonwn Uri:" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String word = values.getAsString("word");
        String meaning = values.getAsString("meaning");
        String sample = values.getAsString("sample");
        long id = wordDao.insertOne(word, meaning, sample);
        if (id > 0) {
            Uri newUri = ContentUris.withAppendedId(Words.Word.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        WordsDBHelper wordsDBHelper = new WordsDBHelper(getContext());
        this.wordDao = new WordDaoImpl(wordsDBHelper);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<WordContent.WordItem> query(Uri uri) {
        WordContent.WordItem oneById = wordDao.getOneById(uri.getPathSegments().get(1));
        List<WordContent.WordItem> wordItems = new ArrayList<>();
        wordItems.add(oneById);
        return wordItems;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        wordDao.updateOneById(uri.getPathSegments().get(1), values);
        getContext().getContentResolver().notifyChange(uri, null);
        throw new UnsupportedOperationException("Not yet implemented");
    }
}