package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.HashMap;

public class AddPurchasesActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    EditText productname,date,price,vendor;
    Button save_btn;
    ProgressDialog Loadingbar;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Purchaseslist> plist;
    PurchaseAdapter purchaseAdapter;
    DatabaseReference databaseReference;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchases);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Purchase details");
        setSupportActionBar(toolbar);
        Loadingbar=new ProgressDialog(AddPurchasesActivity.this);

        floatingActionButton=findViewById(R.id.floating_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(AddPurchasesActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.activity_prchsetryscrn,null);
                final EditText productname=(EditText)mview.findViewById(R.id.productname_edttxt);
                final EditText price=(EditText) mview.findViewById(R.id.price_edttxt);
                final EditText date=(EditText)mview.findViewById(R.id.date_edttxt);
                final EditText vendor=(EditText)mview.findViewById(R.id.vendordtils_edttxt);
                final EditText purchasequnty=(EditText)mview.findViewById(R.id.prchsqnty_edttxt);
                Button save = (Button)mview.findViewById(R.id.save_btn);

                Loadingbar=new ProgressDialog(AddPurchasesActivity.this);


                alert.setView(mview);
                final AlertDialog alertDialog=alert.create();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        createPurchaseDetails();
                    }

                    private void createPurchaseDetails() {
                        String pchsproductname = productname.getText().toString();
                        String pchsvendor = vendor.getText().toString();
                        String  pchsdate = date.getText().toString();
                        String  pchsprice = price.getText().toString();
                        String pchsquantity = purchasequnty.getText().toString();
                        if(TextUtils.isEmpty(pchsproductname))
                        {
                            Toast.makeText(AddPurchasesActivity.this,"Please Enter Your Product Name...",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(pchsvendor))
                        {
                            Toast.makeText(AddPurchasesActivity.this,"Please Enter Vendor Name...",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(pchsdate))
                        {
                            Toast.makeText(AddPurchasesActivity.this,"Please Choose a Valid Date",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(pchsprice))
                        {
                            Toast.makeText(AddPurchasesActivity.this,"Please Enter Purchase Price ...",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(pchsquantity))
                        {
                            Toast.makeText(AddPurchasesActivity.this,"Please Enter Quantity...",Toast.LENGTH_LONG);
                        }
                        else
                        {
                            Loadingbar.setTitle("Adding Details");
                            Loadingbar.setMessage("Please wait!While We Are Adding Details");
                            Loadingbar.setCanceledOnTouchOutside(false);
                            Loadingbar.show();

                        }
                        ValidatePurchaseDetails(pchsproductname,pchsvendor,pchsdate,pchsprice,pchsquantity);



                    }
                    //  Adding Details into Firebase
                    private void ValidatePurchaseDetails(final String pchsproductname, final String pchsvendor, final String pchsdate, final String pchsprice, final String pchsquantity) {
                        final DatabaseReference RootRef;
                        RootRef= FirebaseDatabase.getInstance().getReference();
                        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!(dataSnapshot.child("PurchaseDetails").child(pchsproductname).exists())){
                                    HashMap<String,Object> userdataMap = new HashMap<>();
                                    userdataMap.put("PurchasedProductName",pchsproductname);
                                    userdataMap.put("PurchasedVendorName",pchsvendor);
                                    userdataMap.put("PurchasedDate",pchsdate);
                                    userdataMap.put("PurchasedPrice",pchsprice);
                                    userdataMap.put("PurchasedQuantity",pchsquantity);
                                    RootRef.child("PurchaseDetails").child(pchsproductname).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(AddPurchasesActivity.this,"Added Sale details successfully",Toast.LENGTH_SHORT).show();
                                                Loadingbar.dismiss();
                                            }
                                            else
                                            {
                                                Loadingbar.dismiss();
                                                Toast.makeText(AddPurchasesActivity.this,"Network Error:Please try again after sometime....",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });


                                }
                                else
                                {
                                    //Toast.makeText(AddSalesActivity.this,"This"+phonenum+"Already Exists",Toast.LENGTH_LONG).show();
                                    Loadingbar.dismiss();
                                    Toast.makeText(AddPurchasesActivity.this,"Some Error occured",Toast.LENGTH_SHORT).show();


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });











                    }
                });
                alertDialog.show();
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final EditText editText=(EditText)view.findViewById(R.id.date_edttxt);
                        Calendar calendar=Calendar.getInstance();
                        final int year =calendar.get(Calendar.YEAR);
                        final int month=calendar.get(Calendar.MONTH);
                        final int day=calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datepickerdialog=new DatePickerDialog(AddPurchasesActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month= month+1;
                                String date=day+"/"+month+"/"+year;
                                editText.setText(date);
                            }
                        },year, month,day);
                        datepickerdialog.show();
                    }
                });
            }


        });
//Displaying Details from Firebase
        plist=new ArrayList<Purchaseslist>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("PurchaseDetails");
        recyclerView=findViewById(R.id.prchsesrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Purchaseslist l=dataSnapshot1.getValue(Purchaseslist.class);
                    plist.add(l);
                    Log.d("hurray", String.valueOf(l));
                }
                purchaseAdapter=new PurchaseAdapter(AddPurchasesActivity.this,plist);
                recyclerView.setAdapter(purchaseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddPurchasesActivity.this,"Something is Wrong",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
