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
import com.example.proyectofinal.models.Tour;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {

    private List<Tour> mTour;
    private Context context;

    public TourAdapter(List<Tour> mTour){
        this.mTour = mTour;
    }

    public void reloadData(List<Tour> tours){
        this.mTour = tours;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.img_tour, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TourAdapter.ViewHolder holder, int position) {
        Tour tour = mTour.get(position);
        TextView tourNameTextView = holder.mNombre;
        tourNameTextView.setText(tour.nombre);
        TextView tourDepartamentoTextView = holder.mDepartamento;
        tourDepartamentoTextView.setText(tour.departamento);
        TextView tourCalificacionTextView = holder.mCalificacion;
        tourCalificacionTextView.setText(tour.calificacion);
        ImageView tourImg = holder.mTourImage;
        Glide.with(this.context).load(tour.img).into(tourImg);

    }

    @Override
    public int getItemCount() {
        return mTour.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mTourImage;
        private TextView mNombre;
        private TextView mDepartamento;
        private TextView mCalificacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTourImage = (ImageView) itemView.findViewById(R.id.tour_img);
            mNombre = (TextView) itemView.findViewById(R.id.nombre_tour);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento);
            mCalificacion = (TextView) itemView.findViewById(R.id.calificacion);
            itemView.setOnClickListener(this);
        }

         @Override
         public void onClick(View view) {
             Intent intent = new Intent(view.getContext(), MainActivity.class);
             view.getContext().startActivity(intent);
         }
     }
}
