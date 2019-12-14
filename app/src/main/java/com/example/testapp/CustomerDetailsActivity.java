package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog Loadingbar;
    EditText customername,customerphone,customeraddrss;
    RecyclerView recyclerView;
    ArrayList<CustomerList> cstmrlist;
    CustomerAdapter customerAdapter;
    DatabaseReference databaseReference;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
   //    Intializing  toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Customer details");
        setSupportActionBar(toolbar);
        Loadingbar=new ProgressDialog(CustomerDetailsActivity.this);
        FloatingActionButton floatingActionButton =findViewById(R.id.cstmrfloating_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(CustomerDetailsActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.activity_cstmretryscrn,null);
                customername=mview.findViewById(R.id.cmstrname_edttxt);
                customerphone=mview.findViewById(R.id.cstmrphone_edttxt);
                customeraddrss=mview.findViewById(R.id.cstmraddr_edttxt);
                Button cstmrsalesbtn=mview.findViewById(R.id.cstmrdtilssave_btn);
                alert.setView(mview);
                final AlertDialog alertDialog=alert.create();
                cstmrsalesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        createCustomerDetails();
                    }

                    private void createCustomerDetails() {

                        String cstrname=customername.getText().toString();
                        String cstrphone=customerphone.getText().toString();
                        String cstraddr=customeraddrss.getText().toString();
                        if(TextUtils.isEmpty(cstrname))
                        {
                            Toast.makeText(CustomerDetailsActivity.this,"Please Enter Customer Name...",Toast.LENGTH_LONG).show();
                        }
                        else if(TextUtils.isEmpty(cstrphone))
                        {
                            Toast.makeText(CustomerDetailsActivity.this,"Please Enter Customer Phone Number...",Toast.LENGTH_LONG).show();
                        }
                        else if (TextUtils.isEmpty(cstraddr))
                        {
                            Toast.makeText(CustomerDetailsActivity.this,"Please Enter Customer Address...",Toast.LENGTH_LONG);
                        }
                       validateCustomerDetails(cstrname,cstrphone,cstraddr);
                    }
//                  Adding Customer Details into firebase
                    public void validateCustomerDetails(final String cstrmname,final String cstrmphone,final String cstrmaddr)
                    {
                        final DatabaseReference rootRef;
                        rootRef= FirebaseDatabase.getInstance().getReference();
                        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!(dataSnapshot.child("CustomerDetails").child(cstrmphone).exists())){
                                    HashMap<String,Object> userdataMap = new HashMap<>();
                                    userdataMap.put("CustName",cstrmname);
                                    userdataMap.put("CustPhoneNo",cstrmphone);
                                    userdataMap.put("CustAddress",cstrmaddr);
                                    Log.d("addr",cstrmaddr);
                                    rootRef.child("CustomerDetails").child(cstrmphone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CustomerDetailsActivity.this,"Added Vendor details Successfully",Toast.LENGTH_SHORT).show();
                                                Loadingbar.dismiss();
                                            }
                                            else
                                            {
                                                Loadingbar.dismiss();
                                                Toast.makeText(CustomerDetailsActivity.this,"Network Error:Please try again after sometime....",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });


                                }
                                else
                                {
                                    //Toast.makeText(AddSalesActivity.this,"This"+phonenum+"Already Exists",Toast.LENGTH_LONG).show();
                                    Loadingbar.dismiss();
                                    Toast.makeText(CustomerDetailsActivity.this,"Some Error occured",Toast.LENGTH_SHORT).show();


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(CustomerDetailsActivity.this,"db error",Toast.LENGTH_SHORT).show();

                            }
                        });



                    }
                });
                alertDialog.show();
            }
        });
        cstmrlist=new ArrayList<CustomerList>();
        //        Retrieving Customerdetails from firebase
        databaseReference= FirebaseDatabase.getInstance().getReference().child("CustomerDetails");
        recyclerView=findViewById(R.id.cstmrrecylcerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    CustomerList c=dataSnapshot1.getValue(CustomerList.class);
                    cstmrlist.add(c);
                    Log.d("hurray", String.valueOf(c));
                }
                customerAdapter=new CustomerAdapter(cstmrlist,CustomerDetailsActivity.this);
                recyclerView.setAdapter(customerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CustomerDetailsActivity.this,"Something is Wrong",Toast.LENGTH_SHORT).show();
            }
        });







    }
}
