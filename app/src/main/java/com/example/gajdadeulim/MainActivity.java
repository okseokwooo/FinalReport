package com.example.gajdadeulim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button mainBoardFragButton;
    public Button MenuListFragButton;
    public Button ChattingFragButton;
    public Button myInfoFragButton;

    static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = getIntent().getStringExtra("userID");

        mainBoardFragButton = findViewById(R.id.mainBoardFragButton);
        MenuListFragButton = findViewById(R.id.MenuListFragButton);
        ChattingFragButton = findViewById(R.id.ChattingFragButton);
        myInfoFragButton = findViewById(R.id.myInfoFragButton);

        mainBoardFragButton.setOnClickListener(this);
        MenuListFragButton.setOnClickListener(this);
        ChattingFragButton.setOnClickListener(this);
        myInfoFragButton.setOnClickListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainBoard_Fragment mainBoard_fragment = new MainBoard_Fragment();
        transaction.replace(R.id.main_frame, mainBoard_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainBoardFragButton :{
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                MainBoard_Fragment mainBoard_fragment = new MainBoard_Fragment();
                transaction.replace(R.id.main_frame, mainBoard_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            case R.id.MenuListFragButton :{
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                MenuList_Fragment menuList_fragment = new MenuList_Fragment();
                transaction.replace(R.id.main_frame, menuList_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            case R.id.ChattingFragButton :{
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Chatting_Fragment chatting_fragment = new Chatting_Fragment();
                transaction.replace(R.id.main_frame, chatting_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            case R.id.myInfoFragButton :{
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                MyInfo_Fragment myInfo_fragment = new MyInfo_Fragment();
                transaction.replace(R.id.main_frame, myInfo_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            default:{
                break;
            }
        }
    }

}