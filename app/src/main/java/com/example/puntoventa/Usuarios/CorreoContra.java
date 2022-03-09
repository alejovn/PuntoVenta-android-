package com.example.puntoventa.Usuarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.puntoventa.MainActivity;
import com.example.puntoventa.R;
import com.example.puntoventa.db.DbUsuarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class CorreoContra extends AppCompatActivity {
    private EditText txtCorreo;
    private EditText txtContra;
    private EditText txtContraR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo_contra);

        txtCorreo = (EditText) findViewById(R.id.txtCorreoC_);
        txtContra = (EditText) findViewById(R.id.txtContraC_);
        txtContraR = (EditText) findViewById(R.id.txtContraRC_);
    }

    public void Guardar(View view) {
        String correo = null;
        String contra = null;
        String contraR = null;

        correo = txtCorreo.getText().toString();
        contra = txtContra.getText().toString();
        contraR = txtContraR.getText().toString();


        if(correo.equals("") || contra.equals("") || contraR.equals("")){
            mansageCampos();
        }
        else if(contra.equals(contraR)){
            VariablesGlobales vG = new VariablesGlobales();
            DbUsuarios dbUsuarios = new DbUsuarios(CorreoContra.this);

            vG.setCorreo(correo);
            vG.setContra(contra);

            long id = dbUsuarios.insertarUsuarios(vG.getNombre(), vG.getApellidos(), vG.getCorreo(), vG.getContra(), vG.getFileNuevo().toString());

            if(id>0){
                Toast.makeText(getApplicationContext(),"Registro exitoso",Toast.LENGTH_SHORT).show();
                try{
                    VariablesGlobales variables = new VariablesGlobales();
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
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"No se hizo el registro",Toast.LENGTH_SHORT).show();
            }

        }else{
            mansageContra();
        }


    }
    public void mansageCampos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CorreoContra.this);
        builder.setIconAttribute(android.R.attr.alertDialogIcon);
        builder.setTitle("Message Error");
        builder.setMessage("Hay campos sin completar")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
    public void mansageContra() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CorreoContra.this);
        builder.setIconAttribute(android.R.attr.alertDialogIcon);
        builder.setTitle("Message Error");
        builder.setMessage("Las contraseñas no coinciden")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}