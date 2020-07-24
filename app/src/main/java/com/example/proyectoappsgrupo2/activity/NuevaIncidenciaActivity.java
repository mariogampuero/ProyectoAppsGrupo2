package com.example.proyectoappsgrupo2.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
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
                final String autor = "Pepito";
                final int estado = 0;
                final double lat = 13.12;
                final double lon = 15.18;
                final String ImageUploadId = databaseReference.push().getKey();
                //StorageReference filePath = storageReference.child("fotos").child(uri.getLastPathSegment());
                StorageReference filePath = storageReference.child("fotos").child(ImageUploadId);

                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String descri = descripcion.getText().toString().trim();
                        String title = titulo.getText().toString().trim();
                        Toast.makeText(NuevaIncidenciaActivity.this, "Se subio la incidencia exitosamente", Toast.LENGTH_SHORT).show();
                        Incidencia imageUploadInfo = new Incidencia(taskSnapshot.getUploadSessionUri().toString(), descri, title, autor, estado, lat, lon );
                        //String ImageUploadId = databaseReference.push().getKey();
                        databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                    }
                });
                Intent intent = new Intent(NuevaIncidenciaActivity.this,InicioActivity.class);
                startActivity(intent);
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

    public void botonAtrasAppBar(MenuItem menu){
        Intent i = new Intent(this, InicioActivity.class);
        startActivity(i);
    }
}