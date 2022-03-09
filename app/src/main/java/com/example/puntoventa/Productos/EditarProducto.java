package com.example.puntoventa.Productos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

public class EditarProducto extends AppCompatActivity {
    boolean correcto = false;
    ProductoMostrar producto;
    Spinner unidad, categoria;
    public static int Id_UnidadE=0;
    public static int Id_CategoriaE=0;
    ImageView imageView;
    TextView txtNombre, txtCodigo, txtPrecioV,txtPrecioC, txtDescripcion, txtCantidad, txtStockMi, txtStockMa;
    Button btnGuarda,scanBtn;
    FloatingActionButton fabEditar, fabEliminar;
    int id = 0;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);

        txtNombre = findViewById(R.id.viewNombreP);
        txtCodigo = findViewById(R.id.viewCodigoP);
        txtPrecioC = findViewById(R.id.viewPrecioP);
        txtPrecioV = findViewById(R.id.viewPrecioPV);
        txtStockMi = findViewById(R.id.viewSctokMinimo);
        txtDescripcion = findViewById(R.id.viewDescripcionP);
        txtCantidad = findViewById(R.id.viewCantidadP);
        btnGuarda = findViewById(R.id.btnGuardaP);
        scanBtn = findViewById(R.id.scan_button);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        imageView  = (ImageView) findViewById(R.id.imageView);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        categoria = (Spinner) findViewById(R.id.spinnerEstado);
        unidad = (Spinner) findViewById(R.id.Unidad);
        DbUnidades dbUnidades = new DbUnidades(EditarProducto.this);
        DbCategorias dbCategorias = new DbCategorias(EditarProducto.this);
        NuevoProducto nuevoProducto = new NuevoProducto();

        dbUnidades.leerUnidadesMostrar();
        dbCategorias.leerCategoriasMostrar();

        ArrayAdapter<CharSequence> adapterU = new ArrayAdapter(this, R.layout.spinner_item_producto, nuevoProducto.ListaUnidades);
        unidad.setAdapter(adapterU);
        ArrayAdapter<CharSequence> adapterC = new ArrayAdapter(this, R.layout.spinner_item_producto, nuevoProducto.ListaCategorias);
        categoria.setAdapter(adapterC);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbProductos dbProductos = new DbProductos(EditarProducto.this);
        producto = dbProductos.verProducto(id);

        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtCodigo.setText(producto.getCodigo());
            txtPrecioC.setText(String.valueOf(producto.getPrecioC()));
            txtPrecioV.setText(String.valueOf(producto.getPrecioV()));
            txtDescripcion.setText(producto.getDescripcion());
            txtCantidad.setText(String.valueOf(producto.getCantidad()));
            txtStockMi.setText(String.valueOf(producto.getStockMi()));
            String nombreUnidad = dbUnidades.nombreUnidad(producto.getId_unidad());
            String nombreCategoria = dbCategorias.NombreCategoria(producto.getId_categoria());
            unidad.setSelection(getIndex(unidad, nombreUnidad));
            categoria.setSelection(getIndex(categoria, nombreCategoria));
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("") && !txtPrecioC.getText().toString().equals("")) {
                    correcto = dbProductos.editarProducto(id, txtNombre.getText().toString(), txtCodigo.getText().toString(),
                            Double.parseDouble(txtPrecioV.getText().toString()), Double.parseDouble(txtPrecioC.getText().toString()),
                            txtDescripcion.getText().toString(), Integer.parseInt(txtCantidad.getText().toString()),
                            Integer.parseInt(txtStockMi.getText().toString()),Id_UnidadE,
                            Id_CategoriaE);
                    if(correcto){
                        Toast.makeText(EditarProducto.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarProducto.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarProducto.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(EditarProducto.this);
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });
        unidad.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn,
                                               android.view.View v,
                                               int posicion,
                                               long id) {
                        if(!spn.getItemAtPosition(posicion).toString().equals("Unidad")){
                            Id_UnidadE = dbUnidades.idUnidad(spn.getItemAtPosition(posicion).toString());
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
                            Id_CategoriaE = dbCategorias.idCategoria(spn.getItemAtPosition(posicion).toString());
                        }
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });
    }
    private void verRegistro(){
            Intent intent = new Intent(this, ListaDeProductos.class);
            intent.putExtra("Activity", "Productos");
            startActivity(intent);
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
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

}
