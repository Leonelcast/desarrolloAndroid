package com.example.proyectofinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.proyectofinal.DTO.TourFavGet;
import com.example.proyectofinal.adapter.FavsTourAdapter;
import com.example.proyectofinal.interfaces.FavTourService;
import com.example.proyectofinal.retrofit.connection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavTuristivosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavTuristivosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavTuristivosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavTuristivosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavTuristivosFragment newInstance(String param1, String param2) {
        FavTuristivosFragment fragment = new FavTuristivosFragment();
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


    private CheckBox checkBox;
    private List<TourFavGet> mTour;
    private FavTourService mTourService;
    private FavsTourAdapter tourAdapter = new FavsTourAdapter(new ArrayList<>());
    Spinner favoritoTourSpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_turistivos, container, false);
        mTourService = connection.getRetrofitInstance().create(FavTourService.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        //Spinner
        favoritoTourSpinner = (Spinner) view.findViewById(R.id.idSpinnerTourFav);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.option_tourFav, android.R.layout.simple_spinner_item);
        favoritoTourSpinner.setAdapter(adapter);
        favoritoTourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 1){
                    tourAdapter.Ordenar(0);
                }
                if(position == 2){
                    tourAdapter.Ordenar(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //llamada
        Call<List<TourFavGet>> favTourCall = mTourService.getFavoritos(_id);
        favTourCall.enqueue(new Callback<List<TourFavGet>>() {
            @Override
            public void onResponse(Call<List<TourFavGet>> call, Response<List<TourFavGet>> response) {
                RecyclerView rvTour = (RecyclerView)  view.findViewById(R.id.TourFavList);
                tourAdapter.reloadData(response.body());
                rvTour.setLayoutManager(new LinearLayoutManager(getContext()));
                rvTour.setAdapter(tourAdapter);
            }

            @Override
            public void onFailure(Call<List<TourFavGet>> call, Throwable t) {
                new Exception(t.getMessage());
            }
        });
        return view;
    }
}