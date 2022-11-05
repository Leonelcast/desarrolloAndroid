package com.example.proyectofinal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.adapter.RestauranteAdapter;
import com.example.proyectofinal.interfaces.RestauranteService;
import com.example.proyectofinal.models.Restaurante;
import com.example.proyectofinal.retrofit.connection;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
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
    private List<Restaurante> mRestaurante;
    private RestauranteService mRestauranteService;
    private Button button, ordPopcal, nombreAs, nombreDes, depAS, depDes;
    private RestauranteAdapter restauranteAdapter = new RestauranteAdapter(new ArrayList<>());
    private TextView latitudUser, longitudUser;
    Location location;
    FusedLocationProviderClient client;
    Spinner restauranteSpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        TextView t1;

        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        KAlertDialog pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        latitudUser = view.findViewById(R.id.latitudUser);
        longitudUser = view.findViewById(R.id.longitudUser);
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        t1 = view.findViewById(R.id.textoId);
        t1.setText(_id);
        restauranteSpinner = (Spinner) view.findViewById(R.id.idSpinnerRes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.option_restaurante, android.R.layout.simple_spinner_item);
        restauranteSpinner.setAdapter(adapter);
        //Conexion y recycler view
        mRestauranteService = connection.getRetrofitInstance().create(RestauranteService.class);
        Call<List<Restaurante>> resCall = mRestauranteService.getAllRestaurantes(_id);
        resCall.enqueue(new Callback<List<Restaurante>>() {

            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {

                RecyclerView rvRes = (RecyclerView) view.findViewById(R.id.ResList);

                restauranteAdapter.reloadData(response.body());
                rvRes.setLayoutManager(new LinearLayoutManager(getContext()));
                rvRes.setAdapter(restauranteAdapter);
                pDialog.dismissWithAnimation();
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }else{
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                }


            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
        restauranteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(adapterView.getContext(), adapterView.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                if(position == 1){
                    restauranteAdapter.Ordenar(0);
                }
                if(position == 2){
                    restauranteAdapter.Ordenar(1);
                }
                if(position == 3){
                    restauranteAdapter.Ordenar(2);
                }
                if(position == 4){
                    restauranteAdapter.Ordenar(3);
                }
                if(position == 5){
                    restauranteAdapter.Ordenar(4);
                }
                if(position == 6){
                    restauranteAdapter.Ordenar(5);
                }
                if(position == 7){
                    restauranteAdapter.OrdenarDistancia(location,5000);
                }
                if(position == 8){
                    restauranteAdapter.OrdenarDistancia(location,10000);
                }
                if(position == 9){
                    restauranteAdapter.OrdenarDistancia(location,20000);
                }
                if(position == 10){
                    restauranteAdapter.OrdenarDistancia(location,50000);
                }
                if(position == 11){
                    restauranteAdapter.OrdenarDistancia(location,100000);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return  view;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        }else {
            Toast.makeText(getActivity(), "Permision denied", Toast.LENGTH_LONG);
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    location = task.getResult();
                    if(location != null){
                        latitudUser.setText(String.valueOf(location.getLatitude()));
                        longitudUser.setText(String.valueOf(location.getLongitude()));

                    }else {
                       LocationRequest locationRequest = new LocationRequest()
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();
                                latitudUser.setText(String.valueOf(location1.getLatitude()));
                                longitudUser.setText(String.valueOf(location1.getLongitude()));
                            }
                        };
                        client.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                    }
                }
            });
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

}