package com.example.concerto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.bean.SubProject;

import java.util.List;

public class SubProjectAdapter extends RecyclerView.Adapter<SubProjectAdapter.ViewHolder>{

    private List<SubProject> mSubProjectList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView description;
        TextView laucher;
        TextView time;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.AllTitleTextView);
            description = (TextView)view.findViewById(R.id.AllDescriptionTextView);
            laucher = (TextView)view.findViewById(R.id.LaucherTextView);
            time = (TextView)view.findViewById(R.id.LauchTimeTextView);
        }
    }

    public SubProjectAdapter(List<SubProject> mSubProjectList) {
        this.mSubProjectList = mSubProjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubProject subProject = mSubProjectList.get(position);
        holder.name.setText(subProject.getName());
        holder.description.setText(subProject.getDescription());
        holder.laucher.setText(subProject.getLaucher());
        holder.time.setText(subProject.getTime());
    }

    @Override
    public int getItemCount() {
        return mSubProjectList.size();
    }

    public void addData(int position,SubProject subProject) {
        mSubProjectList.add(subProject);
        notifyItemInserted(position);
    }

}
