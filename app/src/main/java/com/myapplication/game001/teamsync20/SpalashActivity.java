package com.myapplication.game001.teamsync20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

public class SpalashActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                auth = FirebaseAuth.getInstance();
                if(auth.getCurrentUser()==null){
                    Intent i=new Intent(SpalashActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i=new Intent(SpalashActivity.this,Home.class);
                    startActivity(i);
                }

                finish();
            }
        },3000);
    }
}