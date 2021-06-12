package com.example.concerto.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.adapter.GridAdapter;
import com.example.concerto.bean.Condition;
import com.example.concerto.bean.UserItem;
import com.example.concerto.fragment.TaskListFragment;

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
    int type;//0 日程筛选  1任务筛选
    String tagData;
    String nameData;
    JSONArray tagJsonArray;
    EditText et_title;
    String title;
    ImageView btn_select_title;
    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0"; ;
    String projectId="2";

    String pdata;//参与者返回数据
    JSONArray pjsonArray;

    GridAdapter adapter_tags;
    GridAdapter adapter_names;
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
    Button btn_select;



    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public void setWidth(int width) {
        this.width = w/2+width;
    }

    //type:0==日程筛选  1==项目筛选
    public PopWindowUtil(int type,final Activity context, int layout, int width, int x, int y) throws InterruptedException {
        this.type=type;
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
        et_title=conentView.findViewById(R.id.et_select_title);

        btn_select_title=conentView.findViewById(R.id.btn_select_title);
        btn_select_title.setOnClickListener(this);
        getData();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        line_urgent.setOnClickListener(this);
        line_about_me.setOnClickListener(this);
        btn_select=conentView.findViewById(R.id.btn_select);
        btn_select.setOnClickListener(this);
        GridLayoutManager mgr_tags=new GridLayoutManager(context,3);
        GridLayoutManager mgr_names=new GridLayoutManager(context,3);
        rv_tags.setLayoutManager(mgr_tags);
        rv_names.setLayoutManager(mgr_names);
        initdata();
        adapter_tags=new GridAdapter(tags,0,context);

        adapter_names=new GridAdapter(names,1,context);

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

        adapter_tags.setData(tags);
        adapter_names.setData(names);
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


        switch (v.getId()){
            //R.id.line.XX点击一下则打勾，再点击打勾取消
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

            //发送筛选条件
            case R.id.btn_select_title:
            case R.id.btn_select:{
                SharedPreferences sharedPreferences= conentView.getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("titleLimit", et_title.getText().toString());
                editor.commit();

            }
                break;
        }
    }


    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String tagsurl = "http://81.69.253.27:7777/User/Schedule/Tag";
                    OkHttpClient client=new OkHttpClient();
                    Request.Builder reqBuild = new Request.Builder();
                    reqBuild.addHeader("token",token);
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
                    tagJsonArray=(JSONArray)jsonObject.getJSONArray("data");
                    //Log.v("test","+++++++++++++++"+tagJsonArray)  ;
                }catch (Exception e){
                    e.printStackTrace();
                }
                String participantUrl="http://81.69.253.27:7777/project/member";
                try {

                    //参与者返回数据
                    OkHttpClient pclient=new OkHttpClient();
                    Request.Builder preqBuild = new Request.Builder();
                    preqBuild.addHeader("projectId", projectId+"");
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(participantUrl)
                            .newBuilder();
                    urlBuilder.addQueryParameter("projectId", projectId+"");
                    preqBuild.url(urlBuilder.build());
                    Request prequest = preqBuild.build();
                    Response presponse = pclient.newCall(prequest).execute();
                    pdata=presponse.body().string();
                    if(pdata != null && pdata.startsWith("\ufeff"))
                    {
                        pdata =  pdata.substring(1);
                    }
                    //Log.v("ParticipantsFragment","--------参与者---------"+pdata);


                    JSONObject pjsonObject=new JSONObject(pdata);
                    if(pjsonObject.getJSONArray("data")!=null)
                        pjsonArray=pjsonObject.getJSONArray("data");
                   // Log.v("ParticipantsFragment","--------参与者xx---------"+pjsonArray);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }).start();

    }


    public void initdata() {
        try {
            for(int i=0;i<tagJsonArray.length();i++){
                JSONObject object= tagJsonArray.getJSONObject(i);

                //Log.v("test","++++111111+++++++"+object);
                tags.add(object.getString("tagContent"));
                if(i>15)
                    break;
            }
        }  catch (Exception e)  {
            e.printStackTrace();
        }


        if(pjsonArray!=null)
            try {
                for(int i=0;i<pjsonArray.length();i++){
                    JSONObject pjsonObject=pjsonArray.getJSONObject(i);
                    UserItem puserItem=new UserItem();
                    String nameobj=pjsonObject.getString("userName");
                    names.add(nameobj);
                }
            }  catch (Exception e)  {
                e.printStackTrace();
            }
    }
}