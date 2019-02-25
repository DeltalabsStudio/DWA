package com.whatsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.whatsapp.observablelistview.ObservableListView;

import java.util.ArrayList;
import java.util.List;

import id.delta.whatsapp.R;
import id.delta.whatsapp.ui.views.Card;
import id.delta.whatsapp.value.Row;


public class ConversationsFragment extends ListFragment {

    List<Chat> chatList;
    private ChatsAdapter chatsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversations, container, false);
        ObservableListView mList = view.findViewById(android.R.id.list);
        dataChat();
        chatsAdapter = new ChatsAdapter(getContext(), chatList);
        mList.setAdapter(chatsAdapter);
        mList.setDivider(Row.listDivider());

        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).initScroll(this, view);
        }

        return view;
    }

    private void dataChat(){
        chatList = new ArrayList<>();
        Chat chats;
        int avatar[]={
                R.drawable.delta_ic_profile,
                R.drawable.profile_dhian,
                R.drawable.profile_trangga,
                R.drawable.profile_anca,
                R.drawable.profile_ciben,
                R.drawable.delta_ic_profile,
                R.drawable.delta_ic_profile,
                R.drawable.delta_ic_profile,
                R.drawable.profile_dhian,
                R.drawable.profile_trangga,
                R.drawable.profile_anca,
                R.drawable.profile_ciben,
                R.drawable.delta_ic_profile,
                R.drawable.delta_ic_profile,
                R.drawable.delta_ic_profile
        };

        String nama[]={"Rikri Ricardo","Dhian Rusdhiana","Trangga Ken","Anca Imut","Ciben","Arzhella", "Nciek", "Azhar","Dhian Rusdhiana","Trangga Ken","Anca Imut","Ciben","Arzhella", "Nciek", "Azhar"};
        String[] pesan = getResources().getStringArray(R.array.pesan);

        for (int i=0; i<avatar.length;i++){
            chats = new Chat();
            chats.setId(i+1);
            chats.setNama(nama[i]);
            chats.setChat(pesan[i]);
            chats.setAvatar(avatar[i]);
            chatList.add(chats);
        }

    }

    class ChatsAdapter extends BaseAdapter{

        private List<Chat> chatList;
        LayoutInflater mInflater;
        Context context;

        public ChatsAdapter(Context context, List<Chat> chatList){
            this.context = context;
            this.chatList = chatList;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return chatList.size();
        }

        @Override
        public Object getItem(int position) {
            return chatList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            ViewHolder holder = null;
            final Chat chat = chatList.get(i);
            if (view == null) {
                view = mInflater.inflate(R.layout.conversations_row, null);
                holder = new ViewHolder();
                holder.mNama = (TextView)view.findViewById(R.id.mTitle);
                holder.mChats = view.findViewById(R.id.mSubtitle);
                holder.mAvatar = view.findViewById(R.id.mAvatar);
                holder.mCard = view.findViewById(R.id.mCard);
                view.setTag(holder);
            } else {
                holder = (ViewHolder)view.getTag();
            }
            holder.mNama.setText(chat.getNama());
            holder.mChats.setText(chat.getChat());
            holder.mAvatar.setImageResource(chat.getAvatar());
            holder.mNama.setTextColor(Row.listName());
            holder.mChats.setTextColor(Row.listMessage());
            holder.mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, Conversation.class).putExtra("chat", chat));
                }
            });
            return view;
        }

        private class ViewHolder {
            private TextView mNama;
            private TextView mChats;
            private ImageView mAvatar;
            private Card mCard;
        }
    }
}
