package com.example.gajdadeulim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class MembershipActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean IDOverlapStatus = false;
    private Boolean StudentIDAuthenticateStatus = false;
    private String localIDStorage = "";
    private String userEmail = "";

    public Toolbar memberToolbar;

    EditText NameMemberText;
    EditText IDMemberText;
    EditText PWMemberText;
    EditText PWConfirmMemberText;
    EditText StudentIDMemberText;
    EditText GenderMemberText;
    EditText MajorMemberText;
    EditText EmailAuthenticateEditText;

    User_Module userModule;
    Button MoveLoginBtn;
    Button FinishMemberShipBtn;
    Button OverlapConfirmBtn;
    Button StudentIDAuthenticateBtn;
    Button StudentIDConfirmBtn;

    //이메일 인증관련 변수
    long seed = System.currentTimeMillis();
    Random random = new Random(seed);
    int randomNum = 0;

    String Email = "xodus374@gmail.com";
    String password = "gyhmpooddhfhbvpz";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        memberToolbar = findViewById(R.id.MemberToolbar);
        memberToolbar.setTitle(R.string.defaultToolbar);
        setSupportActionBar(memberToolbar);

        NameMemberText = findViewById(R.id.NameMemberText);
        IDMemberText = findViewById(R.id.IDMemberText);
        PWMemberText = findViewById(R.id.PWMemberText);
        PWConfirmMemberText = findViewById(R.id.PWConfirmMemberText);
        StudentIDMemberText = findViewById(R.id.StudentIDMemberText);
        GenderMemberText = findViewById(R.id.GenderMemberText);
        MajorMemberText = findViewById(R.id.MajorMemberText);
        EmailAuthenticateEditText = findViewById(R.id.EmailAuthenticateEditText);

        MoveLoginBtn = findViewById(R.id.MoveLoginBtn);
        FinishMemberShipBtn = findViewById(R.id.FinishMemberShipBtn);
        OverlapConfirmBtn = findViewById(R.id.OverlapConfirmBtn);
        StudentIDAuthenticateBtn = findViewById(R.id.StudentIDAuthenticateBtn);
        StudentIDConfirmBtn = findViewById(R.id.StudentIDConfirmBtn);

        MoveLoginBtn.setOnClickListener(this);
        FinishMemberShipBtn.setOnClickListener(this);
        OverlapConfirmBtn.setOnClickListener(this);
        StudentIDAuthenticateBtn.setOnClickListener(this);
        StudentIDConfirmBtn.setOnClickListener(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.OverlapConfirmBtn : {

                ContentValues values = new ContentValues();
                values.put("inputID", IDMemberText.getText().toString());

                String response = "";
                NetworkTask networkTask = new NetworkTask(resulturl("IsThereIDServlet"), values);
                try {
                    response = networkTask.execute().get();
                    Log.d("",response);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(response.equals("1")){
                    Toast.makeText(getApplicationContext(),"이미있는 아이디 입니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    IDOverlapStatus = true;
                    Toast.makeText(getApplicationContext(),"중복확인이 되었습니다.",Toast.LENGTH_SHORT).show();
                    localIDStorage = IDMemberText.getText().toString();
                }

                break;
            }
            case R.id.StudentIDAuthenticateBtn : {
                ContentValues values = new ContentValues();
                values.put("InputStudentNumber", StudentIDMemberText.getText().toString());

                String response = "";
                NetworkTask networkTask = new NetworkTask(resulturl("IsThereStudentNumberServlet"), values);
                try {
                    response = networkTask.execute().get();
                    Log.d("",response);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(response.equals("1")){
                    Log.d("", "중복됨");
                    Toast.makeText(getApplicationContext(),"이미 등록된 회원의 학번입니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("", "중복안됨");
                    Toast.makeText(getApplicationContext(),StudentIDMemberText.getText().toString() + "@sangmyung.kr 로 인증번호를 발송합니다.",Toast.LENGTH_SHORT).show();
                    randomNum = random.nextInt(10000000);
                    userEmail = StudentIDMemberText.getText().toString() + "@sangmyung.kr";

                    Log.d("",randomNum+"");
                    try {
                        GMailSender gMailSender = new GMailSender(Email, password);
                        gMailSender.sendMail("갖다드림 학번인증키입니다.",String.valueOf(randomNum) , userEmail);//SignatureUtil.applySHA256(String.valueOf(randomNum))
                        Toast.makeText(getApplicationContext(), "해당 이메일로 보낸 인증코드를 아래에 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } catch (SendFailedException e) {
                        Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    } catch (MessagingException e) {
                        Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case R.id.StudentIDConfirmBtn : {
                if(String.valueOf(randomNum).equals(EmailAuthenticateEditText.getText().toString())){
                    Toast.makeText(getApplicationContext(),"인증코드가 맞습니다.",Toast.LENGTH_SHORT).show();
                    StudentIDAuthenticateStatus = true;
                    StudentIDMemberText.setInputType(InputType.TYPE_NULL);
                    EmailAuthenticateEditText.setInputType(InputType.TYPE_NULL);
                    StudentIDAuthenticateBtn.setClickable(false);
                    StudentIDConfirmBtn.setClickable(false);
                }
                else{
                    Toast.makeText(getApplicationContext(),"인증코드가 맞지않습니다.",Toast.LENGTH_SHORT).show();;
                    StudentIDMemberText.setText("");
                    EmailAuthenticateEditText.setText("");
                }
                break;
            }
            case R.id.MoveLoginBtn : {
                Intent intent = new Intent(MembershipActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.FinishMemberShipBtn : {
                if(!PWMemberText.getText().toString().equals(PWConfirmMemberText.getText().toString()) || PWMemberText.getText().equals("")) {
                    PWMemberText.setText("");
                    PWConfirmMemberText.setText("");
                    Toast.makeText(getApplicationContext(),"비밀번호가 없거나 같지않습니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(NameMemberText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();;
                        NameMemberText.requestFocus();
                    }
                    else if(IDMemberText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"아이디를 입력해주세요.",Toast.LENGTH_SHORT).show();;
                        IDMemberText.requestFocus();
                    }
                    else if(!IDOverlapStatus){
                        Toast.makeText(getApplicationContext(),"아이디 중복확인을 해주세요.",Toast.LENGTH_SHORT).show();
                    }
                    else if(!localIDStorage.equals(IDMemberText.getText().toString())){
                        Toast.makeText(getApplicationContext(),"중복확인한 아이디로 입력해주세요.",Toast.LENGTH_SHORT).show();
                        IDMemberText.setText("");
                        IDMemberText.requestFocus();
                    }
                    else if(PWMemberText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();;
                        PWMemberText.requestFocus();
                    }
                    else if(PWConfirmMemberText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"비밀번호확인을 입력해주세요.",Toast.LENGTH_SHORT).show();;
                        PWConfirmMemberText.requestFocus();
                    }
                    else if(!StudentIDAuthenticateStatus){
                        Toast.makeText(getApplicationContext(),"학교인증을 받아주세요",Toast.LENGTH_SHORT).show();;
                    }
                    else if(GenderMemberText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"성별을 입력해주세요.",Toast.LENGTH_SHORT).show();;
                        GenderMemberText.requestFocus();
                    }
                    else if(MajorMemberText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"학과를 입력해주세요.",Toast.LENGTH_SHORT).show();;
                        MajorMemberText.requestFocus();
                    }
                    else{
                        ContentValues values = new ContentValues();
                        values.put("Regi_id", IDMemberText.getText().toString());
                        values.put("Regi_pass",  SignatureUtil.applySHA256(PWMemberText.getText().toString()));
                        values.put("Regi_name", NameMemberText.getText().toString());
                        values.put("Regi_major", MajorMemberText.getText().toString());
                        values.put("Regi_school_number", StudentIDMemberText.getText().toString());
                        values.put("Regi_gender", GenderMemberText.getText().toString());

                        NetworkTask networkTask = new NetworkTask(resulturl("RegisterUserServlet"), values);
                        networkTask.execute();

                        Intent intent = new Intent(MembershipActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
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