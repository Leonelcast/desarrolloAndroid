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
import androidx.annotation.Nullable;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.adapter.TourAdapter;
import com.example.proyectofinal.interfaces.TourService;
import com.example.proyectofinal.interfaces.UserService;
import com.example.proyectofinal.models.Tour;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TourFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Menu menu;

    public TourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TourFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static TourFragment newInstance(String param1, String param2) {
        TourFragment fragment = new TourFragment();
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
    private List<Tour> mTour;
    private TourService mTourService;
    private Button button, ordPopcal, nombreAs, nombreDes, depAS, depDes;
    private TourAdapter tourAdapter = new TourAdapter(new ArrayList<>());
    Spinner tourSpinner, locationSpinner;
    private TextView latitudUser, longitudUser;
    Location location;
    FusedLocationProviderClient client;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_tour, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");

        mTourService = connection.getRetrofitInstance().create(TourService.class);
        latitudUser = view.findViewById(R.id.latitudUserTour);
        longitudUser = view.findViewById(R.id.longitudUserTour);
        client = LocationServices.getFusedLocationProviderClient(getActivity());
       Call<List<Tour>> tourCall = mTourService.getAllTuristicos(_id);
        tourSpinner = (Spinner) view.findViewById(R.id.idSpinnerTour);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.option_tour, android.R.layout.simple_spinner_item);
        tourSpinner.setAdapter(adapter);
        locationSpinner = (Spinner) view.findViewById(R.id.idSpinnerTourLocation);
        ArrayAdapter<CharSequence> adapterLocation = ArrayAdapter.createFromResource(this.getActivity(), R.array.option_km, android.R.layout.simple_spinner_item);
        locationSpinner.setAdapter(adapterLocation);
        tourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    tourAdapter.Ordenar(0);
                }
                if(i == 2){
                    tourAdapter.Ordenar(1);
                }
                if(i == 3){
                    tourAdapter.Ordenar(2);
                }
                if(i == 4){
                    tourAdapter.Ordenar(3);
                }
                if(i == 5){
                    tourAdapter.Ordenar(4);
                }
                if(i == 6){
                    tourAdapter.Ordenar(5);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 1){
                    tourAdapter.OrdenarDistancia(location,5000);
                }
                if(position == 2){
                    tourAdapter.OrdenarDistancia(location,10000);
                }
                if(position == 3){
                    tourAdapter.OrdenarDistancia(location,20000);
                }
                if(position == 4){
                    tourAdapter.OrdenarDistancia(location,50000);
                }
                if(position == 5){
                    tourAdapter.OrdenarDistancia(location,100000);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        KAlertDialog pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        tourCall.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {


                RecyclerView rvTour = (RecyclerView) view.findViewById(R.id.tourList);
                tourAdapter.reloadData(response.body());
                rvTour.setLayoutManager(new LinearLayoutManager(getContext()));
                rvTour.setAdapter(tourAdapter);

                pDialog.dismissWithAnimation();
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }else{
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                }




            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                System.out.print("first statement. ");
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
