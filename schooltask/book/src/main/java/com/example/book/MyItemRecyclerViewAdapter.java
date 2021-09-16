package com.example.book;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    private MainActivity mainActivity;
    private List<WordContent.WordItem> listValue;

    public MyItemRecyclerViewAdapter(List<WordContent.WordItem> items, MainActivity mainActivity) {
        this.listValue = items;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //为列表item绑定值
        Button btn = holder.view.findViewById(R.id.word);
        TextView id = holder.view.findViewById(R.id.id);
        id.setText(listValue.get(position).id);
        btn.setText(listValue.get(position).word);

        //为列表item绑定点击事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //唤起右边fragment
                mainActivity.raiseRightFragment(listValue.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listValue.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
}