package com.myapplication.game001.teamsync20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceConfig;
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceFragment;

public class room_back extends AppCompatActivity {
    TextView mid;
    ImageView shareBtn;
    String meetingID,userID,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_back);
        mid=findViewById(R.id.meetID_text);
        shareBtn=findViewById(R.id.share_btn);

        meetingID=getIntent().getStringExtra("meeting_ID");
        name=getIntent().getStringExtra("name");
        userID=getIntent().getStringExtra("user_ID");

        mid.setText("Meeting ID : "+ meetingID);
        addFragment();
        shareBtn.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"JOIN MEETING IN TEAM SYNC \n Meeting Id : "+meetingID);
            startActivity(Intent.createChooser(intent,"Share Via"));
        });
    }
    public void addFragment() {
        long appID = AppConstants.appId;
        String appSign = AppConstants.appSign;

        ZegoUIKitPrebuiltVideoConferenceConfig config = new ZegoUIKitPrebuiltVideoConferenceConfig();
        config.turnOnCameraWhenJoining=false;
        config.turnOnMicrophoneWhenJoining=false;
        ZegoUIKitPrebuiltVideoConferenceFragment fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(appID, appSign, userID, name,meetingID,config);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNow();
    }
}