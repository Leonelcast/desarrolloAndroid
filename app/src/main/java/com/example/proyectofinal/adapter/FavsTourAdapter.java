package com.example.proyectofinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.FavoritoTourResponse;
import com.example.proyectofinal.DTO.TourFavGet;
import com.example.proyectofinal.FechaFavRestauranteActivity;
import com.example.proyectofinal.FechaFavTourActivity;
import com.example.proyectofinal.R;
import com.example.proyectofinal.interfaces.FavTourService;
import com.example.proyectofinal.retrofit.connection;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavsTourAdapter extends RecyclerView.Adapter<FavsTourAdapter.ViewHolder> {
    private List<TourFavGet> mTours;
    private Context context;

    public FavsTourAdapter(List<TourFavGet> mTours){this.mTours = mTours;}

    public void reloadData(List<TourFavGet> tourFavGets){
        this.mTours = tourFavGets;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FavsTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.favstour, parent, false);
        FavsTourAdapter.ViewHolder viewHolder = new FavsTourAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavsTourAdapter.ViewHolder holder, int position) {
        TourFavGet tourFavGet = mTours.get(position);
        TextView RestauranteNameTxt = holder.mNombre;
        RestauranteNameTxt.setText(tourFavGet.tour.nombre);
        TextView DepNameTxt = holder.mDep;
        String id = tourFavGet._id;
        DepNameTxt.setText(tourFavGet.tour.departamento);
        TextView tourCalificacionTextView = holder.mCalificacion;

       /* if(favRestaurantes.restaurante.calificacion == null){
            favRestaurantes.restaurante.calificacion = "0";
        }*/
        tourCalificacionTextView.setText(tourFavGet.date);
        ImageView tourImg = holder.mTourImage;
        Glide.with(this.context).load(tourFavGet.tour.img).into(tourImg);
        CheckBox favTour = holder.mFavoritosRes;
        if(tourFavGet.getFavoritos() == true){
            favTour.setChecked(true);
        }else{
            favTour.setChecked(false);
        }
        Button date = holder.mDate;
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FechaFavTourActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        favTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FavTourService favTourService = connection.getRetrofitInstance().create(FavTourService.class);
                Call<FavoritoTourResponse> favoritoTourResponseCall = favTourService.deleteFavRest(id);
                favoritoTourResponseCall.enqueue(new Callback<FavoritoTourResponse>() {
                    @Override
                    public void onResponse(Call<FavoritoTourResponse> call, Response<FavoritoTourResponse> response) {
                        FavoritoTourResponse favoritoTourResponse = response.body();
                        if(favoritoTourResponse.ok){
                            new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Has borrado un favorito")
                                    .setContentText("Favorito eliminado")
                                    .show();
                            mTours.remove(holder.getAbsoluteAdapterPosition());
                            notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<FavoritoTourResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mTourImage;
        private TextView mNombre;
        private TextView mDep;
        private TextView mCalificacion;
        private TextView mId;
        private CheckBox mFavoritosRes;
        private Button mDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTourImage = (ImageView) itemView.findViewById(R.id.tour_imgFav);
            mNombre = (TextView) itemView.findViewById(R.id.nombre_tourFav);
            mDep = (TextView) itemView.findViewById(R.id.departamento_tourFav);
            mCalificacion = (TextView) itemView.findViewById(R.id.calificacion_tourFav);
            mFavoritosRes = (CheckBox) itemView.findViewById(R.id.FavTourFav);
            mDate = (Button) itemView.findViewById(R.id.DateTour);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
    public void Ordenar(int position){
        if (position == 0){
            Collections.sort(mTours, new Comparator<TourFavGet>() {
                @Override
                public int compare(TourFavGet r1, TourFavGet r2) {
                    return r2.getDate().compareTo(r1.getDate());
                }
            });
        }if(position == 1){
            Collections.sort(mTours, new Comparator<TourFavGet>() {
                @Override
                public int compare(TourFavGet r1, TourFavGet r2) {
                    return r1.getDate().compareTo(r2.getDate());
                }
            });
        } if(position == 2){
            Collections.sort(mTours, new Comparator<TourFavGet>() {
                @Override
                public int compare(TourFavGet r1, TourFavGet r2) {
                    return r1.tour.getNombre().compareTo(r2.tour.getNombre());
                }
            });
        }if(position == 3){
            Collections.sort(mTours, new Comparator<TourFavGet>() {
                @Override
                public int compare(TourFavGet r1, TourFavGet r2) {
                    return r2.tour.getNombre().compareTo(r1.tour.getNombre());
                }
            });
        }
        if(position == 4){
            Collections.sort(mTours, new Comparator<TourFavGet>() {
                @Override
                public int compare(TourFavGet r1, TourFavGet r2) {
                    return r1.tour.getDepartamento().compareTo(r2.tour.getDepartamento());
                }
            });
        }if(position == 5){
            Collections.sort(mTours, new Comparator<TourFavGet>() {
                @Override
                public int compare(TourFavGet r1, TourFavGet r2) {
                    return r2.tour.getDepartamento().compareTo(r1.tour.getDepartamento());
                }
            });
        }
        if(position == 6){
            Collections.sort(mTours, new Comparator<TourFavGet>() {
                @Override
                public int compare(TourFavGet r1, TourFavGet r2) {
                    return r1.tour.getCalificacion().compareTo(r2.tour.getCalificacion());
                }
            });
        }if(position == 7){
            Collections.sort(mTours, new Comparator<TourFavGet>() {
                @Override
                public int compare(TourFavGet r1, TourFavGet r2) {
                    return r2.tour.getCalificacion().compareTo(r1.tour.getCalificacion());
                }
            });
        }
        notifyDataSetChanged();
    }
}
