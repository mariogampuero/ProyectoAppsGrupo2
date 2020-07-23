package com.example.proyectoappsgrupo2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.proyectoappsgrupo2.MainActivity;
import com.example.proyectoappsgrupo2.R;

public class NuevaIncidenciaActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbarnuevaincidencia,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_incidencia);
    }

    public void botonAtrasAppBar(MenuItem menu){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}