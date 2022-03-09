package com.example.puntoventa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.example.puntoventa.Factura.FacturaTemporal;
import com.example.puntoventa.Productos.ProductoMostrar;
import com.example.puntoventa.Factura.VGFactura;
import com.example.puntoventa.Productos.ProductoMostrarTemporal;

import java.util.ArrayList;

public class DbProductos extends DbHelper{
    public static ArrayList<FacturaTemporal> FacTemporal = new ArrayList<>();
    public static ArrayList<ProductoMostrarTemporal> ProTemporal = new ArrayList<>();
    Context context;
    public DbProductos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarProductos(String nombre, String codigo, double precioV,
                                  double precioC, String descripcion, int cantidad,
                                  int stockMinimo, int id_unidad, int id_categoria, int estado){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("codigo", codigo);
            values.put("precioV", precioV);
            values.put("precioC", precioC);
            values.put("descripcion", descripcion);
            values.put("cantidad", cantidad);
            values.put("stock_minimo", stockMinimo);
            values.put("id_unidad", id_unidad);
            values.put("id_categoria", id_categoria);
            values.put("estado", estado);

            id = db.insert(TABLA_PRODUCTOS, null, values);

        }catch (Exception e){
            e.toString();
        }
        return id;
    }

    public ArrayList<ProductoMostrar> leerProductosMostrar(){
        ArrayList<ProductoMostrar> lista = new ArrayList<>();
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ProductoMostrar producto = null;
            Cursor cursorProductos = null;
            cursorProductos = db.rawQuery("SELECT * FROM "+ TABLA_PRODUCTOS + " WHERE estado = "+ 1, null);
            if(cursorProductos.moveToFirst()){
                do {
                    producto = new ProductoMostrar();
                    producto.setId(cursorProductos.getInt(0));
                    producto.setNombre(cursorProductos.getString(1));
                    producto.setCodigo(cursorProductos.getString(2));
                    producto.setPrecioV(cursorProductos.getDouble(3));
                    producto.setPrecioC(cursorProductos.getDouble(4));
                    producto.setDescripcion(cursorProductos.getString(5));
                    producto.setCantidad(cursorProductos.getInt(6));
                    producto.setStockMi(cursorProductos.getInt(7));

                    lista.add(producto);
                }while (cursorProductos.moveToNext());
            }
            cursorProductos.close();

        }catch (Exception e){
            e.toString();
        }
        return lista;
    }
    public ArrayList<ProductoMostrar> leerProductosMostrarStock(){
        ArrayList<ProductoMostrar> lista = new ArrayList<>();
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ProductoMostrar producto = null;
            Cursor cursorProductos = null;
            cursorProductos = db.rawQuery("SELECT * FROM "+ TABLA_PRODUCTOS + " WHERE cantidad < stock_minimo AND estado = "+ 1, null);
            if(cursorProductos.moveToFirst()){
                do {
                    producto = new ProductoMostrar();
                    producto.setId(cursorProductos.getInt(0));
                    producto.setNombre(cursorProductos.getString(1));
                    producto.setCodigo(cursorProductos.getString(2));
                    producto.setPrecioV(cursorProductos.getDouble(3));
                    producto.setPrecioC(cursorProductos.getDouble(4));
                    producto.setDescripcion(cursorProductos.getString(5));
                    producto.setCantidad(cursorProductos.getInt(6));
                    producto.setStockMi(cursorProductos.getInt(7));

                    lista.add(producto);
                }while (cursorProductos.moveToNext());
            }
            cursorProductos.close();

        }catch (Exception e){
            e.toString();
        }
        return lista;
    }
    public ArrayList<FacturaTemporal> leerFacturaTemporalMostrar(){
        FacturaTemporal factura = new FacturaTemporal();
        VGFactura vgFactura = new VGFactura();
        factura.setId(vgFactura.getId());
        factura.setCantidad(vgFactura.getCantidad());
        factura.setDescripcion(vgFactura.getDescripcion());
        factura.setCodigo(vgFactura.getCodigo());
        factura.setUnidad(vgFactura.getUnidad());
        factura.setTotal(vgFactura.getCantidad()*vgFactura.getUnidad());
        FacTemporal.add(factura);
        return FacTemporal;
    }

    public ProductoMostrar verProducto(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ProductoMostrar producto = null;
        Cursor cursorProductos;
        cursorProductos = db.rawQuery("SELECT * FROM " + TABLA_PRODUCTOS + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorProductos.moveToFirst()) {
            producto = new ProductoMostrar();
            producto.setId(cursorProductos.getInt(0));
            producto.setNombre(cursorProductos.getString(1));
            producto.setCodigo(cursorProductos.getString(2));
            producto.setPrecioV(cursorProductos.getDouble(3));
            producto.setPrecioC(cursorProductos.getDouble(4));
            producto.setDescripcion(cursorProductos.getString(5));
            producto.setCantidad(cursorProductos.getInt(6));
            producto.setStockMi(cursorProductos.getInt(7));
            producto.setId_unidad(cursorProductos.getInt(8));
            producto.setId_categoria(cursorProductos.getInt(9));
            producto.setEstado(cursorProductos.getInt(10));
        }
        cursorProductos.close();
        return producto;
    }
    public boolean editarProducto(int id, String nombre, String codigo, double precioV,
                                  double precioC, String descrip, int cantidad,
                                  int stockMinimo, int id_unidad, int id_categoria) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_PRODUCTOS + " SET nombre = '" + nombre + "', codigo = '" + codigo + "', " +
                    "precioV = '" + precioV + "', precioC = '" + precioC+ "', descripcion = '" + descrip + "', " +
                    " stock_minimo = '" + stockMinimo + "',id_unidad = '" + id_unidad + "',cantidad = '" + cantidad + "', " +
                    "id_categoria = '" + id_categoria + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
    public boolean editarCantidadProducto(int id, int cantidad) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_PRODUCTOS + " SET cantidad = '" + cantidad + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
    public boolean eliminarProducto(int id) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_PRODUCTOS + " SET estado = "+ 0 +" WHERE id = '" + id + "'");
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
