package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.proyectofinal.databinding.ActivityDescriptionResBinding;

public class DescriptionResActivity extends AppCompatActivity {

    ActivityDescriptionResBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescriptionResBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new RestDescFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.descRes:
                    replaceFragment(new RestDescFragment());
                    break;
                case R.id.resComentario:
                    replaceFragment(new RestComentFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameDesRes, fragment);
        fragmentTransaction.commit();

    }
}