package com.example.puntoventa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puntoventa.Categorias.ListaCategorias;
import com.example.puntoventa.Productos.ListaDeProductos;
import com.example.puntoventa.Unidades.ListaUnidades;
import com.example.puntoventa.Usuarios.ListaUsuarios;
import com.example.puntoventa.adaptadores.ListaInventarioAdapter;
import com.example.puntoventa.db.DbMovimientos;
import com.example.puntoventa.db.DbProductos;

public class Principal extends AppCompatActivity {
    TextView notificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                                Caja();
                                break;
                            case R.id.action_schedules:
                                Productos();
                                break;
                            case R.id.action_music:
                                Usuarios();
                                break;
                        }
                        return false;
                    }
                });
        notificacion = findViewById(R.id.notificacion);
        DbMovimientos dbMovimientos = new DbMovimientos(this);
        int valor = dbMovimientos.leerStock();
        notificacion.setText(String.valueOf(valor));
    }
    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicio, menu);
        if(menu instanceof MenuBuilder){

            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemUsuarios:
                Usuarios();
                return true;
            case R.id.itemUnidades:
                Unidades();
                return true;
            case R.id.itemCategoria:
                Categoria();
                return true;
            case R.id.itemProductos:
                Productos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void Usuarios() {
        Intent intent = new Intent(this, ListaUsuarios.class);
        startActivity(intent);
    }
    public void Unidades() {
        Intent intent = new Intent(this, ListaUnidades.class);
        startActivity(intent);
    }
    public void Categoria() {
        Intent intent = new Intent(this, ListaCategorias.class);
        startActivity(intent);
    }
    public void Productos() {
        Intent intent = new Intent(this, ListaDeProductos.class);
        intent.putExtra("Activity", "Productos");
        startActivity(intent);
    }
    public void Caja() {
        Intent intent = new Intent(this, Inicio.class);
        startActivity(intent);
    }

    public void Stock(View view){
        Intent intent = new Intent(this, ListaDeProductos.class);
        intent.putExtra("Activity", "Movimientos");
        startActivity(intent);

    }
    public void Movimiento(View view){
        Intent intent = new Intent(this, Movimientos.class);
        startActivity(intent);
    }
    public void Clientes(View view){
        Toast.makeText(Principal.this,"Clientes",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            MainActivity mainActivity = new MainActivity();
            mainActivity.bandera=false;
            Intent intent = new Intent(Principal.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}