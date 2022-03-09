package com.example.puntoventa.Unidades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puntoventa.Productos.ListaDeProductos;
import com.example.puntoventa.Productos.NuevoProducto;
import com.example.puntoventa.Productos.VPGlobales;
import com.example.puntoventa.R;
import com.example.puntoventa.db.DbProductos;
import com.example.puntoventa.db.DbUnidades;

public class NuevaUnidad extends AppCompatActivity {
    TextView txtNombre, txtNombre_corto;
    Button btnGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_unidad);
        txtNombre = findViewById(R.id.viewNombreL);
        txtNombre_corto = findViewById(R.id.viewNombreCortoL);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().equals("") && !txtNombre_corto.getText().toString().equals("")) {
                    DbUnidades dbUnidades = new DbUnidades(NuevaUnidad.this);
                    long id = dbUnidades.insertarUnidad(txtNombre.getText().toString(),
                            txtNombre_corto.getText().toString(), 1);
                    if (id > 0) {
                        Toast.makeText(NuevaUnidad.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NuevaUnidad.this, ListaUnidades.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(NuevaUnidad.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NuevaUnidad.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}