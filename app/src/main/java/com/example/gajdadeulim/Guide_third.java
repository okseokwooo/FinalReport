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

public class Guide_third extends Fragment {
    private String title;
    private int page;

    public static Guide_third newInstance(int page, String title) {
        Guide_third fragment = new Guide_third();
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
        View view = inflater.inflate(R.layout.guide_line_third, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.fishing_text);

        tvLabel.setText(page + 1 + " 사기를 당하거나 문제가 생겼을 경우 고객센터를 적극 이용해요! ");
        return view;
    }
}
