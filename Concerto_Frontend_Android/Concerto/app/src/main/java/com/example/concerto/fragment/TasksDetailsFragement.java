package com.example.concerto.fragment;

import android.content.ClipboardManager;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.concerto.R;

import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.core.content.ContextCompat.getSystemService;


public class TasksDetailsFragement extends Fragment implements View.OnClickListener {

    Button btn_copy;
    TextView tv_invitationCode;
    String projectId;
    JSONObject object;

    public TasksDetailsFragement(JSONObject jsonObject) {
       object=jsonObject;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_details_tasks, container, false);
        btn_copy=view.findViewById(R.id.btn_copy);
        tv_invitationCode=view.findViewById(R.id.tv_invitationCode);
        btn_copy.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //点击复制邀请码
            case R.id.btn_copy:
                ClipboardManager cm = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(tv_invitationCode.getText());
                Toast.makeText(getActivity(),"复制成功",Toast.LENGTH_LONG).show();
        }

    }


}