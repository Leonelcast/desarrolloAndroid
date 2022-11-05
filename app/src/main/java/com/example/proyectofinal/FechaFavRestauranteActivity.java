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
import com.example.proyectofinal.DTO.FavoritoRestauranteResponse;
import com.example.proyectofinal.databinding.ActivityFechaFavRestauranteBinding;
import com.example.proyectofinal.interfaces.FavRestauranteService;
import com.example.proyectofinal.models.FavRestaurantes;
import com.example.proyectofinal.retrofit.connection;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FechaFavRestauranteActivity extends AppCompatActivity {

    ActivityFechaFavRestauranteBinding binding;
    private EditText idFav, SelectDate;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_fav_restaurante);
        binding = ActivityFechaFavRestauranteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        idFav = findViewById(R.id.FAVId);
        idFav.setText(bundle.getString("id"));


        SelectDate = findViewById(R.id.fechaRestaurante);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(FechaFavRestauranteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        SelectDate.setText(date);
                    }
                }, year, month, day);
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        button = findViewById(R.id.btn_fechaRes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idFav.getText().toString().isEmpty() && SelectDate.getText().toString().isEmpty()){
                    new KAlertDialog(FechaFavRestauranteActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText("Debes llenar todos los campos")
                            .show();
                    return;
                }
                agregarFecha();
            }

            private void agregarFecha() {
                FavRestauranteService favRestauranteService = connection.getRetrofitInstance().create(FavRestauranteService.class);
                FavRestaurantes favRestaurantes = new FavRestaurantes();
                favRestaurantes.setDate(SelectDate.getText().toString());
                Call<FavoritoRestauranteResponse> call = favRestauranteService.updateFavRest(bundle.getString("id"), favRestaurantes);
                call.enqueue(new Callback<FavoritoRestauranteResponse>() {
                    @Override
                    public void onResponse(Call<FavoritoRestauranteResponse> call, Response<FavoritoRestauranteResponse> response) {
                        FavoritoRestauranteResponse favoritoRestauranteResponse = response.body();
                        if(favoritoRestauranteResponse.ok){
                            KAlertDialog pDialog = new KAlertDialog(FechaFavRestauranteActivity.this, KAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Loading");
                            pDialog.setCancelable(false);
                            pDialog.show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            new KAlertDialog(FechaFavRestauranteActivity.this, KAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error...")
                                    .setContentText(favoritoRestauranteResponse.msg)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FavoritoRestauranteResponse> call, Throwable t) {

                    }
                });
            }
        });





    }
}