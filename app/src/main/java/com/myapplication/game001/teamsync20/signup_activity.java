package com.myapplication.game001.teamsync20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class signup_activity extends AppCompatActivity {
    Button signup,login;
    EditText name,mail,password;
    FirebaseAuth auth;
    ProgressDialog pd;
    CircleImageView circle;
    Uri imageURI;
    String imageuri;
    FirebaseDatabase database;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();
        mail=findViewById(R.id.mail);
        name=findViewById(R.id.user_name);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup_btn);
        login=findViewById(R.id.login_btn);
        circle=findViewById(R.id.profilerg);
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        pd=new ProgressDialog(signup_activity.this);
        pd.setTitle("Creating Account");
        pd.setMessage("We are Creating your account");
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select picture"),10);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                String email,pass,uname;
                email=mail.getText().toString();
                pass=password.getText().toString();
                uname=name.getText().toString();
                String status = "Hey I'm Using This Application";

                if ((TextUtils.isEmpty(email))){
                    pd.dismiss();
                    Toast.makeText(signup_activity.this, "Enter The Email", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(pass)){
                    pd.dismiss();
                    Toast.makeText(signup_activity.this, "Enter The Password", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(uname)){
                    pd.dismiss();
                    Toast.makeText(signup_activity.this, "Enter The Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                String id =task.getResult().getUser().getUid();
                                DatabaseReference reference =database.getReference().child("user").child(id);
                                StorageReference storageReference=storage.getReference().child("Upload").child(id);
                                if(imageURI!=null){
                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageuri = uri.toString();
                                                        Users users = new Users(id,uname,email,pass,imageuri,status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    pd.show();
                                                                    Intent intent = new Intent(signup_activity.this,MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }else {
                                                                    Toast.makeText(signup_activity.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                                else{
                                    String status="hey there";
                                    imageuri="https://firebasestorage.googleapis.com/v0/b/team-sync-f32f5.appspot.com/o/empty.png?alt=media&token=c4692e37-0d6a-4f35-9ae3-6d78a2469040";
                                    Users users = new Users(id,uname,email,pass,imageuri,status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                pd.show();
                                                Intent intent = new Intent(signup_activity.this,MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                Toast.makeText(signup_activity.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                Toast.makeText(signup_activity.this, "Created succesfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(signup_activity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup_activity.this,MainActivity.class));
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10){
            if (data!=null){
                imageURI = data.getData();
                circle.setImageURI(imageURI);
            }
        }
    }
}