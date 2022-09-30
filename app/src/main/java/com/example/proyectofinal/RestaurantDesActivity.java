package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.databinding.ActivityRestaurantDesBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class RestaurantDesActivity extends AppCompatActivity {
    ActivityRestaurantDesBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_des);

        binding = ActivityRestaurantDesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.collapsing_toolbar);
        toolbarLayout.setTitle("qwertyu");
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        ImageView detailImage = findViewById(R.id.res_detail_img);
        Glide.with(this).load(bundle.getString("img")).into(detailImage);

    }
}