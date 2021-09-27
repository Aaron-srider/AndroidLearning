package com.example.workbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void raiseRightFragment(WordContent.WordItem wordItem) {
        RightFragment rightFragment = (RightFragment)
                getSupportFragmentManager().findFragmentById(R.id.right_fragment);
        if (rightFragment != null) {
            rightFragment.updateView(wordItem);
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
}