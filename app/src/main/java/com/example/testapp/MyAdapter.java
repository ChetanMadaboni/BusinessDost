package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        Context context;
        ArrayList<ProductList> productLists;
        public MyAdapter(Context c,ArrayList<ProductList> p)
        {
            context=c;
            productLists=p;
        }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.productname.setText(productLists.get(position).getProductname());
        holder.productdescription.setText(productLists.get(position).getProductDescription());
        Picasso.get().load(productLists.get(position).getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
            TextView productname,productdescription;
            ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productname=itemView.findViewById(R.id.productnametv);
            productdescription=itemView.findViewById(R.id.descriptiontv);
            imageView=itemView.findViewById(R.id.productimg);
        }
    }

}
