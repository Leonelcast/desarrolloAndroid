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
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.UserResponse;
import com.example.proyectofinal.interfaces.UserService;
import com.example.proyectofinal.models.User;
import com.example.proyectofinal.retrofit.connection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    private EditText emailtxt, passtxt;
    private TextView signuptxt;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailtxt = findViewById(R.id.emailLogin);
        passtxt = findViewById(R.id.loginPassword);
        button = findViewById(R.id.btn_login);
        signuptxt = findViewById(R.id.txt_signup);
        sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", null);
        if(_id != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailtxt.getText().toString().isEmpty() && passtxt.getText().toString().isEmpty()) {
                    new KAlertDialog(LoginActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText("Debes ingresar email y password")
                            .show();
                    return;
                }
                autenticar();


            }

        });

    }
        private void autenticar(){
            UserService userService = connection.getRetrofitInstance().create(UserService.class);
            User user = new User();
            user.setEmail(emailtxt.getText().toString());
            user.setPassword(passtxt.getText().toString());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Call<UserResponse> call = userService.login(user);

            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse userResponse = response.body();
                    if (userResponse.ok){
                        editor.putString("_id", userResponse.usuario.get_id());
                        editor.putString("nombre", userResponse.usuario.getNombre());
                        editor.putString("apellido", userResponse.usuario.getApellido());
                        editor.putString("email", userResponse.usuario.getEmail());
                        editor.putString("nacionalidad", userResponse.usuario.getNacionalidad());
                        editor.putString("telefono", userResponse.usuario.getNumero());
                        editor.commit();
                        KAlertDialog pDialog = new KAlertDialog(LoginActivity.this, KAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Loading");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }else{
                        KAlertDialog pDialog = new KAlertDialog(LoginActivity.this, KAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Loading");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        pDialog.dismissWithAnimation();

                        new KAlertDialog(LoginActivity.this, KAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText(userResponse.msg)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    new KAlertDialog(LoginActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText("No se puede establecer conexion")
                            .show();
                }
            });

        }
}