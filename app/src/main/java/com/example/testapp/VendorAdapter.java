package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VendorViewHolder> {
        Context context;
        ArrayList<VendorList> vendorLists;

    public VendorAdapter(Context c,ArrayList<VendorList> p)
    {
        context=c;
        vendorLists=p;
    }



    @NonNull
    @Override
    public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VendorViewHolder(LayoutInflater.from(context).inflate(R.layout.vendordetails,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VendorViewHolder holder, int position) {
    holder.VendorName.setText(vendorLists.get(position).getVendorName());
    holder.VendorPhoneNo.setText(vendorLists.get(position).getVendorPhoneNo());
    holder.VendorAddress.setText(vendorLists.get(position).getVendorAddress());
    holder.VendorAvProduct.setText(vendorLists.get(position).getVendorAvProduct());
    }

    @Override
    public int getItemCount() {
        return vendorLists.size();
    }

    class VendorViewHolder extends RecyclerView.ViewHolder{

        TextView VendorName,VendorPhoneNo,VendorAddress,VendorAvProduct;
        public VendorViewHolder(@NonNull View itemView) {
            super(itemView);
            VendorName=itemView.findViewById(R.id.vndrname_tv);
            VendorPhoneNo=itemView.findViewById(R.id.vndrphne_tv);
            VendorAddress=itemView.findViewById(R.id.vndraddrs_tv);
            VendorAvProduct=itemView.findViewById(R.id.vndravlbleprdt_tv);
        }
    }
}
