package com.example.proyectofinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofinal.adapter.RestauranteAdapter;
import com.example.proyectofinal.interfaces.RestauranteService;
import com.example.proyectofinal.models.Restaurante;
import com.example.proyectofinal.retrofit.connection;

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
    Menu menu;

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
    private List<Restaurante> mRestaurante;
    private RestauranteService mRestauranteService;
    private Button button, ordPopcal, nombreAs, nombreDes, depAS, depDes;
    private RestauranteAdapter restauranteAdapter = new RestauranteAdapter(new ArrayList<>());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        TextView t1;

        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        mRestauranteService = connection.getRetrofitInstance().create(RestauranteService.class);

        Call<List<Restaurante>> resCall = mRestauranteService.getAllRestaurantes();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        t1 = view.findViewById(R.id.textoId);
        t1.setText(_id);
        resCall.enqueue(new Callback<List<Restaurante>>() {

            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                RecyclerView rvRes = (RecyclerView) view.findViewById(R.id.ResList);

                restauranteAdapter.reloadData(response.body());
                rvRes.setLayoutManager(new LinearLayoutManager(getContext()));
                rvRes.setAdapter(restauranteAdapter);
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
        return  view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filtro, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.asc_nom){
            restauranteAdapter.Ordenar(0);
        } if(id == R.id.des_Nombre){
            restauranteAdapter.Ordenar(1);
        }
         if(id == R.id.asc_dept){
        restauranteAdapter.Ordenar(2);
        }
        if(id == R.id.des_dept){
            restauranteAdapter.Ordenar(3);
        }
        if(id == R.id.mas_pop){
            restauranteAdapter.Ordenar(4);
        }
        if(id == R.id.menos_pop){
            restauranteAdapter.Ordenar(5);
        }
        return super.onOptionsItemSelected(item);
    }


}