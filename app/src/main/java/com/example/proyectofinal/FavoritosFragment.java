package com.example.proyectofinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.RestaurantesFavGet;
import com.example.proyectofinal.adapter.FavsRestauranteAdapter;
import com.example.proyectofinal.interfaces.FavRestauranteService;
import com.example.proyectofinal.retrofit.connection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritosFragment extends Fragment {
    //
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritosFragment newInstance(String param1, String param2) {
        FavoritosFragment fragment = new FavoritosFragment();
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
    private List<RestaurantesFavGet> mRestaurante;
    private FavRestauranteService mRestauranteService;
    private FavsRestauranteAdapter restauranteAdapter = new FavsRestauranteAdapter(new ArrayList<>());
    Spinner favoritoRestauranteSpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
       mRestauranteService = connection.getRetrofitInstance().create(FavRestauranteService.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");

        //Spinner
        favoritoRestauranteSpinner = (Spinner) view.findViewById(R.id.idSpinnerResFav);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.option_restauranteFav, android.R.layout.simple_spinner_item);
        favoritoRestauranteSpinner.setAdapter(adapter);
        favoritoRestauranteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 1){
                    restauranteAdapter.Ordenar(0);
                }
                if(position == 2){
                    restauranteAdapter.Ordenar(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //llamada
        KAlertDialog pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<List<RestaurantesFavGet>> favResCall = mRestauranteService.getFavoritos(_id);

        favResCall.enqueue(new Callback<List<RestaurantesFavGet>>() {
            @Override
            public void onResponse(Call<List<RestaurantesFavGet>> call, Response<List<RestaurantesFavGet>> response) {

                RecyclerView rvRes = (RecyclerView) view.findViewById(R.id.ResFavList);
                restauranteAdapter.reloadData(response.body());
                rvRes.setLayoutManager(new LinearLayoutManager(getContext()));
                rvRes.setAdapter(restauranteAdapter);
                pDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<List<RestaurantesFavGet>> call, Throwable t) {
                System.out.print(t);
            }
        });



       return view;
    }
}