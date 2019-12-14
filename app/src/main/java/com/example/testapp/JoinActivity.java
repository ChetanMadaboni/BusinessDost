package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class JoinActivity extends AppCompatActivity {
    Button createaccnt;
    EditText Name,Phoneno,Email,Create_pass,Address;
    ProgressDialog Loadingbar;

    String[] businessctrgry = { "Sales","Marketing","Sales & Purchases","Tailoring","Manufacturing"};
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, businessctrgry);
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.bsnssctrgry_atcptedt_edtxt);
        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);
        
        
        
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("SIGN UP");
        setSupportActionBar(toolbar);
        createaccnt=findViewById(R.id.createaccnt_btn);
        Name=findViewById(R.id.name_edttxt);
        Phoneno=findViewById(R.id.phoneno_edttxt);
        Email=findViewById(R.id.email_edttxt);
        Address=findViewById(R.id.address_edttxt);
        Create_pass=findViewById(R.id.enterpasswrd_edttxt);
        Loadingbar = new ProgressDialog(this);
        
        createaccnt.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }

            
        });
        
        
    }

    public void createAccount() {
        String name = Name.getText().toString();
        String phonenum = Phoneno.getText().toString();
        String  email = Email.getText().toString();
        String  password = Create_pass.getText().toString();
        String address = Address.getText().toString();
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Please Ente Your Name...",Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(phonenum))
        {
            Toast.makeText(this,"Please Ente Your Phone Number...",Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please Ente Your Email id...",Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Ente Your Password...",Toast.LENGTH_LONG);
        }
         else if(TextUtils.isEmpty(address))
        {
            Toast.makeText(this,"Please Ente Your Address...",Toast.LENGTH_LONG);
        }
         else
        {
        Loadingbar.setTitle("Create Account");
        Loadingbar.setMessage("Please wait!While We Are Checking Your Credentials");
        Loadingbar.setCanceledOnTouchOutside(false);
        Loadingbar.show();

        }

         ValidatephoneNumber( name,phonenum,password,email,address);
    }

    private void ValidatephoneNumber(final String name, final String phonenum, final String password, final String email, final String address) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phonenum).exists())){
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("Name",name);
                    userdataMap.put("Phone",phonenum);
                    userdataMap.put("Password",password);
                    userdataMap.put("Email",email);
                    userdataMap.put("Address",address);
                    RootRef.child("Users").child(phonenum).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(JoinActivity.this,"Congratulations,your account has been successfully created",Toast.LENGTH_SHORT).show();
                                Loadingbar.dismiss();
                                Intent intent= new Intent(JoinActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Loadingbar.dismiss();
                                Toast.makeText(JoinActivity.this,"Network Error:Please try again after sometime....",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
                else
                {
                    Toast.makeText(JoinActivity.this,"This"+phonenum+"Already Exists",Toast.LENGTH_LONG).show();
                    Loadingbar.dismiss();
                    Toast.makeText(JoinActivity.this,"Please ry using another phone number",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JoinActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




}
