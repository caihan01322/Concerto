package com.example.concerto.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;


public class PopWindowUtil extends PopupWindow{

    int layout;
    private View conentView;
    int h,w;
    int x,y;
    int width;

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
        x=x;
        y=y;
        createPopWindow(context);
    }

    public void createPopWindow(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(layout, null);
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

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, x, 0);
        } else {
            this.dismiss();
        }
    }
}