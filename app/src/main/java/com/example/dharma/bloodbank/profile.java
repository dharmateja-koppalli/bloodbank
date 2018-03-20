package com.example.dharma.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class profile extends AppCompatActivity {
    EditText name,age,pincode,mobile;
    RadioGroup gender;
    Spinner group,state,city;
    Button register;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String uid,uemail;
    String s1,s2,s3,s4,s5,s6,s7;
    public String b_group[]={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String states[]={"Andhra pradesh","Tamil nadu","Karnataka","Kerala"};
    String cities[]={"Hyderabad","Chennai","Banglore","Kochi"};
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=(EditText)findViewById(R.id.profile_name);
        age=(EditText)findViewById(R.id.profile_age);
        pincode=(EditText)findViewById(R.id.profile_pincode);
        gender=(RadioGroup)findViewById(R.id.profile_gender);
        group=(Spinner)findViewById(R.id.profile_group);
        state=(Spinner)findViewById(R.id.profile_state);
        city=(Spinner)findViewById(R.id.profile_city);
        mobile=(EditText)findViewById(R.id.profile_Mobile);
        register=(Button)findViewById(R.id.profile_register);
        ArrayAdapter adp_group=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,b_group);
        group.setAdapter(adp_group);
        ArrayAdapter adp_state=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,states);
        state.setAdapter(adp_state);
        ArrayAdapter adp_city=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,cities);
        city.setAdapter(adp_city);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
//        uemail= firebaseUser.getEmail();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference allUsersRef = rootRef.collection("users_profile");

        allUsersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String userName = document.getString("email");
                        if (userName.equals(uemail)) {
                            Toast.makeText(profile.this, "That username already exists.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(profile.this,MainActivity.class);
                            startActivity(i);
                        } else {
                            Log.d("TAG", "onEvent: username does not exists");
                        }
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });

        firebaseFirestore= FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s1=name.getText().toString();
                s2=age.getText().toString();
                s3 =group.getSelectedItem().toString();
                s4=state.getSelectedItem().toString();
                s5=city.getSelectedItem().toString();
                s6=pincode.getText().toString();
                s7=mobile.getText().toString();

                progressDialog = ProgressDialog.show(profile.this,"Please Wait ","While creating your profile",true);

                createprofile();

            }
        });

    }

    public void createprofile() {
        if (TextUtils.isEmpty(s1)) {
            progressDialog.dismiss();
            name.setError("required");

        } else if (TextUtils.isEmpty(s2)) {
            progressDialog.dismiss();
            age.setError("required");

        }else if (TextUtils.isEmpty(s6)) {
            progressDialog.dismiss();
            pincode.setError("required");

        }

        else if (s7.length()!=10) {
            progressDialog.dismiss();
            mobile.setError("error number");

        }

        else {

            final profile1 p1 = new profile1(s1, s2, s3, s4, s5, s6,s7);


            firebaseFirestore.collection("users_profile").document(uid).set(p1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(profile.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(profile.this, "unsuccess", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

   public class profile1{
        private String s1,s2,s3,s4,s5,s6,s7;

        public profile1(){}

        public profile1(String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.s6 = s6;
            this.s7 = s7;
        }


        public String getname() {
            return s1;
        }

        public String getage() {
            return s2;
        }

        public String getmobile() {
            return s7;
        }

        public String getgroup() {
            return s3;
        }

        public String getstate() {
            return s4;
        }

        public String getcity(){
            return s5;
        }

        public String getpincode(){
            return s6;
        }


    }




}
