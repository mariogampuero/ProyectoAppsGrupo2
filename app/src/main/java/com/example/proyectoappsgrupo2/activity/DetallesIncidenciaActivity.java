package com.example.proyectoappsgrupo2.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.auth.FirebaseAuth;
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
    private Spinner estadoDetalles;
    private TextView autorDetalles;
    private TextView autorCodigo;
    private TextView autorCorreo;
    private TextView comentario;
    private TextView tituloComentario;
    private ImageView imagenDetalles;
    private EditText comentarioEditText;
    private Button guardarInfra;
    private static final String INCIDENCIAS = "Incidencias";
    private StorageReference storageReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_incidencia);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        Intent inte = getIntent();
        idIncidencia = inte.getStringExtra("idIncidencia");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference incidenciaRef = FirebaseDatabase.getInstance().getReference().child("Incidencias");
        storageReference = FirebaseStorage.getInstance().getReference();

        tituloDetalles = findViewById(R.id.titleDetalles);
        descripcionDetalles = findViewById(R.id.descripDetalles);

        String[] lista = {"Pendiente","Atendido"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetallesIncidenciaActivity.this,
                android.R.layout.simple_spinner_dropdown_item,lista);
        estadoDetalles = findViewById(R.id.estadoDetalles);
        estadoDetalles.setAdapter(adapter);
        autorDetalles = findViewById(R.id.autorDetalles);
        autorCodigo= findViewById(R.id.autorCodigo);
        imagenDetalles = findViewById(R.id.imageDetalles);
        comentario = (TextView) findViewById(R.id.comentario);
        tituloComentario =  (TextView) findViewById(R.id.textView14);
        comentarioEditText = findViewById(R.id.comentarioPlain);

        String compareValue = "Estado";
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            estadoDetalles.setSelection(spinnerPosition);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        //DatabaseReference incidenciaRef = FirebaseDatabase.getInstance().getReference().child("Incidencias").child("-MCymkPcCpFWC30XrPA6");

        //SE OBTIENEN LOS DATOS DE LA INCIDENCIA

        incidenciaRef.addValueEventListener(new ValueEventListener() {
            String title,descrip,aut,estado,comentarioStr;
            String state;
            Double lat;
            Double lon;
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshotIncidencia) {

               for (DataSnapshot keyId : dataSnapshotIncidencia.getChildren()){
                    if(keyId.getKey().equals(idIncidencia)){
                        title = keyId.child("nombre").getValue(String.class);
                        descrip = keyId.child("descripcion").getValue(String.class);
                        aut = keyId.child("autor").getValue(String.class);
                        state = keyId.child("estado").getValue(String.class);
                        lat = keyId.child("latitud").getValue(Double.class);
                        lon = keyId.child("longitud").getValue(Double.class);
                        estado = keyId.child("estado").getValue(String.class);
                        comentarioStr = keyId.child("comentario").getValue(String.class);
                        break;
                    }
               }
               if(!estado.equals("pendiente")){
                   estadoDetalles.setSelection(1);
               }

               tituloDetalles.setText(title);
               descripcionDetalles.setText(descrip);
               if(comentarioStr != null){
                   comentario.setText(comentarioStr);
                   comentarioEditText.setText(comentarioStr);
               }

                //SE OBTIENE EL ROL DEL USUARIO

                DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");
                usuarioRef.addValueEventListener(new ValueEventListener() {
                    String rol, correo;
                    Integer codigo;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshotUsuario) {

                        for (DataSnapshot keyId : dataSnapshotUsuario.getChildren()){
                            if(keyId.getKey().equals(firebaseAuth.getCurrentUser().getUid())){
                                rol = keyId.child("rol").getValue(String.class);
                                correo = keyId.child("correo").getValue(String.class);
                                codigo = keyId.child("codigo").getValue(Integer.class);
                                break;
                            }
                        }

                        autorDetalles.setText(correo);
                        autorCodigo.setText(codigo.toString());

                        if(rol.equals("miembro-pucp")){
                            comentarioEditText.setVisibility(View.GONE);
                            estadoDetalles.setEnabled(false);
                            estadoDetalles.setClickable(false);
                            autorDetalles.setText(correo);
                            autorCodigo.setText(codigo.toString());
                            if(comentarioStr == null){
                                tituloComentario.setVisibility(View.GONE);
                            }
                        } else {
                            comentario.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //SE OBTIENE LA IMAGEN DE LA INCIDENCIA
        StorageReference fotoRef = storageReference.child("fotos/"+idIncidencia);
        Glide.with(this).load(fotoRef).into(imagenDetalles);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
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
        Intent intent = getIntent();
        /*String lat = intent.getStringExtra("lat");
        String lon = intent.getStringExtra("lon");
        LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
        */
        LatLng latLng = new LatLng(intent.getDoubleExtra("lat", 0), intent.getDoubleExtra("lon", 0));
        map.addMarker(new MarkerOptions().position(latLng).title("Incidencia 1"));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
    }

    public void guardarCambiosInfra(){

    }


}