package com.example.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private Button button, buttonUpdate, passwordIntent;
    private EditText nombretxt, apellidotxt, emailtxt, nacionalidadtxt, telefonotxt;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        String nombre = sharedPreferences.getString("nombre", "");
        String apellido = sharedPreferences.getString("apellido", "");
        String email = sharedPreferences.getString("email", "");
        String nacionalidad = sharedPreferences.getString("nacionalidad", "");
        String numero = sharedPreferences.getString("telefono", "");
        button = view.findViewById(R.id.cerrar);
        buttonUpdate = view.findViewById(R.id.actualizarDatos);
        nombretxt = view.findViewById(R.id.nombreUser);
        nombretxt.setText(nombre);
        apellidotxt = view.findViewById(R.id.apellidoUser);
        apellidotxt.setText(apellido);
        emailtxt = view.findViewById(R.id.emailUser);
        emailtxt.setText(email);
        nacionalidadtxt = view.findViewById(R.id.nacionalidadUser);
        nacionalidadtxt.setText(nacionalidad);
        telefonotxt = view.findViewById(R.id.numeroTelUser);
        telefonotxt.setText(numero);
        passwordIntent = view.findViewById(R.id.updatePass);
        passwordIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();
                startActivity(intent);
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserService userService = connection.getRetrofitInstance().create(UserService.class);
                User user = new User();
                user.setNombre(nombretxt.getText().toString());
                user.setApellido(apellidotxt.getText().toString());
                user.setEmail(emailtxt.getText().toString());
                user.setNacionalidad(nacionalidadtxt.getText().toString());
                user.setNumero(telefonotxt.getText().toString());
                Call<UserResponse> call = userService.updateuser(_id, user);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if(userResponse.ok){
                           SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("nombre", nombretxt.getText().toString());
                            editor.putString("apellido", apellidotxt.getText().toString());
                            editor.putString("email", emailtxt.getText().toString());
                            editor.putString("nacionalidad", nacionalidadtxt.getText().toString());
                            editor.putString("telefono", telefonotxt.getText().toString());
                            editor.apply();
                            new KAlertDialog(getContext(), KAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Has actualizado tu informacion")
                                    .setContentText("Tu informacion se actualizo con exito")
                                    .show();
                        }else{
                            new KAlertDialog(getContext(), KAlertDialog.ERROR_TYPE)
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
        return view;
    }

}