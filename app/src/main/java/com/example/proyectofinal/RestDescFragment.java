package com.example.proyectofinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

          View view = inflater.inflate(R.layout.fragment_rest_desc, container, false);
          binding = FragmentRestDescBinding.inflate(getLayoutInflater());
          binding.getRoot();
          Bundle bundle = getActivity().getIntent().getExtras();
          CollapsingToolbarLayout toolbarLayout = view.findViewById(R.id.collapsing_toolbar);
          toolbarLayout.setTitle("qwertyu");
          toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
          toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
          ImageView detailImage = view.findViewById(R.id.res_detail_img);
          Glide.with(this).load(bundle.getString("img")).into(detailImage);

        return view;
    }
}