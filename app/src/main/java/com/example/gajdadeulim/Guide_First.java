package com.example.gajdadeulim;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Guide_First extends Fragment {
    private String title;
    private int page;


    public static Guide_First newInstance(int page, String title) {
        Guide_First fragment = new Guide_First();
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
        View view = inflater.inflate(R.layout.guide_line_first, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.gift_text);


        tvLabel.setText(page + 1 + " 채팅방을 이용해 기프티콘 및 상품권 사용 가능 ! ");
        return view;
    }
}
