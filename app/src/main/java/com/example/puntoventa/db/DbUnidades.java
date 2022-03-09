package com.example.puntoventa.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.example.puntoventa.Productos.NuevoProducto;
import com.example.puntoventa.Unidades.UnidadesMostrar;
import java.util.ArrayList;
public class DbUnidades extends DbHelper{
    Context context;
    public DbUnidades(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertarUnidad(String nombre, String nombre_corto, int estado){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("nombre_corto", nombre_corto);
            values.put("estado", estado);
            id = db.insert(TABLA_UNIDADES, null, values);
        }catch (Exception e){
            e.toString();
        }
        return id;
    }
    public ArrayList<UnidadesMostrar> leerUnidadesMostrar(){
        ArrayList<UnidadesMostrar> lista = new ArrayList<>();

        try {
            DbHelper dbHelper = new DbHelper(context);
            NuevoProducto nuevoProducto = new NuevoProducto();
            nuevoProducto.ListaUnidades.clear();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            UnidadesMostrar unidad = null;

            Cursor cursorUnidades = null;
            cursorUnidades = db.rawQuery("SELECT * FROM "+ TABLA_UNIDADES + " WHERE estado = 1 ", null);
            if(cursorUnidades.moveToFirst()){
                nuevoProducto.ListaUnidades.add("Unidad");
                do {
                    unidad = new UnidadesMostrar();
                    unidad.setId(cursorUnidades.getInt(0));
                    unidad.setNombre(cursorUnidades.getString(1));
                    unidad.setNombre_corto(cursorUnidades.getString(2));
                    unidad.setEstado(cursorUnidades.getInt(3));
                    lista.add(unidad);
                    nuevoProducto.ListaUnidades.add(cursorUnidades.getString(1));
                }while (cursorUnidades.moveToNext());
            }
            cursorUnidades.close();

        }catch (Exception e){
            e.toString();
        }
        return lista;
    }
    public UnidadesMostrar verUnidad(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        UnidadesMostrar unidad = null;
        Cursor cursorUnidad;
        cursorUnidad = db.rawQuery("SELECT * FROM " + TABLA_UNIDADES + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorUnidad.moveToFirst()) {
            unidad = new UnidadesMostrar();
            unidad.setId(cursorUnidad.getInt(0));
            unidad.setNombre(cursorUnidad.getString(1));
            unidad.setNombre_corto(cursorUnidad.getString(2));
            unidad.setEstado(cursorUnidad.getInt(3));
        }
        cursorUnidad.close();
        return unidad;
    }
    public int idUnidad(String nombre){
        int id=0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorUnidad;
        cursorUnidad = db.rawQuery("SELECT id FROM " + TABLA_UNIDADES + " WHERE nombre = '" + nombre + "' LIMIT 1", null);
        if (cursorUnidad.moveToFirst()) {
            id=cursorUnidad.getInt(0);
        }
        return id;
    }
    public String nombreUnidad(int id){
        String nombre="";
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorUnidad;
        cursorUnidad = db.rawQuery("SELECT nombre FROM " + TABLA_UNIDADES + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorUnidad.moveToFirst()) {
            nombre=cursorUnidad.getString(0);
        }
        return nombre;
    }
    public boolean editarUnidad(int id, String nombre, String nombre_corto) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_UNIDADES + " SET nombre = '" + nombre + "', " +
                    "nombre_corto = '" + nombre_corto + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
    public boolean editarEstadoUnidad(int id) {
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
