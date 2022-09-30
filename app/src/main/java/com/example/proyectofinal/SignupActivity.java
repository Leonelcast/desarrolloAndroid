package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.UserResponse;
import com.example.proyectofinal.interfaces.UserService;
import com.example.proyectofinal.models.User;
import com.example.proyectofinal.retrofit.connection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private TextView atrasicon;
    private Button button;
    private EditText nombretxt, apellidotxt, emailSignuptxt, nacionalidadtxt, numeroTeltxt, signupPasswordtxt, confirmPasstxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        nombretxt = findViewById(R.id.nombre);
        apellidotxt = findViewById(R.id.apellido);
        emailSignuptxt= findViewById(R.id.emailSignup);
        nacionalidadtxt = findViewById(R.id.nacionalidad);
        numeroTeltxt = findViewById(R.id.numeroTel);
        signupPasswordtxt = findViewById(R.id.signupPassword);
        confirmPasstxt = findViewById(R.id.confirmPassword);
        atrasicon = findViewById(R.id.atras);
        button = findViewById(R.id.btn_register);
        atrasicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombretxt.getText().toString().isEmpty() && apellidotxt.getText().toString().isEmpty() && emailSignuptxt.getText().toString().isEmpty()
                        && nacionalidadtxt.getText().toString().isEmpty() && numeroTeltxt.getText().toString().isEmpty() && signupPasswordtxt.getText().toString().isEmpty()
                        && confirmPasstxt.getText().toString().isEmpty()) {
                    new KAlertDialog(SignupActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText("Debes llenar todos los campos")
                            .show();
                    return;
                }
                registrar();
            }
        });

    }

    private void registrar() {
        UserService userService = connection.getRetrofitInstance().create(UserService.class);
        User user = new User();
        user.setNombre(nombretxt.getText().toString());
        user.setApellido(apellidotxt.getText().toString());
        user.setEmail(emailSignuptxt.getText().toString());
        user.setNacionalidad(nacionalidadtxt.getText().toString());
        user.setNumero(numeroTeltxt.getText().toString());
        user.setPassword(signupPasswordtxt.getText().toString());
        user.setConfirmPassword(confirmPasstxt.getText().toString());

        Call<UserResponse> call = userService.signUpUser(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse.ok) {
                    KAlertDialog pDialog = new KAlertDialog(SignupActivity.this, KAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    new KAlertDialog(SignupActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText(userResponse.msg)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                new KAlertDialog(SignupActivity.this, KAlertDialog.ERROR_TYPE)
                        .setTitleText("Error...")
                        .setContentText("Error de conexión")
                        .show();
            }
        });
    }
}