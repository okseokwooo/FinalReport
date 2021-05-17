package com.example.gajdadeulim;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {


    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    private Context context;
    private ArrayList<MainData> dataList;

    public MainAdapter(ArrayList<MainData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.CustomViewHolder holder, final int position) {
        String str = dataList.get(position).getErrandTime();
        String o_s = str.substring(str.length()-2,str.length());
        String o_m = str.substring(str.length()-4,str.length()-2);
        String o_h = str.substring(str.length()-6,str.length()-4);
        int o_sec = Integer.parseInt(o_s);
        int o_min = Integer.parseInt(o_m);
        int o_hour = Integer.parseInt(o_h);
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);

        holder.iv_profile.setImageResource(dataList.get(position).getIv_profile());
        holder.orderID.setText(dataList.get(position).getOrderID());
        holder.errandContent.setText(dataList.get(position).getErrandContent());
        holder.errandPrice.setText(dataList.get(position).getErrandPrice()+"원");
        if(hour == o_hour){
            if(min == o_min){
                holder.errandTime.setText(sec-o_sec+"초 전");
            }
            else holder.errandTime.setText(min-o_min+"분 전");
        }
        else holder.errandTime.setText(hour-o_hour+"시간 전");
        holder.errandProgress.setText(dataList.get(position).getErrandProgress());
        holder.errandTitle.setText(dataList.get(position).getErrandTitle());
        holder.onBind(dataList.get(position),position);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.orderID.getText().toString();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("야",holder.errandProgress.getText().toString()+"");
                if(holder.errandProgress.getText().toString().equals("@@Waiting") && holder.orderID.getText().toString().equals(MainActivity.userID) ){
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(v.getContext());
                    myAlertBuilder.setTitle("주문 취소");
                    myAlertBuilder.setMessage("주문을 취소하시겠습니까?");
                    myAlertBuilder.setPositiveButton("주문 취소", new DialogInterface.OnClickListener() {
                        //Ok 버튼 눌렀을때
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                    ContentValues values = new ContentValues();
                    values.put("@@Cancel",String.valueOf(dataList.get(position).getO_Number()));
                    NetworkTask networkTask = new NetworkTask(resulturl("OrderCancelServlet"), values);
                    networkTask.execute();

                    remove(holder.getAdapterPosition());
                    notifyDataSetChanged();
                        }
                    });
                    myAlertBuilder.setNegativeButton("뒤로가기", new DialogInterface.OnClickListener() {
                        //No 버튼 눌렀을때
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    myAlertBuilder.show();
                }
                return false;
            }
        });
    }

    public void remove(int position){
        try {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
        catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MainData data;
        protected ImageView iv_profile;
        protected TextView orderID;
        protected TextView errandContent;
        protected TextView errandTime;
        protected TextView errandPrice;
        protected TextView errandProgress;
        protected TextView errandTitle;
        private int position;
        protected LinearLayout errandLayout;
        protected LinearLayout hiddenLayout;
        protected Button orderApply, btn_board_completed;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.orderID = itemView.findViewById(R.id.sendID);
            this.errandContent = itemView.findViewById(R.id.errandContent);
            this.errandTime = itemView.findViewById(R.id.errandTime);
            this.errandPrice = itemView.findViewById(R.id.errandPrice);
            this.errandProgress = itemView.findViewById(R.id.errandProgress);
            this.errandTitle = itemView.findViewById(R.id.errandTitle);
            this.errandLayout = itemView.findViewById(R.id.errandLayout);
            this.hiddenLayout = itemView.findViewById(R.id.HiddenLayout);
            this.orderApply = itemView.findViewById(R.id.btn_OrderApply);
            this.btn_board_completed = itemView.findViewById(R.id.btn_board_completed);
            orderApply.setOnClickListener(this);
            btn_board_completed.setOnClickListener(this);
        }

        void onBind(MainData data, int position) {
            this.data = data;
            this.position = position;

            errandLayout.setOnClickListener(this);

            changeVisibility(selectedItems.get(position));


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.errandLayout:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;

                case R.id.btn_OrderApply:
                    if(dataList.get(position).getOrderID().equals(MainActivity.userID)) {
                        Toast.makeText(context, "자신이 등록한 게시글입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        ContentValues values = new ContentValues();
                        values.put("ErrandUserID", MainActivity.userID);
                        values.put("@@Ongoing", String.valueOf(dataList.get(position).getO_Number()));

                        String response = "";
                        NetworkTask networkTask = new NetworkTask(resulturl("EditProgressSevlet"), values);
                        try {
                            response = networkTask.execute().get();
                            Log.d("", response);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (response.contains("@@AcceptOrder")) {
                            Toast.makeText(context, "정상적으로 수락 되었습니다.", Toast.LENGTH_SHORT).show();
                            remove(getAdapterPosition());
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "이미 수락된 심부름 입니다.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                case R.id.btn_board_completed:
                    ContentValues values = new ContentValues();
                    values.put("UserOwnOrders", String.valueOf(dataList.get(position).getO_Number()));

                    String response = "";
                    NetworkTask networkTask = new NetworkTask(resulturl("OrderCompleteServlet"), values);
                    try {
                        response = networkTask.execute().get();
                        Log.d("", response);
                        remove(getAdapterPosition());
                        notifyDataSetChanged();
                        Toast.makeText(context, "주문이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                default:break;
            }

        }
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    hiddenLayout.getLayoutParams().height = value;
                    hiddenLayout.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    hiddenLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

                    if((dataList.get(position).getOrderID().equals(MainActivity.userID) || dataList.get(position).getErrandID().equals(MainActivity.userID)) && dataList.get(position).getErrandProgress().equals("@@Ongoing")) {
                            btn_board_completed.setVisibility(View.VISIBLE);
                    }
                    else {
                        btn_board_completed.setVisibility(View.GONE);
                    }
                    if(!dataList.get(position).getOrderID().equals(MainActivity.userID) && dataList.get(position).getErrandProgress().equals("@@Waiting")) {
                        orderApply.setVisibility(View.VISIBLE);
                    }
                    else {
                        orderApply.setVisibility(View.GONE);
                    }
                }
            });
            // Animation start
            va.start();
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