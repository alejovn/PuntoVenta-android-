package com.example.puntoventa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 22;
    private static final String DATABASE_NOMBRE = "PuntoVenta.db";
    public  static final String TABLA_USERS = " t_usuarios";
    public  static final String TABLA_UNIDADES = "t_unidades";
    public  static final String TABLA_CATEGORIAS = "t_categorias";
    public  static final String TABLA_PRODUCTOS = "t_productos";
    public  static final String TABLA_MOVIMIENTOS = "t_movimientos";
    public  static final String TABLA_FATURA = "t_factura";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABLA_USERS +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellidos TEXT NOT NULL," +
                "correo TEXT NOT NULL," +
                "contra TEXT NOT NULL," +
                "imagen TEXT)");

        db.execSQL("CREATE TABLE "+ TABLA_PRODUCTOS +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "codigo TEXT," +
                "precioV REAL NOT NULL," +
                "precioC REAL NOT NULL," +
                "descripcion TEXT," +
                "cantidad INTEGER," +
                "stock_minimo INTEGER," +
                "id_unidad INTEGER," +
                "id_categoria INTEGER," +
                "estado INTEGER)");

        db.execSQL("CREATE TABLE "+ TABLA_UNIDADES +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "nombre_corto TEXT," +
                "estado INTEGER)");

        db.execSQL("CREATE TABLE "+ TABLA_CATEGORIAS +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "estado INTEGER)");

        db.execSQL("CREATE TABLE "+ TABLA_MOVIMIENTOS +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "producto_id INTEGER NOT NULL," +
                "factura_id INTEGER NOT NULL," +
                "cantidad INTEGER NOT NULL," +
                "descripcion TEXT," +
                "unidad REAL NOT NULL," +
                "total REAL NOT NULL," +
                "estado TEXT," +
                "fecha TEXT)");

        db.execSQL("CREATE TABLE "+ TABLA_FATURA +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "total REAL NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ TABLA_USERS);
        db.execSQL("DROP TABLE "+ TABLA_PRODUCTOS);
        db.execSQL("DROP TABLE "+ TABLA_UNIDADES);
        db.execSQL("DROP TABLE "+ TABLA_CATEGORIAS);
        db.execSQL("DROP TABLE "+ TABLA_MOVIMIENTOS);
        db.execSQL("DROP TABLE "+ TABLA_FATURA);

        onCreate(db);
    }
}
