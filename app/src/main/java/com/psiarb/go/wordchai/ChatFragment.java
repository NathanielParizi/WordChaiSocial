package com.psiarb.go.wordchai;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 *
 *
 *
 */





public class ChatFragment extends Fragment {


    private FirebaseAuth mAuth;

    private RecyclerView mChatList;
    private DatabaseReference mChatDatabase;

    private String mCurrent_user_id;
    private View mMainView;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_chat, container, false);

        mChatList = (RecyclerView) mMainView.findViewById(R.id.chat_list);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chatroom").child(mCurrent_user_id);

        mChatList.setHasFixedSize(true);
        mChatList.setLayoutManager(new LinearLayoutManager(getContext()));





        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

}
