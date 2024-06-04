package com.example.tarea1grupo6;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class RegistroActivity extends AppCompatActivity {

    EditText txtCedula, txtNombresApellidos , txtEdad, txtFechaNacimiento;
    Spinner spNacionalidad;
    Spinner genero;

    RadioButton rbnSoltero, rbnCasado, rbnDivorciado;

    Button btnCalendario;

    RatingBar rtNivelIngles;

    private Calendar fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtCedula = findViewById(R.id.txtCedula);
        txtCedula.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                StringBuilder filteredInput = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    char currentChar = input.charAt(i);
                    if (Character.isDigit(currentChar)) {
                        filteredInput.append(currentChar);
                    }
                }
                if(filteredInput.length() >=10){
                    filteredInput.setLength(10);
                }
                if (!input.equals(filteredInput.toString())) {
                    txtCedula.setText(filteredInput.toString());
                    txtCedula.setSelection(filteredInput.length());
                }

            }
        });

        txtNombresApellidos = findViewById(R.id.txtNombresApellidos);
        txtNombresApellidos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                StringBuilder filteredInput = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    char currentChar = input.charAt(i);
                    if (Character.isLetter(currentChar) || Character.isSpaceChar(currentChar)) {
                        filteredInput.append(currentChar);
                    }
                }
                if (!input.equals(filteredInput.toString())) {
                    txtNombresApellidos.setText(filteredInput.toString());
                    txtNombresApellidos.setSelection(filteredInput.length());
                }

            }
        });

        txtEdad = findViewById(R.id.txtEdad);
        txtEdad.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                StringBuilder filteredInput = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    char currentChar = input.charAt(i);
                    if (Character.isDigit(currentChar)) {
                        filteredInput.append(currentChar);
                    }
                }
                if(filteredInput.length() > 2){
                    filteredInput.setLength(2);
                }
                if (!input.equals(filteredInput.toString())) {
                    txtEdad.setText(filteredInput.toString());
                    txtEdad.setSelection(filteredInput.length());
                }

            }
        });

        txtFechaNacimiento = findViewById(R.id.txtFechaNcimiento);
        fechaSeleccionada = Calendar.getInstance();
        // Si el EditText tiene una fecha previamente seleccionada, usar esa fecha
        if (!txtFechaNacimiento.getText().toString().isEmpty()) {
            String[] partesFecha = txtFechaNacimiento.getText().toString().split("/");
            int dia = Integer.parseInt(partesFecha[0]);
            int mes = Integer.parseInt(partesFecha[1]) - 1; // Restar 1 porque Calendar.MONTH comienza desde 0
            int anio = Integer.parseInt(partesFecha[2]);
            fechaSeleccionada.set(anio, mes, dia);
        }

        txtEdad = findViewById(R.id.txtEdad);
        btnCalendario = findViewById(R.id.btnCalendario);
        spNacionalidad = findViewById(R.id.spNacionalidad);
        genero = findViewById(R.id.spGenero);
        rbnSoltero = findViewById(R.id.rbnSoltero);
        rbnCasado = findViewById(R.id.rbnCasado);
        rbnDivorciado = findViewById(R.id.rbnDivorciado);
        rtNivelIngles = findViewById(R.id.rtNivelIngles);

        String[] datos = new String[] {"--Seleccione--",  "Ecuatoriana", "Colombiana", "Peruana", "Venezolana", "Chilena", "Argentina", "Mexicana"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_items_blanco, datos);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spNacionalidad.setAdapter(adapter);

        String[] datosGenero = new String[] {"--Seleccione--", "Masculino", "Femenino", "Otro"};
        ArrayAdapter<String> adapterGenero = new ArrayAdapter<>(this,
                R.layout.spinner_items_blanco, datosGenero);
        adapterGenero.setDropDownViewResource(R.layout.spinner_dropdown_item);
        genero.setAdapter(adapterGenero);

        btnCalendario.setOnClickListener(view -> abrirDialogo());
    }

    private void abrirDialogo(){

        int anio = fechaSeleccionada.get(Calendar.YEAR);
        int mes = fechaSeleccionada.get(Calendar.MONTH);
        int dia = fechaSeleccionada.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            txtFechaNacimiento.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            fechaSeleccionada.set(year, month, dayOfMonth);
        }, anio, mes, dia);
        datePickerDialog.show();
    }

    private boolean verificarCampos(){
        boolean isValido = true;
        EditText[] campos = {txtCedula, txtNombresApellidos, txtEdad, txtFechaNacimiento};
        for(EditText campo : campos){
            if(campo.getText().toString().isEmpty()){
                campo.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                isValido = false;
            } else{
                campo.getBackground().clearColorFilter();
            }
        }

        if(spNacionalidad.getSelectedItemPosition() == 0){
            toastError("Debe seleccionar una nacionalidad");
            isValido = false;
        }
        if(genero.getSelectedItemPosition() == 0){
            toastError("Debe seleccionar un género");
            isValido = false;
        }
        if(!rbnSoltero.isChecked() && !rbnCasado.isChecked() && !rbnDivorciado.isChecked()){
            toastError("Debe seleccionar un estado civil");
            isValido = false;
        }
        if(rtNivelIngles.getRating() == 0){
            toastError("Debe seleccionar un nivel de inglés");
            isValido = false;
        }
        return isValido;
    }

    private String obtenerDatos(){
        String cedula = txtCedula.getText().toString();
        String nombresApellidos = txtNombresApellidos.getText().toString();
        String edad = txtEdad.getText().toString();
        String fechaNacimiento = txtFechaNacimiento.getText().toString();
        String nacionalidad = spNacionalidad.getSelectedItem().toString();
        String genero = this.genero.getSelectedItem().toString();
        String estadoCivil = "";
        if(rbnSoltero.isChecked()){
            estadoCivil = "Soltero";
        } else if(rbnCasado.isChecked()){
            estadoCivil = "Casado";
        } else if(rbnDivorciado.isChecked()){
            estadoCivil = "Divorciado";
        }
        String nivelIngles = String.valueOf(rtNivelIngles.getRating());

        return "Cédula: " + cedula + "; " +
                "Nombres y apellidos: " + nombresApellidos + "; " +
                "Edad: " + edad + "; " +
                "Fecha de nacimiento: " + fechaNacimiento + "; " +
                "Nacionalidad: " + nacionalidad + "; " +
                "Género: " + genero + "; " +
                "Estado civil: " + estadoCivil + "; " +
                "Nivel de inglés: " + nivelIngles +"\n";
    }

    public int verificarEstadoSD() {
        String estado = Environment.getExternalStorageState();
        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            toastOk("La tarjeta SD está disponible");
            return 0;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            toastVacio("La tarjeta SD está disponible pero en modo de solo lectura");
            return 1;
        } else {
            toastError("La tarjeta SD no está disponible");
            return 2;
        }
    }

    private boolean guardarDatosSD(String data){
        try{
            File file = new File(getExternalFilesDir(null),"RegistrarUsuario");
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true));
            out.write(data);
            out.close();

            return true;

        } catch (Exception ex){
            Log.e("Fichero", "Error al escribir en la tarjeta SD");
            return false;
        }

    }

    /*public void guardarRegistro(View view){
        if(verificarCampos()){
            if(verificarEstadoSD() == 0){
                if(guardarDatosSD(obtenerDatos())){
                    toastOk("Registro guardado con éxito");
                    limpiarCampos();
                } else {
                    toastError("Error al guardar datos en la SD");
                }
            }

        } else{
            toastVacio("Todos los campos son obligatorios");
        }
    }*/

    public void guardarRegistro(View view){
        if(verificarCampos()){
            if(verificarEstadoSD() == 0){
                guardarBD(txtCedula.getText().toString(), txtNombresApellidos.getText().toString(), txtEdad.getText().toString(), spNacionalidad.getSelectedItem().toString(), genero.getSelectedItem().toString(), txtFechaNacimiento.getText().toString());
                toastOk("Registro guardado con éxito en BD");
                if(guardarDatosSD(obtenerDatos())){
                    toastOk("Registro guardado con éxito en SD");
                    limpiarCampos();
                } else {
                    toastError("Error al guardar datos en la SD");
                }
            }

        } else{
            toastVacio("Todos los campos son obligatorios");
        }
    }

    public void cancelarRegistro(View view){
        Intent ventanaLogin = new Intent(this, LoginActivity.class);
        startActivity(ventanaLogin);
    }
    private void mostrarRegistro(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Datos de registro ingresados");
        builder.setMessage(obtenerDatos());
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent ventanaLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(ventanaLogin);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void guardarBD(String cedula, String nombresApellidos, String edad, String nacionalidad , String genero, String fechanNacimiento) {
        MyOpenHelper dbTarea1Grupo6 = new MyOpenHelper(this);
        final SQLiteDatabase dbTarea1Grupo6Mode = dbTarea1Grupo6.getWritableDatabase();

        if (dbTarea1Grupo6Mode != null) {
            ContentValues cv = new ContentValues();
            cv.put("cedula", cedula);
            cv.put("nombresApellidos", nombresApellidos);
            cv.put("edad", edad);
            cv.put("nacionalidad", nacionalidad);
            cv.put("genero", genero);
            cv.put("fechaNacimiento", fechanNacimiento);

            dbTarea1Grupo6Mode.insert("usuario", null, cv);
        }

    }

    public void limpiarCampos(){
        txtCedula.setText("");
        txtNombresApellidos.setText("");
        txtEdad.setText("");
        txtFechaNacimiento.setText("");
        spNacionalidad.setSelection(0);
        genero.setSelection(0);
        rbnSoltero.setChecked(false);
        rbnCasado.setChecked(false);
        rbnDivorciado.setChecked(false);
        rtNivelIngles.setRating(0);
    }

    public void borrarCampos(View view){

            txtCedula.setText("");
            txtNombresApellidos.setText("");
            txtEdad.setText("");
            txtFechaNacimiento.setText("");
            spNacionalidad.setSelection(0);
            genero.setSelection(0);
            rbnSoltero.setChecked(false);
            rbnCasado.setChecked(false);
            rbnDivorciado.setChecked(false);
            rtNivelIngles.setRating(0);

            Toast.makeText(view.getContext(), "Datos de registro borrados", Toast.LENGTH_SHORT).show();

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
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}