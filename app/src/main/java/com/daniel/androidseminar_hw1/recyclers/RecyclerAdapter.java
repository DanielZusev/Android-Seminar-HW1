package com.daniel.androidseminar_hw1.recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.androidseminar_hw1.R;
import com.daniel.androidseminar_hw1.utils.retrofit.Country;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Country> countries;
    private ItemClickListener itemClickListener;

    public RecyclerAdapter(Context context, List<Country> countries, ItemClickListener itemClickListener) {
        this.context = context;
        this.countries = countries;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.country_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.countryName.setText(countries.get(position).getName());
        holder.nativeCountryName.setText(countries.get(position).getNativeName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.OnItemClick(countries.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public interface ItemClickListener {
        void OnItemClick(Country country);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView countryName;
        TextView nativeCountryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_name);
            nativeCountryName = itemView.findViewById(R.id.native_country_name);
        }
    }
}
