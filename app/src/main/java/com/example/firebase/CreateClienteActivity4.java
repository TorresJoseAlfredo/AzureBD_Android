package com.example.firebase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.DBConnection;
import com.example.firebase.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateClienteActivity4 extends AppCompatActivity {

    Button btagregar;
    EditText namec, apec, numc;
    TextView terror;
    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cliente4);
        terror = findViewById(R.id.terror);
        namec = findViewById(R.id.namec);
        apec = findViewById(R.id.apec);
        numc = findViewById(R.id.numc);
        btagregar = findViewById(R.id.btagregar);


        btagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terror.setText("");
                terror.setBackgroundColor(12);

                String nombrer = namec.getText().toString();
                String apellidor = apec.getText().toString();
                String telefono = numc.getText().toString();
                //Validación
                if (nombrer.isEmpty() ) {
                    //Toast.makeText(getApplicationContext(), "El Nombre es obligatorio", Toast.LENGTH_SHORT).show();
                    terror.setText("El Nombre es obligatorio");
                    return;
                }
                if (telefono.length()>10  ) {
                    Toast.makeText(getApplicationContext(), "El número de teléfono no puede ser mayor a 10 dígitos", Toast.LENGTH_SHORT).show();
                    terror.setText("El número de teléfono no puede ser mayor a 10 dígitos");
                    return;
                }
                //Tratamiento de datos
                String nombres = nombrer.substring(0,1).toUpperCase()+ nombrer.substring(1);
                String apellidos = apellidor.substring(0,1).toUpperCase()+ apellidor.substring(1);

                Connection connection = null;
                try {
                    connection = conexionBD();
                    if (connection != null) {
                        PreparedStatement stm = connection.prepareStatement("INSERT INTO CLIENTES (nombre, apellido, telefono) Values(?,?,?)");
                        stm.setString(1, nombres);
                        stm.setString(2,apellidos);
                        stm.setString(3, numc.getText().toString());
                        stm.executeUpdate();
                        Toast.makeText(getBaseContext(), "Registro Guardado Correctamente", Toast.LENGTH_SHORT).show();
                        namec.setText("");
                        apec.setText("");
                        numc.setText("");
                    }
                } catch (SQLException e) {
                    Toast.makeText(getBaseContext(), "Error de Conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "SQLException: " + e.getMessage());
                    terror.append("SQLException: " + e.getMessage());

                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            Log.e("MainActivity", "Error al cerrar la conexión: " + e.getMessage());
                            terror.append("SQLException: " + e.getMessage());
                        }
                    }
                }
            }
        });
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
            Log.e("MainActivity", "Error de Conexión: " + se.getMessage());
            terror.append("Error de Conexión: " + se.getMessage());
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Error de Conexión: Controlador no encontrado", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error de Conexión: Controlador no encontrado: " + e.getMessage());
            terror.append("Error de Conexión: Controlador no encontrado: " + e.getMessage());
        } catch (Exception e) {
            Toast.makeText(this, "Error de Conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error de Conexión: " + e.getMessage());
            terror.append("Error de Conexión: " + e.getMessage());
        }
        return cnn;
    }
}
