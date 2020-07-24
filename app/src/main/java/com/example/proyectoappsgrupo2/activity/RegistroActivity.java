package com.example.proyectoappsgrupo2.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoappsgrupo2.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private EditText correoView;
    private EditText codigoPucpView;
    private EditText contrasenaView;
    private String correo;
    private String codigoPucp;
    private String contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button registroBtn = (Button) findViewById(R.id.RegistrarseBoton);
        correoView = (EditText) findViewById(R.id.correoPucpRegistro);
        codigoPucpView = (EditText) findViewById(R.id.codigoPucpRegistro);
        contrasenaView = (EditText) findViewById(R.id.contraseniaRegistro);

        registroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = correoView.getText().toString();
                codigoPucp = codigoPucpView.getText().toString();
                contrasena = contrasenaView.getText().toString();

                if (contrasena.isEmpty()){
                    contrasenaView.setError("Ingrese una contraseña");
                } else if (codigoPucp.isEmpty()){
                    codigoPucpView.setError("Ingrese un código PUCP");
                } else if (correo.isEmpty()){
                    correoView.setError("ingrese un correo PUCP");
                } else {
                    registrarUsuario();
                }

            }
        });

    }


    public void registrarUsuario(){

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegistroActivity.this, "Registro exitoso. Revise su correo para verificar su cuenta", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(RegistroActivity.this, "Ocurrió un error en el envío de correo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegistroActivity.this, "Ocurrió un error en el registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}