package com.example.gajdadeulim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderActivity extends AppCompatActivity {

    Toolbar OrderToolbar;

    private Button order_btn_gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        OrderToolbar = findViewById(R.id.OrderToolbar);
        OrderToolbar.setTitle(R.string.defaultToolbar);
        setSupportActionBar(OrderToolbar);

        order_btn_gps = findViewById(R.id.order_btn_gps);
        order_btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //보류
                Intent intent = new Intent(OrderActivity.this , AddressActivity.class);
                startActivity(intent);
            }
        });
    }
}