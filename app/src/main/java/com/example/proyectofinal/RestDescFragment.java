package com.example.proyectofinal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.databinding.FragmentRestDescBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestDescFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestDescFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestDescFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestDescFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestDescFragment newInstance(String param1, String param2) {
        RestDescFragment fragment = new RestDescFragment();
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
    FragmentRestDescBinding binding;
    private TextView txtNombre, txtDepartamento, txtDescripcion, txtcalificacion;
    private Button buttonMaps, Waze;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

          View view = inflater.inflate(R.layout.fragment_rest_desc, container, false);
          binding = FragmentRestDescBinding.inflate(getLayoutInflater());
          binding.getRoot();
          Bundle bundle = getActivity().getIntent().getExtras();
          txtNombre = view.findViewById(R.id.NombreResDesc);
          txtNombre.setText(bundle.getString("nombre"));
          txtDepartamento = view.findViewById(R.id.DescResDep);
          txtDepartamento.setText(bundle.getString("departamento"));
        txtcalificacion = view.findViewById(R.id.DescCalificacion);
        txtcalificacion.setText(bundle.getString("califiacion"));
        txtDescripcion = view.findViewById(R.id.DescRestaurante);
        txtDescripcion.setText(bundle.getString("descRes"));
          CollapsingToolbarLayout toolbarLayout = view.findViewById(R.id.collapsing_toolbar);
          toolbarLayout.setTitle(bundle.getString("nombre"));
          toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
          toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
          ImageView detailImage = view.findViewById(R.id.res_detail_img);
          Glide.with(this).load(bundle.getString("img")).into(detailImage);
          buttonMaps = view.findViewById(R.id.MapsRest);
          Waze = view.findViewById(R.id.WazeRest);
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