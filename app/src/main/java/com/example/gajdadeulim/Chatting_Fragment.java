package com.example.gajdadeulim;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Chatting_Fragment extends Fragment {

    public View view;
    public Toolbar chatToolbar;
    public ArrayList<ChatList_Module> chatArray = new ArrayList<ChatList_Module>();
    public ChatList_Module chatList;
    public String[] JsonList = new String[50];;

    private ArrayList<ChattingListData> dataList;
    private ChatAdapter chatAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chatting_fragment, container, false);
        setHasOptionsMenu(true);

        chatToolbar = view.findViewById(R.id.chatToolbar);
        chatToolbar.setTitle(R.string.mainToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(chatToolbar);

        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        dataList = new ArrayList<>();
        chatAdapter = new ChatAdapter(dataList);
        recyclerView.setAdapter(chatAdapter);
        getBoard();

        chatAdapter.notifyDataSetChanged();

        return view;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.defaultmenu,menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

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
                chatList = new ChatList_Module();
                JSONObject jsonObject = new JSONObject(JsonList[i]);
                chatList.setNum(jsonObject.getInt("increase_num"));
                chatList.setOrderID(jsonObject.getString("orderID"));
                chatList.setErrandID(jsonObject.getString("errandID"));
                chatArray.add(chatList);
            }

            for (int i = 0; i < chatArray.size(); i++) {
                ChattingListData chatData = new ChattingListData(R.drawable.human, chatArray.get(i).getNum(), chatArray.get(i).getOrderID(), chatArray.get(i).getErrandID());
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
