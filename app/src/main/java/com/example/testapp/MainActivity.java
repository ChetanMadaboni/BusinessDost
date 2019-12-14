package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testapp.Model.Users;
import com.example.testapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button joinnw , mainlogin;
    ProgressDialog Loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainlogin=(Button)findViewById(R.id.mainlogin_btn);
        joinnw=(Button)findViewById(R.id.joinnw_btn);
        Loadingbar = new ProgressDialog (this);
        Paper.init(this);
        mainlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        joinnw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
        String UserPhoneKey= Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);
        if(UserPhoneKey!=""&& UserPasswordKey!=""){
            if(!TextUtils.isEmpty(UserPhoneKey)&&!TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserPhoneKey,UserPasswordKey);
                Loadingbar.setTitle("Already Logged in...");
                Loadingbar.setMessage("Please Wait....");
                Loadingbar.setCanceledOnTouchOutside(false);
                Loadingbar.show();
            }
        }

    }


        private void AllowAccess(final String phone, final String password) {
            final DatabaseReference RootRef;
            RootRef= FirebaseDatabase.getInstance().getReference();
            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child("Users").child(phone).exists()) {

                        Users userData=dataSnapshot.child("Users").child(phone).getValue(Users.class);
//                    Log.d("UserData", String.valueOf(userData));
                        if(userData.getPhone().equals(phone)){
                            Loadingbar.dismiss();
                            if(userData.getPassword().equals(password)){
                                Loadingbar.dismiss();
                                Toast.makeText(MainActivity.this,"Logged in Successfully...",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                Prevalent.currentOnlineUser=userData;
                                startActivity(intent);
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Account with this " +phone+" Do not Exists",Toast.LENGTH_SHORT).show();
                        Loadingbar.dismiss();

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
