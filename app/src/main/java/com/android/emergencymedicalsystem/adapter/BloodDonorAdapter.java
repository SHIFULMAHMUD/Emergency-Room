package com.android.emergencymedicalsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.user.blooddonor.DonorDetailsActivity;
import com.android.emergencymedicalsystem.user.nurse.NurseDetailsActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BloodDonorAdapter extends RecyclerView.Adapter<BloodDonorAdapter.MyViewHolder>{
    private List<User> donors;
    Context context;
    public BloodDonorAdapter(Context context, List<User> donors) {
        this.context = context;
        this.donors = donors;
    }

    @Override
    public BloodDonorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_item, parent, false);
        return new BloodDonorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BloodDonorAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(donors.get(position).getName());
        holder.group.setText(donors.get(position).getBlood());
        holder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ donors.get(position).getCell()));
                context.startActivity(callIntent);
            }
        });

    }

    @Override
    public int getItemCount() { return donors.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,group;
        ImageView cell;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            cell = itemView.findViewById(R.id.cell);
            group = itemView.findViewById(R.id.blood_group);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, DonorDetailsActivity.class);
            i.putExtra("name", donors.get(getAdapterPosition()).getName());
            i.putExtra("cell", donors.get(getAdapterPosition()).getCell());
            i.putExtra("group", donors.get(getAdapterPosition()).getBlood());
            i.putExtra("div", donors.get(getAdapterPosition()).getDivision());
            i.putExtra("area", donors.get(getAdapterPosition()).getArea());
            i.putExtra("status", donors.get(getAdapterPosition()).getStatus());
            context.startActivity(i);
        }
    }
}
