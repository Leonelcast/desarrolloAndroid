package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.ComentarioResResponse;
import com.example.proyectofinal.databinding.ActivityCommentResBinding;
import com.example.proyectofinal.interfaces.ComentarioResService;
import com.example.proyectofinal.models.ComentarioRest;
import com.example.proyectofinal.retrofit.connection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentResActivity extends AppCompatActivity {
    private TextView atrasicon;
    private Button button;
    private EditText idUserTxt, idRestTxt, calificacionTxt, comentarioTxt;

    ActivityCommentResBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_res);
        binding = ActivityCommentResBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        idUserTxt = findViewById(R.id.usuarioId);
        idUserTxt.setText(_id);
        idRestTxt = findViewById(R.id.restauranteId);
        //bundle.getString("img");
        idRestTxt.setText(bundle.getString("_idRest"));
        calificacionTxt = findViewById(R.id.Calificacion);
        comentarioTxt = findViewById(R.id.comentarioDelRes);

        button = findViewById(R.id.btn_comment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idUserTxt.getText().toString().isEmpty() && idRestTxt.getText().toString().isEmpty() && comentarioTxt.getText().toString().isEmpty()){
                    new KAlertDialog(CommentResActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText("Debes llenar todos los campos")
                            .show();
                    return;

                }
                if(calificacionTxt.getText().toString().isEmpty()){
                    calificacionTxt.setText("0.0");
                }

                comentar();

            }

            private void comentar() {
                ComentarioResService comentarioResService = connection.getRetrofitInstance().create(ComentarioResService.class);
                ComentarioRest comentarioRest = new ComentarioRest();
                comentarioRest.setUsuario(idUserTxt.getText().toString());
                comentarioRest.setRestaurante(idRestTxt.getText().toString());
                comentarioRest.setCalificacion(Double.parseDouble(calificacionTxt.getText().toString()));
                comentarioRest.setComentario(comentarioTxt.getText().toString());
                Call<ComentarioResResponse> call = comentarioResService.comentarRestaurante(comentarioRest);
                call.enqueue(new Callback<ComentarioResResponse>() {
                    @Override
                    public void onResponse(Call<ComentarioResResponse> call, Response<ComentarioResResponse> response) {
                        ComentarioResResponse comentarioResResponse = response.body();
                        if(comentarioResResponse.ok){

                            KAlertDialog pDialog = new KAlertDialog(CommentResActivity.this, KAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Loading");
                            pDialog.setCancelable(false);
                            pDialog.show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else {
                            new KAlertDialog(CommentResActivity.this, KAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error...")
                                    .setContentText(comentarioResResponse.msg)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ComentarioResResponse> call, Throwable t) {
                        new KAlertDialog(CommentResActivity.this, KAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText("Error de conexión")
                                .show();

                    }
                });

            }
        });

    }
}