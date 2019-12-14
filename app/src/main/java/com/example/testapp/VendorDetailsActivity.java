package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

public class VendorDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog Loadingbar;
    FloatingActionButton floatingActionButton;
    EditText vendorname,vendorphone,vendoraddr,vndravproduct;
    Button vndrsalesbtn;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<VendorList> vndrlist;
    VendorAdapter vendorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_details);


//        Intializing Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vendor details");
        setSupportActionBar(toolbar);
        Loadingbar=new ProgressDialog(VendorDetailsActivity.this);
        vndrlist=new ArrayList<VendorList>();
//        Retrieving Vendordetails from firebase
        databaseReference= FirebaseDatabase.getInstance().getReference().child("VendorDetails");
        recyclerView=findViewById(R.id.vendordetlsferecylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    VendorList v=dataSnapshot1.getValue(VendorList.class);
                    vndrlist.add(v);
                    Log.d("hurray", String.valueOf(v));
                }
                vendorAdapter=new VendorAdapter(VendorDetailsActivity.this,vndrlist);
                recyclerView.setAdapter(vendorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(VendorDetailsActivity.this,"Something is Wrong",Toast.LENGTH_SHORT).show();
            }
        });



        floatingActionButton=findViewById(R.id.vndrfloating_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(VendorDetailsActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.activity_vndretryscrn,null);
                vendorname=mview.findViewById(R.id.vendorname_edttxt);
                vendorphone=mview.findViewById(R.id.vndrphne_edttxt);
                vendoraddr=mview.findViewById(R.id.vndraddr_edttxt);
                vndravproduct=mview.findViewById(R.id.availableproduct_edttxt);
                Button  vndrsalesbtn=mview.findViewById(R.id.vndrdtilssave_btn);
                alert.setView(mview);
                final AlertDialog alertDialog=alert.create();
                vndrsalesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        createVendorDetails();
                    }

                    private void createVendorDetails() {

                        String vndrname=vendorname.getText().toString();
                        String vndrphone=vendorphone.getText().toString();
                        String vndraddrss=vendoraddr.getText().toString();
                        String vndravprdt=vndravproduct.getText().toString();
                        if(TextUtils.isEmpty(vndrname))
                        {
                            Toast.makeText(VendorDetailsActivity.this,"Please Enter Vendor Name...",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(vndrphone))
                        {
                            Toast.makeText(VendorDetailsActivity.this,"Please Enter Vendor Phone Number...",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(vndravprdt))
                        {
                            Toast.makeText(VendorDetailsActivity.this,"Please Enter Available Product...",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(vndraddrss))
                        {
                            Toast.makeText(VendorDetailsActivity.this,"Please Enter Vendor Address...",Toast.LENGTH_LONG);
                        }
                        ValidVendorDetails(vndrname,vndrphone,vndraddrss,vndravprdt);

                    }
//                    Adding VendorDetails into firebase
                    private void ValidVendorDetails(final String vndrname, final String vndrphone, final String vndraddrss, final String vndravprdt) {


                        final DatabaseReference RootRef;
                        RootRef= FirebaseDatabase.getInstance().getReference();
                        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!(dataSnapshot.child("VendorDetails").child(vndrname).exists())){
                                    HashMap<String,Object> userdataMap = new HashMap<>();
                                    userdataMap.put("VendorName",vndrname);
                                    userdataMap.put("VendorPhoneNo",vndrphone);
                                    userdataMap.put("VendorAddress",vndraddrss);
                                    userdataMap.put("VendorAvProduct",vndravprdt);
                                    RootRef.child("VendorDetails").child(vndrname).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(VendorDetailsActivity.this,"Added Vendor details Successfully",Toast.LENGTH_SHORT).show();
                                                Loadingbar.dismiss();
                                            }
                                            else
                                            {
                                                Loadingbar.dismiss();
                                                Toast.makeText(VendorDetailsActivity.this,"Network Error:Please try again after sometime....",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });


                                }
                                else
                                {
                                    //Toast.makeText(AddSalesActivity.this,"This"+phonenum+"Already Exists",Toast.LENGTH_LONG).show();
                                    Loadingbar.dismiss();
                                    Toast.makeText(VendorDetailsActivity.this,"Some Error occured",Toast.LENGTH_SHORT).show();


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                });
                alertDialog.show();


            }
        });

    }
}
