package com.example.tarea1grupo6;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class InformacionRegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informacion_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button mostrarRegistros = findViewById(R.id.btnMostrarRegistros);
        mostrarRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatosSD();
            }
        });
    }

    private String leerDatosSD() {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(getExternalFilesDir(null), "RegistrarUsuario");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String linea;
            while ((linea = br.readLine()) != null) {
                data.append(linea).append("\n");
            }
            br.close();
        } catch (Exception ex) {
            Log.e("Fichero", "Error al leer datos de la tarjeta SD");
        }

        return data.toString();
    }

    private void mostrarDatosSD() {
        String datos = leerDatosSD();
        if ( datos == null) {
            datos = "No se pudieron leer los datos o el archivo está vacío";
        }

        new AlertDialog.Builder(this)
                .setTitle("Datos de registro en la tarjeta SD")
                .setMessage(datos)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public void salir(View view) {
        Intent ventanaLogin = new Intent(this, LoginActivity.class);
        startActivity(ventanaLogin);
    }
}