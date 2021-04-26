package com.example.gajdadeulim;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class MenuList_Fragment extends Fragment {

    public View view;
    public Toolbar mainToolbar;
    public ArrayList<Board_Module> boardsArray = new ArrayList<Board_Module>();

    public Board_Module boards;
    public String[] JsonList = new String[50];

    private ArrayList<MainData> dataList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menulist_fragment, container, false);
        setHasOptionsMenu(true);

        mainToolbar = view.findViewById(R.id.mainToolbar);
        mainToolbar.setTitle(R.string.mainToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mainToolbar);

        recyclerView = view.findViewById(R.id.rv_menulist);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        dataList = new ArrayList<>();
        mainAdapter = new MainAdapter(dataList);
        recyclerView.setAdapter(mainAdapter);
        getBoard();
        for(int i=dataList.size()-1;i>=0;i--){
            if(!dataList.get(i).getErrandProgress().equals("@@Completed")){
                dataList.remove(i);
            }
        }
        mainAdapter.notifyDataSetChanged();

        return view;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menulist_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.waiting: {
                dataList.clear();
                getBoard();
                for(int i=dataList.size()-1;i>=0;i--){
                    if(!dataList.get(i).getErrandProgress().equals("@@Waiting")){
                        dataList.remove(i);//
                    }
                }
                mainAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "대기중인 주문", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.ongoing: {
                dataList.clear();
                getBoard();
                for(int i=dataList.size()-1;i>=0;i--){
                    if(!dataList.get(i).getErrandProgress().equals("@@Ongoing")){
                        dataList.remove(i);
                    }
                }
                mainAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "진행중인 주문", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.completed: {
                dataList.clear();
                getBoard();
                for(int i=dataList.size()-1;i>=0;i--){
                    if(!dataList.get(i).getErrandProgress().equals("@@Completed")){
                        dataList.remove(i);
                    }
                }
                mainAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "완료된 주문", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.logout: {
                Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.Systheme: {
                SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("systheme", MODE_PRIVATE);    // test 이름의 기본모드 설정
                final SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언

                final int[] selectedItem = {0};
                final String[] items = new String[]{"다크모드", "라이트모드", "시스템 설정값"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(this.getActivity());
                dialog.setTitle("테마선택")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedItem[0] = which;
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (selectedItem[0] == 0) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                    Toast.makeText(getContext(), "어두운테마 적용됨", Toast.LENGTH_LONG).show();
                                    editor.putString("theme", "dark");
                                    editor.commit();
                                } else if (selectedItem[0] == 1) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                    Toast.makeText(getContext(), "밝은테마 적용됨", Toast.LENGTH_LONG).show();
                                    editor.putString("theme", "light");
                                    editor.commit();
                                } else {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                    Toast.makeText(getContext(), "시스템설정값 적용됨", Toast.LENGTH_LONG).show();
                                    editor.putString("theme", "system");
                                    editor.commit();
                                }
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "취소 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.create();
                dialog.show();
                return true;

            }
            default: {
                return true;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    public void getBoard(){
        //boardsArray 및 JsonList 초기화
        boardsArray.clear();
        for(int i =0;i<JsonList.length;i++){
            JsonList[i] = null;
        }

        //서버에서 데이터 받아오는곳
        ContentValues values = new ContentValues();
        values.put("UserOwnOrders",MainActivity.userID);
        String response = "";
        NetworkTask networkTask = new NetworkTask(resulturl("LoadOwnOrdersServlet"), values);
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
                Log.d("결과값? : ",JsonList[i]);
            }

            for(int i =0;i<JsonList.length;i++){
                boards = new Board_Module();
                JSONObject jsonObject = new JSONObject(JsonList[i]);
                boards.setO_number(jsonObject.getInt("o_number"));
                boards.setOrders(jsonObject.getString("orders"));
                boards.setTitle(jsonObject.getString("title"));
                boards.setText(jsonObject.getString("text"));
                boards.setLatitude(jsonObject.getDouble("latitude"));
                boards.setLongitude(jsonObject.getDouble("longitude"));
                boards.setDetail_address(jsonObject.getString("detail_address"));
                boards.setPrice(jsonObject.getInt("price"));
                boards.setO_time(jsonObject.getString("o_time").substring(8));
                boards.setProgress(jsonObject.getString("progress"));
                boardsArray.add(boards);
            }

            for(int i=0; i<boardsArray.size();i++){
                MainData mainData = new MainData(R.drawable.human,boardsArray.get(i).getOrders(),boardsArray.get(i).getO_time(),boardsArray.get(i).getText(),String.valueOf(boardsArray.get(i).getPrice()),boardsArray.get(i).getProgress(),boardsArray.get(i).getTitle(),boardsArray.get(i).getO_number());
                dataList.add(mainData);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
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
