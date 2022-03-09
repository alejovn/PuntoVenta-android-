package com.example.puntoventa.Usuarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.puntoventa.R;
import com.example.puntoventa.db.DbUsuarios;

import java.io.File;

public class VerUsuario extends AppCompatActivity {
    ImageView imageView;
    EditText txtNombre, txtApellidos, txtCorreo;
    Button btnGuarda, btnBuscar;
    FloatingActionButton fabEditar, fabEliminar;
    UsuarioMostrar usuario;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuario);
        txtNombre = findViewById(R.id.viewNombre);
        txtApellidos = findViewById(R.id.viewApellidos);
        txtCorreo = findViewById(R.id.viewCorreo);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnGuarda = findViewById(R.id.btnGuarda);
        imageView  = (ImageView) findViewById(R.id.imageView);
        btnBuscar.setVisibility(View.INVISIBLE);
        btnGuarda.setVisibility(View.INVISIBLE);
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
        final DbUsuarios dbUsuarios = new DbUsuarios(VerUsuario.this);
        usuario = dbUsuarios.verContacto(id);
        if(usuario != null){
            txtNombre.setText(usuario.getNombre());
            txtApellidos.setText(usuario.getApellidos());
            txtCorreo.setText(usuario.getCorreo());
            File file = new File(usuario.getImagen());
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtApellidos.setInputType(InputType.TYPE_NULL);
            txtCorreo.setInputType(InputType.TYPE_NULL);
        }
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerUsuario.this, EditarUsuario.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerUsuario.this);
                builder.setMessage("¿Desea eliminar este usuario?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbUsuarios.eliminarContacto(id)){
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
        Intent intent = new Intent(this, ListaUsuarios.class);
        startActivity(intent);
    }
}