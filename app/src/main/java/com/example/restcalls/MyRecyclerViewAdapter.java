package com.example.restcalls;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restcalls.model.users.Result;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    ArrayList<Result> results;

    public MyRecyclerViewAdapter(ArrayList list1) {
        results = list1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.user_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Result currentResult = results.get(i);
        viewHolder.tvUsername.setText(currentResult.getName().getFirst());
        viewHolder.tvUserAddress.setText(currentResult.getLocation().getStreet());
        viewHolder.tvUserPhone.setText(currentResult.getCell());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("result",results.get(i));
                Intent intentTostartDetails = new Intent(v.getContext(),DetailActivity.class);
                intentTostartDetails.putExtras(bundle);
                v.getContext().startActivity(intentTostartDetails);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        TextView tvUserAddress;
        TextView tvUserPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvUserAddress = itemView.findViewById(R.id.tvUserAddress);
            tvUserPhone = itemView.findViewById(R.id.tvUserPhone);
        }
    }
}
