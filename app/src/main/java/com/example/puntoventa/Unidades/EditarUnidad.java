package com.example.puntoventa.Unidades;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puntoventa.Productos.EditarProducto;
import com.example.puntoventa.Productos.ListaDeProductos;
import com.example.puntoventa.R;
import com.example.puntoventa.db.DbProductos;
import com.example.puntoventa.db.DbUnidades;

public class EditarUnidad extends AppCompatActivity {
    boolean correcto = false;
    TextView txtNombre, txtAbreviatura;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;
    UnidadesMostrar unidad;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_unidad);

        txtNombre = findViewById(R.id.viewNombreP);
        txtAbreviatura = findViewById(R.id.viewAbreviaturaP);
        btnGuarda = findViewById(R.id.btnGuardaP);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbUnidades dbUnidades = new DbUnidades(EditarUnidad.this);
        unidad = dbUnidades.verUnidad(id);

        if (unidad != null) {
            txtNombre.setText(unidad.getNombre());
            txtAbreviatura.setText(unidad.getNombre_corto());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("") && !txtAbreviatura.getText().toString().equals("")) {
                    correcto = dbUnidades.editarUnidad(id, txtNombre.getText().toString(), txtAbreviatura.getText().toString());
                    if(correcto){
                        Toast.makeText(EditarUnidad.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarUnidad.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarUnidad.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void verRegistro(){
        Intent intent = new Intent(this, ListaUnidades.class);
        startActivity(intent);
    }
}