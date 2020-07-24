package com.example.proyectoappsgrupo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyectoappsgrupo2.activity.InicioActivity;
import com.example.proyectoappsgrupo2.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, InicioActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

    }
}
