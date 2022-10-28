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
import com.example.proyectofinal.DTO.FavoritoTourResponse;
import com.example.proyectofinal.databinding.FragmentDescTourBinding;
import com.example.proyectofinal.interfaces.FavTourService;
import com.example.proyectofinal.models.FavTours;
import com.example.proyectofinal.retrofit.connection;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescTourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescTourFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DescTourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DescTourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DescTourFragment newInstance(String param1, String param2) {
        DescTourFragment fragment = new DescTourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    FragmentDescTourBinding binding;
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
    private TextView txtNombre, txtDepartamento, txtDescripcion, txtcalificacion, atras;
    private Button buttonMaps, Waze;
    private CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_desc_tour, container, false);
        binding = FragmentDescTourBinding.inflate(getLayoutInflater());
        binding.getRoot();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        Bundle bundle = getActivity().getIntent().getExtras();
        txtNombre = view.findViewById(R.id.NombreTourDesc);
        txtNombre.setText("Nombre del lugar turistico:"+" "+bundle.getString("nombre"));
        txtDepartamento = view.findViewById(R.id.DescTourDep);
        txtDepartamento.setText("Departamento:"+" "+bundle.getString("departamento"));
        txtcalificacion = view.findViewById(R.id.DescTourCalificacion);
        txtcalificacion.setText("Calificacion:"+" "+bundle.getString("califiacion"));
        txtDescripcion = view.findViewById(R.id.DescTour);
        txtDescripcion.setText("Descripcion:"+" "+bundle.getString("descTour"));
        CollapsingToolbarLayout toolbarLayout = view.findViewById(R.id.collapsing_toolbarTour);
        toolbarLayout.setTitle(bundle.getString("nombre"));
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        ImageView detailImage = view.findViewById(R.id.tour_detail_img);
        Glide.with(this).load(bundle.getString("img")).into(detailImage);
        buttonMaps = view.findViewById(R.id.MapsTour);
        Waze = view.findViewById(R.id.WazeTour);
        checkBox = view.findViewById(R.id.FavTourDesc);
        atras = view.findViewById(R.id.atrasDescTour);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        if(bundle.get("idFavTour").toString().isEmpty()){
            checkBox.setChecked(false);
        }else{
            checkBox.setChecked(true);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox)view).isChecked();
                if(checked){
                    FavTours favTours  = new FavTours();
                    favTours.setUsuario(_id);
                    favTours.setTuristico(bundle.getString("_idTour"));
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
                        FavTourService favTourService = connection.getRetrofitInstance().create(FavTourService.class);
                        Call<FavoritoTourResponse> favoritoTourResponseCall = favTourService.deleteFavRest(bundle.getString("idFavTour"));
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
        });
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