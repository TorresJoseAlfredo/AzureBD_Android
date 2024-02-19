package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btn_venta, btn_producto,btn_cliente,btn_vercliente,btn_cita,btn_vercita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_venta = findViewById(R.id.btn_venta);
        btn_producto = findViewById(R.id.btn_producto);
        btn_cliente = findViewById(R.id.btn_cliente);
        btn_vercliente = findViewById(R.id.btn_vercliente);
        btn_cita = findViewById(R.id.btn_cita);
        btn_vercita = findViewById(R.id.btn_vercita);

        btn_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CreateClienteActivity4.class));
            }
        });

        btn_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CreateCitaActivity3.class));
            }
        });

        btn_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CreateVentaActivity2.class));
            }
        });

        btn_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateProductFragment fm = new CreateProductFragment();
                fm.show(getSupportFragmentManager(),"Navegar a fragmento");

            }
        });


    }
}