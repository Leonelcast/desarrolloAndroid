package com.example.proyectofinal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectofinal.adapter.TourAdapter;
import com.example.proyectofinal.interfaces.TourService;
import com.example.proyectofinal.interfaces.UserService;
import com.example.proyectofinal.models.Tour;
import com.example.proyectofinal.retrofit.connection;

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
    private List<Tour> mTour;
    private TourService mTourService;
    private Button button, ordPopcal, nombreAs, nombreDes, depAS, depDes;
    private TourAdapter tourAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tour, container, false);

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

        mTourService = connection.getRetrofitInstance().create(TourService.class);

       Call<List<Tour>> tourCall = mTourService.getAllTuristicos();

        tourCall.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                RecyclerView rvTour = (RecyclerView) view.findViewById(R.id.tourList);
                tourAdapter = new TourAdapter(new ArrayList<>());
                tourAdapter.reloadData(response.body());
                rvTour.setLayoutManager(new LinearLayoutManager(getContext()));
                rvTour.setAdapter(tourAdapter);

            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
        return  view;
    }
    public void nombreOrdenDes(View view) {

        Call<List<Tour>> tourCall = mTourService.getNombreDes();

        tourCall.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                tourAdapter.reloadData(response.body());
                tourAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }
    public void depOrdenDes(View view) {

        Call<List<Tour>> tourCall = mTourService.getDepDes();

        tourCall.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                tourAdapter.reloadData(response.body());
                tourAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }
    public void depOrdenAs(View view) {

        Call<List<Tour>> tourCall = mTourService.getDepAs();

        tourCall.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                tourAdapter.reloadData(response.body());
                tourAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }
    public void nombreOrdenAs(View view) {

        Call<List<Tour>> tourCall = mTourService.getNombreAs();

        tourCall.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                tourAdapter.reloadData(response.body());
                tourAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }
    public void menosPopular(View view) {

        Call<List<Tour>> tourCall = mTourService.getMenosPoupular();

        tourCall.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                tourAdapter.reloadData(response.body());
                tourAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }
    public void masPopular(View view) {

        Call<List<Tour>> tourCall = mTourService.getAllTuristicos();

        tourCall.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                tourAdapter.reloadData(response.body());
                tourAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                System.out.print("first statement. ");
            }
        });
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            }



}
