package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
        Context context;
        ArrayList<CustomerList> customerLists;

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerViewHolder(LayoutInflater.from(context).inflate(R.layout.customerdetails,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.custname.setText(customerLists.get(position).getCustName());
        holder.custphoneno.setText(customerLists.get(position).getCustPhoneNo());
        holder.custaddress.setText(customerLists.get(position).getCustAddress());
    }

    @Override
    public int getItemCount() {
        return customerLists.size();
    }

         public CustomerAdapter(ArrayList<CustomerList> p, Context c)
        {
            customerLists = p;
            context = c;
        }






    class CustomerViewHolder extends RecyclerView.ViewHolder{
            TextView custname,custaddress,custphoneno;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            custname=itemView.findViewById(R.id.name_tv);
            custaddress=itemView.findViewById(R.id.address_tv);
            custphoneno=itemView.findViewById(R.id.phoneno_tv);
        }
    }
}
