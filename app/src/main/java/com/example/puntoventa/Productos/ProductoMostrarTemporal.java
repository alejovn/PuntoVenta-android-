package com.example.puntoventa.Productos;

public class ProductoMostrarTemporal {
    private int id=0;
    private String nombre = null;
    private String codigo = null;
    private int cantidad = 0;
    private int StockMa = 0;
    private int StockMi = 0;
    private double precioV = 0;
    private double precioC = 0;

    private String imagen = null;
    private String descripcion = null;

    public ProductoMostrarTemporal() {
    }

    public int getStockMa() {
        return StockMa;
    }

    public void setStockMa(int stockMa) {
        this.StockMa = stockMa;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
}
