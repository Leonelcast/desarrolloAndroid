package com.example.proyectofinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.CommentResActivity;
import com.example.proyectofinal.DTO.ComentarioResGetResponse;
import com.example.proyectofinal.DTO.ComentarioResResponse;
import com.example.proyectofinal.DescriptionResActivity;
import com.example.proyectofinal.LoginActivity;
import com.example.proyectofinal.MainActivity;
import com.example.proyectofinal.R;
import com.example.proyectofinal.interfaces.ComentarioResService;
import com.example.proyectofinal.models.ComentarioRest;
import com.example.proyectofinal.models.Restaurante;
import com.example.proyectofinal.retrofit.connection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentariosRestAdapter extends RecyclerView.Adapter<ComentariosRestAdapter.ViewHolder> {
    private List<ComentarioResGetResponse> mComentarioRest;
    private Context context;

    public ComentariosRestAdapter(List<ComentarioResGetResponse> mComentarioRest){this.mComentarioRest = mComentarioRest; }

    public void reloadData(List<ComentarioResGetResponse> comentarioRests){
        this.mComentarioRest = comentarioRests;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ComentariosRestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.comment_res, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComentariosRestAdapter.ViewHolder holder, int position) {
        ComentarioResGetResponse comentarioRest = mComentarioRest.get(position);
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        TextView idUserTxt = holder.mId;
        idUserTxt.setText(comentarioRest.get_id());
        TextView nombreUsuarioTextView = holder.mNombre;
        nombreUsuarioTextView.setText(comentarioRest.user.getNombre());
        TextView apellidoUsuarioTextView = holder.mApellido;
        apellidoUsuarioTextView.setText(comentarioRest.user.getApellido());
        TextView calificacionTextView = holder.mCalificacion;
        System.out.println(_id);
        Button buttonDel = holder.mButton;
        if(comentarioRest.calificacion == null){
            comentarioRest.calificacion= 0.0;
        }
        calificacionTextView.setText(comentarioRest.calificacion.toString());
        TextView comentarioTextView = holder.mComentario;
        comentarioTextView.setText(comentarioRest.comentario);

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    ComentarioResService comentarioResService = connection.getRetrofitInstance().create(ComentarioResService.class);
                    Call<ComentarioResResponse>  comentarioResResponseCall = comentarioResService.deleteComentarioRest(comentarioRest.get_id());
                    comentarioResResponseCall.enqueue(new Callback<ComentarioResResponse>() {
                        @Override
                        public void onResponse(Call<ComentarioResResponse> call, Response<ComentarioResResponse> response) {
                            ComentarioResResponse comentarioResResponse = response.body();
                            if(comentarioResResponse.ok){
                                new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Has borrado tu comentario")
                                        .setContentText("Comentario eliminado")
                                        .show();
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                view.getContext().startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<ComentarioResResponse> call, Throwable t) {
                            Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });

       /* if(idUserTxt.getText() == _id ){
            idUserTxt.setVisibility(View.VISIBLE);

        }else{
            idUserTxt.setVisibility(View.INVISIBLE);
        }
        idUserTxt.setText(comentarioRest.user.get_id());

*/



    }

    @Override
    public int getItemCount() {
        return mComentarioRest.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
       private TextView mNombre;
        private TextView mApellido;
        private TextView mCalificacion;
        private TextView mComentario;
        private Button mButton;
        private TextView mId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNombre = (TextView) itemView.findViewById(R.id.NombreUserRes);
            mApellido = (TextView) itemView.findViewById(R.id.NombreApellidoRes);
            mCalificacion = (TextView) itemView.findViewById(R.id.CalificacionUserRes);
            mComentario = (TextView)  itemView.findViewById(R.id.ComentarioUserRes);
            mId = (TextView)  itemView.findViewById(R.id.idUserRest);
            mButton = (Button) itemView.findViewById(R.id.eliminarCommentRes);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {



        }
    }


}
