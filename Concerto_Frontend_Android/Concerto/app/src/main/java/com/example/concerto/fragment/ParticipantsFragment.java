package com.example.concerto.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concerto.R;
import com.example.concerto.adapter.CandidateAdapter;
import com.example.concerto.adapter.ParticipantsAdapter;
import com.example.concerto.adapter.TaskAdapter;
import com.example.concerto.adapter.UserAdapter;
import com.example.concerto.bean.UserItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ParticipantsFragment extends Fragment {

    RecyclerView candidateList;
    RecyclerView participantsList;
    List<UserItem> candidates;
    List<UserItem> participants;
    String projectId;
    String pdata;//参与者返回数据
    String cdata;//申请者返回数据
    JSONArray pjsonArray;
    JSONArray cjsonArray;



    public ParticipantsFragment(String id) {
        participants=new ArrayList<>();
        candidates=new ArrayList<>();
        projectId=id;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_participants, container, false);
         candidateList=view.findViewById(R.id.rv_candidate_list);
         participantsList=view.findViewById(R.id.rv_participants_list);
        LinearLayoutManager plinearLayoutManager=new LinearLayoutManager(this.getContext());
        LinearLayoutManager clinearLayoutManager=new LinearLayoutManager(this.getContext());
        candidateList.setLayoutManager(clinearLayoutManager);
        participantsList.setLayoutManager(plinearLayoutManager);

        initData();
        CandidateAdapter cadapter=new CandidateAdapter(candidates,projectId);
        cadapter.setMusers(candidates);
        ParticipantsAdapter padapter=new ParticipantsAdapter(participants);
        padapter.setMusers(participants);
        candidateList.setAdapter(cadapter);
        participantsList.setAdapter(padapter);

        return view;
    }


    public void getData() {
        String participantUrl="http://81.69.253.27:7777/project/member";
        String candidateUrl="http://81.69.253.27:7777/project/applicant";

        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    Log.v("ParticipantsFragment","--------参与者---------"+pdata);


                    JSONObject pjsonObject=new JSONObject(pdata);
                    if(pjsonObject.getJSONArray("data")!=null)
                        pjsonArray=pjsonObject.getJSONArray("data");
                    Log.v("ParticipantsFragment","--------参与者xx---------"+pjsonArray);
                }catch (Exception e){
                    e.printStackTrace();
                }

            try {

                    //申请者返回数据
                    OkHttpClient cclient=new OkHttpClient();
                    Request.Builder creqBuild = new Request.Builder();
                    creqBuild.addHeader("projectId", 1+"");
                    HttpUrl.Builder curlBuilder = HttpUrl.parse(candidateUrl)
                            .newBuilder();
                    curlBuilder.addQueryParameter("projectId", projectId+"");
                    creqBuild.url(curlBuilder.build());
                    Request crequest = creqBuild.build();
                    Response cpresponse = cclient.newCall(crequest).execute();
                    cdata=cpresponse.body().string();
                    if(cdata != null && cdata.startsWith("\ufeff"))
                    {
                        cdata =  cdata.substring(1);
                    }
                    Log.v("ParticipantsFragment","--------申请者---------"+cdata);
                    JSONObject cjsonObject=new JSONObject(cdata);
                    if(cjsonObject.getJSONArray("data")!=null)
                        cjsonArray=cjsonObject.getJSONArray("data");
                    Log.v("ParticipantsFragment","--------申请者xx---------"+cjsonArray);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();

    }


    public void initData(){
        try {
            participants.clear();
            candidates.clear();
            for(int i=0;i<pjsonArray.length();i++){
                JSONObject pjsonObject=pjsonArray.getJSONObject(i);
                UserItem puserItem=new UserItem();
                puserItem.setUserId(pjsonObject.getInt("userId"));
                puserItem.setName(pjsonObject.getString("userName"));
                puserItem.setMailbox(pjsonObject.getString("userEmail"));
                participants.add(puserItem);
            }

            for (int i=0;i<cjsonArray.length();i++){
                JSONObject cjsonObject=cjsonArray.getJSONObject(i);
                UserItem cuserItem=new UserItem();
                cuserItem.setUserId(cjsonObject.getInt("userId"));
                cuserItem.setName(cjsonObject.getString("userName"));
                cuserItem.setMailbox(cjsonObject.getString("userEmail"));
                candidates.add(cuserItem);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

}