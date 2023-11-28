package com.myapplication.game001.teamsync20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat_window extends AppCompatActivity {
    String rimg,ruid,rname,suid;
    CircleImageView profile;
    TextView name;
    CardView msendbtn;
    EditText mtext;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    public static String senderImg;
    public static String reciverImg;
    String senderRoom;
    String recieverRoom;
    RecyclerView messAdapter;
    ArrayList<msgModelclass> messagessArrayList;
    messagesAdapter mmessagesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        rname=getIntent().getStringExtra("rname");
        rimg=getIntent().getStringExtra("rimg");
        ruid=getIntent().getStringExtra("ruid");

        messagessArrayList= new ArrayList<>();

        mtext=findViewById(R.id.mtext);
        msendbtn=findViewById(R.id.msendbtn);
        profile=findViewById(R.id.pchat);
        name=findViewById(R.id.rnameee);
        messAdapter=findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messAdapter.setLayoutManager(linearLayoutManager);
        //check
        mmessagesAdapter = new messagesAdapter(chat_window.this,messagessArrayList);
        messAdapter.setAdapter(mmessagesAdapter);


        Picasso.get().load(rimg).into(profile);
        name.setText(" "+rname);

        suid = firebaseAuth.getUid();

        senderRoom=suid+ruid;
        recieverRoom=ruid+suid;

        DatabaseReference reference = database.getReference().child("user").child(Objects.requireNonNull(firebaseAuth.getUid()));
        DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagessArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    msgModelclass messages =dataSnapshot.getValue(msgModelclass.class);
                    messagessArrayList.add(messages);
                }
                mmessagesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg=snapshot.child("profilepic").getValue().toString();
                reciverImg=rimg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        msendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smesg=mtext.getText().toString();
                if(smesg.isEmpty()){
                    Toast.makeText(chat_window.this, "Enter the message first", Toast.LENGTH_SHORT).show();
                    return;
                }
                mtext.setText("");
                Date date =new Date();
                msgModelclass messagess = new msgModelclass(smesg,suid,date.getTime());
                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats").child(senderRoom).child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats").child(recieverRoom).child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                    }
                });
            }
        });
    }
}