package com.example.puntoventa.Categorias;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.puntoventa.R;
import com.example.puntoventa.Unidades.EditarUnidad;
import com.example.puntoventa.Unidades.ListaUnidades;
import com.example.puntoventa.Unidades.UnidadesMostrar;
import com.example.puntoventa.Unidades.VerUnidad;
import com.example.puntoventa.db.DbCategorias;
import com.example.puntoventa.db.DbUnidades;

public class VerCategoria extends AppCompatActivity {
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
        btnGuarda.setVisibility(View.INVISIBLE);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        final DbCategorias dbCategorias = new DbCategorias(this);
        categoria = dbCategorias.verCategoria(id);
        if(categoria != null){

            txtNombre.setText(categoria.getNombre());

            txtNombre.setInputType(InputType.TYPE_NULL);
        }
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerCategoria.this, EditarCategoria.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerCategoria.this);
                builder.setMessage("¿Desea eliminar esta categoria?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbCategorias.editarEstadoCategoria(id)){
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }
    private void lista(){
        Intent intent = new Intent(this, ListaCategorias.class);
        startActivity(intent);
    }
}