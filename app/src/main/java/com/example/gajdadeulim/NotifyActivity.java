package com.example.gajdadeulim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NotifyActivity extends AppCompatActivity {

    public Notify_Module notify;
    public ArrayList<Notify_Module> notiArray = new ArrayList<>();
    public String[] JsonList = new String[50];;
    public String userID = "";
    public ArrayList<NoticeData> dataList;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private NoticeAdapter noticeAdapter;
    private Toolbar notiToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        notiToolbar = findViewById(R.id.notiToolbar);
        notiToolbar.setTitle(R.string.mainToolbar);
        setSupportActionBar(notiToolbar);

        userID = getIntent().getStringExtra("userID");
        dataList = new ArrayList<NoticeData>();

        recyclerView = findViewById(R.id.noti_rv);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        noticeAdapter = new NoticeAdapter(dataList);
        recyclerView.setAdapter(noticeAdapter);
        getBoard();
        noticeAdapter.notifyDataSetChanged();
    }

    public void getBoard(){
        //boardsArray 및 JsonList 초기화
        notiArray.clear();
        for(int i =0;i<JsonList.length;i++){
            JsonList[i] = null;
        }

        //서버에서 데이터 받아오는곳
        ContentValues values = new ContentValues();
        String response = "";
        NetworkTask networkTask = new NetworkTask(resulturl("NotifySenderServlet"), values);
        try {
            response = networkTask.execute().get();
            Log.d("",response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //데이터 세분화 및 추가
        try{
            response=response.substring(1,response.length()-1);
            JsonList = response.split("\\},", 50);
            for(int i =0;i<JsonList.length-1;i++){
                JsonList[i] += "}";
            }

            for(int i =0;i<JsonList.length;i++){
                notify = new Notify_Module();
                JSONObject jsonObject = new JSONObject(JsonList[i]);
                notify.setNum(jsonObject.getString("num"));
                notify.setTitle(jsonObject.getString("title"));
                notify.setContent(jsonObject.getString("content"));
                notify.setTime(jsonObject.getString("time"));
                notiArray.add(notify);
            }
            for(Notify_Module noti : notiArray){
                Log.d("f",noti.getNum()+noti.getTitle()+noti.getContent()+noti.getTime());
                NoticeData noticeData = new NoticeData(noti.getNum(),noti.getTitle(),noti.getContent(),noti.getTime());
                dataList.add(noticeData);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.noticemenu,menu);
        return true;
    }

    // 우리 어플은 오전 7시에 시작해서 오후 10시에 끝나는 특성상 날짜를 기제 할 필요가 없음.
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh : {
                dataList.clear();
                getBoard();
                noticeAdapter.notifyDataSetChanged();
                return true;
            }
            default:{
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