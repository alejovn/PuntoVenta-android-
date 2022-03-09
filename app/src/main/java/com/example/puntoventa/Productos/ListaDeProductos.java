package com.example.puntoventa.Productos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import com.example.puntoventa.R;
import com.example.puntoventa.adaptadores.ListaProductosAdapter;
import com.example.puntoventa.db.DbProductos;

import java.util.ArrayList;

public class ListaDeProductos extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView txtBuscar;
    RecyclerView listaProductos;
    ArrayList<ProductoMostrar> listaArrayProductos;
    FloatingActionButton fabNuevo;
    ListaProductosAdapter adapter;
    String valor="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_productos);

        txtBuscar = findViewById(R.id.txtBuscar);
        listaProductos = findViewById(R.id.listaProductos);
        fabNuevo = findViewById(R.id.favNuevo);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        DbProductos dbProductos = new DbProductos(ListaDeProductos.this);

        listaArrayProductos = new ArrayList<>();
        valor = getIntent().getExtras().getString("Activity");
        if(valor.equals("Movimientos")){
            adapter = new ListaProductosAdapter(dbProductos.leerProductosMostrarStock());
            listaProductos.setAdapter(adapter);
            fabNuevo.setVisibility(View.INVISIBLE);
        }else{
            adapter = new ListaProductosAdapter(dbProductos.leerProductosMostrar());
            listaProductos.setAdapter(adapter);
        }
        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }

    private void nuevoRegistro(){
        Intent intent = new Intent(this, NuevoProducto.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}