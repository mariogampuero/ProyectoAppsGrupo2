package com.example.proyectoappsgrupo2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoappsgrupo2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText correoView;
    private EditText contrasenaView;
    private String correo;
    private String contrasena;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        correoView = (EditText) findViewById(R.id.correoPucp);
        contrasenaView = (EditText) findViewById(R.id.contrasenia);
        Button loginBtn = (Button) findViewById(R.id.IngresarBoton);
        Button registroBtn = (Button) findViewById(R.id.IrARegistroBoton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = correoView.getText().toString();
                contrasena = contrasenaView.getText().toString();

                if (correo.isEmpty()){
                    correoView.setError("Debe ingresar un correo");
                } else if(contrasena.isEmpty()){
                    contrasenaView.setError("Debe ingresar una contraseña");
                } else {
                    firebaseAuth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this, InicioActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, "Debe verificar su correo.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Ocurrió un error en el inicio de sesión.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        registroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });


    }
}