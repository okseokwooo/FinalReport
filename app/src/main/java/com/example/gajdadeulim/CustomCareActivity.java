package com.example.gajdadeulim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import java.util.concurrent.ExecutionException;

public class CustomCareActivity extends AppCompatActivity {

    public String userID = "";
    public Toolbar customCareToolbar;
    public CheckBox cb_1,cb_2,cb_3,cb_4,cb_5;
    public EditText customCareContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_care);

        userID = getIntent().getStringExtra("userID");
        customCareToolbar = findViewById(R.id.CustomCareToolbar);
        customCareToolbar.setTitle(R.string.mainToolbar);
        setSupportActionBar(customCareToolbar);

        cb_1 = findViewById(R.id.customcarecb_1);
        cb_2 = findViewById(R.id.customcarecb_2);
        cb_3 = findViewById(R.id.customcarecb_3);
        cb_4 = findViewById(R.id.customcarecb_4);
        cb_5 = findViewById(R.id.customcarecb_5);

        customCareContent = findViewById(R.id.CustomCareContent);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.customcareconfirm, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.CustomCareConfirm: {
                String str_result = "";
                if(cb_1.isChecked()){
                    str_result += cb_1.getText().toString() + " , ";
                }
                if(cb_2.isChecked()){
                    str_result += cb_2.getText().toString() + " , ";
                }
                if(cb_3.isChecked()){
                    str_result += cb_3.getText().toString() + " , ";
                }
                if(cb_4.isChecked()){
                    str_result += cb_4.getText().toString() + " , ";
                }
                if(cb_5.isChecked()){
                    str_result += cb_5.getText().toString() + " , ";
                }
                if (customCareContent.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "신고내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    customCareContent.requestFocus();
                }
                if (str_result.equals("")){
                    Toast.makeText(getApplicationContext(), "선택사항을 하나 이상 체크해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    str_result = str_result.substring(0,str_result.length()-2);
                    Log.d("" , str_result);
                    ContentValues values = new ContentValues();
                    values.put("Reporter", userID);
                    values.put("CheckContent", str_result);
                    values.put("Content", customCareContent.getText().toString());

                    NetworkTask networkTask = new NetworkTask(resulturl("CustomReportServlet"), values);
                    networkTask.execute();
                    Toast.makeText(getApplicationContext(), "신고접수 완료.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CustomCareActivity.this , MainActivity.class);
                    intent.putExtra("userID",userID);
                    startActivity(intent);
                }
                return true;
            }
            default: {
                return true;
            }
        }
    }




    public String resulturl(String url) { //ip 값 바꿔주는 부분
        String resultUrl = "http://10.0.2.2:8080/" + url;
        return resultUrl;
    }
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        NetworkTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
}
}