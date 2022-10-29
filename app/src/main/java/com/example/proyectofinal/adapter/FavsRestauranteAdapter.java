package com.example.proyectofinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.CommentTourActivity;
import com.example.proyectofinal.DTO.FavoritoRestauranteResponse;
import com.example.proyectofinal.DTO.RestaurantesFavGet;
import com.example.proyectofinal.FechaFavRestauranteActivity;
import com.example.proyectofinal.MainActivity;
import com.example.proyectofinal.R;
import com.example.proyectofinal.interfaces.FavRestauranteService;
import com.example.proyectofinal.models.FavRestaurantes;
import com.example.proyectofinal.models.Restaurante;
import com.example.proyectofinal.retrofit.connection;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavsRestauranteAdapter extends RecyclerView.Adapter<FavsRestauranteAdapter.ViewHolder> {
    private List<RestaurantesFavGet> mRestaurante;
    private Context context;

    public FavsRestauranteAdapter(List<RestaurantesFavGet> mRestaurante){this.mRestaurante = mRestaurante;}

    public void reloadData(List<RestaurantesFavGet> restaurantesFavs){
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
        RestaurantesFavGet favRestaurantes = mRestaurante.get(position);
        TextView RestauranteNameTxt = holder.mNombre;
        RestauranteNameTxt.setText(favRestaurantes.restaurante.nombre);
        TextView DepNameTxt = holder.mDep;
        String id = favRestaurantes._id;
        DepNameTxt.setText(favRestaurantes.restaurante.departamento);
        TextView tourCalificacionTextView = holder.mCalificacion;

       /* if(favRestaurantes.restaurante.calificacion == null){
            favRestaurantes.restaurante.calificacion = "0";
        }*/
        tourCalificacionTextView.setText(favRestaurantes.date);
        ImageView tourImg = holder.mRestauranteImage;
        Glide.with(this.context).load(favRestaurantes.restaurante.img).into(tourImg);
        CheckBox favRes = holder.mFavoritosRes;
        if(favRestaurantes.getFavoritos() == true){
            favRes.setChecked(true);
        }else{
            favRes.setChecked(false);
        }
        Button date = holder.mDate;
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FechaFavRestauranteActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        favRes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FavRestauranteService favRestauranteService = connection.getRetrofitInstance().create(FavRestauranteService.class);
                Call<FavoritoRestauranteResponse> favoritoRestauranteResponseCall = favRestauranteService.deleteFavRest(id);
                favoritoRestauranteResponseCall.enqueue(new Callback<FavoritoRestauranteResponse>() {
                    @Override
                    public void onResponse(Call<FavoritoRestauranteResponse> call, Response<FavoritoRestauranteResponse> response) {
                        FavoritoRestauranteResponse favoritoRestauranteResponse = response.body();
                        if (favoritoRestauranteResponse.ok){
                            new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Has borrado un favorito")
                                    .setContentText("Favorito eliminado")
                                    .show();

                            mRestaurante.remove(holder.getAbsoluteAdapterPosition());
                            notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                            notifyDataSetChanged();

                        }
                        Integer position = mRestaurante.indexOf(favRestaurantes);
                        mRestaurante.remove(position);
                        notifyItemRemoved(position);
                    }

                    @Override
                    public void onFailure(Call<FavoritoRestauranteResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRestaurante.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private ImageView mRestauranteImage;
        private TextView mNombre;
        private TextView mDep;
        private TextView mCalificacion;
        private TextView mId;
        private CheckBox mFavoritosRes;
        private Button mDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mRestauranteImage = (ImageView) itemView.findViewById(R.id.res_imgFav);
            mNombre = (TextView) itemView.findViewById(R.id.nombre_resFav);
            mDep = (TextView) itemView.findViewById(R.id.departamento_resFav);
            mCalificacion = (TextView) itemView.findViewById(R.id.calificacion_resFav);
            mFavoritosRes = (CheckBox) itemView.findViewById(R.id.FavResFav);
            mDate = (Button) itemView.findViewById(R.id.DateRes);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
    public void Ordenar(int position){
        if(position == 0){
            Collections.sort(mRestaurante, new Comparator<RestaurantesFavGet>() {
                @Override
                public int compare(RestaurantesFavGet r1, RestaurantesFavGet r2) {
                    return r2.getDate().compareTo(r1.getDate());
                }
            });
        } if(position == 1){
            Collections.sort(mRestaurante, new Comparator<RestaurantesFavGet>() {
                @Override
                public int compare(RestaurantesFavGet r1, RestaurantesFavGet r2) {
                    return r1.getDate().compareTo(r2.getDate());
                }
            });
        }
        notifyDataSetChanged();
    }
}
