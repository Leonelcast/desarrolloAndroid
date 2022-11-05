package com.example.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.ComentarioResGetResponse;
import com.example.proyectofinal.adapter.ComentariosRestAdapter;
import com.example.proyectofinal.databinding.FragmentRestComentBinding;
import com.example.proyectofinal.databinding.FragmentRestDescBinding;
import com.example.proyectofinal.interfaces.ComentarioResService;
import com.example.proyectofinal.models.ComentarioRest;
import com.example.proyectofinal.retrofit.connection;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestComentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestComentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestComentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestComentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestComentFragment newInstance(String param1, String param2) {
        RestComentFragment fragment = new RestComentFragment();
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
    private Button button, buttonDel;
    private FragmentRestComentBinding binding;
    private TextView urlText;
    private ComentarioResService mcomentarioResService;
    private String nombre, departamento, calificacion, url, descripcion, lat, longitud, idfavRes;
    //private ComentariosRestAdapter comentariosRestAdapter;
    private List<ComentarioResGetResponse> mComentarioResGetResponses;

    private ComentariosRestAdapter comentariosRestAdapter = new ComentariosRestAdapter(new ArrayList<>());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rest_coment, container, false);
        binding = FragmentRestComentBinding.inflate(getLayoutInflater());
        binding.getRoot();
        Bundle bundle = getActivity().getIntent().getExtras();
        urlText = view.findViewById(R.id.prueba);
        urlText.setText(bundle.getString("_idRest"));
        nombre = bundle.getString("nombre");
        departamento = bundle.getString("departamento");
        calificacion = bundle.getString("califiacion");
        url = bundle.getString("img");
        descripcion = bundle.getString("descRes");
        lat = bundle.getString("lat");
        longitud = bundle.getString("long");
        idfavRes = bundle.getString("idFav");
        button = view.findViewById(R.id.comentarioRestaurante);

        //Toast.makeText(getContext(), "hola", Toast.LENGTH_SHORT).show();
        mcomentarioResService = connection.getRetrofitInstance().create(ComentarioResService.class);

        Call<List<ComentarioResGetResponse>> comentResGetResponse = mcomentarioResService.getComentariosRest(bundle.getString("_idRest"));
        KAlertDialog pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        comentResGetResponse.enqueue(new Callback<List<ComentarioResGetResponse>>() {
            @Override
            public void onResponse(Call<List<ComentarioResGetResponse>> call, Response<List<ComentarioResGetResponse>> response) {


                RecyclerView rvRestComment = (RecyclerView)  view.findViewById(R.id.CommentResList);
                comentariosRestAdapter = new ComentariosRestAdapter(new ArrayList<>());
                comentariosRestAdapter.reloadData(response.body());
                rvRestComment.setLayoutManager(new LinearLayoutManager(getContext()));
                rvRestComment.setAdapter(comentariosRestAdapter);
                pDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<List<ComentarioResGetResponse>> call, Throwable t) {
                new Exception(t.getMessage());
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CommentResActivity.class);
                intent.putExtra("_idRest", urlText.getText().toString());
                intent.putExtra("nombre", nombre);
                intent.putExtra("departamento", departamento);
                intent.putExtra("califiacion", calificacion);
                intent.putExtra("img",url);
                intent.putExtra("descRes",descripcion);
                intent.putExtra("lat",lat);
                intent.putExtra("long",longitud);
                intent.putExtra("idFav", idfavRes);
                startActivity(intent);
            }
        });
        return view;
    }
}