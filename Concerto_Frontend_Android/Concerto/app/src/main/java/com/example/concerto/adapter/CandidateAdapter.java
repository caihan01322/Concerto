package com.example.concerto.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.activity.ProjectDetailsActivity;
import com.example.concerto.bean.UserItem;

import org.json.JSONObject;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//申请者
public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {

    List<UserItem> musers;
    long userId;
    String projectId;
    String operation;
    String message="";

    public CandidateAdapter(List<UserItem> users ,String id){
        musers=users;
        projectId=id;
    }
    public void setMusers(List<UserItem> musers) {
        this.musers = musers;
        notifyDataSetChanged();
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
                musers.remove(position);
                notifyDataSetChanged();
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

        String result=sendOperation(projectId,userId,operation,position);
        return holder;
    }

    public String sendOperation(String pid,long uid,String op,int position){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://81.69.253.27:7777//project/applicant/auth";
                    OkHttpClient client=new OkHttpClient();
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("projectId",2);
                    jsonObject.put("userId",2);
                    jsonObject.put("operation","true");
                    RequestBody requestBody = RequestBody.create(JSON,String.valueOf(jsonObject));
                    Request.Builder reqBuild = new Request.Builder();
                    reqBuild.addHeader("Content-Type","text/json;charset=UTF-8");

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                            .newBuilder();
                    reqBuild.url(urlBuilder.build());
                    reqBuild.post(requestBody);
                    Request request = reqBuild.build();

                    Response response = client.newCall(request).execute();
                    String data=response.body().string();
                    Log.v("test",data);
                    if(data != null && data.startsWith("\ufeff"))
                    {
                        data =  data.substring(1);
                    }
                    JSONObject jsonObject1=new JSONObject(data);
                    message=jsonObject.getString("message");
                    Log.v("test","*************"+jsonObject1);
                    int status = jsonObject1.getInt("status");
                    if(status==200){
                        musers.remove(position);
                        notifyDataSetChanged();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
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
