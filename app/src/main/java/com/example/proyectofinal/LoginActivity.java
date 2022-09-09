package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailtxt = findViewById(R.id.emailLogin);
        passtxt = findViewById(R.id.loginPassword);
        button = findViewById(R.id.btn_login);
        signuptxt = findViewById(R.id.txt_signup);
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
                    Toast.makeText(LoginActivity.this, "Debes ingresar tu email y password", Toast.LENGTH_SHORT).show();
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

            Call<UserResponse> call = userService.login(user);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse userResponse = response.body();
                    if (userResponse.ok){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(LoginActivity.this, userResponse.msg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "No se puede establecer conexion", Toast.LENGTH_SHORT).show();
                }
            });

        }
}