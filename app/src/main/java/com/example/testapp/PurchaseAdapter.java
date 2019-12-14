package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewolder> {
    Context context;
    ArrayList<Purchaseslist> purchaseslists;

    public PurchaseAdapter(Context c,ArrayList<Purchaseslist> pl)
    {
        context=c;
        purchaseslists=pl;
    }

    @NonNull
    @Override
    public PurchaseViewolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PurchaseViewolder(LayoutInflater.from(context).inflate(R.layout.purchasedetails,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewolder holder, int position) {
        holder.pproductname.setText(purchaseslists.get(position).getPurchasedProductName());
        holder.pdate.setText(purchaseslists.get(position).getPurchasedDate());
        holder.pprice.setText(purchaseslists.get(position).getPurchasedPrice());
        holder.pquantity.setText(purchaseslists.get(position).getPurchasedQuantity());
        holder.pvendorname.setText(purchaseslists.get(position).getPurchasedVendorName());

    }

    @Override
    public int getItemCount() {
        return purchaseslists.size();
    }

    class PurchaseViewolder extends RecyclerView.ViewHolder{
        TextView pproductname,pdate,pprice,pvendorname,pquantity;
        public PurchaseViewolder(@NonNull View itemView) {
            super(itemView);
            pproductname=itemView.findViewById(R.id.pproductname_tv);
            pdate=itemView.findViewById(R.id.pdate_tv);
            pprice=itemView.findViewById(R.id.pprice_tv);
            pvendorname=itemView.findViewById(R.id.pvndrname_tv);
            pquantity=itemView.findViewById(R.id.pqntity_tv);
        }
    }
}
