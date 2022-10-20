package com.example.proyectofinal.adapter;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.kalert.KAlertDialog;
import com.example.proyectofinal.DTO.ComentarioResGetResponse;
import com.example.proyectofinal.DTO.ComentarioTourGet;
import com.example.proyectofinal.DTO.ComentarioTourResponse;
import com.example.proyectofinal.MainActivity;
import com.example.proyectofinal.R;
import com.example.proyectofinal.interfaces.ComentarioTourService;
import com.example.proyectofinal.retrofit.connection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComentarioTourAdapter extends RecyclerView.Adapter<ComentarioTourAdapter.ViewHolder> {

    private List<ComentarioTourGet> mComentarioTour;
    private Context context;

    public ComentarioTourAdapter(List<ComentarioTourGet> mComentarioTour){this.mComentarioTour = mComentarioTour;}
    public void reloadData(List<ComentarioTourGet> comentarioTourGets){
        this.mComentarioTour = comentarioTourGets;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ComentarioTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView =  inflater.inflate(R.layout.comment_tour,parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioTourAdapter.ViewHolder holder, int position) {
        ComentarioTourGet comentarioTourGet = mComentarioTour.get(position);
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String _id = sharedPreferences.getString("_id", "");
        TextView idUserTxt = holder.mId;
        idUserTxt.setText(comentarioTourGet.get_idTour());
        TextView nombreUsuarioTextView = holder.mNombre;
        nombreUsuarioTextView.setText(comentarioTourGet.user.getNombre());
        TextView apellidoUsuarioTextView = holder.mApellido;
        apellidoUsuarioTextView.setText(comentarioTourGet.user.getApellido());
        TextView calificacionTextView = holder.mCalificacion;
        System.out.println(_id);
        Button buttonDel = holder.mButton;
        if(comentarioTourGet.calificacionTour == null){
            comentarioTourGet.calificacionTour= 0.0;
        }if(comentarioTourGet.calificacionTour == 0.0){
            calificacionTextView.setText("sin calificar");
        }else{
            calificacionTextView.setText(comentarioTourGet.calificacionTour.toString());
        }

        TextView comentarioTextView = holder.mComentario;
        comentarioTextView.setText(comentarioTourGet.comentario);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer position = mComentarioTour.indexOf(comentarioTourGet);
                mComentarioTour.remove(position);
                // notifyDataSetChanged();
                notifyItemRemoved(position);
                ComentarioTourService comentarioTourService = connection.getRetrofitInstance().create(ComentarioTourService.class);
                Call<ComentarioTourResponse> comentarioTourResponseCall = comentarioTourService.deleteComentarioTour(comentarioTourGet.get_idTour());
                comentarioTourResponseCall.enqueue(new Callback<ComentarioTourResponse>() {
                    @Override
                    public void onResponse(Call<ComentarioTourResponse> call, Response<ComentarioTourResponse> response) {
                        ComentarioTourResponse comentarioTourResponse = response.body();
                        if(comentarioTourResponse.ok){
                            new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Has borrado tu comentario")
                                    .setContentText("Comentario eliminado")
                                    .show();
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            view.getContext().startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ComentarioTourResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mComentarioTour.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNombre;
        private TextView mApellido;
        private TextView mCalificacion;
        private TextView mComentario;
        private Button mButton;
        private TextView mId;
        public ViewHolder(View itemView) {
            super(itemView);
            mNombre = (TextView) itemView.findViewById(R.id.NombreUserTour);
            mApellido = (TextView) itemView.findViewById(R.id.NombreApellidoTour);
            mCalificacion = (TextView) itemView.findViewById(R.id.CalificacionUserTour);
            mComentario = (TextView)  itemView.findViewById(R.id.ComentarioUserTour);
            mId = (TextView)  itemView.findViewById(R.id.idUserTour);
            mButton = (Button) itemView.findViewById(R.id.eliminarCommentTour);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
