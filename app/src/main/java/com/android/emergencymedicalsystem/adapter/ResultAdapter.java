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
import com.android.emergencymedicalsystem.model.Sample;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder>{
    private List<Sample> result;
    Context context;
    public ResultAdapter(Context context, List<Sample> result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public ResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new ResultAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(result.get(position).getName());
        holder.cell.setText(result.get(position).getCell());
        holder.address.setText(result.get(position).getAddress());
        holder.blood.setText(result.get(position).getBlood());
        holder.gender.setText(result.get(position).getGender());
        holder.age.setText(result.get(position).getAge());
        holder.status.setText(result.get(position).getStatus());
    }

    @Override
    public int getItemCount() { return result.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,cell,address,blood,gender,age,status;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            cell = itemView.findViewById(R.id.cell);
            address = itemView.findViewById(R.id.address);
            blood = itemView.findViewById(R.id.blood);
            gender = itemView.findViewById(R.id.sex);
            age = itemView.findViewById(R.id.age);
            status = itemView.findViewById(R.id.result);
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
