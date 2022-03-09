package com.example.puntoventa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PdfMostrar extends AppCompatActivity {
    ProgressBar progressBar;
    PDFView pdfView;
    File url=null;
    String valor="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_mostrar);
        System.out.println("ruta "+getIntent().getExtras().getString("Ruta"));
        valor = getIntent().getExtras().getString("Ruta");
        progressBar = findViewById(R.id.progressBar);
        pdfView = findViewById(R.id.pdf_viewer);


        url = new File(valor);
        InputStream targetStream=null;
        try {
            targetStream = new FileInputStream(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pdfView.fromStream(targetStream).load();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            if(url.getName().equals("Movimientos.pdf")){
                Intent intent = new Intent(PdfMostrar.this, Movimientos.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(PdfMostrar.this, Inicio.class);
                startActivity(intent);
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}