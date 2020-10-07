package com.android.emergencymedicalsystem.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.Nurse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private List<Nurse> nurses;
    Context context;
    public RecyclerViewAdapter(Context context, List<Nurse> nurses) {
        this.context = context;
        this.nurses = nurses;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nurse_item, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(nurses.get(position).getName());
        holder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+nurses.get(position).getCell()));
                context.startActivity(callIntent);
            }
        });
        holder.hospital.setText(nurses.get(position).getHospital());
    }

    @Override
    public int getItemCount() { return nurses.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,hospital;
        ImageView cell;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            cell = itemView.findViewById(R.id.cell);
            hospital = itemView.findViewById(R.id.hospital);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*Intent i = new Intent(context, FlatActivity.class);
            i.putExtra("flat", nurses.get(getAdapterPosition()).getFlat_no());
            context.startActivity(i);*/
        }
    }
}
