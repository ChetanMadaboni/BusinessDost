package com.example.testapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class ProductListActivity extends AppCompatActivity {
    String productname,description,prffedvendor,saveCurrentDate,saveCurrentTime,productRandomKey,downloadImageUri;
        FloatingActionButton floatingActionButton;
        ImageView addproductPht;
        EditText addproductname,addproductdescription,addpreferdvndr;
        private static final int GalleryPick = 1;
        private Uri ImageUri;
    //    FirebaseStorage firebaseStorage;
        StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Products List");
        setSupportActionBar(toolbar);
        addproductPht=findViewById(R.id.addproductphtoimageview);
        addproductname=findViewById(R.id.productentryname_edttxt);
        addproductdescription=findViewById(R.id.prdtdscpt_edttxt);
        addpreferdvndr=findViewById(R.id.prdtprffdvndr_edttxt);
        LoadingBar=new ProgressDialog(this);
        Button productlistsavebtn=findViewById(R.id.pdtlstsave_btn);
        storageReference=FirebaseStorage.getInstance().getReference().child("Product Images");

        addproductPht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }



        });
      productlistsavebtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              ValidateProductData();
          }
      });


    }

    public void ValidateProductData() {

         productname=addproductname.getText().toString();
         description=addproductdescription.getText().toString();
         prffedvendor=addpreferdvndr.getText().toString();
        if(ImageUri == null){
            Toast.makeText(ProductListActivity.this,"Product image is mandatory...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productname)){
            Toast.makeText(ProductListActivity.this,"Please enter product Name...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(ProductListActivity.this,"Enter Description...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(prffedvendor)){
            Toast.makeText(ProductListActivity.this,"Please enter Preffered vendor name...",Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {

        LoadingBar.setTitle("Add new product");
        LoadingBar.setMessage("Please wait, while we are adding new Product...");
        LoadingBar.setCanceledOnTouchOutside(false);
        LoadingBar.show();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMMM yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
         saveCurrentTime=currentTime.format(calendar.getTime());
        productRandomKey= saveCurrentDate+saveCurrentTime;
        final StorageReference filePath=storageReference.child(ImageUri.getLastPathSegment()+ productRandomKey +".jpg");
        final UploadTask uploadTask=filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                LoadingBar.dismiss();
                String message=e.toString();
                Toast.makeText(ProductListActivity.this,"Error:"+message,Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ProductListActivity.this,"Product Image uploaded successfully",Toast.LENGTH_SHORT).show();
            }
        });
        Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();

                }
               downloadImageUri=filePath.getDownloadUrl().toString();
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    downloadImageUri=task.getResult().toString();
                    Toast.makeText(ProductListActivity.this,"getting Product image save to Database Succesfully..",Toast.LENGTH_SHORT).show();
                    SaveProductInfoToDatabase();
                }

            }
        });


    }

    private void SaveProductInfoToDatabase() {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("ProductDetails").child(productname).exists())){
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("ProductName",productname);
                    userdataMap.put("ProductDescription",description);
                    userdataMap.put("Preffrdvendor",prffedvendor);
                    userdataMap.put("Image",downloadImageUri);
                    RootRef.child("ProductDetails").child(productname).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent=new Intent(ProductListActivity.this,StockActivity.class);
                                startActivity(intent);
                                Toast.makeText(ProductListActivity.this,"Added Sale details successfully",Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                            }
                            else
                            {
                                LoadingBar.dismiss();
                                Toast.makeText(ProductListActivity.this,"Network Error:Please try again after sometime....",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
                else
                {
                    //Toast.makeText(AddSalesActivity.this,"This"+phonenum+"Already Exists",Toast.LENGTH_LONG).show();
                    LoadingBar.dismiss();
                    Toast.makeText(ProductListActivity.this,"Some Error occured",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ProductListActivity.super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            addproductPht.setImageURI(ImageUri);


        }
    }



    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);

    }

}
