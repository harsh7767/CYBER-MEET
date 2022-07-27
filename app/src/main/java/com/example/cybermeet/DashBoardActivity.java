package com.example.cybermeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;


public class DashBoardActivity extends AppCompatActivity {
EditText secretCodeBox;
Button joinBtn,shareBtn,demobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        secretCodeBox =findViewById(R.id.codeBox);
        joinBtn =findViewById(R.id.joinBtn);
        shareBtn=findViewById(R.id.shareBtn);

        URL serverURL;

         try {
             serverURL =new URL("https://meet.jit.si");
             JitsiMeetConferenceOptions defaultOptions=new JitsiMeetConferenceOptions.Builder()
                     .setServerURL(serverURL)

                     .build();

         } catch (MalformedURLException e) {
             e.printStackTrace();
         }


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()

                        .setRoom(secretCodeBox.getText().toString())
                        .setAudioMuted(false)
                        .setVideoMuted(false)
                        .setAudioOnly(false)
                        .setConfigOverride("requireDisplayName", true)
                        .build();

                JitsiMeetActivity.launch(DashBoardActivity.this,options);
            }
        });



shareBtn=(Button) findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string =secretCodeBox.getText().toString();
                Intent shareintent=new Intent();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.putExtra(Intent.EXTRA_TEXT,string);
            shareintent.setType("text/plain");
            startActivity(shareintent);
            }
        });


        demobtn =(Button) findViewById(R.id.demobtn);
        demobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashBoardActivity.this,LoginActivity.class));
            }
        });
    }

}
