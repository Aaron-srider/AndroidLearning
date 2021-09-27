package com.example.recycleview.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.R;

public class UserMessageViewHolder extends RecyclerView.ViewHolder {

    TextView tvLeft, tvRight;

    public UserMessageViewHolder(@NonNull View itemView) {
        super(itemView);

        tvLeft =itemView.findViewById(R.id.left_msg);
        tvRight =itemView.findViewById(R.id.right_msg);
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public void setTvLeft(TextView tvLeft) {
        this.tvLeft = tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public void setTvRight(TextView tvRight) {
        this.tvRight = tvRight;
    }
}
