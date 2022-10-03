package com.example.proyectofinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.MainActivity;
import com.example.proyectofinal.R;
import com.example.proyectofinal.RestaurantDesActivity;
import com.example.proyectofinal.models.Restaurante;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteAdapter.ViewHolder> {
    private List<Restaurante> mRestaurante;
    private Context context;
    Menu menu;

    public RestauranteAdapter(List<Restaurante> mRestaurante){this.mRestaurante = mRestaurante;}

    public void reloadData(List<Restaurante> restaurantes){
        this.mRestaurante = restaurantes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestauranteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.img_restaurante, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteAdapter.ViewHolder holder, int position) {
        Restaurante restaurante = mRestaurante.get(position);
        TextView tourNameTextView = holder.mNombre;
        tourNameTextView.setText(restaurante.nombre);
        TextView tourDepartamentoTextView = holder.mDepartamento;
        tourDepartamentoTextView.setText(restaurante.departamento);
        TextView tourCalificacionTextView = holder.mCalificacion;
        if(restaurante.calificacion == null){
            restaurante.calificacion = "0";
        }
        tourCalificacionTextView.setText(restaurante.calificacion);
        TextView urlTextView = holder.mURL;
        urlTextView.setText(restaurante.img);
        ImageView tourImg = holder.mRestauranteImage;
        Glide.with(this.context).load(restaurante.img).into(tourImg);

    }

    @Override
    public int getItemCount() {
        return mRestaurante.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private ImageView mRestauranteImage;
        private TextView mNombre;
        private TextView mDepartamento;
        private TextView mCalificacion;
        private TextView mURL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRestauranteImage = (ImageView) itemView.findViewById(R.id.res_img);
            mNombre = (TextView) itemView.findViewById(R.id.nombre_res);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento_res);
            mCalificacion = (TextView) itemView.findViewById(R.id.calificacion_res);
            mURL = (TextView) itemView.findViewById(R.id.url);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), RestaurantDesActivity.class);
            intent.putExtra("nombre", mNombre.getText().toString());
            intent.putExtra("departamento", mDepartamento.getText().toString());
            intent.putExtra("califiacion", mCalificacion.getText().toString());
            intent.putExtra("img", mURL.getText().toString());
            view.getContext().startActivity(intent);
        }
    }

    public void Ordenar(int i){
        switch (i){
            case 0:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.getNombre().compareTo(r2.getNombre());
                    }
                });
                break;
            case 1:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r2.getNombre().compareTo(r1.getNombre());
                    }
                });
                break;
            case 2:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.getDepartamento().compareTo(r2.getDepartamento());
                    }
                });
                break;
            case 3:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r2.getDepartamento().compareTo(r1.getDepartamento());
                    }
                });
                break;
            case 4:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r2.getCalificacion().compareTo(r1.getCalificacion());

                    }
                });
                break;
            case 5:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.getCalificacion().compareTo(r2.getCalificacion());
                    }
                });
                break;
        }
        notifyDataSetChanged();
    }
}
