package com.example.puntoventa.Usuarios;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

public class NuevoRegistro extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    EditText txtNombre, txtApellidos, txtCorreo, txtContra;
    Button btnGuarda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_registro);

        txtNombre = findViewById(R.id.viewNombre1);
        txtApellidos = findViewById(R.id.viewApellidos1);
        txtCorreo = findViewById(R.id.viewCorreo1);
        txtContra = findViewById(R.id.viewContra1);
        imageView  = (ImageView) findViewById(R.id.imageView);
        btnGuarda = findViewById(R.id.btnGuarda);

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtNombre.getText().toString().equals("") && !txtApellidos.getText().toString().equals("") &&
                        !txtCorreo.getText().toString().equals("") && !txtContra.getText().toString().equals("")) {

                    DbUsuarios dbUsuarios = new DbUsuarios(NuevoRegistro.this);
                    VariablesGlobales variables = new VariablesGlobales();
                    long id = dbUsuarios.insertarUsuarios(txtNombre.getText().toString(),
                            txtApellidos.getText().toString(), txtCorreo.getText().toString(),
                            txtContra.getText().toString(), variables.getFileNuevo().toString());

                    if (id > 0) {
                        Toast.makeText(NuevoRegistro.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        try{
                            FileInputStream fis = new FileInputStream(variables.getFileViejo());
                            File nuevoFile = new File(variables.getFileNuevo().toString());
                            FileOutputStream fos = new FileOutputStream(nuevoFile);
                            FileChannel inChannel = fis.getChannel();
                            FileChannel outChannel = fos.getChannel();
                            inChannel.transferTo(0, inChannel.size(), outChannel);
                            fis.close();
                            fos.close();
                        }catch (IOException ioe) {
                            System.err.println("Error al Generar Copia");
                        }
                        limpiar();
                    } else {
                        Toast.makeText(NuevoRegistro.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NuevoRegistro.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void limpiar() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");
        txtContra.setText("");
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
                    File ruta = new File(NuevoRegistro.this.getExternalFilesDir(Environment.DIRECTORY_DCIM), "PuntoVenta");
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

    public void showFileChooser(View view) {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }
}