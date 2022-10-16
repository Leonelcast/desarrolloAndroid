package com.example.proyectofinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;
import com.example.proyectofinal.models.FavRestaurantes;
import com.example.proyectofinal.models.Restaurante;


import java.util.List;

public class FavsRestauranteAdapter extends RecyclerView.Adapter<FavsRestauranteAdapter.ViewHolder> {
    private List<FavRestaurantes> mRestaurante;
    private Context context;

    public FavsRestauranteAdapter(List<FavRestaurantes> mRestaurante){this.mRestaurante = mRestaurante;}

    public void reloadData(List<FavRestaurantes> restaurantesFavs){
        this.mRestaurante = restaurantesFavs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavsRestauranteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.favsrestaurante, parent, false);
        FavsRestauranteAdapter.ViewHolder viewHolder = new FavsRestauranteAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavsRestauranteAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder {
    }
}
