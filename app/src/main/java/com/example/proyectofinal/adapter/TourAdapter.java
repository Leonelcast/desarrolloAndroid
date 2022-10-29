package com.example.proyectofinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.FavoritoTourResponse;
import com.example.proyectofinal.MainActivity;
import com.example.proyectofinal.R;
import com.example.proyectofinal.TourDescActivity;
import com.example.proyectofinal.interfaces.FavTourService;
import com.example.proyectofinal.models.FavTours;
import com.example.proyectofinal.models.Tour;
import com.example.proyectofinal.retrofit.connection;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        TextView decRes = holder.mDescripcion;
        decRes.setText(tour.descripcion);
       // Button buttonUb = holder.mButton;
        TextView longitudRes = holder.mLongitud;
        longitudRes.setText(tour.longitud);
        TextView latitudRes = holder.mLatitud;
        latitudRes.setText(tour.lat);
        if(tour.calificacion == null){
            tour.calificacion = "0";
        }
        tourCalificacionTextView.setText(tour.calificacion+"/5");
        TextView urlTextView = holder.mURL;
        urlTextView.setText(tour.img);
        ImageView tourImg = holder.mTourImage;
        TextView idRest = holder.mIdTour;
        idRest.setText(tour._id);
        Glide.with(this.context).load(tour.img).into(tourImg);
        CheckBox favTour = holder.mFavoritosTour;
        if(tour.favTours.size() == 0){
            favTour.setChecked(false);
        }else{
            favTour.setChecked(true);
        }
        TextView idFavTour = holder.mFavoritosTourID;
        if (tour.favTours.size() > 0){
            idFavTour.setText(tour.favTours.get(0).get_id());
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        favTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox)view).isChecked();
                if(checked){
                    FavTours favTours  = new FavTours();
                    favTours.setUsuario(_id);
                    favTours.setTuristico(tour._id);
                    FavTourService favTourService = connection.getRetrofitInstance().create(FavTourService.class);
                    Call<FavoritoTourResponse> favoritoTourResponseCall = favTourService.agregarFavorito(favTours);
                    favoritoTourResponseCall.enqueue(new Callback<FavoritoTourResponse>() {
                        @Override
                        public void onResponse(Call<FavoritoTourResponse> call, Response<FavoritoTourResponse> response) {
                            FavoritoTourResponse favoritoTourResponse = response.body();
                            if(checked == true && favoritoTourResponse.ok){
                                new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Agregaste un favorito!")
                                        .setContentText("Ya puedes observar tu favorito")
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<FavoritoTourResponse> call, Throwable t) {

                        }
                    });
                }else{
                    if(tour.favTours.size()>0){
                        FavTourService favTourService = connection.getRetrofitInstance().create(FavTourService.class);
                        Call<FavoritoTourResponse> favoritoTourResponseCall = favTourService.deleteFavRest(tour.favTours.get(0).get_id());
                        favoritoTourResponseCall.enqueue(new Callback<FavoritoTourResponse>() {
                            @Override
                            public void onResponse(Call<FavoritoTourResponse> call, Response<FavoritoTourResponse> response) {
                                FavoritoTourResponse favoritoTourResponse = response.body();
                                if(favoritoTourResponse.ok){
                                    new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Has borrado un favorito")
                                            .setContentText("Favorito eliminado")
                                            .show();

                                }
                            }

                            @Override
                            public void onFailure(Call<FavoritoTourResponse> call, Throwable t) {
                                Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });

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
         private TextView mIdTour;
         private CheckBox mFavoritosTour;
         private TextView mLongitud;
         private TextView mLatitud;
         private TextView mFavoritosTourID;

         private TextView mDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTourImage = (ImageView) itemView.findViewById(R.id.tour_img);
            mNombre = (TextView) itemView.findViewById(R.id.nombre_tour);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento);
            mCalificacion = (TextView) itemView.findViewById(R.id.calificacion);
            mURL = (TextView) itemView.findViewById(R.id.url);
            mIdTour = (TextView) itemView.findViewById(R.id.idTour);
            mFavoritosTour = (CheckBox) itemView.findViewById(R.id.FavTour);

            mDescripcion =(TextView) itemView.findViewById(R.id.DescIndividualTour);
            mFavoritosTourID = (TextView) itemView.findViewById(R.id.tourtxtid);

            mLatitud = (TextView) itemView.findViewById(R.id.LatTour);
            mLongitud =(TextView) itemView.findViewById(R.id.LongTour);
            itemView.setOnClickListener(this);
        }

         @Override
         public void onClick(View view) {
             Intent intent = new Intent(view.getContext(), TourDescActivity.class);
             intent.putExtra("nombre", mNombre.getText().toString());
             intent.putExtra("departamento", mDepartamento.getText().toString());
             intent.putExtra("califiacion", mCalificacion.getText().toString());
             intent.putExtra("img", mURL.getText().toString());
             intent.putExtra("_idTour", mIdTour.getText().toString());
             intent.putExtra("descTour",mDescripcion.getText().toString());
             intent.putExtra("lat", mLatitud.getText().toString());
             intent.putExtra("long",mLongitud.getText().toString());
             intent.putExtra("idFavTour", mFavoritosTourID.getText().toString());
             view.getContext().startActivity(intent);
         }
     }

     public void Ordenar(int position){
        if(position == 0){
            Collections.sort(mTour, new Comparator<Tour>() {
                @Override
                public int compare(Tour t1, Tour t2) {

                    return t1.getNombre().compareTo(t2.getNombre());
                }
            });
        }if(position == 1){
             Collections.sort(mTour, new Comparator<Tour>() {
                 @Override
                 public int compare(Tour t1, Tour t2) {

                     return t2.getNombre().compareTo(t1.getNombre());
                 }
             });
         }
         if(position == 2){
             Collections.sort(mTour, new Comparator<Tour>() {
                 @Override
                 public int compare(Tour t1, Tour t2) {

                     return t1.getDepartamento().compareTo(t2.getDepartamento());
                 }
             });
         }if(position == 3){
             Collections.sort(mTour, new Comparator<Tour>() {
                 @Override
                 public int compare(Tour t1, Tour t2) {

                     return t2.getDepartamento().compareTo(t1.getDepartamento());
                 }
             });
         }if(position == 4){
             Collections.sort(mTour, new Comparator<Tour>() {
                 @Override
                 public int compare(Tour t1, Tour t2) {

                     return t2.getCalificacion().compareTo(t1.getCalificacion());
                 }
             });
         }if(position == 5){
             Collections.sort(mTour, new Comparator<Tour>() {
                 @Override
                 public int compare(Tour t1, Tour t2) {

                     return t1.getCalificacion().compareTo(t2.getCalificacion());
                 }
             });
        }
         notifyDataSetChanged();
     }
}
