package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.proyectofinal.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.proyectofinal.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        setContentView(binding.getRoot());
        replaceFragment(new RestaurantFragment());
        BottomNavigationView b = binding.bottomNavbarFavs;
        b.setVisibility(View.GONE);
        binding.bottomNavbar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                /*case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;*/
                case R.id.restaurant:
                    replaceFragment(new RestaurantFragment());
                    b.setVisibility(View.GONE);
                    break;
                case R.id.tour:
                    replaceFragment(new TourFragment());
                    b.setVisibility(View.GONE);
                    break;
                case R.id.fav:
                    replaceFragment(new FavoritosFragment());
                    b.setVisibility(View.VISIBLE);
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    b.setVisibility(View.GONE);
                    break;


            }
            return true;
        });

        binding.bottomNavbarFavs.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.favoritosRes:
                    replaceFragment2(new FavoritosFragment());
                    break;
                case R.id.favoritosTour:
                    replaceFragment2(new FavTuristivosFragment());
                    break;
            }
            return true;
        });




    }

    private void replaceFragment2(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}