package com.example.dharma.bloodbank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {

    EditText e1,e2;
    Button b1;
    Context context;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        e1=(EditText)findViewById(R.id.signup_username);
        e2=(EditText)findViewById(R.id.signup_password);
        b1=(Button)findViewById(R.id.signup_signup);
        firebaseAuth=FirebaseAuth.getInstance();
        context=this;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
String s1=e1.getText().toString().trim();
String s2=e2.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(s1,s2).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = firebaseAuth.getCurrentUser();

                                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(signup.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });

            }
        });
    }
}
