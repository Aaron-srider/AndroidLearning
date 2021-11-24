package com.example.readcontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyWordsTag";
    private  ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolver= this.getContentResolver();

        Button btnAll = findViewById(R.id.butAll);
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                String msg = "";
                while (cursor.moveToNext()) {
                    //name
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    msg = msg + " name:" + name + "\n";
                    Log.v("as", "aj;lsd");
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });


    }
}

;

