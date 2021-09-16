package com.example.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeftFragment extends Fragment implements RefreshListObserver {
    View view;

    RecyclerView recyclerView;

    RefreshDetailObserver refreshDetailObserver;

    MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    List<WordContent.WordItem> allItems;

    public void setRefreshDetailObserver(RefreshDetailObserver refreshDetailObserver) {
        this.refreshDetailObserver = refreshDetailObserver;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.left_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        allItems = MainActivityHolder.getInstance().getAll();

        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(allItems, (MainActivity) this.getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(myItemRecyclerViewAdapter);

        return view;
    }

    public void notifyRight(){
        if(allItems.size()!=0) {
            refreshDetailObserver.refreshDetail(allItems.get(0));
        } else {
            refreshDetailObserver.refreshDetail(null);
        }
    }

    @Override
    public void refresh() {
        allItems = MainActivityHolder.getInstance().getAll();

        refreshList();
    }

    public void refreshList() {
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(allItems, (MainActivity) this.getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(myItemRecyclerViewAdapter);
    }
}
