package com.example.puntoventa.Categorias;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import com.example.puntoventa.R;
import com.example.puntoventa.Unidades.ListaUnidades;
import com.example.puntoventa.Unidades.NuevaUnidad;
import com.example.puntoventa.Unidades.UnidadesMostrar;
import com.example.puntoventa.adaptadores.ListaCategoriasAdapter;
import com.example.puntoventa.adaptadores.ListaUnidadesAdapter;
import com.example.puntoventa.db.DbCategorias;
import com.example.puntoventa.db.DbUnidades;

import java.util.ArrayList;

public class ListaCategorias extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView txtBuscar;
    RecyclerView listaCategoria;
    ArrayList<CategoriasMostrar> listaArrayCategoria;
    FloatingActionButton fabNuevo;
    ListaCategoriasAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categorias);

        txtBuscar = findViewById(R.id.txtBuscar);
        listaCategoria = findViewById(R.id.listaUnidad);
        fabNuevo = findViewById(R.id.favNuevo);
        listaCategoria.setLayoutManager(new LinearLayoutManager(this));
        DbCategorias dbCategorias = new DbCategorias(ListaCategorias.this);
        listaArrayCategoria = new ArrayList<>();
        adapter = new ListaCategoriasAdapter(dbCategorias.leerCategoriasMostrar());
        listaCategoria.setAdapter(adapter);
        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }
    private void nuevoRegistro(){
        Intent intent = new Intent(this, NuevaCategoria.class);
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
