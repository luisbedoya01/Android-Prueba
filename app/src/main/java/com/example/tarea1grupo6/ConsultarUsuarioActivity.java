package com.example.tarea1grupo6;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class ConsultarUsuarioActivity extends AppCompatActivity {

    EditText txtCedula, txtNombresApellidos, txtEdad, txtFechaNacimiento;
    Spinner spnGenero, spnNacionalidad;

    Button btnCalendar, btnEliminar, btnEditar;

    private Calendar fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtCedula = findViewById(R.id.txtCedulaC);
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
                if (filteredInput.length() >= 10) {
                    filteredInput.setLength(10);
                }
                if (!input.equals(filteredInput.toString())) {
                    txtCedula.setText(filteredInput.toString());
                    txtCedula.setSelection(filteredInput.length());
                }

            }
        });

        txtNombresApellidos = findViewById(R.id.txtNombresApellidosC);
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


        txtEdad = findViewById(R.id.txtEdadC);
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
                if (filteredInput.length() > 2) {
                    filteredInput.setLength(2);
                }
                if (!input.equals(filteredInput.toString())) {
                    txtEdad.setText(filteredInput.toString());
                    txtEdad.setSelection(filteredInput.length());
                }

            }
        });


        txtFechaNacimiento = findViewById(R.id.txtFechaNacimientoC);
        fechaSeleccionada = Calendar.getInstance();


        btnCalendar = findViewById(R.id.btnCalendario);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEditar = findViewById(R.id.btnEditar);

        spnGenero = findViewById(R.id.spnGeneroC);
        spnNacionalidad = findViewById(R.id.spnNacionalidadC);

        String[] datos = new String[]{"--Seleccione--", "Ecuatoriana", "Colombiana", "Peruana", "Venezolana", "Chilena", "Argentina", "Mexicana"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_items_blanco, datos);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spnNacionalidad.setAdapter(adapter);

        spnGenero = findViewById(R.id.spnGeneroC);
        String[] datosGenero = new String[]{"--Seleccione--", "Masculino", "Femenino", "Otro"};
        ArrayAdapter<String> adapterGenero = new ArrayAdapter<>(this,
                R.layout.spinner_items_blanco, datosGenero);
        adapterGenero.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spnGenero.setAdapter(adapterGenero);


        btnCalendar.setOnClickListener(v -> abrirCalendario());

        disableFields();

    }

    private void disableFields() {
        txtNombresApellidos.setEnabled(false);
        txtEdad.setEnabled(false);
        spnNacionalidad.setEnabled(false);
        spnGenero.setEnabled(false);
        btnCalendar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void enabledFields() {
        txtNombresApellidos.setEnabled(true);
        txtEdad.setEnabled(true);
        spnNacionalidad.setEnabled(true);
        spnGenero.setEnabled(true);
        btnCalendar.setEnabled(true);
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void abrirCalendario() {
        String fechaActual = txtFechaNacimiento.getText().toString();
        int dia, mes, anio;

        if (!fechaActual.isEmpty()) {
            String[] dateParts = fechaActual.split("/");
            dia = Integer.parseInt(dateParts[0]);
            mes = Integer.parseInt(dateParts[1]) - 1;
            anio = Integer.parseInt(dateParts[2]);
        } else {
            Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            fechaSeleccionada.set(year, month, dayOfMonth);
            txtFechaNacimiento.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, anio, mes, dia);

        datePickerDialog.show();
    }

    private boolean validarCampos() {
        boolean camposValidos = true;
        final int COLOR_RED = Color.RED;
        final int DURACION_COLOR = 2000;

        EditText[] campos = {txtCedula, txtNombresApellidos, txtEdad, txtFechaNacimiento};
        for (EditText campo : campos) {
            if (campo.getText().toString().isEmpty()) {
                campo.getBackground().setColorFilter(COLOR_RED, PorterDuff.Mode.SRC_IN);

                new Handler().postDelayed(() -> campo.getBackground().setColorFilter(null), DURACION_COLOR);

                camposValidos = false;
                break;
            }
        }

        if (spnNacionalidad.getSelectedItemPosition() == 0 && spnGenero.getSelectedItemPosition() == 0) {
            camposValidos = false;
        }

        if (!camposValidos) {
            toastVacio("Debe completar todos los campos");
        }

        return camposValidos;
    }

    @SuppressLint("Range")
    public void consultarUsuario(View view) {
        MyOpenHelper dbTarea1Grupo6 = new MyOpenHelper(this);
        final SQLiteDatabase dbTarea1Grupo6Mode = dbTarea1Grupo6.getReadableDatabase();

        String cedulaUsuario = txtCedula.getText().toString();
        Cursor cursor = dbTarea1Grupo6Mode.rawQuery("SELECT nombresApellidos, edad, nacionalidad, genero, fechaNacimiento FROM usuario WHERE cedula = '" + cedulaUsuario + "'", null);


        if (cedulaUsuario.isEmpty()) {
            txtCedula.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            toastVacio("Ingrese cédula para consultar usuario");

            new Handler().postDelayed(() -> txtCedula.getBackground().setColorFilter(null), 2000);

        } else if (cursor != null) {
            if (cursor.moveToFirst()) {
                txtNombresApellidos.setText(cursor.getString(cursor.getColumnIndex("nombresApellidos")));
                txtEdad.setText(cursor.getString(cursor.getColumnIndex("edad")));
                txtFechaNacimiento.setText(cursor.getString(cursor.getColumnIndex("fechaNacimiento")));
                spnNacionalidad.setSelection(((ArrayAdapter<String>) spnNacionalidad.getAdapter()).getPosition(cursor.getString(cursor.getColumnIndex("nacionalidad"))));
                spnGenero.setSelection(((ArrayAdapter<String>) spnGenero.getAdapter()).getPosition(cursor.getString(cursor.getColumnIndex("genero"))));
                enabledFields();
            } else {
                toastError("No existe usuario con cédula " + cedulaUsuario);
            }
            cursor.close();
        }
    }

    public void actualizarRegistro(View view) {
        if (validarCampos()) {
            MyOpenHelper dbTarea1Grupo6 = new MyOpenHelper(this);
            final SQLiteDatabase dbTarea1Grupo6Mode = dbTarea1Grupo6.getWritableDatabase();

            String cedulaUsuario = txtCedula.getText().toString();
            String nombresApellidos = txtNombresApellidos.getText().toString();
            String edad = txtEdad.getText().toString();
            String fechaNacimiento = txtFechaNacimiento.getText().toString();
            String nacionalidad = spnNacionalidad.getSelectedItem().toString();
            String genero = spnGenero.getSelectedItem().toString();

            dbTarea1Grupo6Mode.execSQL("UPDATE usuario SET nombresApellidos = '" + nombresApellidos + "', edad = '" + edad + "', fechaNacimiento = '" + fechaNacimiento + "', nacionalidad = '" + nacionalidad + "', genero = '" + genero + "' WHERE cedula = '" + cedulaUsuario + "'");
            limpiarCampos();
            disableFields();
            toastOk("Registro actualizado con éxito");
        }
    }

    public void limpiarCampos() {
        txtCedula.setText("");
        txtNombresApellidos.setText("");
        txtEdad.setText("");
        txtFechaNacimiento.setText("");
        spnNacionalidad.setSelection(0);
        spnGenero.setSelection(0);
    }

    public void eliminarRegistro(final View view) {
        String cedulaUsuario = txtCedula.getText().toString();
        if (cedulaUsuario.isEmpty()) {
            txtCedula.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            toastVacio("Ingrese cédula para eliminar usuario");
            new Handler().postDelayed(() -> txtCedula.getBackground().setColorFilter(null), 2000);

        } else {
            confirmarEliminacion(view, cedulaUsuario);
        }
    }

    public void confirmarEliminacion(View vista, String cedulaUsuario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro de eliminar el registro?")
                .setCancelable(false)
                .setPositiveButton("Sí", (dialog, which) -> {
                    MyOpenHelper dbTarea1Grupo6 = new MyOpenHelper(vista.getContext());
                    final SQLiteDatabase dbTarea1Grupo6Mode = dbTarea1Grupo6.getWritableDatabase();
                    dbTarea1Grupo6Mode.execSQL("DELETE FROM usuario WHERE cedula = '" + cedulaUsuario + "'");
                    limpiarCampos();
                    disableFields();
                    toastOk("Registro eliminado con éxito");
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void toastOk(String mensaje) {
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

    public void toastVacio(String mensaje) {
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


    public void toastError(String mensaje) {
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

    public void cancelarConsulta(View view) {
        Intent ventanaPrincipal = new Intent(this, MainActivity.class);
        startActivity(ventanaPrincipal);
    }
}