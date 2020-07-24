package com.example.proyectoappsgrupo2.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoappsgrupo2.R;
import com.example.proyectoappsgrupo2.entity.Incidencia;

import java.util.ArrayList;

public class InicioListAdapter extends RecyclerView.Adapter<InicioListAdapter.InicioViewHolder> {

    private ArrayList<Incidencia> listaIncidencias;
    private Context context;

    public InicioListAdapter(ArrayList<Incidencia> incidenciaData, Context context){
        this.listaIncidencias = incidenciaData;
        this.context = context;
    }

    public static class InicioViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public Button verMasDetallesRV;
        public Incidencia incidencia;
        Context context;

        public InicioViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            this.context= context;
            this.textView1 = itemView.findViewById(R.id.nombreIncidenciaRV);
            this.textView2 = itemView.findViewById(R.id.estadoRV);
            this.verMasDetallesRV =itemView.findViewById(R.id.verMasDetallesRV);

            try{
                verMasDetallesRV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetallesIncidenciaActivity.class);
                        intent.putExtra("nombre",incidencia.getNombre());
                        intent.putExtra("descripcion",incidencia.getDescripcion());
                        intent.putExtra("autor",incidencia.getAutor());
                        intent.putExtra("estado",incidencia.getEstado());
                        intent.putExtra("latitud", incidencia.getLatitud());
                        intent.putExtra("longitud",incidencia.getLongitud());
                        context.startActivity(intent);
                    }
                });
            }catch (NullPointerException exception){

            }

        }
    }

    @NonNull
    @Override
    public InicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.lista_incidenciasrv, parent, false);
        InicioViewHolder inicioViewHolder = new InicioViewHolder(itemView, context);
        return inicioViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull InicioViewHolder holder, int position) {
        String nombre = listaIncidencias.get(position).getNombre();
        String estado = listaIncidencias.get(position).getEstado();

        holder.textView1.setText(nombre);
        holder.textView2.setText(estado);
        holder.incidencia = listaIncidencias.get(position);

    }

    @Override
    public int getItemCount() {
        return listaIncidencias.size();
    }



}
