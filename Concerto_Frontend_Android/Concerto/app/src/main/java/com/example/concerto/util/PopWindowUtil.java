package com.example.concerto.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.adapter.GridAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PopWindowUtil extends PopupWindow implements View.OnClickListener{

    int layout;
    private View conentView;
    int h,w;
    int x,y;
    int width;
    String tagData;
    String nameData;
    JSONArray jsonArray;

    List<String> tags;
    List<String> names;
    RecyclerView rv_tags;
    RecyclerView rv_names;
    LinearLayout line_time;
    LinearLayout line_urgent;
    LinearLayout line_about_me;
    ImageView iv_time;
    ImageView iv_urgent;
    ImageView iv_me;

    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public void setWidth(int width) {
        this.width = w/2+width;
    }

    public PopWindowUtil(final Activity context, int layout, int width, int x, int y){
        setLayout(layout);
        setWidth(width);
        this.x=x;
        this.y=y;
        createPopWindow(context);

    }

    public void createPopWindow(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        conentView = inflater.inflate(layout, null);

        tags=new ArrayList<>();
        names=new ArrayList<>();
        rv_tags=conentView.findViewById(R.id.rv_tags);
        rv_names=conentView.findViewById(R.id.rv_names);
        line_time=conentView.findViewById(R.id.line_time);
        iv_time=conentView.findViewById(R.id.iv_order_time);
        iv_urgent=conentView.findViewById(R.id.iv_order_urgent);
        iv_me=conentView.findViewById(R.id.iv_order_participate);
        line_time.setOnClickListener(this);
        line_urgent=conentView.findViewById(R.id.line_urgent);
        line_about_me=conentView.findViewById(R.id.line_about_me);

        GridLayoutManager mgr_tags=new GridLayoutManager(context,3);
        GridLayoutManager mgr_names=new GridLayoutManager(context,3);
        rv_tags.setLayoutManager(mgr_tags);
        rv_names.setLayoutManager(mgr_names);



        initdata();
        GridAdapter adapter_tags=new GridAdapter(tags);

        GridAdapter adapter_names=new GridAdapter(names);

        rv_tags.setAdapter(adapter_tags);
        rv_names.setAdapter(adapter_names);


        h = context.getWindowManager().getDefaultDisplay().getHeight();
        w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
    }

    public void initdata() {
        for(int i=0;i<13;i++){
            tags.add("tag111111"+i);
            names.add("name"+i);
        }

    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, x, 0);
        } else {
            this.dismiss();
        }
    }

    int flag_time=0,flag_urgent=0,flag_me=0;
    @Override
    public void onClick(View v) {

        //R.id.line.XX点击一下则打勾，再点击打勾取消
        switch (v.getId()){
            case R.id.line_time:
                switch (flag_time){
                    case 0:
                        flag_time=1;
                        iv_time.setBackgroundResource(R.drawable.tick);
                        break;
                    case 1:
                        flag_time=0;
                        iv_time.setBackgroundResource(R.color.white);
                        break;
                }
                break;

            case R.id.line_urgent:
                switch (flag_urgent){
                    case 0:
                        flag_urgent=1;
                        iv_urgent.setBackgroundResource(R.drawable.tick);
                        break;
                    case 1:
                        flag_urgent=0;
                        iv_urgent.setBackgroundResource(R.color.white);
                        break;
                }
                break;
            case R.id.line_about_me:
                switch (flag_me){
                    case 0:
                        flag_me=1;
                        iv_me.setBackgroundResource(R.drawable.tick);
                        break;
                    case 1:
                        flag_me=0;
                        iv_me.setBackgroundResource(R.color.white);
                        break;
                }
                break;
        }
    }


    public void getData() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String tagsurl = "";
                    String nameUrl="";
                    OkHttpClient client=new OkHttpClient();
                    Request.Builder reqBuild = new Request.Builder();
                    //reqBuild.addHeader();
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(tagsurl)
                            .newBuilder();
                    reqBuild.url(urlBuilder.build());
                    Request request = reqBuild.build();
                    Response response = client.newCall(request).execute();
                    tagData=response.body().string();

                    if(tagData != null && tagData.startsWith("\ufeff"))
                    {
                        tagData =  tagData.substring(1);
                    }


                    JSONObject jsonObject=new JSONObject(tagData);
                    jsonArray=(JSONArray)jsonObject.getJSONArray("data");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}