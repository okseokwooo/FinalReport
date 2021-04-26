package com.example.gajdadeulim;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Guide_second extends Fragment {
    private String title;
    private int page;

    public static Guide_second newInstance(int page, String title) {
        Guide_second fragment = new Guide_second();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guide_line_second, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.weight_text);


        tvLabel.setText(page + 1 + " 가격 측정 시 무게를 고려해 주세요! ");
        return view;
    }
}
