package com.example.puntoventa.Categorias;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puntoventa.R;
import com.example.puntoventa.Unidades.EditarUnidad;
import com.example.puntoventa.Unidades.ListaUnidades;
import com.example.puntoventa.Unidades.UnidadesMostrar;
import com.example.puntoventa.db.DbCategorias;
import com.example.puntoventa.db.DbUnidades;

public class EditarCategoria extends AppCompatActivity {
    boolean correcto = false;
    TextView txtNombre;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;
    CategoriasMostrar categoria;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_categoria);

        txtNombre = findViewById(R.id.viewNombreP);
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

        final DbCategorias dbCategorias = new DbCategorias(EditarCategoria.this);
        categoria = dbCategorias.verCategoria(id);

        if (categoria != null) {
            txtNombre.setText(categoria.getNombre());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("")) {
                    correcto = dbCategorias.editarCategoria(id, txtNombre.getText().toString());
                    if(correcto){
                        Toast.makeText(EditarCategoria.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarCategoria.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarCategoria.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void verRegistro(){
        Intent intent = new Intent(this, ListaCategorias.class);
        startActivity(intent);
    }
}