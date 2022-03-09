package com.example.puntoventa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.example.puntoventa.Factura.FacturaMostrar;

import java.util.ArrayList;

public class DbFactura extends DbHelper{
    Context context;
    public DbFactura(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarMovimiento(int producto_id, int factura_id, int cantidad, double unidad, String descripcion,
                                  double total, String fecha, String estado){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("producto_id", producto_id);
            values.put("factura_id", factura_id);
            values.put("cantidad", cantidad);
            values.put("descripcion", descripcion);
            values.put("unidad", unidad);
            values.put("total", total);
            values.put("estado", estado);
            values.put("fecha", fecha);

            id = db.insert(TABLA_MOVIMIENTOS, null, values);

        }catch (Exception e){
            e.toString();
        }
        return id;
    }
    public int obtenerIdFactura(){
        int id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorFacturas = null;
            cursorFacturas = db.rawQuery("SELECT * FROM "+ TABLA_FATURA, null);
            if(cursorFacturas.moveToLast()){
                    id=cursorFacturas.getInt(0);
            }
            cursorFacturas.close();
        }catch (Exception e){

        }
        return id;
    }
    public long insertarFacturas(double total){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("total", total);

            id = db.insert(TABLA_FATURA, null, values);

        }catch (Exception e){
            e.toString();
        }
        return id;
    }
    public ArrayList<FacturaMostrar> leerFacturaMostrar(){
        ArrayList<FacturaMostrar> lista = new ArrayList<>();
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            FacturaMostrar factura = null;
            Cursor cursorFacturas = null;
            cursorFacturas = db.rawQuery("SELECT * FROM "+ TABLA_MOVIMIENTOS, null);
            if(cursorFacturas.moveToFirst()){
                do {
                    factura = new FacturaMostrar();
                    factura.setId(cursorFacturas.getInt(0));
                    factura.setCantidad(cursorFacturas.getInt(2));
                    factura.setDescripcion(cursorFacturas.getString(3));
                    factura.setUnidad(cursorFacturas.getDouble(4));
                    factura.setTotal(cursorFacturas.getDouble(5));
                    lista.add(factura);
                }while (cursorFacturas.moveToNext());
            }
            cursorFacturas.close();

        }catch (Exception e){
            e.toString();
        }
        return lista;
    }
}
