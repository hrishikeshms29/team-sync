package com.myapplication.game001.teamsync20;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {
    Chat_page chat_page;
    ArrayList<Users> usersArrayList;
    public UserAdapter(Chat_page chat_page, ArrayList<Users> usersArrayList) {
        this.chat_page=chat_page;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(chat_page).inflate(R.layout.user_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Users users = usersArrayList.get(position);
        holder.username.setText(users.userName);
        holder.userstatus.setText(users.status);
        Picasso.get().load(users.profilepic).into(holder.userimg);
        //redirect to chat window
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chat_page,chat_window.class);
                intent.putExtra("rname",users.getUserName());
                intent.putExtra("rimg",users.getProfilepic());
                intent.putExtra("ruid",users.getUserId());
                chat_page.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CircleImageView userimg;
        TextView username;
        TextView userstatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userimg = itemView.findViewById(R.id.userimg);
            username = itemView.findViewById(R.id.username);
            userstatus = itemView.findViewById(R.id.userstatus);
        }
    }
}
