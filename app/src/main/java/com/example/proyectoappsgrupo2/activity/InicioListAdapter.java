package com.example.proyectoappsgrupo2.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoappsgrupo2.R;
import com.example.proyectoappsgrupo2.entity.Incidencia;

import java.util.ArrayList;
import java.util.List;

public class InicioListAdapter extends RecyclerView.Adapter<InicioListAdapter.InicioViewHolder> {

    private ArrayList<Incidencia> listaIncidencias;
    private static Context context;

    public InicioListAdapter(ArrayList<Incidencia> incidenciaData, Context context){
        Log.d("TAGGGG", "adapter initialized");
        this.listaIncidencias = incidenciaData;
        this.context = context;

    }

    public static class InicioViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public ImageButton verMasDetallesRV;
        public Incidencia incidencia;
        //Context context;


        public InicioViewHolder(View itemView, final Context context ) {

            super(itemView);
           // this.context= context;
            this.textView1 = itemView.findViewById(R.id.nombreIncidenciaRV);
            this.textView2 = itemView.findViewById(R.id.estadoRV);
            this.verMasDetallesRV =itemView.findViewById(R.id.verMasDetallesRV);


        }

        public void asignarDatos(Incidencia incidencia) {
            textView1.setText(incidencia.getNombre());
            textView2.setText(incidencia.getEstado());
        }

        public void botonDetalles(final Incidencia incidencia) {


            try{
                verMasDetallesRV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, DetallesIncidenciaActivity.class);
                        // intent.putExtra("nombre",incidencia.getNombre());
                        intent.putExtra("idIncidencia",incidencia.getDescripcion());
                        intent.putExtra("lat", incidencia.getLatitud());
                        intent.putExtra("lon", incidencia.getLongitud());

                  /*      intent.putExtra("autor",incidencia.getAutor());
                        intent.putExtra("estado",incidencia.getEstado());
                        intent.putExtra("latitud", incidencia.getLatitud());
                        intent.putExtra("longitud",incidencia.getLongitud());*/
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

        Log.d("TAG2222222", "onCreateViewHolder: inside");

        View itemView = LayoutInflater.from(context).inflate(R.layout.lista_incidenciasrv, parent, false);


        InicioViewHolder inicioViewHolder = new InicioViewHolder(itemView,context);
        return inicioViewHolder;



    }

    @Override
    public void onBindViewHolder(@NonNull InicioViewHolder holder, int position) {
        //Incidencia inci = listaIncidencias.get(position);
        //String nombre = listaIncidencias.get(position).getNombre();
        //String estado = listaIncidencias.get(position).getEstado();
        //Log.d("probando100", nombre);
        //holder.textView1.setText(inci.getNombre());
        //holder.textView2.setText(inci.getEstado());
        //holder.incidencia = listaIncidencias.get(position);

        holder.asignarDatos(listaIncidencias.get(position));
        holder.botonDetalles(listaIncidencias.get(position));

    }

    @Override
    public int getItemCount() {
        if (listaIncidencias != null){
            return listaIncidencias.size();
        } else {
            return 0;
        }
    }



}
