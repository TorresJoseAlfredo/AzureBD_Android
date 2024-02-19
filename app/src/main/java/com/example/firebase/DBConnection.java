package com.example.firebase;

import android.content.Context;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection(View.OnClickListener context) {
        Connection connection = null;
        try {
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://rengoku.database.windows.net:1433;DatabaseName=RafasTaller;user=CloudSA24723188@rengoku;password=1pNedu1869.lock;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
        } catch (Exception e) {
            e.printStackTrace();
            showToast((Context) context, "Error de conexión: " + e.getMessage());
        }
        return connection;
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
