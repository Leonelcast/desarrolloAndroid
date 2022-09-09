package com.example.proyectofinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.R;
import com.example.proyectofinal.models.Tour;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {

    private List<Tour> mTour;
    private Context context;
    public TourAdapter(List<Tour> mTour){
        this.mTour = mTour;
    }
    @NonNull
    @Override
    public TourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.fragment_tour, parent, false);

        // Return a new holder instance
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
        Glide.with(this.context).load("http://goo.gl/gEgYUd").into(tourImg);

    }

    @Override
    public int getItemCount() {
        return mTour.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mTourImage;
        private TextView mNombre;
        private TextView mDepartamento;
        private TextView mCalificacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTourImage = (ImageView) itemView.findViewById(R.id.imagenTour);
            mNombre = (TextView) itemView.findViewById(R.id.touristicNombre);
            mDepartamento = (TextView) itemView.findViewById(R.id.touristicDepartamento);
            mCalificacion = (TextView) itemView.findViewById(R.id.touristicCalificacion);
        }
    }
}
