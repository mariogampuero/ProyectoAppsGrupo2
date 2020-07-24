package com.example.proyectoappsgrupo2.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectoappsgrupo2.MainActivity;
import com.example.proyectoappsgrupo2.R;
import com.example.proyectoappsgrupo2.entity.Incidencia;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NuevaIncidenciaActivity extends AppCompatActivity {

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Button btnUpload;
    private static final int GALLERY_INTENT = 1;
    private EditText descripcion;
    private EditText titulo;
    private ImageView img;
    private Button btnSubirIncidencia;
    private Uri uri;
    private LatLng latLng;



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbarnuevaincidencia,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_incidencia);

        titulo = (EditText) findViewById(R.id.titleIncidencia);
        descripcion = (EditText) findViewById(R.id.descripcionIncidencia);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Incidencias");
        btnUpload = (Button) findViewById(R.id.buttonFoto);
        img = (ImageView) findViewById(R.id.fotoIncidencia);
        btnSubirIncidencia = (Button) findViewById(R.id.buttonGuardarIncidencia);
        Button btnAgregarGeolocalizacion = (Button) findViewById(R.id.buttonLocal);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });

        btnSubirIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latLng != null){
                    final String autor = "Pepito";
                    final int estado = 0;
                    final String ImageUploadId = databaseReference.push().getKey();
                    //StorageReference filePath = storageReference.child("fotos").child(uri.getLastPathSegment());
                    StorageReference filePath = storageReference.child("fotos").child(ImageUploadId);

                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String descri = descripcion.getText().toString().trim();
                            String title = titulo.getText().toString().trim();
                            Toast.makeText(NuevaIncidenciaActivity.this, "Se subio la incidencia exitosamente", Toast.LENGTH_SHORT).show();
                            Incidencia imageUploadInfo = new Incidencia(taskSnapshot.getUploadSessionUri().toString(), descri, title, autor, estado, latLng.latitude, latLng.longitude);
                            //String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
                    Intent intent = new Intent(NuevaIncidenciaActivity.this,InicioActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NuevaIncidenciaActivity.this, "Debe agregar la geolozalización", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnAgregarGeolocalizacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                obtenerUbicacion();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == GALLERY_INTENT && resultCode ==  RESULT_OK){


            uri = data.getData();
            img.setImageURI(uri);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("infoApp", "Permisos concedidos");
            } else {
                Log.e("infoApp", "Permisos no concedidos");
            }
        }
    }


    public void botonAtrasAppBar(MenuItem menu){
        Intent i = new Intent(this, InicioActivity.class);
        startActivity(i);
    }

    public void obtenerUbicacion() {
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        Toast.makeText(NuevaIncidenciaActivity.this, "Se añadió la ubicación", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            fusedLocationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }


}