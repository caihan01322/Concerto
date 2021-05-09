package com.example.concerto.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concerto.R;
import com.example.concerto.adapter.ParticipantsAdapter;
import com.example.concerto.adapter.TaskAdapter;
import com.example.concerto.adapter.UserAdapter;
import com.example.concerto.bean.UserItem;

import java.util.ArrayList;
import java.util.List;


public class ParticipantsFragment extends Fragment {

    RecyclerView candidateList;
    RecyclerView participantsList;
    List<UserItem> candidates;
    List<UserItem> participants;


    public ParticipantsFragment() {
        participants=new ArrayList<>();
        candidates=new ArrayList<>();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
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
        UserAdapter cadapter=new UserAdapter(candidates);
        ParticipantsAdapter padapter=new ParticipantsAdapter(participants);
        candidateList.setAdapter(cadapter);
        participantsList.setAdapter(padapter);

        return view;
    }

    public void initData(){
        for(int i=0;i<9;i++){
            UserItem user=new UserItem("name"+i,"mailbox"+i);
            candidates.add(user);
            participants.add(user);
        }

    }
}