package com.example.puntoventa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.puntoventa.MovimientosP.VMGlobales;
import com.example.puntoventa.adaptadores.DatePickerFecha;
import com.example.puntoventa.db.DbMovimientos;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import harmony.java.awt.Color;

public class Movimientos extends AppCompatActivity {
    String NOMBRE_DIRECTORIO = "MisPDFs";
    String NOMBRE_DOCUMENTO = "Movimientos.pdf";
    public static ArrayList<VMGlobales> MovimientoTemporal = new ArrayList<>();
    public static ArrayList<VMGlobales> lista = new ArrayList<>();
    EditText etPlannedDateI, etPlannedDateF;
    Spinner estado;
    public static String estadoSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        etPlannedDateF = (EditText) findViewById(R.id.etPlannedDateF);
        etPlannedDateI = (EditText) findViewById(R.id.etPlannedDateI);
        estado = (Spinner) findViewById(R.id.spinnerEstado);

        estado.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn,
                                               android.view.View v,
                                               int posicion,
                                               long id) {
                        if(spn.getItemAtPosition(posicion).toString().equals("Entrada")){
                            estadoSpinner=spn.getItemAtPosition(posicion).toString();
                        }else if(spn.getItemAtPosition(posicion).toString().equals("Salida")){
                            estadoSpinner=spn.getItemAtPosition(posicion).toString();
                        }else{
                            estadoSpinner="";
                        }

                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });
        etPlannedDateI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogInicio();
            }
        });
        etPlannedDateF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogFinal();
            }
        });
    }
    private void showDatePickerDialogInicio() {
        DatePickerFecha newFragment = DatePickerFecha.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                etPlannedDateI.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    private void showDatePickerDialogFinal() {
        DatePickerFecha newFragment = DatePickerFecha.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                etPlannedDateF.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void Fecha(View view) throws ParseException {
        if(!etPlannedDateI.getText().equals("") && !etPlannedDateF.getText().equals("")){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = formatter.parse(etPlannedDateI.getText().toString());
            Date endDate = formatter.parse(etPlannedDateF.getText().toString());
            DbMovimientos dbMovimientos = new DbMovimientos(this);
            MovimientoTemporal = dbMovimientos.leerMovimientosMostrar(estadoSpinner);
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);

            VMGlobales producto = null;
            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String Fecha = format1.format(date);
                if(estadoSpinner.equals("")){

                }else{
                    for (int i = 0; i < MovimientoTemporal.size(); i++) {
                        producto = new VMGlobales();
                        if(Fecha.equals(MovimientoTemporal.get(i).getFecha())){
                            System.out.println(MovimientoTemporal.get(i).getProducto());
                            producto.setFecha(MovimientoTemporal.get(i).getFecha());
                            producto.setCantidad(MovimientoTemporal.get(i).getCantidad());
                            producto.setProducto(MovimientoTemporal.get(i).getProducto());
                            producto.setVenta(MovimientoTemporal.get(i).getVenta());
                            producto.setCompra(MovimientoTemporal.get(i).getCompra());
                            producto.setTotal(MovimientoTemporal.get(i).getTotal());
                            producto.setUtilidad((MovimientoTemporal.get(i).getVenta()-MovimientoTemporal.get(i).getCompra())*(MovimientoTemporal.get(i).getCantidad()));
                            lista.add(producto);
                        }
                    }

                }

            }
            crearPDF();
            lista.clear();
        }else{

        }
    }
    public void crearPDF(){
        Document documento = new Document();
        documento.setPageSize(PageSize.A4.rotate());
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO);

            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            System.out.println("hola.... aqui esta el error "+file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);
            documento.open();

            Font fuenteTitle = new Font();
            fuenteTitle.setStyle(Font.NORMAL);
            fuenteTitle.setColor(Color.black);
            fuenteTitle.setSize(10);
            PdfPTable tableNombre = new PdfPTable(1);
            Cell celdaNommbre = new Cell(new Phrase("NOMBRE DEL NEGOCIO",fuenteTitle));
            celdaNommbre.setBackgroundColor(Color.white);
            celdaNommbre.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableNombre.addCell(celdaNommbre.createPdfPCell());
            Cell celdaDireccion = new Cell(new Phrase("LA ISLA, SAN VITO COTO BRUS",fuenteTitle));
            celdaDireccion.setBackgroundColor(Color.white);
            celdaDireccion.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableNombre.addCell(celdaDireccion.createPdfPCell());
            Cell celdaCedula = new Cell(new Phrase("CÉDULA JURIDICA: 114920078",fuenteTitle));
            celdaCedula.setBackgroundColor(Color.white);
            celdaCedula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableNombre.addCell(celdaCedula.createPdfPCell());
            Cell celdaTelefono = new Cell(new Phrase("TELÉFONO: 83138642",fuenteTitle));
            celdaTelefono.setBackgroundColor(Color.white);
            celdaTelefono.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableNombre.addCell(celdaTelefono.createPdfPCell());

            Cell celdaCondicion = new Cell(new Phrase("Condición: Contado",fuenteTitle));
            celdaCondicion.setBackgroundColor(Color.white);
            celdaCondicion.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableNombre.addCell(celdaCondicion.createPdfPCell());

            Cell celdaCaja = new Cell(new Phrase("Caja Nro: 2",fuenteTitle));
            celdaCaja.setBackgroundColor(Color.white);
            celdaCaja.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableNombre.addCell(celdaCaja.createPdfPCell());

            Cell celdaVendedor = new Cell(new Phrase("Vendedor: Alejandro",fuenteTitle));
            celdaVendedor.setBackgroundColor(Color.white);
            celdaVendedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableNombre.addCell(celdaVendedor.createPdfPCell());

            documento.add(tableNombre);
            documento.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(6);
            table.setTotalWidth(600);
            table.setWidths(new int[]{100, 100,100,100, 100,100});

            Cell c1 = new Cell(new Phrase("Fecha",fuenteTitle));
            c1.setBackgroundColor(Color.lightGray);
            c1.setBorder(3);
            c1.setBorderColorBottom(Color.black);
            table.addCell(c1.createPdfPCell());

            Cell c2 = new Cell(new Phrase("Cantidad",fuenteTitle));
            c2.setBackgroundColor(Color.lightGray);
            c2.setBorder(3);
            c2.setBorderColorBottom(Color.black);
            table.addCell(c2.createPdfPCell());

            Cell c3 = new Cell(new Phrase("Producto",fuenteTitle));
            c3.setBackgroundColor(Color.lightGray);
            c3.setBorder(3);
            c3.setBorderColorBottom(Color.black);
            table.addCell(c3.createPdfPCell());

            Cell c4 = new Cell(new Phrase("Precio Venta",fuenteTitle));
            c4.setBackgroundColor(Color.lightGray);
            c4.setBorder(3);
            c4.setBorderColorBottom(Color.black);
            table.addCell(c4.createPdfPCell());

            Cell c5 = new Cell(new Phrase("Total",fuenteTitle));
            c5.setBackgroundColor(Color.lightGray);
            c5.setBorder(3);
            c5.setBorderColorBottom(Color.black);
            table.addCell(c5.createPdfPCell());

            Cell c6 = new Cell(new Phrase("Utilidad",fuenteTitle));
            c6.setBackgroundColor(Color.lightGray);
            c6.setBorder(3);
            c6.setBorderColorBottom(Color.black);
            table.addCell(c6.createPdfPCell());

            Font fuente = new Font();
            double total=0;
            double utilidad=0;
            for (int i = 0; i < lista.size(); i++) {
                fuente.setStyle(Font.NORMAL);
                fuente.setColor(Color.gray);
                fuente.setSize(10);
                Color color = Color.white;
                total=total+lista.get(i).getTotal();
                utilidad=utilidad+lista.get(i).getUtilidad();
                Cell celdaFecha = new Cell(new Phrase(""+lista.get(i).getFecha(),fuente));
                celdaFecha.setBackgroundColor(color);
                table.addCell(celdaFecha.createPdfPCell());
                Cell celdaCantidad = new Cell(new Phrase(""+lista.get(i).getCantidad(),fuente));
                celdaCantidad.setBackgroundColor(color);
                table.addCell(celdaCantidad.createPdfPCell());
                Cell celdaProducto = new Cell(new Phrase(""+lista.get(i).getProducto(),fuente));
                celdaProducto.setBackgroundColor(color);
                table.addCell(celdaProducto.createPdfPCell());

                Cell celdaVenta = new Cell(new Phrase(""+lista.get(i).getVenta(),fuente));
                celdaVenta.setBackgroundColor(color);
                table.addCell(celdaVenta.createPdfPCell());
                Cell celdaTotal = new Cell(new Phrase(""+lista.get(i).getTotal(),fuente));
                celdaTotal.setBackgroundColor(color);
                table.addCell(celdaTotal.createPdfPCell());
                Cell celdaUtilidad = new Cell(new Phrase(""+lista.get(i).getUtilidad(),fuente));
                celdaUtilidad.setBackgroundColor(color);
                table.addCell(celdaUtilidad.createPdfPCell());
            }
            documento.add(table);
            documento.add(new Paragraph("\n"));
            PdfPTable tableTotal = new PdfPTable(6);
            tableTotal.setTotalWidth(600);
            tableTotal.setWidths(new int[]{100, 100, 100, 100, 100, 100});
            Cell celda1 = new Cell(new Phrase("",fuenteTitle));
            celda1.setBackgroundColor(Color.white);
            tableTotal.addCell(celda1.createPdfPCell());
            Cell celda2 = new Cell(new Phrase("",fuenteTitle));
            celda2.setBackgroundColor(Color.white);
            tableTotal.addCell(celda2.createPdfPCell());
            Cell celda3 = new Cell(new Phrase("",fuenteTitle));
            celda3.setBackgroundColor(Color.white);
            tableTotal.addCell(celda3.createPdfPCell());
            Cell celda4 = new Cell(new Phrase("",fuenteTitle));
            celda4.setBackgroundColor(Color.white);
            tableTotal.addCell(celda4.createPdfPCell());
            Cell celdaTotal = new Cell(new Phrase(""+total,fuenteTitle));
            celdaTotal.setBackgroundColor(Color.white);
            tableTotal.addCell(celdaTotal.createPdfPCell());
            Cell celdaUtilidad = new Cell(new Phrase(""+utilidad,fuenteTitle));
            celdaUtilidad.setBackgroundColor(Color.white);
            tableTotal.addCell(celdaUtilidad.createPdfPCell());
            documento.add(tableTotal);

        }catch (DocumentException e){
            System.out.println("Si llego aqui men "+e);
        }catch (IOException e){
            System.out.println("hola "+e);
        }finally {
            documento.close();
        }
        mostrarPDF();
    }
    public File crearFichero(String nombreFichero) {
        File ruta = getRuta();
        File fichero = null;
        if(ruta != null) {
            System.out.println("hola....");
            fichero = new File(ruta, nombreFichero);
        }
        return fichero;
    }
    public File getRuta() {
        File ruta = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ruta = new File(Movimientos.this.getExternalFilesDir(Environment.DIRECTORY_DCIM), NOMBRE_DIRECTORIO);
            if(ruta != null) {
                if(!ruta.mkdirs()) {
                    if(!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }
    public void mostrarPDF() {
        File file = crearFichero(NOMBRE_DOCUMENTO);
        File arch = new File(file.getAbsolutePath());
        Intent intent = new Intent(this, PdfMostrar.class);
        intent.putExtra("Ruta", arch.toString());
        startActivity(intent);
    }
}