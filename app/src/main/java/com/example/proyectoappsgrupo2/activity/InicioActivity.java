package com.example.proyectoappsgrupo2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.google.firebase.database.ThrowOnExtraProperties;
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
    private ArrayList<Incidencia> listaIncidencias = new ArrayList<>();
    private String est;
    private String nombre;
    InicioListAdapter inicioListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        /*
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mostrarIncidencias();

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
                ArrayList<Incidencia> listita = new ArrayList<>();

                for (DataSnapshot keyId : dataSnapshot.getChildren()){

                    if(keyId.child("autor").getValue().equals("R3MpfReFycYfKhncOwdU96hwmA22")){
                        Incidencia incidencia = new Incidencia();
                        nombre = keyId.child("nombre").getValue(String.class);
                        est = keyId.child("estado").getValue(String.class);

                        incidencia.setNombre(nombre);
                        incidencia.setEstado(est);
                        Log.d("GREYS ANATOMY SEASON 17", nombre);
                        listita.add(incidencia);
                    }
                }



                inicioListAdapter = new InicioListAdapter(listita,InicioActivity.this);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(InicioActivity.this));
                recyclerView.setAdapter(inicioListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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

            desplegarCerrarSession();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void botonAñadirIncidencia(MenuItem menu){
        Intent i = new Intent(this, NuevaIncidenciaActivity.class);
        startActivity(i);
    }


    public void desplegarCerrarSession(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("¿Esta seguro que quiere cerrar sesión?");
        alertDialog.setMessage("Esta a punto de salir de la aplicación");
        alertDialog.setPositiveButton("CERRAR SESIÓN",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(InicioActivity.this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InicioActivity.this, MainActivity.class));
                    }
                });
        alertDialog.setNegativeButton("REGRESAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("msgAlerta", "Negativo");
                    }
                });
        alertDialog.show();
    }

}