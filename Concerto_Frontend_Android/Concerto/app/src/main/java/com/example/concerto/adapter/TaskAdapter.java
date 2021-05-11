package com.example.concerto.adapter;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.bean.TaskItem;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskItem> mtasks;
    String[] colors={"#FFB6C1","#BA55D3","#1E90FF","#00FFFF","#00FF7F"};
    Context mcontext;

    public TaskAdapter(List<TaskItem> tasks ,Context context){
        mtasks=tasks;
        mcontext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        ViewHolder holder=new ViewHolder(view);

        //点击跳转至任务表单
        holder.task_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return holder;
    }

    public void setData(List<TaskItem> tasks){
        mtasks=tasks;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskItem task=mtasks.get(position);
        int days=task.getDays();
        holder.tv_days.setText(days+"天");
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
        holder.timebar.setBackgroundColor(Color.parseColor(colors[position%5]));

        //如果完成则。。。
        if(task.getComplete()==1){
            holder.tv_complete.setText("*");
            holder.tv_complete.setTextColor(Color.parseColor(colors[position%5]));
            holder.line.setBackgroundColor(Color.parseColor("#808080"));
        }

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

            holder.names.addView(textView);
        }



    }

    @Override
    public int getItemCount() {
        return mtasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout task_item_layout;
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
            task_item_layout=itemView.findViewById(R.id.task_item);
            tv_days=itemView.findViewById(R.id.tv_task_days);
        }
    }
}
