package com.example.puntoventa.Productos;

import java.io.File;

public class VPGlobales {
    public static File fileNuevo;
    public static File fileViejo;

    public VPGlobales() {
    }

    public static File getFileNuevo() {
        return fileNuevo;
    }

    public static void setFileNuevo(File fileNuevo) {
        VPGlobales.fileNuevo = fileNuevo;
    }

    public static File getFileViejo() {
        return fileViejo;
    }

    public static void setFileViejo(File fileViejo) {
        VPGlobales.fileViejo = fileViejo;
    }
}
