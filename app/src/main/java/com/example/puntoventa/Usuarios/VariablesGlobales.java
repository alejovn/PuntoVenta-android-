package com.example.puntoventa.Usuarios;

import java.io.File;

public class VariablesGlobales {

    public static int id=0;
    public static String nombre=null;
    public static String apellidos=null;
    public static String correo=null;
    public static String contra=null;

    public static File fileViejo;
    public static File fileNuevo;

    public VariablesGlobales() {
    }

    public static File getFileViejo() {
        return fileViejo;
    }

    public static void setFileViejo(File fileViejo) {
        VariablesGlobales.fileViejo = fileViejo;
    }

    public static File getFileNuevo() {
        return fileNuevo;
    }

    public static void setFileNuevo(File fileNuevo) {
        VariablesGlobales.fileNuevo = fileNuevo;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        VariablesGlobales.id = id;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String mombre) {
        VariablesGlobales.nombre = mombre;
    }

    public static String getApellidos() {
        return apellidos;
    }

    public static void setApellidos(String apellidos) {
        VariablesGlobales.apellidos = apellidos;
    }

    public static String getCorreo() {
        return correo;
    }

    public static void setCorreo(String correo) {
        VariablesGlobales.correo = correo;
    }

    public static String getContra() {
        return contra;
    }

    public static void setContra(String contra) {
        VariablesGlobales.contra = contra;
    }
}
