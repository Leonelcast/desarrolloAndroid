package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.ComentarioTourResponse;
import com.example.proyectofinal.databinding.ActivityCommentTourBinding;
import com.example.proyectofinal.interfaces.ComentarioTourService;
import com.example.proyectofinal.models.ComentarioTour;
import com.example.proyectofinal.retrofit.connection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentTourActivity extends AppCompatActivity {
    private TextView atrasicon;
    private Button button;
    private EditText idUserTxt, idRestTxt, comentarioTxt;
    private CheckBox checkBox;
    private RatingBar calificacionTxt;
    ActivityCommentTourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_tour);
        binding = ActivityCommentTourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        idUserTxt = findViewById(R.id.usuarioIdTour);
        idUserTxt.setText(_id);
        idRestTxt = findViewById(R.id.touristicoId);
        //bundle.getString("img");
        idRestTxt.setText(bundle.getString("_idTour"));
        calificacionTxt = findViewById(R.id.CalificacionTour);
        comentarioTxt = findViewById(R.id.comentarioDelTour);
        button = findViewById(R.id.btn_commentTour);
        checkBox = findViewById(R.id.comentCheckTour);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox)view).isChecked();
                if(checked == true){
                    calificacionTxt.setVisibility(View.VISIBLE);
                }else{
                    calificacionTxt.setVisibility(View.GONE);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idUserTxt.getText().toString().isEmpty() && idRestTxt.getText().toString().isEmpty() && comentarioTxt.getText().toString().isEmpty()){
                    new KAlertDialog(CommentTourActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText("Debes llenar todos los campos")
                            .show();
                    return;

                }

                comentar();
            }

            private void comentar() {
                ComentarioTourService comentarioTourService = connection.getRetrofitInstance().create(ComentarioTourService.class);
                ComentarioTour comentarioTour = new ComentarioTour();
                comentarioTour.setUsuario(idUserTxt.getText().toString());
                comentarioTour.setTuristico(idRestTxt.getText().toString());
                comentarioTour.setCalificacionTour(Double.parseDouble(String.valueOf(calificacionTxt.getRating())));
                comentarioTour.setComentario(comentarioTxt.getText().toString());
                Call<ComentarioTourResponse> call = comentarioTourService.comentarTour(comentarioTour);
                call.enqueue(new Callback<ComentarioTourResponse>() {
                    @Override
                    public void onResponse(Call<ComentarioTourResponse> call, Response<ComentarioTourResponse> response) {
                        ComentarioTourResponse comentarioTourResponse = response.body();
                        if (comentarioTourResponse.ok){
                            KAlertDialog pDialog = new KAlertDialog(CommentTourActivity.this, KAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Loading");
                            pDialog.setCancelable(false);
                            pDialog.show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            new KAlertDialog(CommentTourActivity.this, KAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error...")
                                    .setContentText(comentarioTourResponse.msg)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ComentarioTourResponse> call, Throwable t) {
                        new KAlertDialog(CommentTourActivity.this, KAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText("Error de conexión")
                                .show();


                    }
                });
            }
        });
    }
}