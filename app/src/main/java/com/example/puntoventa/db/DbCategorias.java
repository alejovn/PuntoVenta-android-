package com.example.puntoventa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import com.example.puntoventa.Categorias.CategoriasMostrar;
import com.example.puntoventa.Productos.NuevoProducto;
import com.example.puntoventa.Unidades.UnidadesMostrar;

import java.util.ArrayList;

public class DbCategorias extends DbHelper{
    Context context;
    public DbCategorias(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertarCategorias(String nombre, int estado){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("estado", estado);
            id = db.insert(TABLA_CATEGORIAS, null, values);
        }catch (Exception e){
            e.toString();
        }
        return id;
    }
    public int idCategoria(String nombre){
        int id=0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorUnidad;
        cursorUnidad = db.rawQuery("SELECT id FROM " + TABLA_CATEGORIAS + " WHERE nombre = '" + nombre + "' LIMIT 1", null);
        if (cursorUnidad.moveToFirst()) {
            id=cursorUnidad.getInt(0);
        }
        return id;
    }
    public String NombreCategoria(int id){
        String nombre="";
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorUnidad;
        cursorUnidad = db.rawQuery("SELECT nombre FROM " + TABLA_CATEGORIAS + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorUnidad.moveToFirst()) {
            nombre=cursorUnidad.getString(0);
        }
        return nombre;
    }
    public ArrayList<CategoriasMostrar> leerCategoriasMostrar(){
        ArrayList<CategoriasMostrar> lista = new ArrayList<>();
        try {
            DbHelper dbHelper = new DbHelper(context);
            NuevoProducto nuevoProducto = new NuevoProducto();
            nuevoProducto.ListaCategorias.clear();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            CategoriasMostrar categoria = null;
            Cursor cursorCategoria = null;
            cursorCategoria = db.rawQuery("SELECT * FROM "+ TABLA_CATEGORIAS, null);
            if(cursorCategoria.moveToFirst()){
                nuevoProducto.ListaCategorias.add("Categoria");
                do {
                    categoria = new CategoriasMostrar();
                    categoria.setId(cursorCategoria.getInt(0));
                    categoria.setNombre(cursorCategoria.getString(1));
                    categoria.setEstado(cursorCategoria.getInt(2));
                    lista.add(categoria);
                    nuevoProducto.ListaCategorias.add(cursorCategoria.getString(1));
                }while (cursorCategoria.moveToNext());
            }
            cursorCategoria.close();

        }catch (Exception e){
            e.toString();
        }
        return lista;
    }
    public CategoriasMostrar verCategoria(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        CategoriasMostrar categoria = null;
        Cursor cursorCategoria;
        cursorCategoria = db.rawQuery("SELECT * FROM " + TABLA_CATEGORIAS + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorCategoria.moveToFirst()) {
            categoria = new CategoriasMostrar();
            categoria.setId(cursorCategoria.getInt(0));
            categoria.setNombre(cursorCategoria.getString(1));
            categoria.setEstado(cursorCategoria.getInt(2));
        }
        cursorCategoria.close();
        return categoria;
    }
    public boolean editarCategoria(int id, String nombre) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_CATEGORIAS + " SET nombre = '" + nombre + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
    public boolean editarEstadoCategoria(int id) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_UNIDADES + " SET estado = " + 0 + " WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
}
