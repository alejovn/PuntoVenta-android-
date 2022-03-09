package com.example.puntoventa.Categorias;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puntoventa.R;
import com.example.puntoventa.Unidades.ListaUnidades;
import com.example.puntoventa.Unidades.NuevaUnidad;
import com.example.puntoventa.db.DbCategorias;
import com.example.puntoventa.db.DbUnidades;
public class NuevaCategoria extends AppCompatActivity {
    TextView txtNombre;
    Button btnGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_categoria);
        txtNombre = findViewById(R.id.viewNombreL);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().equals("")) {
                    DbCategorias dbCategorias = new DbCategorias(NuevaCategoria.this);
                    long id = dbCategorias.insertarCategorias(txtNombre.getText().toString(), 1);
                    if (id > 0) {
                        Toast.makeText(NuevaCategoria.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NuevaCategoria.this, ListaCategorias.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(NuevaCategoria.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NuevaCategoria.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}