package com.example.concerto.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concerto.R;
import com.example.concerto.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.List;


public class PopWindowUtil extends PopupWindow{

    int layout;
    private View conentView;
    int h,w;
    int x,y;
    int width;

    List<String> tags;
    List<String> names;
    RecyclerView rv_tags;
    RecyclerView rv_names;

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

        rv_tags=conentView.findViewById(R.id.rv_tags);
        rv_names=conentView.findViewById(R.id.rv_names);


        GridLayoutManager mgr_tags=new GridLayoutManager(context,3);
        GridLayoutManager mgr_names=new GridLayoutManager(context,3);
        rv_tags.setLayoutManager(mgr_tags);
        rv_names.setLayoutManager(mgr_names);

        tags=new ArrayList<>();
        names=new ArrayList<>();

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
        for(int i=0;i<15;i++){
            tags.add("tag"+i);
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
}