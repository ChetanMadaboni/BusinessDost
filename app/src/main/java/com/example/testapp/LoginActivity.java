package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.Model.Users;
import com.example.testapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    EditText inputphonenum , inputpasswrd;
    CheckBox checkBox;
    ProgressDialog Loadingbar;
    Button loginbtn;
    TextView forgetpasswd;
    private String parentDbName="Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputphonenum=findViewById(R.id.phoneno_edttxt);
        inputpasswrd=findViewById(R.id.passwrd_edttxt);
        checkBox=findViewById(R.id.check_box);
        loginbtn=findViewById(R.id.login_btn);
        forgetpasswd=findViewById(R.id.frgtpasswd_txtvw);
        Loadingbar=new ProgressDialog(this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        Paper.init(this);
    }

    private void LoginUser() {
        String phonenum=inputphonenum.getText().toString();
        String password=inputpasswrd.getText().toString();
        if(TextUtils.isEmpty(phonenum)){
            Toast.makeText(LoginActivity.this,"Please enter your Phone number...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Please enter your Password...",Toast.LENGTH_SHORT).show();

        }
        else{
            Loadingbar.setTitle("Login Account");
            Loadingbar.setMessage("Please Wait While We Are Checking Your Credentials");
            Loadingbar.setCanceledOnTouchOutside(false);
            Loadingbar.show();
            AllowAccessToAccount(phonenum,password);
        }
    }

    private void AllowAccessToAccount(final String phonenum, final String password) {

        if(checkBox.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phonenum);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }




        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phonenum).exists()) {
                    Users userData=dataSnapshot.child(parentDbName).child(phonenum).getValue(Users.class);
//                    Log.d("user",userData.toString());
//                    Log.d("UserData", String.valueOf(userData));
                    //               Long phne = Long.valueOf(phone);
                    if (userData!= null && userData.getPhone().equals(phonenum)) {
                        Loadingbar.dismiss();
                        if (userData.getPassword().equals(password)) {

                              if (parentDbName.equals("Users")) {
                                Toast.makeText(LoginActivity.this, "Logged in Successfully...", Toast.LENGTH_SHORT).show();
//                                LoadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                Prevalent.currentOnlineUser=userData;
                                Log.d("currentOnlineuser",Prevalent.currentOnlineUser.toString());

                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Account with this " +phonenum+" Do not Exists",Toast.LENGTH_SHORT).show();
                    Loadingbar.dismiss();

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
