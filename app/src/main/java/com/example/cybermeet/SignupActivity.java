package com.example.cybermeet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth auth;

    EditText emailBox,passwordBox,nameBox;
    Button backBtn,signupBtn;
     FirebaseFirestore database;
     ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        auth =FirebaseAuth.getInstance();
        database= FirebaseFirestore.getInstance();
        progressDialog =new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");
        emailBox=findViewById(R.id.emailBox);
        passwordBox=findViewById(R.id.passwordBox);
        nameBox=findViewById(R.id.namebox);

        signupBtn=findViewById(R.id.signupbtn);


        backBtn =findViewById(R.id.backbtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        progressDialog.show();
        String email;
        String pass;
        String name;
        email= emailBox.getText().toString();
        pass=passwordBox.getText().toString();
        name= nameBox.getText().toString();

        User user =new User();
        user.setEmail(email);
user.setPass(pass);
user.setName(name);



        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();
                if(task.isSuccessful()) {


             database.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void unused) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                     Toast.makeText(SignupActivity.this,"Account created Successfully",Toast.LENGTH_SHORT).show();
                 }
             });



;

                }   else{
                    Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();}


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
    }
});
    }
}