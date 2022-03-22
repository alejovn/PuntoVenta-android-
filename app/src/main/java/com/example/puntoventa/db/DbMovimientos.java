package com.example.puntoventa.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.example.puntoventa.MovimientosP.VMGlobales;
import com.example.puntoventa.Productos.ProductoMostrar;

import java.util.ArrayList;

public class DbMovimientos extends DbHelper{
    Context context;
    public DbMovimientos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<VMGlobales> leerMovimientosMostrar(String estado){
        ArrayList<VMGlobales> lista = new ArrayList<>();
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            VMGlobales producto = null;
            Cursor cursorProductos = null;
            cursorProductos = db.rawQuery("SELECT " +
                    "t_movimientos.fecha, t_movimientos.cantidad, t_productos.nombre," +
                    "t_productos.precioV, t_productos.precioC, t_movimientos.total " +
                    "FROM "+ TABLA_PRODUCTOS + " INNER JOIN " + TABLA_MOVIMIENTOS + " ON t_movimientos.producto_id" +
                    "= t_productos.id WHERE t_movimientos.estado = '"+ estado +"'", null);
            if(cursorProductos.moveToFirst()){
                do {
                    producto = new VMGlobales();
                    producto.setFecha(cursorProductos.getString(0));
                    producto.setCantidad(cursorProductos.getInt(1));
                    producto.setProducto(cursorProductos.getString(2));
                    producto.setVenta(cursorProductos.getDouble(3));
                    producto.setCompra(cursorProductos.getDouble(4));
                    producto.setTotal(cursorProductos.getDouble(5));

                    lista.add(producto);
                }while (cursorProductos.moveToNext());
            }
            cursorProductos.close();

        }catch (Exception e){
            e.toString();
        }
        return lista;
    }
    public int leerStock(){
        int contador=0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorProductos = null;
            cursorProductos = db.rawQuery("SELECT * FROM "+ TABLA_PRODUCTOS + " WHERE cantidad < stock_minimo", null);
            if(cursorProductos.moveToFirst()){
                do {
                    contador++;
                }while (cursorProductos.moveToNext());
            }
            cursorProductos.close();

        }catch (Exception e){
            e.toString();
        }
        return contador;
    }
}
