package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.FavoritoTourResponse;
import com.example.proyectofinal.databinding.ActivityFechaFavRestauranteBinding;
import com.example.proyectofinal.databinding.ActivityFechaFavTourBinding;
import com.example.proyectofinal.interfaces.FavTourService;
import com.example.proyectofinal.models.FavTours;
import com.example.proyectofinal.retrofit.connection;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FechaFavTourActivity extends AppCompatActivity {
    ActivityFechaFavTourBinding binding;
    private EditText idFav, SelectDate;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_fav_tour);
        binding = ActivityFechaFavTourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        idFav = findViewById(R.id.FAVTourId);
        idFav.setText(bundle.getString("id"));

        SelectDate = findViewById(R.id.fechaTour);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(FechaFavTourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        SelectDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
        button = findViewById(R.id.btn_fechaTour);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idFav.getText().toString().isEmpty() && SelectDate.getText().toString().isEmpty()){
                    new KAlertDialog(FechaFavTourActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText("Debes llenar todos los campos")
                            .show();
                    return;
                }
                agregarFecha();
            }

            private void agregarFecha() {
                FavTourService favTourService = connection.getRetrofitInstance().create(FavTourService.class);
                FavTours favTours = new FavTours();
                favTours.setDate(SelectDate.getText().toString());
                Call<FavoritoTourResponse> call = favTourService.updateFavRest(bundle.getString("id"), favTours);
                call.enqueue(new Callback<FavoritoTourResponse>() {
                    @Override
                    public void onResponse(Call<FavoritoTourResponse> call, Response<FavoritoTourResponse> response) {
                        FavoritoTourResponse favoritoTourResponse = response.body();
                        if(favoritoTourResponse.ok){
                            KAlertDialog pDialog = new KAlertDialog(FechaFavTourActivity.this, KAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Loading");
                            pDialog.setCancelable(false);
                            pDialog.show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            new KAlertDialog(FechaFavTourActivity.this, KAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error...")
                                    .setContentText(favoritoTourResponse.msg)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FavoritoTourResponse> call, Throwable t) {

                    }
                });
            }
        });

    }
}