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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserItem> musers;

    public UserAdapter(List<UserItem> users){
        musers=users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    public void setMusers(List<UserItem> musers) {
        this.musers = musers;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

