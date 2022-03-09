package com.example.puntoventa.Productos;

public class ProductoMostrar {
    private int id=0;
    private String nombre = null;
    private String codigo = null;
    private int cantidad = 0;
    private int StockMi = 0;
    private double precioV = 0;
    private double precioC = 0;
    private String descripcion = null;
    private int id_unidad=0;
    private int id_categoria=0;
    private int estado=0;

    public ProductoMostrar() {
    }

    public int getStockMi() {
        return StockMi;
    }

    public void setStockMi(int stockMi) {
        this.StockMi = stockMi;
    }

    public double getPrecioC() {
        return precioC;
    }

    public void setPrecioC(double precioC) {
        this.precioC = precioC;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int contidad) {
        this.cantidad = contidad;
    }

    public double getPrecioV() {
        return precioV;
    }

    public void setPrecioV(double precio) {
        this.precioV = precio;
    }

    public int getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
