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
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.CustomViewHolder> {


    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    private Context context;
    private ArrayList<NoticeData> dataList;

    public NoticeAdapter(ArrayList<NoticeData> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public NoticeAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noti_itemlist,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NoticeAdapter.CustomViewHolder holder, final int position) {
        holder.noticeTitle.setText(dataList.get(position).getTitle());
        holder.noticeTime.setText(dataList.get(position).getTime());
        holder.noticeContent.setText(dataList.get(position).getContent());

        holder.onBind(dataList.get(position),position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView noticeTime;
        private TextView noticeTitle;
        private TextView noticeContent;

        private NoticeData data;

        private int position;
        protected LinearLayout noticeLayout;
        protected LinearLayout hiddenLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.noticeTime = itemView.findViewById(R.id.noticeTime);
            this.noticeTitle = itemView.findViewById(R.id.noticeTitle);
            this.noticeContent = itemView.findViewById(R.id.notiContent);
            this.noticeLayout = itemView.findViewById(R.id.noticeLayout);
            this.hiddenLayout = itemView.findViewById(R.id.HiddenLayout);
        }

        void onBind(NoticeData data, int position) {
            this.data = data;
            this.position = position;
            noticeLayout.setOnClickListener(this);
            changeVisibility(selectedItems.get(position));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.noticeLayout:
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
                }
            });
            // Animation start
            va.start();
        }
    }
}
