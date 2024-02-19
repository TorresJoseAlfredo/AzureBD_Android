package com.example.firebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateVentaActivity2 extends AppCompatActivity {

   /* @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }*/
    EditText edtxtinstalacion, edtxtauto,edtxtcliente;
    Button btagregar;
   private FirebaseFirestore mfirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_venta2);
       mfirestore = FirebaseFirestore.getInstance();


        edtxtinstalacion = findViewById(R.id.edtxtinstalacion);
        edtxtauto = findViewById(R.id.edtxtauto);
        edtxtcliente = findViewById(R.id.edtxtcliente);
        btagregar = findViewById(R.id.btagregar);

        btagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cliente = edtxtcliente.getText().toString().trim();
                String auto = edtxtauto.getText().toString().trim();
                String instalacion = edtxtinstalacion.getText().toString().trim();
                if(cliente.isEmpty() && auto.isEmpty() && instalacion.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingresar  los datos ",Toast.LENGTH_SHORT).show();
                }else{
                   postVenta(cliente,auto,instalacion);
                }
            }

        });

        //this.setTitle("Insertar Venta");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void postVenta(String cliente, String auto, String instalacion) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


        Map<String,Object> map = new HashMap<>();

        map.put("cliente",cliente);
        map.put("coche",auto);
        map.put("instalacion",instalacion);

        mfirestore.collection("Venta").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Creado exitosamente",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al ingresar",Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Error adding document", e);
            }
        });    }


}