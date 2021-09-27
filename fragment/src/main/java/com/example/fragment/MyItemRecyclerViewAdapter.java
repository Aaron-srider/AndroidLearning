package com.example.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragment.placeholder.WordContent;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //为列表item绑定值
        TextView textView = holder.view.findViewById(R.id.list_item);
        textView.setText(listValue.get(position).word);
        //为列表item绑定点击事件
        holder.view.setOnClickListener(new View.OnClickListener() {
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