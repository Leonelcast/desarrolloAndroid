package com.example.proyectofinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.MainActivity;
import com.example.proyectofinal.R;
import com.example.proyectofinal.models.Restaurante;

import java.util.List;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteAdapter.ViewHolder> {
    private List<Restaurante> mRestaurante;
    private Context context;

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
        tourCalificacionTextView.setText(restaurante.calificacion);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRestauranteImage = (ImageView) itemView.findViewById(R.id.res_img);
            mNombre = (TextView) itemView.findViewById(R.id.nombre_res);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento_res);
            mCalificacion = (TextView) itemView.findViewById(R.id.calificacion_res);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
