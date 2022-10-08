package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.proyectofinal.databinding.ActivityTourDescBinding;

public class TourDescActivity extends AppCompatActivity {
    ActivityTourDescBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTourDescBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new DescTourFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.descTour:
                    replaceFragment(new DescTourFragment());
                    break;
                case R.id.TourComentario:
                    replaceFragment(new ComentarioTourFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameTourRes, fragment);
        fragmentTransaction.commit();
    }
}