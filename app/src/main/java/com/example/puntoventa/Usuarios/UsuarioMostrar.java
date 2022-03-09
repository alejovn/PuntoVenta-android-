package com.example.puntoventa.Usuarios;

public class UsuarioMostrar {
    private int id=0;
    private String nombre=null;
    private String apellidos=null;
    private String correo=null;
    private String imagen=null;

    public UsuarioMostrar() {
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

    public void setNombre(String mombre) {
        this.nombre = mombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


}
