package com.example.puntoventa.Factura;

public class VGFactura {
    public static int id=0;
    public static int cantidad=0;
    public static String codigo="";
    public static String descripcion="";
    public static double unidad=0;
    public static double total=0;
    public static String fecha="";
    public static String estado="";

    public VGFactura() {
    }

    public static String getCodigo() {
        return codigo;
    }

    public static void setCodigo(String codigo) {
        VGFactura.codigo = codigo;
    }

    public static String getFecha() {
        return fecha;
    }

    public static void setFecha(String fecha) {
        VGFactura.fecha = fecha;
    }

    public static String getEstado() {
        return estado;
    }

    public static void setEstado(String estado) {
        VGFactura.estado = estado;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        VGFactura.id = id;
    }

    public static int getCantidad() {
        return cantidad;
    }

    public static void setCantidad(int cantidad) {
        VGFactura.cantidad = cantidad;
    }

    public static String getDescripcion() {
        return descripcion;
    }

    public static void setDescripcion(String decripcion) {
        VGFactura.descripcion = decripcion;
    }

    public static double getUnidad() {
        return unidad;
    }

    public static void setUnidad(double unidad) {
        VGFactura.unidad = unidad;
    }

    public static double getTotal() {
        return total;
    }

    public static void setTotal(double total) {
        VGFactura.total = total;
    }
}
