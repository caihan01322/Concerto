package com.example.concerto.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.TaskItem;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskItem> mtasks;
    String[] colors={"#778899","#ff8c00","#fa8072","#7fff00","#1e90ff"};

    public TaskAdapter(List<TaskItem> tasks){
        mtasks=tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskItem task=mtasks.get(position);
        int days=task.getDays();
        holder.line.setBackground(Drawable.createFromPath(colors[days%5]));



    }

    @Override
    public int getItemCount() {
        return mtasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View line;
        TextView tv_complete;
        TextView tv_title;
        TextView tv_process;
        LinearLayout tags;
        LinearLayout names;
        ProgressBar timebar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            line=itemView.findViewById(R.id.view_line);
            tv_complete=itemView.findViewById(R.id.btn_task_item_complete);
            tv_title=itemView.findViewById(R.id.tv_task_item_title);
            tv_process=itemView.findViewById(R.id.tv_task_item_processbar);
            tags=itemView.findViewById(R.id.line_tags);
            names=itemView.findViewById(R.id.line_names);
            timebar=itemView.findViewById(R.id.tv_task_item_timebar);
        }
    }
}
