package com.example.proyectoappsgrupo2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectoappsgrupo2.MainActivity;
import com.example.proyectoappsgrupo2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetallesIncidenciaActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    private String idIncidencia;
    private TextView tituloDetalles;
    private TextView descripcionDetalles;
    private TextView estadoDetalles;
    private TextView autorDetalles;
    private ImageView imagenDetalles;
    private static final String INCIDENCIAS = "Incidencias";
    private StorageReference storageReference;
    private String idPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_incidencia);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent inte = getIntent();
        idIncidencia = inte.getStringExtra("idIncidencia");

        idPrueba = "-MCymkPcCpFWC30XrPA6";

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference incidenciaRef = FirebaseDatabase.getInstance().getReference().child("Incidencias");
        storageReference = FirebaseStorage.getInstance().getReference();

        tituloDetalles = findViewById(R.id.titleDetalles);
        descripcionDetalles = findViewById(R.id.descripDetalles);
        estadoDetalles = findViewById(R.id.estadoDetalles);
        autorDetalles = findViewById(R.id.autorDetalles);
        imagenDetalles = findViewById(R.id.imageDetalles);

        //DatabaseReference incidenciaRef = FirebaseDatabase.getInstance().getReference().child("Incidencias").child("-MCymkPcCpFWC30XrPA6");

        incidenciaRef.addValueEventListener(new ValueEventListener() {
            String title,descrip,aut;
            String state;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               for (DataSnapshot keyId : dataSnapshot.getChildren()){
                   //Log.d("probando89", "uwu");
                   Log.d("ahhhhhhh funcionaaaa", idIncidencia);
                    if(keyId.getKey().equals(idIncidencia)){
                        //Log.d("probando", "uwu");
                        title = keyId.child("nombre").getValue(String.class);
                        descrip = keyId.child("descripcion").getValue(String.class);
                        aut = keyId.child("autor").getValue(String.class);
                        state = keyId.child("estado").getValue(String.class);
                        break;
                    }
                }

              /*
                title = dataSnapshot.child("nombre").getValue(String.class);
                descrip = dataSnapshot.child("descripcion").getValue(String.class);
                aut = dataSnapshot.child("autor").getValue(String.class);
                state = dataSnapshot.child("estado").getValue(Integer.class);
*/
                tituloDetalles.setText(title);
                descripcionDetalles.setText(descrip);
                autorDetalles.setText(aut);
                estadoDetalles.setText(state);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        StorageReference fotoRef = storageReference.child("fotos/"+idIncidencia);

        Glide.with(this).load(fotoRef).into(imagenDetalles);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbardetallesincidencia, menu);
        return true;
    }

    public void botonAtrasAppBar(MenuItem menu){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng latLng = new LatLng(-12.060896, -77.041357);
        map.addMarker(new MarkerOptions().position(latLng).title("Incidencia 1"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}