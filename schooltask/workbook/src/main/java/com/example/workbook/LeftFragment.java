package com.example.workbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LeftFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.left_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        MyItemRecyclerViewAdapter myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(WordContent.ITEMS, (MainActivity) this.getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(myItemRecyclerViewAdapter);
        return view;
    }
}
