package com.example.gajdadeulim;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Guide_Fourth extends Fragment {
    private String title;
    private int page;
    Button main_go_btn;

    public static Guide_Fourth newInstance(int page, String title) {
        Guide_Fourth fragment = new Guide_Fourth();
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
        View view = inflater.inflate(R.layout.guide_line_fourth, container, false);

        main_go_btn = view.findViewById(R.id.main_go_btn);
        main_go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , MainActivity.class);
                intent.putExtra("userID",GuideActivity.userID);
                startActivity(intent);
            }
        });

        return view;
    }
}
