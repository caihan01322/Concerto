package com.example.concerto.popwindow;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.appcompat.app.AlertDialog;

import com.example.concerto.R;

import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PopWindowJoinProject extends PopupWindow{
    private View conentView;
    private int h;
    private int w;
    public PopWindowJoinProject(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.activity_join_project_pop_window, null);
        h = context.getWindowManager().getDefaultDisplay().getHeight();
        w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w/2+200);
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

        Button cancelButton = conentView.findViewById(R.id.JoinCancelButton);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Button confirmButton = conentView.findViewById(R.id.JoinConfirmButton);
        EditText inviteCodeEditText = conentView.findViewById(R.id.InviteCodeEditText);

        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            String inviteCode = inviteCodeEditText.getText().toString();
                            String url = "http://81.69.253.27:7777//Project/Join/"+inviteCode;
                            String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0";
                            OkHttpClient client=new OkHttpClient();
                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            JSONObject jsonObject = new JSONObject();
                            RequestBody requestBody = RequestBody.create(JSON,String.valueOf(jsonObject));
                            Request.Builder reqBuild = new Request.Builder();
                            reqBuild.addHeader("Content-Type","text/json;charset=UTF-8")
                                    .addHeader("token",token);

                            HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
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
                            int status = jsonObject1.getInt("status");
                            Looper.prepare();
                            if (status==400)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("尝试加入一个无管理者的项目或者项目不存在");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }
                            else if (status==401)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("请勿重复加入项目");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }
                            else if (status==200)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("成功加入项目");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }
                            else if (status==404)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("邀请码为空，请输入邀请码");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }
                            Looper.loop();
                        }
                        catch (Exception e){
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
            this.showAsDropDown(parent, 200, 400);
        } else {
            this.dismiss();
        }
    }
}