package com.example.tarea1grupo6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void cerrarSesion(View view){
        Intent ventanaLogin = new Intent(this, LoginActivity.class);
        startActivity(ventanaLogin);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuPrincipal =  getMenuInflater();
        menuPrincipal.inflate(R.menu.menuprincipal, menu);

        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        if (item.getItemId() == R.id.itemAcercaDe) {
            Intent ventanaAcercaDe = new Intent(this, AcercaDeActivity.class);
            startActivity(ventanaAcercaDe);
            return true;
        }
        if (item.getItemId() == R.id.itemInfoRegistro) {
            Intent ventanaInfoRegistro = new Intent(this, InformacionRegistroActivity.class);
            startActivity(ventanaInfoRegistro);
            return true;
        }
        if (item.getItemId() == R.id.itemConsultarUsuario) {
            Intent ventanaConsultarUsuario = new Intent(this, ConsultarUsuarioActivity.class);
            startActivity(ventanaConsultarUsuario);
            return true;
        }
        return true;
    }

    public void borrarPreferencias(View view) {
        SharedPreferences shpIniciarSesion = getSharedPreferences("InicioSesion", MODE_PRIVATE);

        if (!shpIniciarSesion.getAll().isEmpty()) {
            SharedPreferences.Editor shpEditor = shpIniciarSesion.edit();
            shpEditor.clear();
            shpEditor.apply();

            Toast.makeText(this, "Se han borrado las preferencias", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "No hay preferencias que borrar", Toast.LENGTH_SHORT).show();
        }
    }

}

