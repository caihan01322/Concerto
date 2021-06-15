package com.example.concerto.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.concerto.R;


public class PopWindowTest extends PopupWindow{
    private View conentView;
    private int h;
    private int w;
    private Activity activity;
    public PopWindowTest(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_window, null);
        h = context.getWindowManager().getDefaultDisplay().getHeight();
        w = context.getWindowManager().getDefaultDisplay().getWidth();
        activity = context;
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w /2+ 40);
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

        conentView.findViewById(R.id.joinProject).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                PopWindowJoinProject pw = new PopWindowJoinProject(context);
                pw.setOnDismissListener(() -> {
                    WindowManager.LayoutParams lp1 = context.getWindow().getAttributes();
                    lp1.alpha = 1f;
                    context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    context.getWindow().setAttributes(lp1);
                });
                PopWindowTest.this.dismiss();
                pw.showPopupWindow(view.findViewById(R.id.joinProject));
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 0.4f; //设置透明度
                context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                context.getWindow().setAttributes(lp);

            }
        });

        conentView.findViewById(R.id.addProject).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                PopWindowAddProject pw = new PopWindowAddProject(context);
                pw.setOnDismissListener(() -> {
                    WindowManager.LayoutParams lp1 = context.getWindow().getAttributes();
                    lp1.alpha = 1f;
                    context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    context.getWindow().setAttributes(lp1);
                });
                PopWindowTest.this.dismiss();
                pw.showPopupWindow(view.findViewById(R.id.addProject));
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 0.4f; //设置透明度
                context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                context.getWindow().setAttributes(lp);

            }
        });
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }
}