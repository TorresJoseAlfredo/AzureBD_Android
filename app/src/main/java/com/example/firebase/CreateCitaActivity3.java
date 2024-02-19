package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateCitaActivity3 extends AppCompatActivity {
CalendarView calendarv;
Calendar calendar;
    TimePicker timePicker;
    int dia1, mes1,anio1;
    String fechaHoraSQL;
    Button btagregar;
EditText fecha, descripcion, namec;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cita3);
        calendarv = findViewById(R.id.calendar);
        btagregar = findViewById(R.id.btagregar);
        calendar = Calendar.getInstance();
        fecha = findViewById(R.id.fecha);
        descripcion = findViewById(R.id. descripcion);
        namec = findViewById(R.id. namec);
        timePicker = findViewById(R.id.timePicker);

        // Establecer límite inferior (8 am)


        calendarv.setMinDate(System.currentTimeMillis());
        calendarv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                 mes1= month +1;
                 dia1 = dayOfMonth;
                 anio1 = year;
                Toast.makeText(CreateCitaActivity3.this, dayOfMonth+"/"+ mes1+"/"+year, Toast.LENGTH_SHORT).show();
                cambiarFecha(dayOfMonth,mes1,year);
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                cambiarFecha(dia1,mes1,anio1);
            }
        });
        btagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Connection connection = null;
                try {
                    connection = conexionBD();
                    if (connection != null) {
                        PreparedStatement stm = connection.prepareStatement("INSERT INTO PCITAS (cliente, fecha, descripcion) Values(?,?,?)");
                        stm.setString(1, namec.getText().toString());
                        stm.setString(2,fechaHoraSQL);
                        stm.setString(3, descripcion.getText().toString());
                        stm.executeUpdate();
                        Toast.makeText(getBaseContext(), "Registro Guardado Correctamente", Toast.LENGTH_SHORT).show();
                        namec.setText("");
                        fecha.setText("");
                        descripcion.setText("");
                    }
                } catch (SQLException e) {
                    Toast.makeText(getBaseContext(), "Error de Conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "SQLException: " + e.getMessage());
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            Log.e("MainActivity", "Error al cerrar la conexión: " + e.getMessage());
                        }
                    }
                }
            }
        });



    }

    public void getDate(){
        long date = calendarv.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        calendar.setTimeInMillis(date);
        String selected_Date = simpleDateFormat.format(calendar.getTime());
        Toast.makeText(this, "Eligio:"+selected_Date, Toast.LENGTH_SHORT).show();
        fecha.setText(selected_Date);
    }

    public void cambiarFecha(int dia, int mes, int anio) {
        String fechaSeleccionada = String.format(Locale.getDefault(), "%02d/%02d/%d", dia, mes, anio);

        int hora = timePicker.getHour();
        int minutos = timePicker.getMinute();

        String horaSeleccionada = String.format(Locale.getDefault(), "%02d:%02d", hora, minutos);
        fecha.setText(fechaSeleccionada+" "+ horaSeleccionada);
        // Formatear la fecha y hora al formato de SQL Server
        fechaHoraSQL = convertirFechaHoraSQL(fechaSeleccionada, horaSeleccionada);

    }

    // Método para convertir la fecha y hora al formato de SQL Server
    private String convertirFechaHoraSQL(String fecha, String hora) {
        String fechaHoraFormatoSQL = "";

        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            // Parsear y formatear la fecha y hora
            String fechaHoraCombinada = fecha + " " + hora;
            fechaHoraFormatoSQL = formatoSalida.format(formatoEntrada.parse(fechaHoraCombinada));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fechaHoraFormatoSQL;
    }

    public Connection conexionBD() {
        Connection cnn = null;
        try {
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionString = "jdbc:jtds:sqlserver://rengoku.database.windows.net:1433;DatabaseName=RafasTaller;user=CloudSA24723188@rengoku;password=Rengoku1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=request;";
            cnn = DriverManager.getConnection(connectionString);

            Toast.makeText(this, "Conexión exitosa", Toast.LENGTH_SHORT).show();
        } catch (SQLException se) {
            Toast.makeText(this, "Error de Conexión: " + se.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "SQLException: " + se.getMessage());
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Error de Conexión: Controlador no encontrado", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "ClassNotFoundException: " + e.getMessage());
        } catch (Exception e) {
            Toast.makeText(this, "Error de Conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Exception: " + e.getMessage());
        }
        return cnn;
    }
}