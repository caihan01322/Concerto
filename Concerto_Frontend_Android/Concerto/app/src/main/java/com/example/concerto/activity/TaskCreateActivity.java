package com.example.concerto.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.concerto.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaskCreateActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TaskCreateActivity.HomeAdapter mAdapter;

    private RecyclerView mRecyclerView1;
    private TaskCreateActivity.HomeAdapter1 mAdapter1;

    private ArrayList<String> subTaskId;
    private ArrayList<String> title;
    private ArrayList<String> joiner;
    private ArrayList<Integer> status;

    private ArrayList<String> user;
    private ArrayList<String> time;
    private ArrayList<String> message;

    private EditText etTaskName;
    private TextView tvEditStartTime;
    private TextView tvEditEndTime;
    private TextView tvEditTag;
    private TextView tvEditPRI;
    private TextView tvEditJoiner;
    private TextView tvEditRecord;
    private TextView tvCreateSubTask;
    private EditText etMessage;
    private TextView tvSaveMessage;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private AlertDialog alertDialog1; //多选框
    private AlertDialog alertDialog2; //单选框
    private AlertDialog alertDialog3; //多选框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
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
            actionBar.setTitle("新建任务");
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

//        etMessage = (EditText) TaskCreateActivity.this.findViewById(R.id.etMessage);
//        tvSaveMessage = (TextView) TaskCreateActivity.this.findViewById(R.id.tvSaveMessage);
//        tvSaveMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String mes = etMessage.getText().toString();
//                if(!mes.equals("")){
//                    user.add("letto0");
//                    time.add("2021.3.10 13:30");
//                    message.add(mes);
//                    mAdapter1.notifyDataSetChanged();
//                    Toast.makeText(TaskCreateActivity.this,"留言成功！",Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(TaskCreateActivity.this,"留言内容不能为空！",Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        tvCreateSubTask = (TextView) TaskCreateActivity.this.findViewById(R.id.tvCreateSubTask);
        tvCreateSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskCreateActivity.this, TaskCreateActivity.class);
                startActivity(intent);
            }
        });

        tvEditRecord = (TextView) TaskCreateActivity.this.findViewById(R.id.tvEditRecord);
        tvEditRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskCreateActivity.this, TaskEditRecActivity.class);
                startActivity(intent);
            }
        });

        tvEditStartTime = (TextView) TaskCreateActivity.this.findViewById(R.id.tvEditStartTime);
        tvEditStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentSystemDate = Calendar.getInstance();// 系统当前时间
                Calendar startDate = Calendar.getInstance();// 控件起始时间
                startDate.set(1990,1,1);// 设置该控件从1990年1月1日开始
                Calendar endDate = Calendar.getInstance();// 控件结束时间
                endDate.set(2050, 11, 31);//该控件到2050年2月28日结束
                try {
                    // 获取系统当前时间
                    Date date = new Date();
                    simpleDateFormat.format(date);
                    //指定控件初始值显示哪一天
                    currentSystemDate.setTime(date);
                }catch (Exception e){

                }
                // 时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(TaskCreateActivity.this,new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date,View v) {
                        String choiceTime = simpleDateFormat.format(date);// 选择时间
                        long longTime = date.getTime();// 日期long--例如：1595489105000
                        tvEditStartTime.setText(choiceTime);
                    }
                }).setDate(currentSystemDate)// 设置系统时间为当前时间
                        .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                        .setType(new boolean[]{true, true, true, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .isCenterLabel(true)// 是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setDividerColor(0xFF24AD9D)// 分割线颜色
                        .isCyclic(true)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                        .build();
                pvTime.show();
            }
        });

        tvEditEndTime = (TextView) TaskCreateActivity.this.findViewById(R.id.tvEditEndTime);
        tvEditEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentSystemDate = Calendar.getInstance();// 系统当前时间
                Calendar startDate = Calendar.getInstance();// 控件起始时间
                startDate.set(1990,1,1);// 设置该控件从1990年1月1日开始
                Calendar endDate = Calendar.getInstance();// 控件结束时间
                endDate.set(2050, 11, 31);//该控件到2050年2月28日结束
                try {
                    // 获取系统当前时间
                    Date date = new Date();
                    simpleDateFormat.format(date);
                    //指定控件初始值显示哪一天
                    currentSystemDate.setTime(date);
                }catch (Exception e){

                }
                // 时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(TaskCreateActivity.this,new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date,View v) {
                        String choiceTime = simpleDateFormat.format(date);// 选择时间
                        tvEditEndTime.setText(choiceTime);
                    }
                }).setDate(currentSystemDate)// 设置系统时间为当前时间
                        .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                        .setType(new boolean[]{true, true, true, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .isCenterLabel(true)// 是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setDividerColor(0xFF24AD9D)// 分割线颜色
                        .isCyclic(true)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                        .build();
                pvTime.show();
            }
        });

        tvEditTag = (TextView) TaskCreateActivity.this.findViewById(R.id.tvEditTag);
        tvEditTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"tag1", "tag2", "tag3"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TaskCreateActivity.this);
                alertBuilder.setTitle("请选择任务tag");

                alertBuilder.setMultiChoiceItems(items, new boolean[]{true, true, true}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (isChecked){
                            Toast.makeText(TaskCreateActivity.this, "选择" + items[i], Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TaskCreateActivity.this, "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog1.dismiss();
                    }
                });

                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog1.dismiss();
                    }
                });

                alertDialog1 = alertBuilder.create();
                alertDialog1.show();
            }
        });

        tvEditPRI = (TextView) TaskCreateActivity.this.findViewById(R.id.tvEditPRI);
        tvEditPRI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"普通", "有点紧急", "紧急"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TaskCreateActivity.this);
                alertBuilder.setTitle("请选择任务优先级");
                alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvEditPRI.setText(items[i]);
                        Toast.makeText(TaskCreateActivity.this, items[i], Toast.LENGTH_SHORT).show();
                    }
                });

                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog2.dismiss();
                    }
                });

                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog2.dismiss();
                    }
                });

                alertDialog2 = alertBuilder.create();
                alertDialog2.show();
            }
        });

        tvEditJoiner = (TextView) TaskCreateActivity.this.findViewById(R.id.tvEditJoiner);
        tvEditJoiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"R1", "R2", "R3", "R4"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TaskCreateActivity.this);
                alertBuilder.setTitle("请选择任务参与者");

                alertBuilder.setMultiChoiceItems(items, new boolean[]{true, true, true, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (isChecked){
                            Toast.makeText(TaskCreateActivity.this, "选择" + items[i], Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TaskCreateActivity.this, "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog3.dismiss();
                    }
                });

                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog3.dismiss();
                    }
                });

                alertDialog3 = alertBuilder.create();
                alertDialog3.show();
            }
        });

        etTaskName = (EditText) TaskCreateActivity.this.findViewById(R.id.etTaskName);
        etTaskName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etNameGetFocus(etTaskName);
                } else {
                    etNameLostFocus(etTaskName);
                }
            }
        });

        String name = "XXXXXXXXXXXXX";
        etTaskName.setText(name);
        etNameLostFocus(etTaskName);

//        mRecyclerView = (RecyclerView) TaskCreateActivity.this.findViewById(R.id.rvSubTask);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//        mAdapter = new TaskCreateActivity.HomeAdapter();
//        mRecyclerView.setAdapter(mAdapter);
//
//        mRecyclerView1 = (RecyclerView) TaskCreateActivity.this.findViewById(R.id.rvMessage);
//        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));
//        //mRecyclerView1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//        mAdapter1 = new TaskCreateActivity.HomeAdapter1();
//        mRecyclerView1.setAdapter(mAdapter1);
    }


    // 重置edittext, 居中并失去焦点
    private void etNameLostFocus(EditText etName) {
        etName.setGravity(Gravity.CENTER);
        etName.clearFocus();
        InputMethodManager manager = (InputMethodManager) etName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(etName.getWindowToken(), 0);
    }

    // 获取焦点
    private void etNameGetFocus(final EditText etName) {
        etName.requestFocus();
        etName.setGravity(Gravity.START);
        etName.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager manager = (InputMethodManager) etName.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                manager.showSoftInput(etName, 0);
            }
        });
        // 光标置于文字最后
        etName.setSelection(etName.getText().length());
    }

    public interface OnRecyclerViewItemClickListener {
        public void onRecyclerViewItemClickListener(RecyclerView.ViewHolder holder, View view, int pos);
    }

    private TaskCreateActivity.OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = new TaskCreateActivity.OnRecyclerViewItemClickListener() {
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
            Toast.makeText(TaskCreateActivity.this,"修改任务状态成功！",Toast.LENGTH_SHORT).show();
        }
    };

    class HomeAdapter extends RecyclerView.Adapter<TaskCreateActivity.HomeAdapter.MyViewHolder>{
        @Override
        public TaskCreateActivity.HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TaskCreateActivity.this).inflate(R.layout.item_task,parent,false);
            TaskCreateActivity.HomeAdapter.MyViewHolder holder = new TaskCreateActivity.HomeAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(TaskCreateActivity.HomeAdapter.MyViewHolder holder, int position) {
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

    class HomeAdapter1 extends RecyclerView.Adapter<TaskCreateActivity.HomeAdapter1.MyViewHolder>{
        @Override
        public TaskCreateActivity.HomeAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TaskCreateActivity.this).inflate(R.layout.item_message,parent,false);
            TaskCreateActivity.HomeAdapter1.MyViewHolder holder = new TaskCreateActivity.HomeAdapter1.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(TaskCreateActivity.HomeAdapter1.MyViewHolder holder, int position) {
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
