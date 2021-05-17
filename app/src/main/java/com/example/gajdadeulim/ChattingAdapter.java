package com.example.gajdadeulim;

import android.content.ContentValues;
import android.content.Context;
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

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.CustomViewHolder> {

    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    private Context context;
    private ArrayList<ChattingContentData> dataList;

    public ChattingAdapter(ArrayList<ChattingContentData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override

    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChattingAdapter.CustomViewHolder holder, final int position) {
        if(dataList.get(position).getUserID().equals(MainActivity.userID)){//자기가 보낸 메시지
            holder.receiveLayout.setVisibility(View.GONE);
            holder.sendID.setText(dataList.get(position).getUserID());
            holder.sendText.setText(dataList.get(position).getChatText());
            holder.sendTime.setText(dataList.get(position).getC_Time());
        }
        else{
            holder.sendLayout.setVisibility(View.GONE);
            holder.iv_profile.setImageResource(dataList.get(position).getIv_profile());
            holder.receiveID.setText(dataList.get(position).getUserID());
            holder.receiveText.setText(dataList.get(position).getChatText());
            holder.receiveTime.setText(dataList.get(position).getC_Time());
        }

        holder.onBind(dataList.get(position),position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ChattingContentData data;
        protected ImageView iv_profile;
        protected TextView receiveID,sendID;
        protected TextView receiveText,sendText;
        protected TextView receiveTime,sendTime;

        private int position;
        protected LinearLayout receiveLayout,sendLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.receiveID = itemView.findViewById(R.id.reciveID);
            this.receiveText = itemView.findViewById(R.id.reciveText);
            this.receiveTime = itemView.findViewById(R.id.reciveTime);
            this.receiveLayout = itemView.findViewById(R.id.recive_layout);

            this.sendID = itemView.findViewById(R.id.sendID);
            this.sendText = itemView.findViewById(R.id.sendText);
            this.sendTime = itemView.findViewById(R.id.sendTime);
            this.sendLayout = itemView.findViewById(R.id.send_Layout);
        }

        void onBind(ChattingContentData data, int position) {
            this.data = data;
            this.position = position;
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