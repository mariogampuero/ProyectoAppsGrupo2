package com.example.proyectoappsgrupo2.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.proyectoappsgrupo2.MainActivity;
import com.example.proyectoappsgrupo2.R;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class InicioActivity extends AppCompatActivity {

    private Menu menu;
    FirebaseAuth firebaseAuth;
    //DatabaseReference databaseReference;
    private List<Incidencia> listaIncidencias = new List<Incidencia>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Incidencia> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(Incidencia incidencia) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends Incidencia> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends Incidencia> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Incidencia get(int index) {
            return null;
        }

        @Override
        public Incidencia set(int index, Incidencia element) {
            return null;
        }

        @Override
        public void add(int index, Incidencia element) {

        }

        @Override
        public Incidencia remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<Incidencia> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Incidencia> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<Incidencia> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    private Incidencia incidencia = new Incidencia();
    private String est;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mostrarIncidencias();



/*
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

                        ArrayList<Incidencia> lista =dtoListaIncidencias.getListaincidencias();

                        InicioListAdapter adapter = new InicioListAdapter(listaincidencias,InicioActivity.this);

                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(InicioActivity.this));


                    }
                };


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/



    }

    public void mostrarIncidencias (){
        DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        DatabaseReference incidenciaRef = FirebaseDatabase.getInstance().getReference().child("Incidencias");
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        incidenciaRef.addValueEventListener(new ValueEventListener() {
            String title,descrip,aut;
            int state;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId : dataSnapshot.getChildren()){

                    if(keyId.child("autor").getValue().equals("R3MpfReFycYfKhncOwdU96hwmA22")){
                        nombre = keyId.child("nombre").getValue(String.class);
                        est = keyId.child("estado").getValue(String.class);

                        incidencia.setNombre(nombre);
                        incidencia.setEstado(est);
                        listaIncidencias.add(incidencia);

                        Log.d("probando9", nombre);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("probandoededede9", "nombre");
        InicioListAdapter inicioListAdapter = new InicioListAdapter(listaIncidencias,InicioActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Log.d("probandogbgbgbgbgb9", "nombre");
        recyclerView.setAdapter(inicioListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(InicioActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbarinicio,menu);
        /*firebaseAuth = FirebaseAuth.getInstance();
        this.menu.findItem(R.id.usernameInicio).setTitle(firebaseAuth.getCurrentUser().getEmail());*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cerrarSesion) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(InicioActivity.this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(InicioActivity.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void botonAñadirIncidencia(MenuItem menu){
        Intent i = new Intent(this, NuevaIncidenciaActivity.class);
        startActivity(i);
    }

}