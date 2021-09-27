package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.recycleview.model.UserMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<UserMessage> userMessageList = new ArrayList<>(Arrays.asList(
            new UserMessage("你好", UserMessage.TYPE_SEND),
            new UserMessage("好久不见", UserMessage.TYPE_RECEIVE),
            new UserMessage("最近过得怎么样", UserMessage.TYPE_SEND),
            new UserMessage("这样的生活很难受", UserMessage.TYPE_RECEIVE)));
    RecyclerView recyclerView = null;
    UserMessageAdapter userMessageAdapter = new UserMessageAdapter(userMessageList);
    EditText msgEditor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       /* for(int i = 0; i < 10000; i++) {
            userMessageList.add(new UserMessage("ni你好", UserMessage.TYPE_SEND));
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        msgEditor=findViewById(R.id.msgEditor);


        recyclerView.setAdapter(userMessageAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        for (int i = 0; i < userMessageList.size(); i++) {
            userMessageAdapter.notifyItemInserted(i);
        }

        Button button = findViewById(R.id.sendbtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable text = msgEditor.getText();
                addSendMessage(text.toString());
            }
        });

    }

    /**
     * 增加发送消息
     * @param content
     */
    private void addSendMessage(String content) {
        UserMessage userWordMessage = new UserMessage(content, UserMessage.TYPE_SEND);
        userMessageList.add(userWordMessage);
        // 表示在消息的末尾插入内容
        userMessageAdapter.notifyItemInserted(userMessageList.size() - 1);
        // 让 RecyclerView自动滚动到最底部
        recyclerView.scrollToPosition(userMessageList.size() - 1);
        msgEditor.setText("");
    }


}