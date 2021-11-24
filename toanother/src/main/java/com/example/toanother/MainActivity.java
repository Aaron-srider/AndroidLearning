package com.example.toanother;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button Thesender;
    private TextView YouGet;
    private EditText YouSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thesender=findViewById(R.id.Send);
        YouGet=findViewById(R.id.TheGetter);
        YouSend=findViewById(R.id.TheSender);
        Thesender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent msg = new Intent(MainActivity.this,MainActivity2.class);
                msg.putExtra("Msg",YouSend.getText().toString());
                startActivityForResult(msg,1);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);{
            System.out.println("调用成功！");
         if(resultCode==1){
             String returnData =data.getStringExtra("Msg");
             YouGet.setText(returnData);
         }
         else
         {
             YouGet.setText("成功进入方法,你的resultCode是"+resultCode);
         }
        }
    }
}