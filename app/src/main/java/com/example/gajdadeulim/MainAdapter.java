package com.example.gajdadeulim;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> dataList;

    public MainAdapter(ArrayList<MainData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.CustomViewHolder holder, int position) {
        holder.iv_profile.setImageResource(dataList.get(position).getIv_profile());
        holder.errandName.setText(dataList.get(position).getErrandName());
        holder.errandContent.setText(dataList.get(position).getErrandContent());
        holder.errandPrice.setText(dataList.get(position).getErrandPrice());
        holder.errandTime.setText(dataList.get(position).getErrandTime());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.errandName.getText().toString();
                Toast.makeText(v.getContext(),curName,Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return true;
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

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;
        protected TextView errandName;
        protected TextView errandContent;
        protected TextView errandTime;
        protected TextView errandPrice;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            this.errandName = (TextView) itemView.findViewById(R.id.errandName);
            this.errandContent = (TextView) itemView.findViewById(R.id.errandContent);
            this.errandTime = (TextView) itemView.findViewById(R.id.errandTime);
            this.errandPrice = (TextView) itemView.findViewById(R.id.errandPrice);
        }
    }
}
