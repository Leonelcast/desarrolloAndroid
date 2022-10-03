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
import com.example.proyectofinal.models.Tour;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {

    private List<Tour> mTour;
    private Context context;
    Menu menu;

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
        if(tour.calificacion == null){
            tour.calificacion = "0";
        }
        tourCalificacionTextView.setText(tour.calificacion);
        TextView urlTextView = holder.mURL;
        urlTextView.setText(tour.img);
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
         private TextView mURL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTourImage = (ImageView) itemView.findViewById(R.id.tour_img);
            mNombre = (TextView) itemView.findViewById(R.id.nombre_tour);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento);
            mCalificacion = (TextView) itemView.findViewById(R.id.calificacion);
            mURL = (TextView) itemView.findViewById(R.id.url);
            itemView.setOnClickListener(this);
        }

         @Override
         public void onClick(View view) {
             Intent intent = new Intent(view.getContext(), MainActivity.class);
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
                Collections.sort(mTour, new Comparator<Tour>() {
                    @Override
                    public int compare(Tour t1, Tour t2) {

                        return t1.getNombre().compareTo(t2.getNombre());
                    }
                });
                break;
            case 1:
                Collections.sort(mTour, new Comparator<Tour>() {
                    @Override
                    public int compare(Tour t1, Tour t2) {

                        return t2.getNombre().compareTo(t1.getNombre());
                    }
                });
                break;
            case 2:
                Collections.sort(mTour, new Comparator<Tour>() {
                    @Override
                    public int compare(Tour t1, Tour t2) {

                        return t1.getDepartamento().compareTo(t2.getDepartamento());
                    }
                });
                break;
            case 3:
                Collections.sort(mTour, new Comparator<Tour>() {
                    @Override
                    public int compare(Tour t1, Tour t2) {

                        return t2.getDepartamento().compareTo(t1.getDepartamento());
                    }
                });
                break;
            case 4:
                Collections.sort(mTour, new Comparator<Tour>() {
                    @Override
                    public int compare(Tour t1, Tour t2) {

                        return t2.getCalificacion().compareTo(t1.getCalificacion());
                    }
                });
                break;
            case 5:
                Collections.sort(mTour, new Comparator<Tour>() {
                    @Override
                    public int compare(Tour t1, Tour t2) {

                        return t1.getCalificacion().compareTo(t2.getCalificacion());
                    }
                });
                break;
        }
         notifyDataSetChanged();
     }
}
