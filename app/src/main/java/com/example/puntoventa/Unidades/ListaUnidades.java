package com.example.puntoventa.Unidades;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import com.example.puntoventa.Productos.NuevoProducto;
import com.example.puntoventa.R;
import com.example.puntoventa.adaptadores.ListaUnidadesAdapter;
import com.example.puntoventa.db.DbUnidades;

import java.util.ArrayList;

public class ListaUnidades extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView txtBuscar;
    RecyclerView listaUnidades;
    ArrayList<UnidadesMostrar> listaArrayUnidades;
    FloatingActionButton fabNuevo;
    ListaUnidadesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_unidades);
        txtBuscar = findViewById(R.id.txtBuscar);
        listaUnidades = findViewById(R.id.listaUnidad);
        fabNuevo = findViewById(R.id.favNuevo);
        listaUnidades.setLayoutManager(new LinearLayoutManager(this));
        DbUnidades dbUnidades = new DbUnidades(ListaUnidades.this);
        listaArrayUnidades = new ArrayList<>();
        adapter = new ListaUnidadesAdapter(dbUnidades.leerUnidadesMostrar());
        listaUnidades.setAdapter(adapter);
        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }
    private void nuevoRegistro(){
        Intent intent = new Intent(this, NuevaUnidad.class);
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