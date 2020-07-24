package com.example.proyectoappsgrupo2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.example.proyectoappsgrupo2.MainActivity;
import com.example.proyectoappsgrupo2.R;
import com.example.proyectoappsgrupo2.dto.DtoListaIncidencias;
import com.example.proyectoappsgrupo2.entity.Incidencia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbarinicio,menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Incidencias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Lista de Incidencias
                final ArrayList<Incidencia> listaincidencias = new ArrayList<Incidencia>();

                //Incidencia una por una
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    Incidencia incidenciaIndividual = child.getValue(Incidencia.class);
                    listaincidencias.add(incidenciaIndividual);
                }



                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Log.d("no se", response);

                        Gson gson = new Gson();
                        DtoListaIncidencias dtoListaIncidencias = gson.fromJson(response,DtoListaIncidencias.class);

                        InicioListAdapter adapter = new InicioListAdapter(listaincidencias,InicioActivity.this);

                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(InicioActivity.this));


                    }
                };



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void botonAñadirIncidencia(MenuItem menu){
        Intent i = new Intent(this, NuevaIncidenciaActivity.class);
        startActivity(i);
    }

}