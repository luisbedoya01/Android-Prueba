package com.example.tarea1grupo6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class LoginActivity extends AppCompatActivity {

    EditText usuario;
    EditText clave;

    CheckBox mantenerSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usuario = findViewById(R.id.txtUsuario);
        clave = findViewById(R.id.txtClave);
        mantenerSesion = findViewById(R.id.chkMantenerSesion);
        //cargarCrendenciales();
        SharedPreferences shpIniciarSesion = getSharedPreferences("InicioSesion", MODE_PRIVATE);
        boolean inicioSesion = shpIniciarSesion.getBoolean("inicioSesionSHP", false);

        if(inicioSesion){
            Intent ventanaPrincipal = new Intent(this, MainActivity.class);
            startActivity(ventanaPrincipal);
        }

    }

    private void guardarCredencialesAcceso(String usuario, String clave){
        SharedPreferences shpIniciarSesion = getSharedPreferences("InicioSesion", MODE_PRIVATE);
        SharedPreferences.Editor shpEditor = shpIniciarSesion.edit();
        shpEditor.putString("usuarioSHP", usuario);
        shpEditor.putString("claveSHP", clave);
        shpEditor.putBoolean("inicioSesionSHP", true);
        shpEditor.apply();
    }

    private void cargarCrendenciales() {
        SharedPreferences shpIniciarSesion = getSharedPreferences("InicioSesion", MODE_PRIVATE);
        String usuarioTmp = shpIniciarSesion.getString("usuarioSHP", "");
        String claveTmp = shpIniciarSesion.getString("claveSHP", "");
        this.usuario.setText(usuarioTmp);
        this.clave.setText(claveTmp);
        if (usuarioTmp != "" && claveTmp != "") {
            mantenerSesion.setChecked(true);
        } else {
            mantenerSesion.setChecked(false);
        }
    }

    public void iniciarSesion(View view){

        String[][] usuarios = {
                {"luisbedoya", "bedoya1307"},
                {"angelovalencia", "valencia2307"},
                {"fiorellaachi","achi2509"}
        };

        String usuario = this.usuario.getText().toString();
        String clave = this.clave.getText().toString();


        boolean encontrado = encontrarUsuario(usuarios, usuario, clave);
        if(!usuario.isEmpty() && !clave.isEmpty()){
            if (encontrado) {
                if(mantenerSesion.isChecked()){
                    guardarCredencialesAcceso(usuario, clave);
                }
                toastOk("Acceso concedido");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                toastError("Usuario y/o contraseña incorrecta");
                this.usuario.setText("");
                this.clave.setText("");
            }

        } else {
            toastVacio("Debe ingresar sus credenciales de acceso");
        }
    }

    public boolean encontrarUsuario (String[][] usuarios, String usuario, String clave) {
        for (String[] user : usuarios) {
            if(user[0].equals(usuario) && user[1].equals(clave)) {
                return true;
            }
        }
        return false;
    }

    public void toastOk(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.custom_toast_ok, findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = layout.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void toastVacio(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.custom_toast_vacio, findViewById(R.id.ll_custom_toast_vacio));
        TextView txtMensaje = layout.findViewById(R.id.txtMensajeToastVacio);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void toastError(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.custom_toast_error, findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = layout.findViewById(R.id.txtMensajeToastError);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void registrar(View view){
        Intent ventanaRegistro = new Intent(this, RegistroActivity.class);
        startActivity(ventanaRegistro);
    }

}