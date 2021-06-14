package com.example.concerto.adapter;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.activity.NavActivity;
import com.example.concerto.bean.TagsItem;
import com.example.concerto.bean.TaskItem;
import com.example.concerto.fragment.TaskListFragment;

import org.json.JSONObject;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskItem> mtasks;
    String[] colors={"#FFB6C1","#BA55D3","#1E90FF","#00FFFF","#00FF7F"};
    Context mcontext;
    TaskListFragment fragment;
    int type;

    public void setType(int type) {
        this.type = type;
    }

    public TaskAdapter(List<TaskItem> tasks , Context context, TaskListFragment fragment){
        mtasks=tasks;
        mcontext=context;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    public void setData(List<TaskItem> tasks){
        mtasks=tasks;
        this.notifyDataSetChanged();
    }

    public void setNewData(List<TaskItem> tasks){
        mtasks.clear();
        mtasks.addAll(tasks);
        this.notifyDataSetChanged();
        //Log.v("testprojectselect","*******888888****");
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskItem task=mtasks.get(position);
        int days=task.getDays();

        holder.line.setBackgroundColor(Color.parseColor(colors[position%5]));
        holder.tv_title.setText(task.getTaskTitle());

        int tw=180,th=70;//tag的宽和高
        int nw=100,nh=70;//name的宽和高
        int strokeWidth = 5;//边框宽度
        int roundRadius = 38; // 圆角半径

        //左上角圆框
        int strokeColor_complete = Color.parseColor(colors[position%5]);//边框颜色
        GradientDrawable gdc = new GradientDrawable();
        gdc.setStroke(strokeWidth, strokeColor_complete);
        gdc.setCornerRadius(roundRadius);
        holder.tv_complete.setBackgroundDrawable(gdc);
        holder.tv_process.setBackground(gdc);


        //如果完成则。。。
        if(task.getComplete()==1){
            holder.tv_complete.setTextColor(Color.parseColor(colors[position%5]));
            holder.line.setBackgroundColor(Color.parseColor("#808080"));
            holder.tv_complete.setText("√");
        }

        if(task.getComplete()==0){
            holder.timebar.setBackgroundColor(Color.parseColor(colors[position%5]));
            if(days>0)
                holder.tv_days.setText(days+"天");
            else
                holder.tv_days.setText("超时");
            holder.tv_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskItem task=mtasks.get(position);
                    fragment.completeTask(task,position);

                    //完成任务，将状态更新至服务器
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String url = "http://81.69.253.27:7777/task/status"+"/"+task.getTaskId();
                                OkHttpClient client=new OkHttpClient();
                                MediaType JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
                                RequestBody requestBody = RequestBody.create(JSON,task.getTaskId());
                                Request.Builder reqBuild = new Request.Builder();

                                HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                                        .newBuilder();
                                reqBuild.url(urlBuilder.build());
                                reqBuild.post(requestBody);
                                Request request = reqBuild.build();

                                Response response = client.newCall(request).execute();
                                String data=response.body().string();
                                //Log.v("test","------"+data);


                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        }


        holder.tv_process.setText(task.getSubTaskCompletedNum()+"/"+task.getSubTaskNum());

        //添加Tags
        for(int i=0;i<task.getTags().size();i++){
            if(i>=3)
                break;
            TextView textView = new TextView(mcontext);
            textView.setText(task.tags.get(i).tagContent);
            textView.setWidth(tw);
            textView.setHeight(th);

            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(15, 0, 0, 0);
            textView.setLayoutParams(layout);

            textView.setGravity(Gravity.CENTER);

            //设置圆角
            int strokeColor = Color.parseColor(task.tags.get(i).tagColor);//边框颜色
            int fillColor = Color.parseColor(task.tags.get(i).tagColor);//内部填充颜色

            GradientDrawable gd = new GradientDrawable();//创建drawable
            gd.setColor(fillColor);
            gd.setCornerRadius(roundRadius);
            gd.setStroke(strokeWidth, strokeColor);
            textView.setBackgroundDrawable(gd);
            holder.tags.addView(textView);
        }

        //添加成员
        for(int i=0;i<task.getNames().size();i++){
            if(i>=3)
                break;
            TextView textView = new TextView(mcontext);
            textView.setText(task.getNames().get(i));
            textView.setWidth(nw);
            textView.setHeight(nh);
            
            //textView.setBackgroundColor(Color.parseColor(colors[i%5]));
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(0, 2, 12, 0);
            textView.setLayoutParams(layout);

            textView.setGravity(Gravity.CENTER);

            /**
            int strokeColor = Color.parseColor(colors[i%5]);//边框颜色
            int fillColor = Color.parseColor(colors[i%5]);//内部填充颜色

            GradientDrawable gd = new GradientDrawable();//创建drawable
            gd.setColor(fillColor);
            gd.setCornerRadius(roundRadius);
            gd.setStroke(strokeWidth, strokeColor);
            textView.setBackgroundDrawable(gd);
             */
            textView.setSingleLine();
            holder.names.addView(textView);
        }

        if(type==4||type==5){
            //点击跳转至任务表单
            holder.clickArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=mtasks.get(position).getTaskId();
                    Intent intent=new Intent(mcontext, NavActivity.class);
                    intent.putExtra("taskId",id);
                    mcontext.startActivity(intent);
                }
            });
            holder.clickArea2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=mtasks.get(position).getTaskId();
                    Intent intent=new Intent(mcontext, NavActivity.class);
                    intent.putExtra("taskId",id);
                    mcontext.startActivity(intent);
                }
            });
            holder.tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=mtasks.get(position).getTaskId();
                    Intent intent=new Intent(mcontext, NavActivity.class);
                    intent.putExtra("taskId",id);
                    mcontext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mtasks.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout clickArea;
        LinearLayout clickArea2;
        View line;
        TextView tv_days;
        TextView tv_complete;
        TextView tv_title;
        TextView tv_process;
        LinearLayout tags;
        LinearLayout names;
        TextView timebar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            line=itemView.findViewById(R.id.view_line);
            tv_complete=itemView.findViewById(R.id.btn_task_item_complete);
            tv_title=itemView.findViewById(R.id.tv_task_item_title);
            tv_process=itemView.findViewById(R.id.tv_task_item_processbar);
            tags=itemView.findViewById(R.id.line_tags);
            names=itemView.findViewById(R.id.line_names);
            timebar=itemView.findViewById(R.id.tv_task_item_time);
            clickArea=itemView.findViewById(R.id.click_area);
            clickArea2=itemView.findViewById(R.id.click_area2);
            tv_days=itemView.findViewById(R.id.tv_task_days);
        }
    }
}
