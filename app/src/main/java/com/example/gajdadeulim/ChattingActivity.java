package com.example.gajdadeulim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChattingActivity extends AppCompatActivity implements View.OnClickListener {

    public String userID;
    public String orderID;
    public String errandID;

    public Toolbar chatToolbar;
    public Button btn_send;
    public EditText et_chat;

    public View view;
    public ArrayList<Chat_Module> chatArray = new ArrayList<Chat_Module>();
    public Chat_Module chat;
    public String[] JsonList = new String[50];;

    private ArrayList<ChattingContentData> dataList;
    private ChattingAdapter chattingAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        userID = getIntent().getStringExtra("userID");
        orderID = getIntent().getStringExtra("orderID");
        errandID = getIntent().getStringExtra("errandID");

        btn_send = findViewById(R.id.btn_send);
        et_chat = findViewById(R.id.Et_chat);

        chatToolbar = findViewById(R.id.cttoolbar);
        chatToolbar.setTitle("갖다드림");
        setSupportActionBar(chatToolbar);

        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        dataList = new ArrayList<>();
        //chattingAdapter = new ChattingAdapter(dataList);
        recyclerView.setAdapter(chattingAdapter);
        getBoard();

        chattingAdapter.notifyDataSetChanged();

        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        ContentValues values = new ContentValues();
//        values.put("sender_id", userID);
//        values.put("content", et_chat.getText().toString());
//
//        NetworkTask networkTask = new NetworkTask(resulturl("CustomReportServlet"), values);
//        networkTask.execute();
        // 서버 만들어지면 하겠습니다.
        et_chat.setText("");
    }
// 서버 만들어지면  2
    public void getBoard() {
        //boardsArray 및 JsonList 초기화
        chatArray.clear();
        for (int i = 0; i < JsonList.length; i++) {
            JsonList[i] = null;
        }
        //서버에서 데이터 받아오는곳
        ContentValues values = new ContentValues();
        String response = "";
        values.put("InputIDForChat",MainActivity.userID);
        NetworkTask networkTask = new NetworkTask(resulturl("OrdererErranderID"), values);
        try {
            response = networkTask.execute().get();
            Log.d("", response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //데이터 세분화 및 추가
        try {
            response = response.substring(1, response.length() - 1);
            JsonList = response.split("\\},", 50);
            for (int i = 0; i < JsonList.length - 1; i++) {
                JsonList[i] += "}";
                Log.d("결과값? : ", JsonList[i]);
            }

            for (int i = 0; i < JsonList.length; i++) {
                chat = new Chat_Module();
                JSONObject jsonObject = new JSONObject(JsonList[i]);
                chat.setNum(jsonObject.getInt("increase_num"));
                chat.setuserID(jsonObject.getString("userID"));
                chat.setC_time(jsonObject.getString("C_Time"));
                chat.setC_number(jsonObject.getInt("c_number"));
                chat.setChattingText(jsonObject.getString("ChattingText"));
                chatArray.add(chat);
            }

            for (int i = 0; i < chatArray.size(); i++) {
                ChattingContentData chatData = new ChattingContentData(R.drawable.human, chatArray.get(i).getNum(), chatArray.get(i).getuserID(), chatArray.get(i).getChattingText(),chatArray.get(i).getC_time());
                dataList.add(chatData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
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