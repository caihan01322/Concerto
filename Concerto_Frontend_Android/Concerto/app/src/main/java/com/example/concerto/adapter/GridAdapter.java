package com.example.concerto.adapter;



import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;

import java.util.ArrayList;
import java.util.List;


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    List<String> data;
    List<String> tags;
    List<String> names;
    int type;//0 tags 1 names;

    public List<String> getNames() {
        return names;
    }

    public List<String> getTags() {
        return tags;
    }

    public GridAdapter(List<String> datas, int type){
        data=datas;
        this.type=type;
        tags=new ArrayList<>();
        names=new ArrayList<>();
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tags_view,parent,false);
        GridAdapter.ViewHolder holder=new GridAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
        Log.v("test","999999999"+data);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            int flag=0;
            @Override
            public void onClick(View v) {
                switch (flag){
                    case 0:
                        flag=1;
                        holder.textView.setBackgroundColor(Color.parseColor("#2FFFC107"));
                        try{
                        if(type==0){
                            for(int i=0;i<tags.size();i++){
                                if(tags.get(i).equals(data.get(position)))
                                    tags.remove(i);
                            }
                        }else if(type==1){
                            for(int i=0;i<names.size();i++){
                                if(names.get(i).equals(data.get(position)))
                                    names.remove(i);
                            }
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        flag=0;
                        holder.textView.setBackgroundColor(Color.parseColor("#60DD65"));
                        try{
                        if(type==0){
                            tags.add(data.get(position));
                        }else if(type==1){
                            names.add(data.get(position));
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                }

                Log.v("testtags","99999tag9999"+tags);
                Log.v("testnames","99999names9999"+names);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           textView=itemView.findViewById(R.id.tv_tag_item);
        }
    }
}
