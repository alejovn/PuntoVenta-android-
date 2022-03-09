package com.example.puntoventa;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.puntoventa.Usuarios.Registro;
import com.example.puntoventa.Usuarios.UsuariosLogin;
import com.example.puntoventa.db.DbHelper;
import com.example.puntoventa.db.DbUsuarios;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<UsuariosLogin> lista;
    public static boolean bandera = false;
    private EditText txtCorreo;
    private EditText txtContra;
    int REQUEST_CODE=200;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*DbHelper dbhelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        if (db != null){
            Toast.makeText(MainActivity.this,"DB CREADA",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this,"DB NO CREADA",Toast.LENGTH_LONG).show();
        }*/
        lista = new ArrayList<>();
        txtCorreo = (EditText) findViewById(R.id.txtCorreo_);
        txtContra = (EditText) findViewById(R.id.txtContra_);
        verificarPermisos();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verificarPermisos() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permiso== PackageManager.PERMISSION_GRANTED){
        }else{
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
        }
        int permisoLeer = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permisoLeer== PackageManager.PERMISSION_GRANTED){
        }else{
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
        }
        int permisoCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permisoCamera== PackageManager.PERMISSION_GRANTED){
        }else{
            requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CODE);
        }
    }
    public void Ingresar(View view) {
        String vector [] = new String[3];
        DbUsuarios db = new DbUsuarios(MainActivity.this);
        lista = db.leerUsuarios();
        String correo = null;
        String contra = null;
        correo = txtCorreo.getText().toString();
        contra = txtContra.getText().toString();
        int cont=0;
        for(UsuariosLogin control:(ArrayList<UsuariosLogin>) lista) {
            if(lista.get(cont).getCorreo().equals(correo) && lista.get(cont).getContra().equals(contra)){
                bandera = true;
            }
            cont++;
        }
        if(bandera==true){
            Intent intent = new Intent(this, Principal.class);
            startActivity(intent);
        }
        else{
            mansage();
        }
    }
    public void mansage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIconAttribute(android.R.attr.alertDialogIcon);
        builder.setTitle("No se encuentra la cuenta");
        builder.setMessage("Parece que los datos ingresados no coinciden con ninguna cuenta existente")
                .setPositiveButton("Crear cuenta", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this, Registro.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
    public void Registro(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    protected void sendEmail() {
        Log.e("Test email:", "enviando email");
        String[] TO = {"alejandrovalencia2011@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Mensaje");
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.e("Test email:", "Fin envio email");

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
