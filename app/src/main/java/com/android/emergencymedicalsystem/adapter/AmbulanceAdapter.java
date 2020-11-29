package com.android.emergencymedicalsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.user.ambulance.AmbulanceDetailsActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AmbulanceAdapter extends RecyclerView.Adapter<AmbulanceAdapter.MyViewHolder>  {
    private List<Ambulance> ambulances;
    Context context;

    public AmbulanceAdapter(Context context, List<Ambulance> ambulances) {
        this.context = context;
        this.ambulances = ambulances;
    }

    @Override
    public AmbulanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambulance_item, parent, false);
        return new AmbulanceAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmbulanceAdapter.MyViewHolder holder, int position) {
        holder.name.setText(ambulances.get(position).getName());
        holder.type.setText(ambulances.get(position).getType());
        holder.regno.setText(ambulances.get(position).getV_reg_no());
        holder.fair.setText("Tk "+ ambulances.get(position).getFair()+" per hour");
    }

    @Override
    public int getItemCount() {
        return ambulances.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,type,regno,fair;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.amb_name_tv);
            type = itemView.findViewById(R.id.amb_type_tv);
            regno = itemView.findViewById(R.id.amb_reg_no_tv);
            fair = itemView.findViewById(R.id.amb_fair_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, AmbulanceDetailsActivity.class);
            i.putExtra("name", ambulances.get(getAdapterPosition()).getName());
            i.putExtra("cell", ambulances.get(getAdapterPosition()).getCell());
            i.putExtra("type", ambulances.get(getAdapterPosition()).getType());
            i.putExtra("fair", ambulances.get(getAdapterPosition()).getFair());
            i.putExtra("reg", ambulances.get(getAdapterPosition()).getV_reg_no());
            i.putExtra("facility", ambulances.get(getAdapterPosition()).getFacility());
            i.putExtra("division", ambulances.get(getAdapterPosition()).getDivision());
            i.putExtra("area", ambulances.get(getAdapterPosition()).getArea());
            i.putExtra("img", ambulances.get(getAdapterPosition()).getImage());
            i.putExtra("token", ambulances.get(getAdapterPosition()).getToken());
            context.startActivity(i);
        }
    }
}
