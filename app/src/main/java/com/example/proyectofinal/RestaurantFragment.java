package com.example.proyectofinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    private RestauranteAdapter restauranteAdapter;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        button = view.findViewById(R.id.menosPopular);
        ordPopcal= view.findViewById(R.id.maspopular);
        nombreAs = view.findViewById(R.id.nombreOrdAs);
        nombreDes = view.findViewById(R.id.nombreOrdDes);
        depAS = view.findViewById(R.id.depaAs);
        depDes = view.findViewById(R.id.depaDes);
        depDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depOrdenDes(view);
            }
        });
        depAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depOrdenAs(view);
            }
        });
        nombreDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreOrdenDes(view);
            }
        });
        nombreAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreOrdenAs(view);
            }
        });
        ordPopcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masPopular(view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menosPopular(view);
            }

        });

        mRestauranteService = connection.getRetrofitInstance().create(RestauranteService.class);

        Call<List<Restaurante>> resCall = mRestauranteService.getAllRestaurantes();

        resCall.enqueue(new Callback<List<Restaurante>>() {

            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                RecyclerView rvRes = (RecyclerView) view.findViewById(R.id.ResList);
                restauranteAdapter = new RestauranteAdapter(new ArrayList<>());
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
    public void nombreOrdenDes(View view) {
        Collections.sort(mRestaurante, new Comparator<Restaurante>() {
            @Override
            public int compare(Restaurante restaurante, Restaurante t1) {
                return 0;
            }
        });


    }
    public void depOrdenDes(View view) {

        Call<List<Restaurante>> resCall = mRestauranteService.getDepDes();

        resCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                restauranteAdapter.reloadData(response.body());
                restauranteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }
    public void depOrdenAs(View view) {

        Call<List<Restaurante>> resCall = mRestauranteService.getDepAs();

        resCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                restauranteAdapter.reloadData(response.body());
                restauranteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }
    public void nombreOrdenAs(View view) {

        Call<List<Restaurante>> resCall = mRestauranteService.getNombreAs();

        resCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                restauranteAdapter.reloadData(response.body());
                restauranteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }
    public void menosPopular(View view) {

        Call<List<Restaurante>> resCall = mRestauranteService.getMenosPoupular();

        resCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                restauranteAdapter.reloadData(response.body());
                restauranteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }
    public void masPopular(View view) {

        Call<List<Restaurante>> resCall = mRestauranteService.getAllRestaurantes();

        resCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                restauranteAdapter.reloadData(response.body());
                restauranteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }


}