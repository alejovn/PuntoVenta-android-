package com.example.puntoventa.Usuarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.puntoventa.MainActivity;
import com.example.puntoventa.R;
import com.example.puntoventa.Usuarios.NombreApellidos;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void regreso(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void siguiente(View view){
        Intent intent = new Intent(this, NombreApellidos.class);
        startActivity(intent);
    }

}