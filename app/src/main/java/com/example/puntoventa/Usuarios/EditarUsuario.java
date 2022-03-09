package com.example.puntoventa.Usuarios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.puntoventa.R;
import com.example.puntoventa.db.DbUsuarios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class EditarUsuario extends AppCompatActivity {
    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    EditText txtNombre, txtTelefono, txtCorreo;
    Button btnGuarda, btnBuscar;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;
    UsuarioMostrar usuario;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuario);

        txtNombre = findViewById(R.id.viewNombre);
        txtTelefono = findViewById(R.id.viewApellidos);
        txtCorreo = findViewById(R.id.viewCorreo);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        imageView  = (ImageView) findViewById(R.id.imageView);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

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

        final DbUsuarios dbContactos = new DbUsuarios(EditarUsuario.this);
        usuario = dbContactos.verContacto(id);

        if (usuario != null) {
            File file = new File(usuario.getImagen());
            VariablesGlobales variables = new VariablesGlobales();
            variables.setFileNuevo(file);
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
            txtNombre.setText(usuario.getNombre());
            txtTelefono.setText(usuario.getApellidos());
            txtCorreo.setText(usuario.getCorreo());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("") && !txtTelefono.getText().toString().equals("")) {
                    VariablesGlobales var = new VariablesGlobales();
                    correcto = dbContactos.editarContacto(id, txtNombre.getText().toString(), txtTelefono.getText().toString(), txtCorreo.getText().toString(),var.getFileNuevo().toString());

                    if(correcto){
                        Toast.makeText(EditarUsuario.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        try{
                            FileInputStream fis = new FileInputStream(var.getFileViejo());
                            File nuevoFile = new File(var.getFileNuevo().toString());
                            FileOutputStream fos = new FileOutputStream(nuevoFile);
                            FileChannel inChannel = fis.getChannel();
                            FileChannel outChannel = fos.getChannel();
                            inChannel.transferTo(0, inChannel.size(), outChannel);
                            fis.close();
                            fos.close();
                        }catch (IOException ioe) {
                            System.err.println("Error al Generar Copia");
                        }
                        verRegistro();
                    } else {
                        Toast.makeText(EditarUsuario.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarUsuario.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE_REQUEST);
            }
        });
    }
    private void verRegistro(){
        Intent intent = new Intent(this, ListaUsuarios.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VariablesGlobales variables = new VariablesGlobales();
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(filePath,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            System.out.println(picturePath);
            File file = new File(picturePath);
            variables.setFileViejo(file);
            cursor.close();
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            try {
                try{
                    File ruta = new File(EditarUsuario.this.getExternalFilesDir(Environment.DIRECTORY_DCIM), "PuntoVenta");
                    ruta.mkdirs();
                    File nuevoFile = new File(ruta+"/"+file.getName());
                    variables.setFileNuevo(nuevoFile);

                }catch (Exception ioe) {
                    System.err.println("Error al Generar Copia");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}