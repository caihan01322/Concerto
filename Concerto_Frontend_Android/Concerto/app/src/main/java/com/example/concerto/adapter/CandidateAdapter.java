package com.example.concerto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.bean.UserItem;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {

    List<UserItem> musers;

    public CandidateAdapter(List<UserItem> users){
        musers=users;
    }
    @NonNull
    @Override
    public CandidateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        CandidateAdapter.ViewHolder holder=new CandidateAdapter.ViewHolder(view);
        return holder;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_username=itemView.findViewById(R.id.tv_username);
            tv_userMailbox=itemView.findViewById(R.id.tv_userMailbox);

        }
    }
}