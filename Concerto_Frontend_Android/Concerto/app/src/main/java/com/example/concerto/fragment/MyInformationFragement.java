package com.example.concerto.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.concerto.R;

import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyInformationFragement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context context ;
    private TextView UserNameTextView ;
    private TextView UserPhoneTextView;
    private TextView AdviceTextView;
    private ImageView AdiveImageView;
    private Button ExitButton;
    private LayoutInflater inflater;
    private EditText AdviceEditText;

    public MyInformationFragement() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MyInformationFragement newInstance(String param1, String param2) {
        MyInformationFragement fragment = new MyInformationFragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_information,container,false);
        ExitButton = view.findViewById(R.id.ExitButton);
        this.inflater = inflater;
        UserNameTextView = view.findViewById(R.id.UserNameTextView);
        UserPhoneTextView = view.findViewById(R.id.UserPhoneTextView);
        AdviceTextView = view.findViewById(R.id.AdviceTextView);
        AdiveImageView = view.findViewById(R.id.AdviceImageView);
        initUser();
        initAdvice();
        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、使用Dialog、设置style
                final Dialog dialog = new Dialog(context, R.style.ExitDialogTheme);
                //2、设置布局
                View view = View.inflate(context, R.layout.exit_account, null);
                dialog.setContentView(view);

                Window window = dialog.getWindow();
                //设置弹出位置
                window.setGravity(Gravity.BOTTOM);
                //设置弹出动画
                window.setWindowAnimations(R.style.main_menu_animStyle);
                //设置对话框大小
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                TextView ExitCancelTextView = dialog.findViewById(R.id.ExitCancelTextView);
                ExitCancelTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView ExitTextView = dialog.findViewById(R.id.ExitTextView);

            }
        });
        return view;
    }
    private void initUser()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0";
                    OkHttpClient client=new OkHttpClient();
                    Request.Builder reqBuild = new Request.Builder();
                    reqBuild.addHeader("token",token);
                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://81.69.253.27:7777//User/info")
                            .newBuilder();
                    reqBuild.url(urlBuilder.build());

                    Request request = reqBuild.build();

                    Response response = client.newCall(request).execute();
                    String data=response.body().string();
                    if(data != null && data.startsWith("\ufeff"))
                    {
                        data =  data.substring(1);
                    }

                    JSONObject jsonObject=new JSONObject(data);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    Log.v("test",data);
                    String userName = jsonObject1.getString("userName");
                    String userPhone = jsonObject1.getString("userEmail");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UserNameTextView.setText(userName);
                            UserPhoneTextView.setText(userPhone);
                        }
                    });

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initAdvice()
    {
        AdviceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = inflater.inflate(R.layout.advice_submit,null);
                builder.setView(view);
                builder.setTitle("意见反馈");
                AdviceEditText = view.findViewById(R.id.AdviceEditText);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String advice = AdviceEditText.getText().toString();
                        Log.v("test",advice);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0";
                                    OkHttpClient client=new OkHttpClient();
                                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                                    JSONObject jsonObject = new JSONObject();
                                    RequestBody requestBody = RequestBody.create(JSON,String.valueOf(jsonObject));

                                    Request.Builder reqBuild = new Request.Builder();
                                    reqBuild.addHeader("Content-Type","text/json;charset=UTF-8")
                                            .addHeader("token",token);
                                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://81.69.253.27:7777//User/Advice?content="+advice)
                                            .newBuilder();
                                    reqBuild.url(urlBuilder.build());
                                    reqBuild.put(requestBody);
                                    Request request = reqBuild.build();

                                    Response response = client.newCall(request).execute();
                                    String data=response.body().string();
                                    Log.v("test",data);
                                    if(data != null && data.startsWith("\ufeff"))
                                    {
                                        data =  data.substring(1);
                                    }
                                    JSONObject jsonObject1=new JSONObject(data);
                                    int status = jsonObject1.getInt("status");
                                    Looper.prepare();
                                    if (status==200)
                                    {
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                        builder1.setMessage("成功提交意见");
                                        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        builder1.create().show();
                                    }
                                    Looper.loop();
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.create().show();

            }
        });
    }
}