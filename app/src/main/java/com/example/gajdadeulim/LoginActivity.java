package com.example.gajdadeulim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText IDEditText;
    EditText PWEditText;
    Button LoginConfirmBtn;
    Button MoveMembershipBtn;

    public Toolbar LoginToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginToolbar = findViewById(R.id.LoginToolbar);
        LoginToolbar.setTitle("");
        setSupportActionBar(LoginToolbar);

        IDEditText = findViewById(R.id.IDEditText);
        PWEditText = findViewById(R.id.PWEditText);
        LoginConfirmBtn = findViewById(R.id.LoginConfirmBtn);
        MoveMembershipBtn = findViewById(R.id.MoveMembershipBtn);

        LoginConfirmBtn.setOnClickListener(this);
        MoveMembershipBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LoginConfirmBtn :{
                //서버에 보낼거
                ContentValues values = new ContentValues();
                values.put("userID", IDEditText.getText().toString());
                values.put("userPassword", SignatureUtil.applySHA256(PWEditText.getText().toString()));

                String response = "";
                NetworkTask networkTask = new NetworkTask(resulturl("LoginConfirmServlet"), values);
                try {
                    response = networkTask.execute().get();
                    Log.d("",response);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(response.equals("1")){
                    Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                    intent.putExtra("userID",IDEditText.getText().toString());
                    startActivity(intent);
                }
                else{
                    //로그인 실패!
                    Log.d("","login failed");
                }

                break;
            }
            case R.id.MoveMembershipBtn : {
                Intent intent = new Intent(LoginActivity.this,MembershipActivity.class);
                startActivity(intent);
                break;
            }
            default: {
                break;
            }
        }
    }
    public String resulturl(String url) { //ip 값 바꿔주는 부분
        String resultUrl = "http://"+FinalURLIP.ip+":"+FinalURLIP.port+"/" + url;
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