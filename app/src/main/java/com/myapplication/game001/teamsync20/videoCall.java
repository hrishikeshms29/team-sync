package com.myapplication.game001.teamsync20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class videoCall extends AppCompatActivity {
    Button join,create;
    EditText code,name;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        sharedPreferences=getSharedPreferences("name_pref",MODE_PRIVATE);
        code=findViewById(R.id.code);
        join=findViewById(R.id.join);
        create=findViewById(R.id.create);
        name=findViewById(R.id.name);
        name.setText(sharedPreferences.getString("name",""));
        join.setOnClickListener(view -> {
            String meetID = code.getText().toString();
            if(meetID.length()!=10){
                code.setError("INVALID MEETING ID");
                Toast.makeText(videoCall.this, "INVALID MEETING ID", Toast.LENGTH_SHORT).show();
                code.requestFocus();
                return;
            }
            String nme = name.getText().toString();
            if(nme.isEmpty()){
                name.setError("Name is a required field");
                Toast.makeText(videoCall.this, "Name is a required field", Toast.LENGTH_SHORT).show();
                name.requestFocus();
                return;
            }
            StartMeet(meetID,nme);
        });
        create.setOnClickListener(view ->  {
            String nme = name.getText().toString();
            if(nme.isEmpty()){
                name.setError("Name is a required field");
                Toast.makeText(videoCall.this, "Name is a required field", Toast.LENGTH_SHORT).show();
                name.requestFocus();
                return;
            }
            StartMeet(getID(),nme);

        });
    }
    void StartMeet(String meetID,String name){
        sharedPreferences.edit().putString("name",name).apply();
        String userID = UUID.randomUUID().toString();
        Intent intent=new Intent(videoCall.this,room_back.class);
        intent.putExtra("meeting_ID",meetID);
        intent.putExtra("name",name);
        intent.putExtra("user_ID",userID);
        startActivity(intent);
    }
    String getID(){
        StringBuilder id = new StringBuilder();
        while (id.length()!=10){
            int random= new Random().nextInt(10);
            id.append(random);
        }
        return id.toString();
    }
}