package com.example.prueba1_nacional_acv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String BROKER_URL = "tcp://broker.emqx.io:1883";
    private static final String CLIENT_ID = "AndroidSample12312312312312312";
    private Mqtt_prueba mqttHandler;
    EditText texto;
    Button envio;

    //lo de firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //lo de los mensajes y su lista
    private List<Mensajem> ListMSJ = new ArrayList<Mensajem>();
    ArrayAdapter<Mensajem> arrayAdapterMensajem;
    ListView lvListadoMensajes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mqttHandler = new Mqtt_prueba();
        mqttHandler.connect(BROKER_URL, CLIENT_ID);


        texto = findViewById(R.id.enombreproducto);
        envio = findViewById(R.id.benviar);
        lvListadoMensajes=findViewById(R.id.listademsj);

        envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = String.valueOf(texto.getText());
                publicarMensaje("inventado/para/prueba/patata", text);

                Mensajem msj = new Mensajem();

                msj.setIdMensaje(UUID.randomUUID().toString());
                msj.setContenido(texto.getText().toString());
                databaseReference.child("Mensaje").child(Mensajem.getIdMensaje()).setValue(msj);

                texto.setText("");
                //databaseReference.child("Mensaje").setValue(text);
            }

        });
        iniciarFirebase();
        listarDatos();
        //databaseReference.child("Mensaje").
        /*
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

    }

    private void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    protected void onDestroy(){
        mqttHandler.disconnect();
        super.onDestroy();
    }

    private void publicarMensaje (String topic, String mensaje){
        Toast.makeText(this, "Publicando: " + mensaje, Toast.LENGTH_SHORT).show();
        mqttHandler.publish(topic, mensaje);
    }

    private void listarDatos() {
        databaseReference.child("Mensaje").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListMSJ.clear();
                for (DataSnapshot objs : snapshot.getChildren()){
                    Mensajem li =objs.getValue(Mensajem.class);
                    ListMSJ.add(li);
                    arrayAdapterMensajem =new ArrayAdapter<Mensajem>(MainActivity.this, android.R.layout.simple_expandable_list_item_1,ListMSJ);
                    lvListadoMensajes.setAdapter(arrayAdapterMensajem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


