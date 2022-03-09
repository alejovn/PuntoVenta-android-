package com.example.puntoventa.Unidades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.puntoventa.Productos.EditarProducto;
import com.example.puntoventa.Productos.ListaDeProductos;
import com.example.puntoventa.Productos.NuevoProducto;
import com.example.puntoventa.Productos.ProductoMostrar;
import com.example.puntoventa.R;
import com.example.puntoventa.db.DbCategorias;
import com.example.puntoventa.db.DbProductos;
import com.example.puntoventa.db.DbUnidades;

public class VerUnidad extends AppCompatActivity {
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
        final DbUnidades dbUnidades = new DbUnidades(this);
        unidad = dbUnidades.verUnidad(id);
        if(unidad != null){

            txtNombre.setText(unidad.getNombre());
            txtAbreviatura.setText(unidad.getNombre_corto());

            txtNombre.setInputType(InputType.TYPE_NULL);
            txtAbreviatura.setInputType(InputType.TYPE_NULL);
        }
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerUnidad.this, EditarUnidad.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerUnidad.this);
                builder.setMessage("¿Desea eliminar esta unidad?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbUnidades.editarEstadoUnidad(id)){
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
        Intent intent = new Intent(this, ListaUnidades.class);
        startActivity(intent);
    }
}