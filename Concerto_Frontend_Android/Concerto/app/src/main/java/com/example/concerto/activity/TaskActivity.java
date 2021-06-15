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
import android.os.Looper;
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

import com.example.concerto.bean.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.defaults.colorpicker.ColorPickerView;

public class TaskActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;

    private RecyclerView mRecyclerView1;
    private HomeAdapter1 mAdapter1;

    private String userId;
    private String taskId;
    private int taskVersion;
    private String taskTitle;
    private int taskPriority;
    private int taskStatus;
    private int taskType;
    private Date taskStartTime;
    private Date taskEndTime;
    private List<Tags> tags;
    private List<Participants> participants;
    private List<SubTasks> subTasks;

    private ArrayList<String> paticId;
    private ArrayList<String> particName;
    private ArrayList<String> tagContent;
    private ArrayList<String> tagColor;
    private ArrayList<Integer> subTaskId;
    private ArrayList<Integer> subTaskStatus;
    private ArrayList<String> subTaskTitle;
    private List<List<Participants>> subTaskPartic;
    private ArrayList<String> subTaskJoiner;
    private boolean[] itemStatus;

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
    private TextView tvTag1,tvTag2,tvTag3;
    private TextView tvSaveInfo;

    private List<TaskComment> comments;  //评论

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private AlertDialog alertDialog0; //对话框
    private AlertDialog alertDialog1; //多选框
    private AlertDialog alertDialog2; //单选框
    private AlertDialog alertDialog3; //多选框

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        userId = "1";
        taskId = "83";
        tags = new ArrayList<>();
        paticId = new ArrayList<>();
        particName = new ArrayList<>();
        subTasks = new ArrayList<>();
        tagContent = new ArrayList<>();
        tagColor = new ArrayList<>();
        subTaskId = new ArrayList<>();
        subTaskStatus = new ArrayList<>();
        subTaskTitle = new ArrayList<>();
        subTaskPartic = new ArrayList<>();
        subTaskJoiner = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    okhttp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void okhttp() throws IOException {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
        String url = "http://1.15.141.65:8866/task/" + taskId;
        Request request = new Request.Builder()
                .url(url)//请求接口。如果需要传参拼接到接口后面。
                .addHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2In0.BWVpUdSvtWB4DKwLpMcYiuxUBmAKBC1kfLvvEmuS61E")//头部添加token
                .build();//创建Request 对象
        Response response = null;
        response = client.newCall(request).execute();//得到Response 对象
        if (response.isSuccessful()) {
            String strByJson = response.body().string();
            System.out.println(strByJson);
            Gson gson = new Gson();
            Type listType = new TypeToken<JsonRootBean>(){}.getType();
            JsonRootBean jsonRootBean = gson.fromJson(strByJson, listType);
            if(jsonRootBean.getStatus()==200){
                Data data = jsonRootBean.getData();
                taskId = data.getTaskId() + "";
                taskVersion = data.getTaskVersion();
                taskTitle = data.getTaskTitle();
                taskStartTime = data.getTaskStartTime();
                taskEndTime = data.getTaskEndTime();
                taskPriority = data.getTaskPriority();
                participants = data.getParticipants();
                for(Participants p : participants){
                    paticId.add(p.getUserId()+"");
                    particName.add(p.getUserName());
                }
                tags = data.getTags();
                for(Tags t : tags){
                    tagContent.add(t.getTagContent());
                    tagColor.add(t.getTagColor());
                }
                subTasks = data.getSubTasks();
                for(SubTasks s : subTasks){
                    subTaskId.add(s.getTaskId());
                    subTaskStatus.add(s.getTaskStatus());
                    subTaskTitle.add(s.getTaskTitle());
                    if(s.getParticipants().size()!=0){
                        subTaskPartic.add(s.getParticipants());
                    } else {
                        subTaskPartic.add(new ArrayList<Participants>());
                    }
                }
                for(List<Participants> lp : subTaskPartic){
                    StringBuilder joiner = new StringBuilder();
                    if(lp.size()!=0){
                        for(Participants p :lp){
                            joiner.append(p.getUserName()).append(" ");
                        }
                    } else {
                        joiner.append("暂无参与者");
                    }
                    subTaskJoiner.add(joiner.toString());
                }
                itemStatus= new boolean[particName.size()];
                for(int m = 0; m<particName.size(); m++){
                    itemStatus[m] = true;
                }
            }
        }
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("任务详情");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9AD3BC")));
        }

        user = new ArrayList<>();
        time = new ArrayList<>();
        message = new ArrayList<>();

        for(int i=0;i<3;i++){
            user.add("letto"+(i+1));
            time.add("2021.3.10 13:37");
            message.add("balabalabalabalalalabalabala"+(i+1));
        }

        for(String s : subTaskJoiner){
            System.out.println(s);
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

        tvCreateSubTask = (TextView) findViewById(R.id.tvCreateSubTask);
//        tvCreateSubTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TaskActivity.this, TaskCreateActivity.class);
//                startActivity(intent);
//            }
//        });
        tvCreateSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View alertDialogView = getLayoutInflater().inflate (R.layout.subtask_layout, null, false);
                final EditText editText = (EditText) alertDialogView.findViewById(R.id.etSubTaskName);
                final TextView textView = (TextView) alertDialogView.findViewById(R.id.tvSelectJoiner);
                final boolean[] iStatus = new boolean[particName.size()];
                final StringBuilder joiner = new StringBuilder();
                for(int m = 0; m<particName.size(); m++){
                    iStatus[m] = false;
                }
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String[] items = particName.toArray(new String[particName.size()]);

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TaskActivity.this);
                        alertBuilder.setTitle("请选择任务参与者");

                        alertBuilder.setMultiChoiceItems(items, iStatus , new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                if (isChecked){
                                    Toast.makeText(TaskActivity.this, "选择" + items[i], Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(TaskActivity.this, "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean flag = false;
                                for(int m = 0; m<particName.size(); m++){
                                    if(iStatus[m]){
                                        joiner.append(items[m]).append(" ");
                                        flag = true;
                                    }
                                }
                                if(!flag){
                                    joiner.append("暂无参与者");
                                }
                                textView.setText(joiner.toString());
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
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TaskActivity.this);
                alertBuilder.setView(alertDialogView);
                alertBuilder.setTitle("请添加子任务");
                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println(editText.getText());
                        System.out.println(textView.getText());
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                                    String url = "http://1.15.141.65:8866/subtask";

                                    JSONArray participants = new JSONArray();
                                    for(int m = 0; m<particName.size(); m++){
                                        if(iStatus[m]){
                                            JSONObject Json = new JSONObject();
                                            Json.put("userId", paticId.get(m));
                                            participants.put(Json);
                                        }
                                    }
                                    JSONObject json = new JSONObject();
                                    json.put("taskVersionModifyUserId",userId);
                                    json.put("parentTaskId",taskId);
                                    json.put("taskTitle",editText.getText().toString());
                                    json.put("participants",participants);

                                    System.out.println(json.toString());
                                    RequestBody body = RequestBody.create(JSON, json.toString());
                                    Request request = new Request.Builder()
                                            .url(url)//请求接口。如果需要传参拼接到接口后面。
                                            .addHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2In0.BWVpUdSvtWB4DKwLpMcYiuxUBmAKBC1kfLvvEmuS61E")//头部添加token
                                            .put(body)
                                            .build();//创建Request 对象
                                    Response response = null;
                                    response = client.newCall(request).execute();//得到Response 对象
                                    if (response.isSuccessful()) {
                                        String strByJson = response.body().string();
                                        System.out.println(strByJson);
                                        JSONObject jsonObj = new JSONObject(strByJson);
                                        int status = jsonObj.getInt("status");
                                        String message = jsonObj.getString("message");
                                        Object data = jsonObj.get("data");
                                        if(status==200){
                                            subTaskId.add(Integer.parseInt(data.toString()));
                                            subTaskTitle.add(editText.getText().toString());
                                            subTaskStatus.add(0);
                                        //  subTaskPartic.add();
                                            if("".equals(joiner.toString())){
                                                joiner.append("暂无参与者");
                                            }
                                            subTaskJoiner.add(joiner.toString());
                                            TaskActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    mAdapter.notifyDataSetChanged();
                                                }
                                            });
                                        }
                                        Looper.prepare();
                                        Toast.makeText(TaskActivity.this,message,Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        t.start();
                        alertDialog0.dismiss();
                    }
                });
                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog0.dismiss();
                    }
                });

                alertDialog0 = alertBuilder.create();
                alertDialog0.show();
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

        tvEditStartTime = (TextView) findViewById(R.id.tvEditStartTime);
        tvEditStartTime.setText(simpleDateFormat.format(taskStartTime));
        tvEditStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentSystemDate = Calendar.getInstance();// 系统当前时间
                Calendar startDate = Calendar.getInstance();// 控件起始时间
                startDate.set(1990,1,1);// 设置该控件从1990年1月1日开始
                Calendar endDate = Calendar.getInstance();// 控件结束时间
                endDate.set(2050, 11, 31);//该控件到2050年2月28日结束
                try {
                    simpleDateFormat.format(taskStartTime);
                    //指定控件初始值显示哪一天
                    currentSystemDate.setTime(taskStartTime);
                }catch (Exception e){

                }
                // 时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(TaskActivity.this,new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date,View v) {
                        String choiceTime = simpleDateFormat.format(date);// 选择时间
                        taskStartTime = date;
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

        tvEditEndTime = (TextView) findViewById(R.id.tvEditEndTime);
        tvEditEndTime.setText(simpleDateFormat.format(taskEndTime));
        tvEditEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentSystemDate = Calendar.getInstance();// 系统当前时间
                Calendar startDate = Calendar.getInstance();// 控件起始时间
                startDate.set(1990,1,1);// 设置该控件从1990年1月1日开始
                Calendar endDate = Calendar.getInstance();// 控件结束时间
                endDate.set(2050, 11, 31);//该控件到2050年2月28日结束
                try {
                    simpleDateFormat.format(taskEndTime);
                    //指定控件初始值显示哪一天
                    currentSystemDate.setTime(taskEndTime);
                }catch (Exception e){

                }
                // 时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(TaskActivity.this,new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date,View v) {
                        String choiceTime = simpleDateFormat.format(date);// 选择时间
                        taskEndTime = date;
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

        tvEditTag = (TextView) findViewById(R.id.tvEditTag);
        tvEditTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View alertDialogView = getLayoutInflater().inflate (R.layout.alertdialog_layout, null, false);
                final ColorPickerView colorPickerView = (ColorPickerView) alertDialogView.findViewById(R.id.colorPicker);
                final EditText editText = (EditText) alertDialogView.findViewById(R.id.etTagName);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TaskActivity.this);
                alertBuilder.setView(alertDialogView);
                alertBuilder.setTitle("请添加任务tag");
                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println(editText.getText());
                        System.out.println(colorPickerView.getColor());//???
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

        final String[] items = {"普通", "有点紧急", "紧急"};
        tvEditPRI = (TextView) findViewById(R.id.tvEditPRI);
        tvEditPRI.setText(items[taskPriority]);
        tvEditPRI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TaskActivity.this);
                alertBuilder.setTitle("请选择任务优先级");
                alertBuilder.setSingleChoiceItems(items, taskPriority, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        taskPriority = i;
                        tvEditPRI.setText(items[taskPriority]);
                        Toast.makeText(TaskActivity.this, items[taskPriority], Toast.LENGTH_SHORT).show();
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

        tvEditJoiner = (TextView) findViewById(R.id.tvEditJoiner);
        StringBuilder joiner = new StringBuilder();
        for(String s : particName){
            joiner.append(s).append(" ");
        }
        tvEditJoiner.setText(joiner.toString());
        tvEditJoiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringBuilder joiner = new StringBuilder();
                final String[] items = particName.toArray(new String[particName.size()]);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TaskActivity.this);
                alertBuilder.setTitle("请选择任务参与者");

                alertBuilder.setMultiChoiceItems(items, itemStatus , new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (isChecked){
                            Toast.makeText(TaskActivity.this, "选择" + items[i], Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TaskActivity.this, "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean flag = false;
                        for(int m = 0; m<particName.size(); m++){
                            if(itemStatus[m]){
                                joiner.append(items[m]).append(" ");
                                flag = true;
                            }
                        }
                        if(!flag){
                            joiner.append("暂无参与者");
                        }
                        tvEditJoiner.setText(joiner.toString());
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

        etTaskName = (EditText) findViewById(R.id.etTaskName);
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

        etTaskName.setText(taskTitle);
        etNameLostFocus(etTaskName);

        tvTag1 = (TextView) findViewById(R.id.tvTag1);
        tvTag1.setText(tags.get(0).getTagContent());
        tvTag1.setBackgroundColor(Color.parseColor(tags.get(0).getTagColor()));
        tvTag2 = (TextView) findViewById(R.id.tvTag2);
        tvTag2.setText(tags.get(1).getTagContent());
        tvTag2.setBackgroundColor(Color.parseColor(tags.get(1).getTagColor()));
        tvTag3 = (TextView) findViewById(R.id.tvTag3);
        tvTag3.setText(tags.get(2).getTagContent());
        tvTag3.setBackgroundColor(Color.parseColor(tags.get(2).getTagColor()));

        tvSaveInfo = (TextView) findViewById(R.id.tvSaveInfo);
        tvSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            saveInfo();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
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

    private void saveInfo() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
        String url = "http://1.15.141.65:8866/task/";

        JSONArray addTags =new JSONArray();
        JSONArray delTags =new JSONArray();
        JSONArray addParticipants = new JSONArray();
        JSONArray delParticipants = new JSONArray();

        JSONObject json = new JSONObject();
        json.put("taskId",taskId);
        json.put("taskVersionModifyUserId",userId);
        json.put("taskVersionDescription","修改了一大堆东西");
        json.put("taskTitle",etTaskName.getText().toString());
        json.put("taskType","0");
        json.put("taskPriority",taskPriority);
        json.put("taskStartTime",simpleDateFormat.format(taskStartTime));
        json.put("taskEndTime",simpleDateFormat.format(taskEndTime));
        json.put("taskVersion",taskVersion);
        json.put("addTags",addTags);    //待完善
        json.put("delTags",delTags);    //待完善
        json.put("addParticipants",addParticipants);    //待完善
        json.put("delParticipants",delParticipants);    //待完善

        System.out.println(json.toString());
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(url)//请求接口。如果需要传参拼接到接口后面。
                .addHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2In0.BWVpUdSvtWB4DKwLpMcYiuxUBmAKBC1kfLvvEmuS61E")//头部添加token
                .post(body)
                .build();//创建Request 对象
        Response response = null;
        response = client.newCall(request).execute();//得到Response 对象
        if (response.isSuccessful()) {
            String strByJson = response.body().string();
            System.out.println(strByJson);
            JSONObject jsonObj = new JSONObject(strByJson);
            int status = jsonObj.getInt("status");
            String message = jsonObj.getString("message");
            Object data = jsonObj.get("data");
            taskVersion = Integer.parseInt(data.toString());
            Looper.prepare();
            Toast.makeText(TaskActivity.this,message,Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
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

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClickListener(RecyclerView.ViewHolder holder, View view, final int pos) {
            if(subTaskStatus.get(pos) == 1) {
                Drawable corner_white = ResourcesCompat.getDrawable(getResources(), R.drawable.corner_white, null);
                corner_white.setBounds(0, 0, corner_white.getMinimumWidth(), corner_white.getMinimumHeight());
                Button btnStatus = (Button) holder.itemView.findViewById(R.id.btnStatus);
                btnStatus.setBackground(corner_white);
                btnStatus.setText("");
                subTaskStatus.set(pos,0);
            } else {
                Drawable corner_darkgreen = ResourcesCompat.getDrawable(getResources(), R.drawable.corner_darkgreen, null);
                corner_darkgreen.setBounds(0, 0, corner_darkgreen.getMinimumWidth(), corner_darkgreen.getMinimumHeight());
                Button btnStatus = (Button) holder.itemView.findViewById(R.id.btnStatus);
                btnStatus.setBackground(corner_darkgreen);
                btnStatus.setText("√");
                subTaskStatus.set(pos,1);
            }
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        updateStatus(pos);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    };

    private void updateStatus(int pos) throws JSONException, IOException {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
        String url = "http://1.15.141.65:8866//task/status/" + subTaskId.get(pos);

        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(url)//请求接口。如果需要传参拼接到接口后面。
                .addHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2In0.BWVpUdSvtWB4DKwLpMcYiuxUBmAKBC1kfLvvEmuS61E")//头部添加token
                .post(body)
                .build();//创建Request 对象
        Response response = null;
        response = client.newCall(request).execute();//得到Response 对象
        if (response.isSuccessful()) {
            String strByJson = response.body().string();
            System.out.println(strByJson);
            JSONObject jsonObj = new JSONObject(strByJson);
            int status = jsonObj.getInt("status");
            String message = jsonObj.getString("message");
            Looper.prepare();
            Toast.makeText(TaskActivity.this,subTaskTitle.get(pos)+message,Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TaskActivity.this).inflate(R.layout.item_task,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if(subTaskStatus.get(position) == 1) {
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
            holder.title.setText(subTaskTitle.get(position));
            holder.joiner.setText(subTaskJoiner.get(position));
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
