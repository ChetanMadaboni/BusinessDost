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
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

public class AddSalesActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    EditText saleproductname,salecustomername,saledate,saleprice;
    Button savesalesbtn;
    Toolbar toolbar;
    ProgressDialog Loadingbar;
    ArrayList<SalesList> slist;
    DatabaseReference databaseReference;
    SalesAdapter salesAdapter;
    RecyclerView recyclerView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);
 //     Intializing toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sales details");
        setSupportActionBar(toolbar);
        Loadingbar=new ProgressDialog(AddSalesActivity.this);






        floatingActionButton=findViewById(R.id.sales_floating_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(AddSalesActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.activity_slsetryscrn,null);
                saleproductname=mview.findViewById(R.id.salesproductname_edttxt);
                salecustomername=mview.findViewById(R.id.salecstmrdtils_edttxt);
                saledate=mview.findViewById(R.id.saledate_edttxt);
                saleprice=mview.findViewById(R.id.saleprice_edttxt);
                final EditText salewqunty = mview.findViewById(R.id.qntydtils_edttxt);
                Button savesalesbtn=mview.findViewById(R.id.salessave_btn);
                alert.setView(mview);

                final AlertDialog alertDialog=alert.create();
                savesalesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        createSalesDetails();
                    }

                    private void createSalesDetails() {
                        String sleproductname = saleproductname.getText().toString();
                        String slcustmrname = salecustomername.getText().toString();
                        String  sldate = saledate.getText().toString();
                        String  slprice = saleprice.getText().toString();
                        String slquantity = salewqunty.getText().toString();
                        if(TextUtils.isEmpty(sleproductname))
                        {
                            Toast.makeText(AddSalesActivity.this,"Please Enter Your Product Name...",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(slcustmrname))
                        {
                            Toast.makeText(AddSalesActivity.this,"Please Enter Customer Name...",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(sldate))
                        {
                            Toast.makeText(AddSalesActivity.this,"Please Choose a Valid Date",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(slprice))
                        {
                            Toast.makeText(AddSalesActivity.this,"Please Enter Selling Price...",Toast.LENGTH_LONG);
                        }
                        else if(TextUtils.isEmpty(slquantity))
                        {
                            Toast.makeText(AddSalesActivity.this,"Please Enter Your Quantity...",Toast.LENGTH_LONG);
                        }
                        else
                        {
                            Loadingbar.setTitle("Adding Details");
                            Loadingbar.setMessage("Please wait!While We Are Adding Details");
                            Loadingbar.setCanceledOnTouchOutside(false);
                            Loadingbar.show();

                        }

                        ValidateSalesDetails(sleproductname,slcustmrname,sldate,slprice,slquantity);
                    }
//                  Adding Sales Details into firebase
                    private void ValidateSalesDetails(final String sleproductname, final String slcustmrname, final String sldate, final String slprice, final String slquantity) {
                        final DatabaseReference RootRef;
                        RootRef= FirebaseDatabase.getInstance().getReference();
                        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!(dataSnapshot.child("SalesDetails").child(sleproductname).exists())){
                                    HashMap<String,Object> userdataMap = new HashMap<>();
                                    userdataMap.put("ProductName",sleproductname);
                                    userdataMap.put("CustomerName",slcustmrname);
                                    userdataMap.put("SaleDate",sldate);
                                    userdataMap.put("SalePrice",slprice);
                                    userdataMap.put("SaleQuantity",slquantity);
                                    RootRef.child("SalesDetails").child(sleproductname).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(AddSalesActivity.this,"Added Sale details successfully",Toast.LENGTH_SHORT).show();
                                                Loadingbar.dismiss();
                                            }
                                            else
                                            {
                                                Loadingbar.dismiss();
                                                Toast.makeText(AddSalesActivity.this,"Network Error:Please try again after sometime....",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });


                                }
                                else
                                {
                                    //Toast.makeText(AddSalesActivity.this,"This"+phonenum+"Already Exists",Toast.LENGTH_LONG).show();
                                    Loadingbar.dismiss();
                                    Toast.makeText(AddSalesActivity.this,"Some Error occured",Toast.LENGTH_SHORT).show();


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }
                });
                alertDialog.show();
//              Retrieving Salesdetails from firebase
                slist=new ArrayList<SalesList>();
                databaseReference= FirebaseDatabase.getInstance().getReference().child("SalesDetails");
                recyclerView=findViewById(R.id.salesrecyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(AddSalesActivity.this));
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {
                            SalesList s=dataSnapshot1.getValue(SalesList.class);
                            slist.add(s);
                            Log.d("hurray", String.valueOf(s));
                        }
                        salesAdapter=new SalesAdapter(AddSalesActivity.this,slist);
                        recyclerView.setAdapter(salesAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AddSalesActivity.this,"Something is Wrong",Toast.LENGTH_SHORT).show();
                    }
                });




                saledate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final EditText editText=(EditText)view.findViewById(R.id.saledate_edttxt);
                        Calendar calendar=Calendar.getInstance();
                        final int year =calendar.get(Calendar.YEAR);
                        final int month=calendar.get(Calendar.MONTH);
                        final int day=calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datepickerdialog=new DatePickerDialog(AddSalesActivity.this, new DatePickerDialog.OnDateSetListener() {
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





    }




}
