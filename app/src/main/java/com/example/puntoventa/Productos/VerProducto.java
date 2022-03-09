package com.example.puntoventa.Productos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.puntoventa.R;
import com.example.puntoventa.db.DbCategorias;
import com.example.puntoventa.db.DbProductos;
import com.example.puntoventa.db.DbUnidades;

public class VerProducto extends AppCompatActivity {
    ImageView imageView;
    TextView txtNombre, txtCodigo, txtPrecioV,txtPrecioC, txtDescripcion, txtCantidad, txtStockMi, txtStockMa;
    Button btnGuarda, btnBuscar, btnTomar, scanBtn;
    FloatingActionButton fabEditar, fabEliminar;
    ProductoMostrar producto;
    Spinner unidad, categoria;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);


        txtNombre = findViewById(R.id.viewNombreP);
        txtCodigo = findViewById(R.id.viewCodigoP);
        txtPrecioC = findViewById(R.id.viewPrecioP);
        txtPrecioV = findViewById(R.id.viewPrecioPV);

        txtDescripcion = findViewById(R.id.viewDescripcionP);
        txtCantidad = findViewById(R.id.viewCantidadP);
        txtStockMi = findViewById(R.id.viewSctokMinimo);

        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda = findViewById(R.id.btnGuardaP);
        scanBtn = findViewById(R.id.scan_button);

        imageView  = (ImageView) findViewById(R.id.imageView);


        btnGuarda.setVisibility(View.INVISIBLE);
        scanBtn.setVisibility(View.INVISIBLE);
        categoria = (Spinner) findViewById(R.id.spinnerEstado);
        unidad = (Spinner) findViewById(R.id.Unidad);
        DbUnidades dbUnidades = new DbUnidades(VerProducto.this);
        DbCategorias dbCategorias = new DbCategorias(VerProducto.this);
        NuevoProducto nuevoProducto = new NuevoProducto();

        dbUnidades.leerUnidadesMostrar();
        dbCategorias.leerCategoriasMostrar();

        ArrayAdapter<CharSequence> adapterU = new ArrayAdapter(this, R.layout.spinner_item_producto, nuevoProducto.ListaUnidades);
        unidad.setAdapter(adapterU);
        ArrayAdapter<CharSequence> adapterC = new ArrayAdapter(this, R.layout.spinner_item_producto, nuevoProducto.ListaCategorias);
        categoria.setAdapter(adapterC);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        final DbProductos dbProductos = new DbProductos(VerProducto.this);
        producto = dbProductos.verProducto(id);
        if(producto != null){

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

            txtNombre.setInputType(InputType.TYPE_NULL);
            txtCodigo.setInputType(InputType.TYPE_NULL);
            txtPrecioV.setInputType(InputType.TYPE_NULL);
            txtPrecioC.setInputType(InputType.TYPE_NULL);
            txtDescripcion.setInputType(InputType.TYPE_NULL);
            txtCantidad.setInputType(InputType.TYPE_NULL);
            txtStockMi.setInputType(InputType.TYPE_NULL);
            unidad.setEnabled(false);
            categoria.setEnabled(false);
        }
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerProducto.this, EditarProducto.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerProducto.this);
                builder.setMessage("¿Desea eliminar este producto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbProductos.eliminarProducto(id)){
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }
    private void lista(){
        Intent intent = new Intent(this, ListaDeProductos.class);
        startActivity(intent);
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