package com.example.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.model.UserMessage;
import com.example.recycleview.model.UserMessageViewHolder;

import java.util.List;

public class UserMessageAdapter extends RecyclerView.Adapter<UserMessageViewHolder> {

    private final List<UserMessage> mUserWordMessageList;

    public UserMessageAdapter(List<UserMessage> mUserWordMessageList) {
        this.mUserWordMessageList = mUserWordMessageList;
    }

    /**
     * 获取view的类型
     *
     * @param position view的下标
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        UserMessage msg = mUserWordMessageList.get(position);
        return msg.getType();
    }

    /**
     * 根据不同view类型创建不同view
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public UserMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == UserMessage.TYPE_SEND) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_right_layout, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_left_layout, parent, false);
        }
        UserMessageViewHolder userMessageViewHolder = new UserMessageViewHolder(view);
        return userMessageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserMessageViewHolder holder, int position) {
        UserMessage msg = mUserWordMessageList.get(position);
        if (msg.getType() == UserMessage.TYPE_SEND) {
            holder.getTvRight().setText(msg.getContent());
        } else {
            holder.getTvLeft().setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mUserWordMessageList.size();
    }
}
