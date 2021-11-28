package com.example.book;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class WordsProvider extends ContentProvider {
    public static final int MULTIPLE_WORDS = 1;
    public static final int SINGLE_WORD = 2;
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private WordDao wordDao;

    static {
        uriMatcher.addURI(Words.AUTHORITY, Words.Word.PATH_SINGLE, SINGLE_WORD);
        uriMatcher.addURI(Words.AUTHORITY, Words.Word.PATH_MULTIPLE, MULTIPLE_WORDS);
    }

    @Override
    public boolean onCreate() {
        WordsDBHelper wordsDBHelper = new WordsDBHelper(getContext());
        this.wordDao = new WordDaoImpl(wordsDBHelper);
        return true;
    }

    public WordsProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        long count = wordDao.deleteOneById(uri.getLastPathSegment());
        getContext().getContentResolver().notifyChange(uri, null);
        return ((Long) count).intValue();
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
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        WordDaoImpl wordDao = (WordDaoImpl) this.wordDao;
        SQLiteDatabase db = wordDao.writableDatabase;
        String sql = null;
        Cursor cursor=null;
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS:
                sql = "select * from words";
                cursor = db.rawQuery(sql, null);
                return cursor;
            //return db.query(Words.Word.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case SINGLE_WORD:
                String id = uri.getLastPathSegment();
                sql = "select * from words where _id=?";
                cursor = db.rawQuery(sql, new String[]{id});
                return cursor;
            //wordDao.getOneById();
            //qb.appendWhere(Words.Word._ID + "=" + uri.getPathSegments().get(1));
            //return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                //System.out.printf("");
                throw new IllegalArgumentException("Unkonwn Uri:" + uri);
        }
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
        int count = wordDao.updateOneById(uri.getLastPathSegment(), values);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}