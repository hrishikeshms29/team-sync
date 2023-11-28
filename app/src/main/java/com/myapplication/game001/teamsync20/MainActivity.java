package com.myapplication.game001.teamsync20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText emailbox,password;
    Button loginbtn,signbtn;
    FirebaseAuth auth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        emailbox=findViewById(R.id.mail);
        password=findViewById(R.id.password);
        loginbtn=findViewById(R.id.login_btn);
        signbtn=findViewById(R.id.signup_btn);
        pd=new ProgressDialog(MainActivity.this);
        pd.setTitle("Please Wait");
        pd.setMessage("Finishing in a moment");
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                String email,pass;
                email=emailbox.getText().toString();
                pass=password.getText().toString();
                if ((TextUtils.isEmpty(email))){
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "Enter The Email", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(pass)) {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "Enter The Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "LOGGED IN", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, Home.class));
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,signup_activity.class));
            }
        });



    }
}