package com.example.proyectofinal.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.FavoritoRestauranteResponse;
import com.example.proyectofinal.DescriptionResActivity;
import com.example.proyectofinal.GoogleMapsResActivity;
import com.example.proyectofinal.MainActivity;
import com.example.proyectofinal.R;
import com.example.proyectofinal.RestDescFragment;
import com.example.proyectofinal.RestaurantDesActivity;
import com.example.proyectofinal.interfaces.FavRestauranteService;
import com.example.proyectofinal.models.FavRestaurantes;
import com.example.proyectofinal.models.Restaurante;
import com.example.proyectofinal.retrofit.connection;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteAdapter.ViewHolder> {
    private List<Restaurante> mRestaurante;
    private List<Restaurante> mRestauranteEnMemoria = new ArrayList<>();
    private Context context;
    Menu menu;

    public RestauranteAdapter(List<Restaurante> mRestaurante){this.mRestaurante = mRestaurante;}

    public void reloadData(List<Restaurante> restaurantes){
        this.mRestaurante = restaurantes;
        this.mRestauranteEnMemoria.addAll(restaurantes);
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
        TextView decRes = holder.mDescripcion;
        decRes.setText(restaurante.descripcion);
        Button buttonUb = holder.mButton;
        TextView longitudRes = holder.mLongitud;
        longitudRes.setText(restaurante.longitud);
        TextView latitudRes = holder.mLatitud;
        latitudRes.setText(restaurante.lat);
        TextView idFavRes = holder.mFavoritosRestaurantes;
        if(restaurante.favRestaurantes.size() >0){
            idFavRes.setText(restaurante.favRestaurantes.get(0).get_id());
        }

        if(restaurante.calificacion == null){
            restaurante.calificacion = "0";
        }
        tourCalificacionTextView.setText(restaurante.calificacion +"/5");
        TextView urlTextView = holder.mURL;
        urlTextView.setText(restaurante.img);
        TextView idRest = holder.mIdRest;
        idRest.setText(restaurante._id);
        ImageView tourImg = holder.mRestauranteImage;
        Glide.with(this.context).load(restaurante.img).into(tourImg);
        CheckBox favRes = holder.mFavoritosRes;
        if(restaurante.favRestaurantes.size() == 0){
            favRes.setChecked(false);
        }else{
            favRes.setChecked(true);
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");

        favRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox)view).isChecked();
                if(checked){
                    FavRestaurantes favRestaurantes = new FavRestaurantes();
                    favRestaurantes.setUsuario(_id);
                    favRestaurantes.setRestaurante(restaurante._id);
                    FavRestauranteService favRestauranteService = connection.getRetrofitInstance().create(FavRestauranteService.class);
                    Call<FavoritoRestauranteResponse> favoritoRestauranteResponseCall = favRestauranteService.agregarFavorito(favRestaurantes);
                    favoritoRestauranteResponseCall.enqueue(new Callback<FavoritoRestauranteResponse>() {
                        @Override
                        public void onResponse(Call<FavoritoRestauranteResponse> call, Response<FavoritoRestauranteResponse> response) {
                            FavoritoRestauranteResponse favoritoRestauranteResponse = response.body();
                            if(checked == true && favoritoRestauranteResponse.ok){
                                new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Agregaste un favorito!")
                                        .setContentText("Ya puedes observar tu favorito")
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<FavoritoRestauranteResponse> call, Throwable t) {

                        }
                    });
                }else{
                    if(restaurante.favRestaurantes.size() > 0){
                        FavRestauranteService favRestauranteService = connection.getRetrofitInstance().create(FavRestauranteService.class);
                        Call<FavoritoRestauranteResponse> favoritoRestauranteResponseCall = favRestauranteService.deleteFavRest(restaurante.favRestaurantes.get(0).get_id());
                        favoritoRestauranteResponseCall.enqueue(new Callback<FavoritoRestauranteResponse>() {
                            @Override
                            public void onResponse(Call<FavoritoRestauranteResponse> call, Response<FavoritoRestauranteResponse> response) {
                                FavoritoRestauranteResponse favoritoRestauranteResponse = response.body();
                                if (favoritoRestauranteResponse.ok){
                                    new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Has borrado un favorito")
                                            .setContentText("Favorito eliminado")
                                            .show();


                                }
                            }

                            @Override
                            public void onFailure(Call<FavoritoRestauranteResponse> call, Throwable t) {
                                Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }



            }
        });

    }
    public void OrdenarDistancia(Location location, int distanciaMaxima){
        List<Restaurante> restaurantesFiltro = new ArrayList<>();
        mRestaurante.clear();
        mRestaurante.addAll(mRestauranteEnMemoria);

        for (Restaurante restaurante: mRestaurante) {
            Location locationRestaurante = new Location(restaurante.nombre);
            locationRestaurante.setLatitude(Double.parseDouble(restaurante.getLat()));
            locationRestaurante.setLongitude(Double.parseDouble(restaurante.getLongitud()));
            Float distancia = location.distanceTo(locationRestaurante);
            restaurante.setDistancia(distancia);
            System.out.println(distancia+ restaurante.nombre);
            if(distancia <= distanciaMaxima){
                restaurantesFiltro.add(restaurante);
            }

        }
        Collections.sort(restaurantesFiltro, new Comparator<Restaurante>() {
            @Override
            public int compare(Restaurante distnacia1, Restaurante distancia2) {
                return distnacia1.getDistancia().compareTo(distancia2.getDistancia());
            }
        });

        mRestaurante.clear();
        mRestaurante.addAll(restaurantesFiltro);
        notifyDataSetChanged();
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
        private TextView mIdRest;
        private TextView mLongitud;
        private TextView mLatitud;

        private TextView mDescripcion;
        private CheckBox mFavoritosRes;
        private Button mButton;
        private TextView mFavoritosRestaurantes;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRestauranteImage = (ImageView) itemView.findViewById(R.id.res_img);
            mNombre = (TextView) itemView.findViewById(R.id.nombre_res);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento_res);
            mCalificacion = (TextView) itemView.findViewById(R.id.calificacion_res);
            mURL = (TextView) itemView.findViewById(R.id.url);
            mIdRest = (TextView) itemView.findViewById(R.id.idRestaurante);
            mDescripcion =(TextView) itemView.findViewById(R.id.DescIndividual);

            mLatitud = (TextView) itemView.findViewById(R.id.LatRes);
            mLongitud =(TextView) itemView.findViewById(R.id.LongRes);
            mFavoritosRestaurantes =(TextView) itemView.findViewById(R.id.restaurantetxtid);
            mFavoritosRes = (CheckBox) itemView.findViewById(R.id.FavRes);

            itemView.setOnClickListener(this);


        }
//adssad
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DescriptionResActivity.class);
            intent.putExtra("nombre", mNombre.getText().toString());
            intent.putExtra("departamento", mDepartamento.getText().toString());
            intent.putExtra("califiacion", mCalificacion.getText().toString());
            intent.putExtra("img", mURL.getText().toString());
            intent.putExtra("_idRest", mIdRest.getText().toString());
            intent.putExtra("descRes",mDescripcion.getText().toString());
            intent.putExtra("lat", mLatitud.getText().toString());
            intent.putExtra("long",mLongitud.getText().toString());
            intent.putExtra("idFav", mFavoritosRestaurantes.getText().toString());
            view.getContext().startActivity(intent);
        }
    }

    public void Ordenar(int position){
        mRestaurante.clear();
        mRestaurante.addAll(mRestauranteEnMemoria);
        if(position == 0){
            Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                @Override
                public int compare(Restaurante r1, Restaurante r2) {
                    return r1.getNombre().compareTo(r2.getNombre());
                }
            });
        } if(position == 1){
            Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                @Override
                public int compare(Restaurante r1, Restaurante r2) {
                    return r2.getNombre().compareTo(r1.getNombre());
                }
            });
        }if(position == 2){
            Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                @Override
                public int compare(Restaurante r1, Restaurante r2) {
                    return r1.getDepartamento().compareTo(r2.getDepartamento());
                }
            });
        }if(position == 3){
            Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                @Override
                public int compare(Restaurante r1, Restaurante r2) {
                    return r2.getDepartamento().compareTo(r1.getDepartamento());
                }
            });
        }if(position == 4){
            Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                @Override
                public int compare(Restaurante r1, Restaurante r2) {
                    return r2.getCalificacion().compareTo(r1.getCalificacion());
                }
            });
        }if(position == 5){
            Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                @Override
                public int compare(Restaurante r1, Restaurante r2) {
                    return r1.getCalificacion().compareTo(r2.getCalificacion());
                }
            });
        }if(position == 6){

            System.out.println("5km");

        }
        if(position == 7){
            Toast.makeText(context, "10km", Toast.LENGTH_LONG);
        }
        if(position == 8){
            Toast.makeText(context, "20km", Toast.LENGTH_LONG);
        }
        if(position == 9){
            Toast.makeText(context, "50km", Toast.LENGTH_LONG);
        }
        if(position == 10){
            Toast.makeText(context, "100km", Toast.LENGTH_LONG);
        }
        notifyDataSetChanged();
    }

}
