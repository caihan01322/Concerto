package com.example.concerto.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.concerto.R;

import com.example.concerto.bean.*;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;

    private RecyclerView mRecyclerView1;
    private HomeAdapter1 mAdapter1;

    private ArrayList<String> subTaskId;
    private ArrayList<String> title;
    private ArrayList<String> joiner;
    private ArrayList<Integer> status;

    private ArrayList<String> user;
    private ArrayList<String> time;
    private ArrayList<String> message;

    private TextView tvEditRecord;
    private TextView tvSaveSubTask;
    private EditText etMessage;
    private TextView tvSaveMessage;
    private List<Subtask> subTasks;      //子任务
    private List<TaskComment> comments;  //评论

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("任务详情");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9AD3BC")));
        }

        subTaskId = new ArrayList<>();
        title = new ArrayList<>();
        joiner = new ArrayList<>();
        status = new ArrayList<>();

        user = new ArrayList<>();
        time = new ArrayList<>();
        message = new ArrayList<>();

        for(int i=0;i<3;i++){
            subTaskId.add(i+"");
            title.add("子任务title"+i);
            joiner.add("R"+(i*3+1)+" R"+(i*3+2)+" R"+(i*3+3));
            status.add(1);

            user.add("letto"+(i+1));
            time.add("2021.3.10 13:37");
            message.add("balabalabalabalalalabalabala"+(i+1));
        }

        etMessage = (EditText) findViewById(R.id.etMessage);
        tvSaveMessage = (TextView) findViewById(R.id.tvSaveMessage);
        tvSaveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = etMessage.getText().toString();
                if(!mes.equals("")){
                    user.add("letto0");
                    time.add("2021.3.10 13:30");
                    message.add(mes);
                    mAdapter1.notifyDataSetChanged();
                    Toast.makeText(TaskActivity.this,"留言成功！",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TaskActivity.this,"留言内容不能为空！",Toast.LENGTH_LONG).show();
                }
            }
        });

        tvSaveSubTask = (TextView) findViewById(R.id.tvSaveSubTask);
        tvSaveSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskActivity.this, TaskCreateActivity.class);
                startActivity(intent);
            }
        });

        tvEditRecord = (TextView) findViewById(R.id.tvEditRecord);
        tvEditRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskActivity.this, TaskEditRecActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rvSubTask);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView1 = (RecyclerView) findViewById(R.id.rvMessage);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter1 = new HomeAdapter1();
        mRecyclerView1.setAdapter(mAdapter1);
    }

    public interface OnRecyclerViewItemClickListener {
        public void onRecyclerViewItemClickListener(RecyclerView.ViewHolder holder, View view, int pos);
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClickListener(RecyclerView.ViewHolder holder, View view, int pos) {
            if(status.get(pos) == 1) {
                Drawable corner_white = ResourcesCompat.getDrawable(getResources(), R.drawable.corner_white, null);
                corner_white.setBounds(0, 0, corner_white.getMinimumWidth(), corner_white.getMinimumHeight());
                Button btnStatus = (Button) holder.itemView.findViewById(R.id.btnStatus);
                btnStatus.setBackground(corner_white);
                btnStatus.setText("");
                status.set(pos,0);
            } else {
                Drawable corner_darkgreen = ResourcesCompat.getDrawable(getResources(), R.drawable.corner_darkgreen, null);
                corner_darkgreen.setBounds(0, 0, corner_darkgreen.getMinimumWidth(), corner_darkgreen.getMinimumHeight());
                Button btnStatus = (Button) holder.itemView.findViewById(R.id.btnStatus);
                btnStatus.setBackground(corner_darkgreen);
                btnStatus.setText("√");
                status.set(pos,1);
            }
            Toast.makeText(TaskActivity.this,"修改任务状态成功！",Toast.LENGTH_SHORT).show();
        }
    };

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TaskActivity.this).inflate(R.layout.item_task,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if(status.get(position) == 1) {
                Drawable corner_darkgreen = ResourcesCompat.getDrawable(getResources(), R.drawable.corner_darkgreen, null);
                corner_darkgreen.setBounds(0, 0, corner_darkgreen.getMinimumWidth(), corner_darkgreen.getMinimumHeight());
                holder.status.setBackground(corner_darkgreen);
                holder.status.setText("√");
            } else {
                Drawable corner_white = ResourcesCompat.getDrawable(getResources(), R.drawable.corner_white, null);
                corner_white.setBounds(0, 0, corner_white.getMinimumWidth(), corner_white.getMinimumHeight());
                holder.status.setBackground(corner_white);
                holder.status.setText("");
            }
            holder.title.setText(title.get(position));
            holder.joiner.setText(joiner.get(position));
            holder.pos = position;
        }

        @Override
        public int getItemCount() {
            return subTaskId.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            Button status;
            TextView title;
            TextView joiner;
            int pos;
            public MyViewHolder(View itemView) {
                super(itemView);
                status = (Button) itemView.findViewById(R.id.btnStatus);
                title = (TextView) itemView.findViewById(R.id.tvTitle);
                joiner = (TextView) itemView.findViewById(R.id.tvJoiner);
                status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _click(v);
                    }
                });
            }
            private final void _click(View v) {
                mOnRecyclerViewItemClickListener.onRecyclerViewItemClickListener(this, v, pos);
            }
        }
    }

    class HomeAdapter1 extends RecyclerView.Adapter<HomeAdapter1.MyViewHolder>{
        @Override
        public HomeAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TaskActivity.this).inflate(R.layout.item_message,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.user.setText(user.get(position));
            holder.time.setText(time.get(position));
            holder.message.setText(message.get(position));
            holder.pos = position;
        }

        @Override
        public int getItemCount() {
            return user.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView user;
            TextView time;
            TextView message;
            int pos;
            public MyViewHolder(View itemView) {
                super(itemView);
                user = (TextView) itemView.findViewById(R.id.tvUser);
                time = (TextView) itemView.findViewById(R.id.tvTime);
                message = (TextView) itemView.findViewById(R.id.tvMessage);
            }
        }
    }
}
