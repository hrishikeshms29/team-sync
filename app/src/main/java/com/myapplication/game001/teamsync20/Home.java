package com.myapplication.game001.teamsync20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {
    ImageView direct_vc,direct_chat,direct_cld;
    ImageView logoutimg,settingBut;
    CircleImageView ppimg;
    TextView welcome;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        direct_vc=findViewById(R.id.direct_vc);
        direct_chat=findViewById(R.id.direct_chat);
        direct_cld=findViewById(R.id.direct_cldn);
        settingBut=findViewById(R.id.settingBut);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        ppimg=findViewById(R.id.ppimg);
        welcome=findViewById(R.id.welcome);
        auth=FirebaseAuth.getInstance();
        DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
        StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("userName").getValue().toString();
                String profile = snapshot.child("profilepic").getValue().toString();
                welcome.setText(name);
                Picasso.get().load(profile).into(ppimg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        direct_vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,videoCall.class));
                //finish();
            }
        });
        direct_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Chat_page.class));
                //finish();
            }
        });
        logoutimg=findViewById(R.id.logoutimg);
        logoutimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this,MainActivity.class));
                finish();
            }
        });
        settingBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Home.this,setting.class));
                Toast.makeText(Home.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        direct_cld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(Home.this,videoCall.class));
            }
        });
    }
}