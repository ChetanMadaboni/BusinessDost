package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder> {
    Context context;
    ArrayList<SalesList> salesLists;

    public SalesAdapter(Context c,ArrayList<SalesList> pl)
    {
        context=c;
        salesLists=pl;
    }

    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SalesViewHolder(LayoutInflater.from(context).inflate(R.layout.salesdetails,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SalesViewHolder holder, int position) {
        holder.custname.setText(salesLists.get(position).getCustomerName());
        holder.productname.setText(salesLists.get(position).getProductName());
        holder.date.setText(salesLists.get(position).getSaleDate());
        holder.price.setText(salesLists.get(position).getSalePrice());
        holder.quntty.setText(salesLists.get(position).getSaleQuantity());
    }

    @Override
    public int getItemCount() {
        return salesLists.size();
    }

    public class SalesViewHolder extends RecyclerView.ViewHolder{
        TextView custname,price,date,quntty,productname;

    public SalesViewHolder(@NonNull View itemView) {
        super(itemView);
        custname=itemView.findViewById(R.id.salescustname_tv);
        price=itemView.findViewById(R.id.salesprice_tv);
        date=itemView.findViewById(R.id.salesdate_tv);
        quntty=itemView.findViewById(R.id.salesquantity_tv);
        productname=itemView.findViewById(R.id.salesprdtname_tv);
    }
}
}
