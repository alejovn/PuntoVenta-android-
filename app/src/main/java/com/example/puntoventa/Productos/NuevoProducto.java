package com.example.puntoventa.Productos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puntoventa.R;
import com.example.puntoventa.db.DbCategorias;
import com.example.puntoventa.db.DbProductos;
import com.example.puntoventa.db.DbUnidades;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class NuevoProducto extends AppCompatActivity {
    TextView txtNombre, txtCodigo, txtPrecioV, txtPrecioC, txtDescripcion, txtCantidad, txtStockMinimo, txtStockMaximo;
    Button btnGuardar, scanBtn;
    Spinner unidad, categoria;
    ImageView imageView;
    public static int Id_Unidad=0;
    public static int Id_Categoria=0;
    public static ArrayList<String> ListaUnidades = new ArrayList();
    public static ArrayList<String> ListaCategorias = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);
        imageView = findViewById(R.id.imageView);
        txtNombre = findViewById(R.id.viewNombreP);
        txtCodigo = findViewById(R.id.viewCodigo);
        txtPrecioV = findViewById(R.id.viewPrecioV);
        txtPrecioC = findViewById(R.id.viewPrecioC);
        txtDescripcion = findViewById(R.id.viewDescripcion);
        txtCantidad = findViewById(R.id.viewCantidad);
        txtStockMinimo = findViewById(R.id.viewStockMinimo);
        btnGuardar = findViewById(R.id.btnGuarda);
        scanBtn = findViewById(R.id.scan_button);
        categoria = (Spinner) findViewById(R.id.spinnerEstado);
        unidad = (Spinner) findViewById(R.id.Unidad);
        DbUnidades dbUnidades = new DbUnidades(NuevoProducto.this);
        DbCategorias dbCategorias = new DbCategorias(NuevoProducto.this);

        dbUnidades.leerUnidadesMostrar();
        dbCategorias.leerCategoriasMostrar();

        ArrayAdapter<CharSequence> adapterU = new ArrayAdapter(this, R.layout.spinner_item_producto, ListaUnidades);
        unidad.setAdapter(adapterU);
        ArrayAdapter<CharSequence> adapterC = new ArrayAdapter(this, R.layout.spinner_item_producto, ListaCategorias);
        categoria.setAdapter(adapterC);

        unidad.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn,
                                               android.view.View v,
                                               int posicion,
                                               long id) {
                        if(!spn.getItemAtPosition(posicion).toString().equals("Unidad")){
                            Id_Unidad = dbUnidades.idUnidad(spn.getItemAtPosition(posicion).toString());
                        }

                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });

        categoria.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn,
                                               android.view.View v,
                                               int posicion,
                                               long id) {
                        if(!spn.getItemAtPosition(posicion).toString().equals("Categoria")){
                            Id_Categoria = dbCategorias.idCategoria(spn.getItemAtPosition(posicion).toString());
                        }
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().equals("") && !txtCodigo.getText().toString().equals("") &&
                        !txtPrecioV.getText().toString().equals("") && !txtDescripcion.getText().toString().equals("") &&
                        !txtCantidad.getText().toString().equals("")) {
                    VPGlobales variables = new VPGlobales();
                    DbProductos dbProductos = new DbProductos(NuevoProducto.this);
                    long id = dbProductos.insertarProductos(txtNombre.getText().toString(),
                            txtCodigo.getText().toString(), Double.parseDouble(txtPrecioV.getText().toString()),
                            Double.parseDouble(txtPrecioC.getText().toString()), txtDescripcion.getText().toString(),
                            Integer.parseInt(txtCantidad.getText().toString()), Integer.parseInt(txtStockMinimo.getText().toString()),
                            Id_Unidad, Id_Categoria, 1);
                    if (id > 0) {
                        Toast.makeText(NuevoProducto.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NuevoProducto.this, ListaDeProductos.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(NuevoProducto.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NuevoProducto.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void abrirScan(View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(NuevoProducto.this);
        intentIntegrator.setPrompt("For flash use volume up key");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents()!=null){
            txtCodigo.setText(intentResult.getContents());
        }else{
            Toast.makeText(getApplicationContext(), "OOPS....",Toast.LENGTH_LONG).show();
        }
    }
}