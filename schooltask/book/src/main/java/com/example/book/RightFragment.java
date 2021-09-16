package com.example.book;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class RightFragment extends Fragment implements RefreshDetailObserver {

    RightFragObserver rightFragObserver;


    View view;
    TextView word;
    TextView meaning;
    TextView sample;
    TextView id;

    Button update;
    Button delete;

    public RightFragment() {
    }

    public void setRightFragObserver(RightFragObserver rightFragObserver) {
        this.rightFragObserver=rightFragObserver;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.right_fragment, container, false);
        update = view.findViewById(R.id.update);
        delete = view.findViewById(R.id.delete);
        id = view.findViewById(R.id.id);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.insert_layout, null);
                new AlertDialog.Builder(view.getContext())
                        .setTitle("修改单词").setView(linearLayout)
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
                                MainActivityHolder.getInstance().update(Integer.parseInt(id.getText().toString()),
                                        contentValues1);

                                rightFragObserver.notifyLeftFrag();
                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create().show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.delete_layout, null);
                new AlertDialog.Builder(view.getContext())
                        .setTitle("删除单词").setView(linearLayout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //插入一条记录
                                MainActivityHolder.getInstance().deleteOne(Integer.parseInt(id.getText().toString()));

                                rightFragObserver.notifyLeftFrag();
                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create().show();
            }
        });

        word = view.findViewById(R.id.word);
        meaning = view.findViewById(R.id.meaning);
        sample = view.findViewById(R.id.sample);
        return view;
    }

    @Override
    public void refreshDetail(WordContent.WordItem wordItem) {
        if(wordItem!=null) {
            word.setText(wordItem.word);
            meaning.setText(wordItem.meaning);
            sample.setText(wordItem.sample);
            id.setText(wordItem.id);
        }
    }
}
