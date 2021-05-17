package com.example.gajdadeulim;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.CustomViewHolder> {

    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    private Context context;
    private ArrayList<ChattingListData> dataList;

    public ChatAdapter(ArrayList<ChattingListData> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public ChatAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatAdapter.CustomViewHolder holder, final int position) {
        holder.iv_profile.setImageResource(dataList.get(position).getIv_profile());
        if(dataList.get(position).getOrderID().equals(MainActivity.userID)){
            holder.chat_other.setText(dataList.get(position).getErrandID());
        }
        else{
            holder.chat_other.setText(dataList.get(position).getOrderID());
        }

        //content랑 time 소켓들어오면 하기

        holder.onBind(dataList.get(position),position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ChattingListData data;
        protected ImageView iv_profile;
        protected TextView chat_other;
        protected TextView chat_content;
        protected TextView chat_time;

        private int position;
        protected LinearLayout chattingLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.chat_other = itemView.findViewById(R.id.chat_other);
            this.chat_content = itemView.findViewById(R.id.chat_content);
            this.chat_time = itemView.findViewById(R.id.chat_time);
            this.chattingLayout = itemView.findViewById(R.id.chattingLayout);
        }

        void onBind(ChattingListData data, int position) {
            this.data = data;
            this.position = position;

            chattingLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.chattingLayout:
                    Intent intent = new Intent(context, ChattingActivity.class);
                    intent.putExtra("userID",MainActivity.userID);
                    intent.putExtra("orderID",dataList.get(position).getOrderID());
                    intent.putExtra("errandID",dataList.get(position).getErrandID());
                    context.startActivity(intent);
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