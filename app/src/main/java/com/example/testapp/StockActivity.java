package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StockActivity extends AppCompatActivity {
        DatabaseReference databaseReference;
        RecyclerView recyclerView;
        ArrayList<ProductList> list;
        MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Stock Details");
        setSupportActionBar(toolbar);

        list=new ArrayList<ProductList>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("ProductDetails");
        recyclerView=findViewById(R.id.productrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
  //    Retrieving Product List Details from  Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    ProductList p=dataSnapshot1.getValue(ProductList.class);
                    list.add(p);
                    Log.d("hurray", String.valueOf(p));
                }
                myAdapter=new MyAdapter(StockActivity.this,list);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StockActivity.this,"Something is Wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
