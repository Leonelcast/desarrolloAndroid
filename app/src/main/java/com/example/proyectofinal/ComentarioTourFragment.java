package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofinal.DTO.ComentarioTourGet;
import com.example.proyectofinal.adapter.ComentarioTourAdapter;
import com.example.proyectofinal.databinding.FragmentComentarioTourBinding;
import com.example.proyectofinal.interfaces.ComentarioTourService;
import com.example.proyectofinal.retrofit.connection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComentarioTourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComentarioTourFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComentarioTourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComentarioTourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComentarioTourFragment newInstance(String param1, String param2) {
        ComentarioTourFragment fragment = new ComentarioTourFragment();
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
    private Button button;
    private FragmentComentarioTourBinding binding;
    private TextView urlText;
    private ComentarioTourService mComentarioTourService;
    private List<ComentarioTourGet> mComentarioTourGet;
    private ComentarioTourAdapter comentarioTourAdapter = new ComentarioTourAdapter(new ArrayList<>());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comentario_tour, container, false);
        binding = FragmentComentarioTourBinding.inflate(getLayoutInflater());
        binding.getRoot();
        Bundle bundle = getActivity().getIntent().getExtras();
        urlText = view.findViewById(R.id.pruebaTour);
        urlText.setText(bundle.getString("_idTour"));
        button = view.findViewById(R.id.comentarioTour);

        mComentarioTourService = connection.getRetrofitInstance().create(ComentarioTourService.class);
        Call<List<ComentarioTourGet>> commentTourGet = mComentarioTourService.getComentarioTour(bundle.getString("_idTour"));
        commentTourGet.enqueue(new Callback<List<ComentarioTourGet>>() {
            @Override
            public void onResponse(Call<List<ComentarioTourGet>> call, Response<List<ComentarioTourGet>> response) {
                RecyclerView rvTourComment = (RecyclerView) view.findViewById(R.id.CommentTourList);
                comentarioTourAdapter = new ComentarioTourAdapter((new ArrayList<>()));
                comentarioTourAdapter.reloadData(response.body());
                rvTourComment.setLayoutManager(new LinearLayoutManager(getContext()));
                rvTourComment.setAdapter(comentarioTourAdapter);
            }

            @Override
            public void onFailure(Call<List<ComentarioTourGet>> call, Throwable t) {
                new Exception(t.getMessage());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CommentTourActivity.class);
                intent.putExtra("_idTour", urlText.getText().toString());
                startActivity(intent);
            }
        });


        return view;
    }
}