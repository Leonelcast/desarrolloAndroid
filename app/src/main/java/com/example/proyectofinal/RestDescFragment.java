package com.example.proyectofinal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.FavoritoRestauranteResponse;
import com.example.proyectofinal.databinding.FragmentRestDescBinding;
import com.example.proyectofinal.interfaces.FavRestauranteService;
import com.example.proyectofinal.models.FavRestaurantes;
import com.example.proyectofinal.retrofit.connection;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestDescFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestDescFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestDescFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestDescFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestDescFragment newInstance(String param1, String param2) {
        RestDescFragment fragment = new RestDescFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
    FragmentRestDescBinding binding;
    private TextView txtNombre, txtDepartamento, txtDescripcion, txtcalificacion, atras;
    private Button buttonMaps, Waze;
    private CheckBox checkBox;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

          View view = inflater.inflate(R.layout.fragment_rest_desc, container, false);
          binding = FragmentRestDescBinding.inflate(getLayoutInflater());
          binding.getRoot();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");

        Bundle bundle = getActivity().getIntent().getExtras();
          txtNombre = view.findViewById(R.id.NombreResDesc);
          txtNombre.setText("Nombre del Restaurante:"+" "+bundle.getString("nombre"));
          txtDepartamento = view.findViewById(R.id.DescResDep);
          txtDepartamento.setText("Departamento:"+ " "+bundle.getString("departamento"));
        txtcalificacion = view.findViewById(R.id.DescCalificacion);
        txtcalificacion.setText( "Calificacion:"+" "+bundle.getString("califiacion"));
        txtDescripcion = view.findViewById(R.id.DescRestaurante);
        txtDescripcion.setText("Descripcion:"+" "+ bundle.getString("descRes"));
          CollapsingToolbarLayout toolbarLayout = view.findViewById(R.id.collapsing_toolbar);
          toolbarLayout.setTitle(bundle.getString("nombre"));
          toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
          toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
          ImageView detailImage = view.findViewById(R.id.res_detail_img);
          Glide.with(this).load(bundle.getString("img")).into(detailImage);
          atras = view.findViewById(R.id.atrasDescRes);
          atras.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(getContext(), MainActivity.class);
                  startActivity(intent);
              }
          });
          buttonMaps = view.findViewById(R.id.MapsRest);
          checkBox = view.findViewById(R.id.FavResDesc);
          if(bundle.get("idFav").toString().isEmpty()){
              checkBox.setChecked(false);
          }else{
              checkBox.setChecked(true);
          }
          checkBox.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  boolean checked = ((CheckBox)view).isChecked();
                  if(checked){
                      FavRestaurantes favRestaurantes = new FavRestaurantes();
                      favRestaurantes.setUsuario(_id);
                      favRestaurantes.setRestaurante(bundle.getString("_idRest"));
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
                          FavRestauranteService favRestauranteService = connection.getRetrofitInstance().create(FavRestauranteService.class);
                          Call<FavoritoRestauranteResponse> favoritoRestauranteResponseCall = favRestauranteService.deleteFavRest(bundle.getString("idFav"));
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

          });

          Waze = view.findViewById(R.id.WazeRest);
          buttonMaps.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Uri gmmIntentUri = Uri.parse("geo:"+bundle.getString("lat")+","+bundle.getString("long")+"?q="+bundle.getString("nombre"));
                  Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                  mapIntent.setPackage("com.google.android.apps.maps");
                  startActivity(mapIntent);
              }
          });
        Waze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Launch Waze

                   // String mapRequest ="https://waze.com/ul?q="+bundle.getString("nombre")+"="+bundle.getString("lat")+","+bundle.getString("long")+"&navigate=yes";
                    String mapRequest ="https://waze.com/ul?q="+bundle.getString("lat")+","+bundle.getString("long")+"&navigate=yes";
                    Uri gmmIntentUri = Uri.parse(mapRequest);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.waze");
                    startActivity(mapIntent);
                } catch (ActivityNotFoundException e)
                {
                    // If Waze is not installed, open it in Google Play
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}