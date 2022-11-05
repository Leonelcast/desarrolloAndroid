package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.UserResponse;
import com.example.proyectofinal.interfaces.UserService;
import com.example.proyectofinal.models.User;
import com.example.proyectofinal.retrofit.connection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button button;
    EditText passwordTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        passwordTxt = findViewById(R.id.changepass);
        button = findViewById(R.id.buttChange);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordTxt.getText().toString().isEmpty()){
                    new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText("Debes ingresar email y password")
                            .show();
                    return;
                }
                UserService userService = connection.getRetrofitInstance().create(UserService.class);
                User user = new User();
                user.setPassword(passwordTxt.getText().toString());
                Call<UserResponse> call = userService.updateuserPassword(_id, user);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if(userResponse.ok){
                            new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Has actualizado tu informacion")
                                    .setContentText("Tu informacion se actualizo con exito")
                                    .show();
                        }else{
                            new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error...")
                                    .setContentText(userResponse.msg)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}