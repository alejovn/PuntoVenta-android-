package com.example.puntoventa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.example.puntoventa.Usuarios.UsuarioMostrar;
import com.example.puntoventa.Usuarios.UsuariosLogin;

import java.util.ArrayList;

public class DbUsuarios extends DbHelper {
    Context context;
    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuarios(String nombre, String apellidos, String correo, String contra, String image){

        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellidos", apellidos);
            values.put("correo", correo);
            values.put("contra", contra);
            values.put("imagen", image);

            id = db.insert(TABLA_USERS, null, values);

        }catch (Exception e){
            e.toString();
        }
        return id;
    }

    public ArrayList<UsuariosLogin> leerUsuarios(){
        ArrayList<UsuariosLogin> lista = new ArrayList<>();
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            UsuariosLogin usuario = null;
            Cursor cursorUsuarios = null;

            cursorUsuarios = db.rawQuery("SELECT id, correo, contra FROM "+ TABLA_USERS, null);

            if(cursorUsuarios.moveToFirst()){
                do {
                    usuario = new UsuariosLogin();
                    usuario.setId(cursorUsuarios.getInt(0));
                    usuario.setCorreo(cursorUsuarios.getString(1));
                    usuario.setContra(cursorUsuarios.getString(2));

                    lista.add(usuario);
                }while (cursorUsuarios.moveToNext());
            }
            cursorUsuarios.close();

        }catch (Exception e){
            e.toString();
        }
        return lista;
    }
    public ArrayList<UsuarioMostrar> leerUsuariosMostrar(){
        ArrayList<UsuarioMostrar> lista = new ArrayList<>();
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            UsuarioMostrar usuario = null;
            Cursor cursorUsuarios = null;

            cursorUsuarios = db.rawQuery("SELECT id, nombre, apellidos, correo, imagen FROM "+ TABLA_USERS, null);

            if(cursorUsuarios.moveToFirst()){
                do {
                    usuario = new UsuarioMostrar();

                    usuario.setId(cursorUsuarios.getInt(0));
                    usuario.setNombre(cursorUsuarios.getString(1));
                    usuario.setApellidos(cursorUsuarios.getString(2));
                    usuario.setCorreo(cursorUsuarios.getString(3));
                    usuario.setImagen(cursorUsuarios.getString(4));

                    lista.add(usuario);
                }while (cursorUsuarios.moveToNext());
            }
            cursorUsuarios.close();

        }catch (Exception e){
            e.toString();
        }
        return lista;
    }
    public String[] leerUsuariosMostrarSesion(int id){
        String[] lista = new String[3];
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorUsuarios = null;

            cursorUsuarios = db.rawQuery("SELECT nombre, correo, imagen FROM "+ TABLA_USERS + " WHERE id = "+ id + " LIMIT 1", null);

            if(cursorUsuarios.moveToFirst()){
                do {
                    lista[0]=cursorUsuarios.getString(0);
                    lista[1]=cursorUsuarios.getString(1);
                    lista[2]=cursorUsuarios.getString(2);
                }while (cursorUsuarios.moveToNext());
            }
            cursorUsuarios.close();

        }catch (Exception e){
            e.toString();
        }
        return lista;
    }
    public UsuarioMostrar verContacto(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        UsuarioMostrar contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT id, nombre, apellidos, correo, imagen FROM " + TABLA_USERS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()) {
            contacto = new UsuarioMostrar();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setApellidos(cursorContactos.getString(2));
            contacto.setCorreo(cursorContactos.getString(3));
            contacto.setImagen(cursorContactos.getString(4));

        }

        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String apellidos, String correo, String image) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_USERS + " SET nombre = '" + nombre + "', apellidos = '" + apellidos + "', correo = '" + correo + "', imagen = '" + image + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarContacto(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLA_USERS + " WHERE id = '" + id + "'");
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
