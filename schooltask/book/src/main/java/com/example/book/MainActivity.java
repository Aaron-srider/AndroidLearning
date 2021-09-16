package com.example.book;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RightFragObserver {

    ContentResolver contentResolver;

    RefreshListObserver leftFrag;

    RightFragment rightFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //初始化单件
        contentResolver = getContentResolver();
        new MainActivityHolder(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftFrag = (RefreshListObserver) getSupportFragmentManager().findFragmentById(R.id.left_fragment);
        rightFrag = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.right_fragment);
        ((LeftFragment)getSupportFragmentManager().findFragmentById(R.id.left_fragment)).setRefreshDetailObserver(rightFrag);
        ((LeftFragment)getSupportFragmentManager().findFragmentById(R.id.left_fragment)).notifyRight();
        rightFrag.setRightFragObserver(this);



        ContentValues contentValues = new ContentValues();
        contentValues.put("word", "apple");

        Button add = findViewById(R.id.add);

        //添加事件
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.insert_layout, null);
                new AlertDialog.Builder(view.getContext())
                        .setTitle("新增单词").setView(linearLayout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String strWord = ((EditText) linearLayout.findViewById(R.id.word)).getText().toString();
                                String strMeaning = ((EditText) linearLayout.findViewById(R.id.meaning)).getText().toString();
                                String strSample = ((EditText) linearLayout.findViewById(R.id.sample)).getText().toString();
                                ContentValues contentValues1 = new ContentValues();
                                contentValues1.put("word", strWord);
                                contentValues1.put("meaning", strMeaning);
                                contentValues1.put("sample", strSample);
                                //插入一条记录
                                insertOne(contentValues1);
                                //更新左边frag
                                List<WordContent.WordItem> items = getAll();

                                leftFrag.refresh();
                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create().show();
            }
        });

    }

    public void update(Integer id, ContentValues contentValues) {
        Uri parse = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + id);
        int update = contentResolver.update(parse, contentValues, null);
    }

    public void deleteOne(Integer id) {
        Uri parse = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + id);
        contentResolver.delete(parse, null);
    }

    public Uri insertOne(ContentValues contentValues) {
        Uri parse = Uri.parse(Words.Word.CONTENT_URI_STRING);
        Uri hasInsert = contentResolver.insert(parse, contentValues);
        return hasInsert;
    }

    public List<WordContent.WordItem> getOne(int id) {
        Uri parse = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + id);
        Cursor cursor = contentResolver.query(parse,
                null, null, null, null);
        List<WordContent.WordItem> list = getAllFromCursor(cursor);
        return list;
    }

    public List<WordContent.WordItem> getAll() {
        Uri parse = Uri.parse(Words.Word.CONTENT_URI_STRING);
        Cursor cursor = contentResolver.query(parse,
                null, null, null, null);
        List<WordContent.WordItem> list = getAllFromCursor(cursor);
        return list;
    }

    public List<WordContent.WordItem> getAllFromCursor(Cursor cursor) {
        WordContent.WordItem wordItem = null;
        List<WordContent.WordItem> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String word = cursor.getString(cursor.getColumnIndex("word"));
            String meaning = cursor.getString(cursor.getColumnIndex("meaning"));
            String sample = cursor.getString(cursor.getColumnIndex("sample"));
            list.add(new WordContent.WordItem(id, word, meaning, sample));
        }
        return list;
    }

    public void raiseRightFragment(WordContent.WordItem wordItem) {
        RightFragment rightFragment = (RightFragment)
                getSupportFragmentManager().findFragmentById(R.id.right_fragment);
        if (rightFragment != null) {
            rightFragment.refreshDetail(wordItem);
        } else {
            RightFragment newFragment = new RightFragment();
            Bundle args = new Bundle();
            args.putSerializable("workItem", wordItem);
            newFragment.setArguments(args);
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.right_fragment, newFragment);
            transaction.commit();
        }
    }

    public void refreshLeft() {
        leftFrag.refresh();
    }

    @Override
    public void notifyLeftFrag() {
        refreshLeft();
    }
}