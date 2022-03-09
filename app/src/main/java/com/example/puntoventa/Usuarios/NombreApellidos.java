package com.example.puntoventa.Usuarios;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.puntoventa.Productos.EditarProducto;
import com.example.puntoventa.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class NombreApellidos extends AppCompatActivity{
    private ImageView imageView;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private EditText txtnombre;
    private EditText txtApellidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre_apellidos);

        txtnombre = (EditText) findViewById(R.id.txtNombre_);
        txtApellidos = (EditText) findViewById(R.id.txtApellidos_);

        imageView  = (ImageView) findViewById(R.id.imageView);

    }
    public void Guardar(View view) {
        String nombre = null;
        String apellidos = null;

        nombre = txtnombre.getText().toString();
        apellidos = txtApellidos.getText().toString();

        if(apellidos.equals("") || nombre.equals("")){
            mansage();
        }
        else{
            VariablesGlobales vG = new VariablesGlobales();
            vG.setNombre(nombre);
            vG.setApellidos(apellidos);
            System.out.println(vG.getNombre());
            System.out.println(vG.getApellidos());

            Intent intent = new Intent(this, CorreoContra.class);
            startActivity(intent);
        }
    }
    public void mansage() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(NombreApellidos.this);
        builder.setIconAttribute(android.R.attr.alertDialogIcon);
        builder.setTitle("ERROR");
        builder.setMessage("Campos sin completar")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
    public void showFileChooser(View view) {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
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
                    File ruta = new File(NombreApellidos.this.getExternalFilesDir(Environment.DIRECTORY_DCIM), "PuntoVenta");
                    ruta.mkdirs();
                    System.out.println(ruta);
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