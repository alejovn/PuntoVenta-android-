package com.example.puntoventa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.puntoventa.Factura.FacturaMostrar;
import com.example.puntoventa.Productos.Capture;
import com.example.puntoventa.Productos.ProductoMostrar;
import com.example.puntoventa.Factura.VGFactura;
import com.example.puntoventa.adaptadores.ListaFacturaTemporalAdapter;
import com.example.puntoventa.adaptadores.ListaInventarioAdapter;
import com.example.puntoventa.db.DbFactura;
import com.example.puntoventa.db.DbProductos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import harmony.java.awt.Color;

public class Inicio extends AppCompatActivity implements SearchView.OnQueryTextListener{
    EditText CantidadComprar, TotalPagar, Efectivo;
    SearchView txtBuscar;
    RecyclerView listaInventario, listaFactura;
    ArrayList<ProductoMostrar> listaArrayInventario;
    ArrayList<FacturaMostrar> listaArrayFactura;
    ListaInventarioAdapter adapter;
    ListaFacturaTemporalAdapter adapterF;
    ProductoMostrar producto;
    public static double totalPagar=0;
    public static int cantidadActual;
    public static boolean bandera = false;
    String NOMBRE_DIRECTORIO = "MisPDFs";
    String NOMBRE_DOCUMENTO = "MiPDF.pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        listaFactura = findViewById(R.id.listaFactura);
        TotalPagar = findViewById(R.id.TotalPagar);
        TotalPagar.setInputType(InputType.TYPE_NULL);
        Efectivo = findViewById(R.id.Efectivo);
        CantidadComprar = findViewById(R.id.CantidadComprar);
        txtBuscar = findViewById(R.id.txtBuscar);
        listaInventario = findViewById(R.id.listaInventario);
        listaInventario.setLayoutManager(new LinearLayoutManager(this));
        final DbProductos dbProductos = new DbProductos(Inicio.this);
        listaArrayInventario = new ArrayList<>();
        listaArrayFactura = new ArrayList<>();
        adapter = new ListaInventarioAdapter(dbProductos.leerProductosMostrar());
        listaInventario.setAdapter(adapter);
        txtBuscar.setOnQueryTextListener(this);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemActualizar:
                actualizar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void actualizar(){
        final DbProductos dbProductos = new DbProductos(Inicio.this);
        adapter = new ListaInventarioAdapter(dbProductos.leerProductosMostrar());
        listaInventario.setAdapter(adapter);
    }

    public void Cargar (int id){
        VGFactura factura = new VGFactura();
        factura.setId(id);
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s,bandera);
        return false;
    }
    public void Entrada(View view){
        if(CantidadComprar.getText().toString().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Llenar campo cantidad")
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            VGFactura fact = new VGFactura();
            DbProductos dbProductos = new DbProductos(Inicio.this);
            producto = dbProductos.verProducto(fact.getId());
            if(producto != null){
                fact.setCantidad(Integer.parseInt(CantidadComprar.getText().toString()));
                fact.setUnidad(producto.getPrecioV());
                fact.setDescripcion(producto.getDescripcion());
                fact.setCodigo(producto.getCodigo());
                cantidadActual = producto.getCantidad();
            }
            cantidadActual=cantidadActual+fact.getCantidad();
            double total=0;
            total=(fact.getCantidad()*fact.getUnidad());
            fact.setTotal(total);
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            fact.setFecha(timeStamp);
            fact.setEstado("Entrada");
            boolean correcto = dbProductos.editarCantidadProducto(fact.getId(), cantidadActual);
            if(correcto){
                Toast.makeText(Inicio.this, "CANTIDAD MODIFICADA", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Inicio.this, "ERROR AL MODIFICAR CANTIDAD", Toast.LENGTH_LONG).show();
            }
            CantidadComprar.setText("");
            adapter = new ListaInventarioAdapter(dbProductos.leerProductosMostrar());
            listaInventario.setAdapter(adapter);
            final DbFactura dbFactura = new DbFactura(Inicio.this);
            long idObtener = dbFactura.insertarMovimiento(fact.getId(), 0,
            fact.getCantidad(), fact.getUnidad(), fact.getDescripcion(), fact.getTotal(), fact.getFecha(), fact.getEstado());
            if (idObtener > 0) {
                Toast.makeText(Inicio.this, "MOVIMIENTOS GUARDADOS", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(Inicio.this, "ERROR AL GUARDAR MOVIMIENTOS", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void Facturar(View view) {
        if(CantidadComprar.getText().toString().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Llenar campo cantidad")
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            VGFactura fact = new VGFactura();
            DbProductos dbProductos = new DbProductos(Inicio.this);
            producto = dbProductos.verProducto(fact.getId());
            if(producto != null){
                fact.setCantidad(Integer.parseInt(CantidadComprar.getText().toString()));
                fact.setUnidad(producto.getPrecioV());
                fact.setDescripcion(producto.getDescripcion());
                fact.setCodigo(producto.getCodigo());
                cantidadActual = producto.getCantidad();
            }
            cantidadActual=cantidadActual-fact.getCantidad();
            double total=0;
            total=(fact.getCantidad()*fact.getUnidad());
            totalPagar=totalPagar+total;
            TotalPagar.setText(String.valueOf(totalPagar));
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            fact.setFecha(timeStamp);
            fact.setEstado("Salida");
            boolean correcto = dbProductos.editarCantidadProducto(fact.getId(), cantidadActual);
            if(correcto){
                Toast.makeText(Inicio.this, "CANTIDAD MODIFICADA", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Inicio.this, "ERROR AL MODIFICAR CANTIDAD", Toast.LENGTH_LONG).show();
            }
            adapter = new ListaInventarioAdapter(dbProductos.leerProductosMostrar());
            listaInventario.setAdapter(adapter);
            listaFactura.setLayoutManager(new LinearLayoutManager(this));
            adapterF = new ListaFacturaTemporalAdapter(dbProductos.leerFacturaTemporalMostrar());
            listaFactura.setAdapter(adapterF);
            CantidadComprar.setText("");
        }
    }
    public void Cobrar(View view){
        if(Efectivo.getText().toString().equals("") || TotalPagar.getText().toString().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Llenar campo efectivo y total")
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            if(Double.parseDouble(Efectivo.getText().toString())>=totalPagar) {
                double vuelto = Double.parseDouble(Efectivo.getText().toString()) - totalPagar;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Su cambio es: ₡" + vuelto)
                        .setPositiveButton("Cobrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                VGFactura fact = new VGFactura();
                                final DbFactura dbFactura = new DbFactura(Inicio.this);
                                DbProductos dbProductos = new DbProductos(Inicio.this);
                                //Insertar una factura
                                long idObFactura = dbFactura.insertarFacturas(totalPagar);
                                if (idObFactura > 0) {
                                    Toast.makeText(Inicio.this, "FACTURA GUARDADA", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Inicio.this, "ERROR AL GUARDAR FACTURA", Toast.LENGTH_LONG).show();
                                }
                                int idFactura = dbFactura.obtenerIdFactura();//obtener el id factura
                                //Insertar un movimiento
                                for (int i = 0; i < dbProductos.FacTemporal.size(); i++) {
                                    long idObtener = dbFactura.insertarMovimiento(dbProductos.FacTemporal.get(i).getId(), idFactura,
                                            dbProductos.FacTemporal.get(i).getCantidad(), dbProductos.FacTemporal.get(i).getUnidad(),
                                            dbProductos.FacTemporal.get(i).getDescripcion(), dbProductos.FacTemporal.get(i).getTotal(),
                                            fact.getFecha(), fact.getEstado());
                                    if (idObtener > 0) {
                                        Toast.makeText(Inicio.this, "MOVIMIENTOS GUARDADOS", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Inicio.this, "ERROR AL GUARDAR MOVIMIENTOS", Toast.LENGTH_LONG).show();
                                    }
                                }
                                crearPDF();
                                dbProductos.FacTemporal.clear();
                                totalPagar = 0;
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Falta efectivo")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        }
    }
    public void crearPDF(){
        Rectangle pageSize = new Rectangle(250f, 400f); //ancho y alto
        Document documento = new Document(pageSize,0,0,20,20);
        VGFactura fact = new VGFactura();
        final DbFactura dbFactura = new DbFactura(Inicio.this);
        DbProductos dbProductos = new DbProductos(Inicio.this);
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter.getInstance(documento,ficheroPDF).setInitialLeading(20);
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
            PdfPTable table = new PdfPTable(3);
            table.setTotalWidth(500);
            table.setWidths(new int[]{80, 280,140});
            Cell c1 = new Cell(new Phrase("Cant",fuenteTitle));
            c1.setBackgroundColor(Color.lightGray);
            c1.setBorder(3);
            c1.setBorderColorBottom(Color.black);
            table.addCell(c1.createPdfPCell());
            Cell c2 = new Cell(new Phrase("Descripción",fuenteTitle));
            c2.setBackgroundColor(Color.lightGray);
            c2.setBorder(3);
            c2.setBorderColorBottom(Color.black);
            table.addCell(c2.createPdfPCell());
            Cell c3 = new Cell(new Phrase("Total",fuenteTitle));
            c3.setBackgroundColor(Color.lightGray);
            c3.setBorder(3);
            c3.setBorderColorBottom(Color.black);
            table.addCell(c3.createPdfPCell());
            int idFactura = dbFactura.obtenerIdFactura();//obtener el id factura
            Font fuente = new Font();
            for (int i = 0; i < dbProductos.FacTemporal.size(); i++) {
                fuente.setStyle(Font.NORMAL);
                fuente.setColor(Color.gray);
                fuente.setSize(10);
                Color color = Color.white;
                Cell celdaCantidad = new Cell(new Phrase(""+dbProductos.FacTemporal.get(i).getCantidad(),fuente));
                celdaCantidad.setBackgroundColor(color);
                table.addCell(celdaCantidad.createPdfPCell());
                Cell celdaCodigo = new Cell(new Phrase(""+dbProductos.FacTemporal.get(i).getCodigo(),fuente));
                celdaCodigo.setBackgroundColor(color);
                table.addCell(celdaCodigo.createPdfPCell());
                Cell celdaTotal = new Cell(new Phrase(""+dbProductos.FacTemporal.get(i).getTotal(),fuente));
                celdaTotal.setBackgroundColor(color);
                table.addCell(celdaTotal.createPdfPCell());
                Cell celdaSinTexto = new Cell(new Phrase(""));
                celdaSinTexto.setBackgroundColor(color);
                celdaSinTexto.setBorder(2);
                celdaSinTexto.setBorderColorBottom(Color.black);
                table.addCell(celdaSinTexto.createPdfPCell());
                Cell celdaDescripcion = new Cell(new Phrase(""+dbProductos.FacTemporal.get(i).getDescripcion(),fuente));
                celdaDescripcion.setBackgroundColor(color);
                celdaDescripcion.setColspan(2);
                celdaDescripcion.setBorder(2);
                celdaDescripcion.setBorderColorBottom(Color.black);
                table.addCell(celdaDescripcion.createPdfPCell());
            }
            documento.add(table);
            documento.add(new Paragraph("\n"));
            PdfPTable tableTotal = new PdfPTable(2);
            tableTotal.setTotalWidth(500);
            tableTotal.setWidths(new int[]{360, 140});
            Cell celdaTotal = new Cell(new Phrase("TOTAL: ",fuenteTitle));
            celdaTotal.setBackgroundColor(Color.white);
            celdaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableTotal.addCell(celdaTotal.createPdfPCell());
            Cell celdaTotalMonto = new Cell(new Phrase(""+totalPagar,fuenteTitle));
            celdaTotalMonto.setBackgroundColor(Color.white);
            tableTotal.addCell(celdaTotalMonto.createPdfPCell());
            documento.add(tableTotal);
        }catch (DocumentException e){
        }catch (IOException e){
        }finally {
            documento.close();
        }
        mostrarPDF();
    }
    public File crearFichero(String nombreFichero) {
        File ruta = getRuta();
        File fichero = null;
        if(ruta != null) {
            fichero = new File(ruta, nombreFichero);
        }
        return fichero;
    }
    public File getRuta() {
        File ruta = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ruta = new File(Inicio.this.getExternalFilesDir(Environment.DIRECTORY_DCIM), NOMBRE_DIRECTORIO);
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

    public void abrirScan(View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(Inicio.this);
        intentIntegrator.setPrompt("For flash use volume up key");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents()!=null){
            bandera=true;
            onQueryTextChange(intentResult.getContents());
        }else{
            Toast.makeText(getApplicationContext(), "OOPS....",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(Inicio.this, Principal.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}