package com.unimas.e_nelayanadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unimas.e_nelayanadmin.Activity.FishermanDetailsActivity;
import com.unimas.e_nelayanadmin.Model.Seller;
import com.unimas.e_nelayanadmin.R;

import java.util.List;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.SellerViewHolder> {
    Context mContext;
    List<Seller> sellerList;

    public SellerAdapter(Context mContext, List<Seller> sellerList) {
        this.mContext = mContext;
        this.sellerList = sellerList;
    }

    @NonNull
    @Override
    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.seller_list_for_rv, parent, false);
        return new SellerAdapter.SellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerViewHolder holder, int position) {
        holder.sellerName.setText(sellerList.get(position).getSellerName());
        holder.sellerSource.setText(sellerList.get(position).getSellingFishSource());
        Glide.with(mContext).load(sellerList.get(position).getSellerImage()).into(holder.sellerImage);
    }

    @Override
    public int getItemCount() {
        return sellerList.size();
    }


    public class SellerViewHolder extends RecyclerView.ViewHolder{
        TextView sellerName, sellerSource;
        ImageView sellerImage;
        Button details;
        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);
            sellerName = itemView.findViewById(R.id.sName);
            sellerSource = itemView.findViewById(R.id.sSource);
            sellerImage = itemView.findViewById(R.id.sImage);
            details = itemView.findViewById(R.id.detailsButton);

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToDetails = new Intent(mContext, FishermanDetailsActivity.class);
                    int position = getAdapterPosition();

                    goToDetails.putExtra("sellerName", sellerList.get(position).getSellerName());
                    goToDetails.putExtra("sellerSource", sellerList.get(position).getSellingFishSource());
                    goToDetails.putExtra("sellerAddress", sellerList.get(position).getAddress());
                    goToDetails.putExtra("sellerImage", sellerList.get(position).getSellerImage());
                    goToDetails.putExtra("sellerId", sellerList.get(position).getSellerId());
                    goToDetails.putExtra("sellingReason", sellerList.get(position).getSellingReason());
                    goToDetails.putExtra("phoneNumber", sellerList.get(position).getPhoneNumber());
                    goToDetails.putExtra("approvalStatus", sellerList.get(position).getApprovalStatus());
                    mContext.startActivity(goToDetails);
                }
            });

        }
    }
}
