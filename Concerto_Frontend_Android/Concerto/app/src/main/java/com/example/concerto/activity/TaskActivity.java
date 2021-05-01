package com.example.concerto.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.concerto.R;

public class TaskActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;

    private RecyclerView mRecyclerView1;
    private HomeAdapter1 mAdapter1;

    private String[] subTaskId = {"1" , "2", "3"};
    private String[] title = {"子任务title0", "子任务title1", "子任务title2"};
    private String[] joiner = {"R1 R2 R3", "R4 R5 R6", "R7 R8 R9"};
    private boolean[] status = {true , false, false};

    private String[] user = {"letto1" , "letto2", "letto3"};
    private String[] time = {"2021.3.10 13:37", "2021.3.11 13:37", "2021.3.12 13:37"};
    private String[] message = {"balabalabalabalalalabalabala1", "balabalabalabalalalabalabala2", "balabalabalabalalalabalabala3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvSubTask);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView1 = (RecyclerView) findViewById(R.id.rvMessage);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter1 = new HomeAdapter1();
        mRecyclerView1.setAdapter(mAdapter1);
    }

    public interface OnRecyclerViewItemClickListener {
        public void onRecyclerViewItemClickListener(RecyclerView.ViewHolder holder, View view, int pos);
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClickListener(RecyclerView.ViewHolder holder, View view, int pos) {
            if(status[pos]) {
                Drawable corner_white = ResourcesCompat.getDrawable(getResources(), R.drawable.corner_white, null);
                corner_white.setBounds(0, 0, corner_white.getMinimumWidth(), corner_white.getMinimumHeight());
                Button btnStatus = (Button) holder.itemView.findViewById(R.id.btnStatus);
                btnStatus.setBackground(corner_white);
                btnStatus.setText("");
                status[pos] = false;
            } else {
                Drawable corner_darkgreen = ResourcesCompat.getDrawable(getResources(), R.drawable.corner_darkgreen, null);
                corner_darkgreen.setBounds(0, 0, corner_darkgreen.getMinimumWidth(), corner_darkgreen.getMinimumHeight());
                Button btnStatus = (Button) holder.itemView.findViewById(R.id.btnStatus);
                btnStatus.setBackground(corner_darkgreen);
                btnStatus.setText("√");
                status[pos] = true;
            }
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
            if(status[position]) {
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
            holder.title.setText(title[position]);
            holder.joiner.setText(joiner[position]);
            holder.pos = position;
        }

        @Override
        public int getItemCount() {
            return subTaskId.length;
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
            holder.user.setText(user[position]);
            holder.time.setText(time[position]);
            holder.message.setText(message[position]);
            holder.pos = position;
        }

        @Override
        public int getItemCount() {
            return user.length;
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
