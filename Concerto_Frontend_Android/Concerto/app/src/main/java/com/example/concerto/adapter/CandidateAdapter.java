package com.example.concerto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.bean.UserItem;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {

    List<UserItem> musers;
    long userId;
    long projectId;
    String operation;

    public CandidateAdapter(List<UserItem> users){
        musers=users;
    }
    @NonNull
    @Override
    public CandidateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        final CandidateAdapter.ViewHolder holder=new CandidateAdapter.ViewHolder(view);
        int position=holder.getAdapterPosition();
        holder.btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId=musers.get(position).getUserId();
                operation="true";
            }
        });

        holder.btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId=musers.get(position).getUserId();
                operation="false";
            }
        });

        String result=sendOperation(projectId,userId,operation);

        return holder;
    }

    public String sendOperation(long pid,long uid,String op){
        String message="";
        return message;
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateAdapter.ViewHolder holder, int position) {
        UserItem user=musers.get(position);
        holder.tv_userMailbox.setText(user.getMailbox());
        holder.tv_username.setText("姓名： "+user.getName());
    }

    @Override
    public int getItemCount() {
        return musers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_username;
        TextView tv_userMailbox;
        Button btn_agree;
        Button btn_refuse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_username=itemView.findViewById(R.id.tv_username);
            tv_userMailbox=itemView.findViewById(R.id.tv_userMailbox);
            btn_agree=itemView.findViewById(R.id.btn_agree);
            btn_refuse=itemView.findViewById(R.id.btn_refuse);
        }
    }
}
