package com.example.concerto.popwindow;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;

import com.example.concerto.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PopWindowAddProject extends PopupWindow{
    private View conentView;

    private int h;
    private int w;
    public PopWindowAddProject(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.activity_add_project, null);
        h = context.getWindowManager().getDefaultDisplay().getHeight();
        w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w-300);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        this.setClippingEnabled(false);


        EditText beginEditText = conentView.findViewById(R.id.BeginEditText);
        EditText endEditText = conentView.findViewById(R.id.EndEditText);
        Button projectCancelButton = conentView.findViewById(R.id.ProjectCancelButton);
        Button projectConfirmButton = conentView.findViewById(R.id.ProjectConfrimButton);
        EditText projectNameEditText = conentView.findViewById(R.id.ProjectNameEditText);
        EditText projectDescriptionEditText = conentView.findViewById(R.id.ProjectDescriptionEditText);

        beginEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = inflater.inflate(R.layout.date_selector, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                //设置日期简略显示 否则详细显示 包括:星期\周
                datePicker.setCalendarViewShown(false);
                //初始化当前日期
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), null);
                builder.setView(view);
                builder.setTitle("设置日期信息");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //日期格式
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth()+1;
                        int day = datePicker.getDayOfMonth();
                        beginEditText.setText(year+"-"+month+"-"+day);
                        beginEditText.setSelection(beginEditText.getText().length());
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });

        endEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = inflater.inflate(R.layout.date_selector, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                //设置日期简略显示 否则详细显示 包括:星期\周
                datePicker.setCalendarViewShown(false);
                //初始化当前日期
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), null);
                builder.setView(view);
                builder.setTitle("设置日期信息");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //日期格式
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth()+1;
                        int day = datePicker.getDayOfMonth();
                        endEditText.setText(year+"-"+month+"-"+day);
                        endEditText.setSelection(endEditText.getText().length());
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });

        projectCancelButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               dismiss();
            }
        });

        projectConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = projectNameEditText.getText().toString();
                String beginTime = beginEditText.getText().toString();
                String endTime = endEditText.getText().toString();
                String description = projectDescriptionEditText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0";
                            OkHttpClient client=new OkHttpClient();
                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("projectName",name);
                            jsonObject.put("projectDescription",description);
                            jsonObject.put("projectStartTime",beginTime);
                            jsonObject.put("projectEndTime",endTime);
                            RequestBody requestBody = RequestBody.create(JSON,String.valueOf(jsonObject));

                            Request.Builder reqBuild = new Request.Builder();
                            reqBuild.addHeader("Content-Type","text/json;charset=UTF-8")
                                    .addHeader("token",token);
                            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://81.69.253.27:7777//Project")
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
                            Log.v("test",""+jsonObject1.getInt("status"));
                            String inviteCode = jsonObject1.getString("data");
                            Looper.prepare();
                            if (jsonObject1.getInt("status")==200)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("成功创建项目,邀请码为"+inviteCode);
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.create().show();

                            }
                            else if (jsonObject1.getInt("status")==403)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("输入信息不完整,无法创建项目");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.create().show();

                            }
                            Looper.loop();
                            Log.v("message","message:"+jsonObject1.getString("message"));

                        }catch (Exception e){
                            e.printStackTrace();

                        }
                    }
                }).start();
            }
        });

    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 150, 100);
        } else {
            this.dismiss();
        }
    }
}