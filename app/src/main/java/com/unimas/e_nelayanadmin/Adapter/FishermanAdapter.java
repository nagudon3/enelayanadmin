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
import com.unimas.e_nelayanadmin.Model.Fisherman;
import com.unimas.e_nelayanadmin.R;

import java.util.List;

public class FishermanAdapter extends RecyclerView.Adapter<FishermanAdapter.MyViewHolder> {
    Context mContext;
    List<Fisherman> fishermanList;

    public FishermanAdapter(Context mContext, List<Fisherman> fishermanList) {
        this.mContext = mContext;
        this.fishermanList = fishermanList;
    }

    @NonNull
    @Override
    public FishermanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.fisherman_list_for_rv, parent, false);
        return new FishermanAdapter.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull FishermanAdapter.MyViewHolder holder, int position) {
        holder.fishermanName.setText(fishermanList.get(position).getFishermanName());
        holder.fishermanLisence.setText(fishermanList.get(position).getFishermanLicenseNumber());
        Glide.with(mContext).load(fishermanList.get(position).getFishermanImage()).into(holder.fishermanImage);
    }

    @Override
    public int getItemCount() {
        return fishermanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fishermanName, fishermanLisence;
        ImageView fishermanImage;
        Button details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fishermanName = itemView.findViewById(R.id.fName);
            fishermanLisence = itemView.findViewById(R.id.fLicense);
            fishermanImage = itemView.findViewById(R.id.fImage);
            details = itemView.findViewById(R.id.detailsButton);

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToDetails = new Intent(mContext, FishermanDetailsActivity.class);
                    int position = getAdapterPosition();

                    if (fishermanList.get(position).getApprovalStatus() == true){
                        goToDetails.putExtra("approvalStatus", "Approved");
                    }else {
                        goToDetails.putExtra("approvalStatus", "Not Approved");
                    }
                    goToDetails.putExtra("fishermanName", fishermanList.get(position).getFishermanName());
                    goToDetails.putExtra("fishermanImage", fishermanList.get(position).getFishermanImage());
                    goToDetails.putExtra("fishermanLicenseNumber", fishermanList.get(position).getFishermanLicenseNumber());
                    goToDetails.putExtra("fishingArea", fishermanList.get(position).getFishingArea());
                    goToDetails.putExtra("address", fishermanList.get(position).getAddress());
                    goToDetails.putExtra("fishermanID", fishermanList.get(position).getFishermanID());
                    goToDetails.putExtra("years", fishermanList.get(position).getYears());
                    goToDetails.putExtra("phoneNumber", fishermanList.get(position).getPhoneNumber());
                    mContext.startActivity(goToDetails);

                }
            });
        }
    }
}
