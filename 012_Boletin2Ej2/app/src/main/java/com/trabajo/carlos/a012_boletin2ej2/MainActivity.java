package com.trabajo.carlos.a012_boletin2ej2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listLlamadas;
    private Spinner spnOpcion;

    String tipo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listLlamadas = (ListView) findViewById(R.id.listLlamadas);
        spnOpcion = findViewById(R.id.spnOpcion);






        final String[] opciones={"Todas", "Entrantes", "Salientes", "Perdidas"};

        ArrayAdapter spAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, opciones);

        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOpcion.setAdapter(spAdapter);

        spnOpcion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                //Toast.makeText(getApplicationContext(), opciones[position], Toast.LENGTH_SHORT).show();

                tipo = spnOpcion.getItemAtPosition(position).toString();




                switch (tipo){
                    case "Todas":

                        ArrayAdapter<String> adaptadorTodas = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getLlamadas(tipo));
                        listLlamadas.setAdapter(adaptadorTodas);

                        break;
                    case "Entrantes":

                        ArrayAdapter<String> adaptadorEntra = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getLlamadas(tipo));
                        listLlamadas.setAdapter(adaptadorEntra);

                        break;
                    case "Salientes":

                        ArrayAdapter<String> adaptadorSal = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getLlamadas(tipo));
                        listLlamadas.setAdapter(adaptadorSal);

                        break;
                    case "Perdidas":

                        ArrayAdapter<String> adaptadorPer = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getLlamadas(tipo));
                        listLlamadas.setAdapter(adaptadorPer);

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private String[] getTodasLlamadas(String tipoLlamada) {

        List<String> llamadas = new LinkedList<String>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

            /**Cursor cur = getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER}, null, null, null);

            int tipo = cur.getColumnIndex(CallLog.Calls.TYPE);

            if (tipo == CallLog.Calls.OUTGOING_TYPE){
                tipoLlamada = "Salientes";
            }else if (tipo == CallLog.Calls.INCOMING_TYPE){
                tipoLlamada = "Entrantes";
            }else if (tipo == CallLog.Calls.MISSED_TYPE){
                tipoLlamada = "Perdidas";
            }**/

            Cursor cur = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
            int number = cur.getColumnIndex(CallLog.Calls.NUMBER);
            //int date = cur.getColumnIndex(CallLog.Calls.DATE);
            //int duration = cur.getColumnIndex(CallLog.Calls.DURATION);
            int type = cur.getColumnIndex(CallLog.Calls.TYPE);

            String callTypeStr = "";

            while (cur.moveToNext()) {
                String phnumber = cur.getString(number);
                //String callduration = cur.getString(duration);
                String calltype = cur.getString(type);
                //String calldate = cur.getString(date);
                //Date d = new Date(Long.valueOf(calldate));

                /*switch (Integer.parseInt(calltype)) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        tipoLlamada = "Outgoing";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        tipoLlamada = "Incoming";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        tipoLlamada = "Missed";
                        break;
                }*/


                switch (tipoLlamada){
                    case "Todas":


                        break;
                    case "Entrantes":


                        break;
                }


            }

            if (cur.moveToFirst()) {

                //int colTelefono = cur.getColumnIndex(CallLog.Calls.NUMBER);

                do {

                    llamadas.add(cur.getString(number) + " " + cur.getString(type));

                } while (cur.moveToNext());

            }

        }

        return llamadas.toArray(new String[llamadas.size()]);



    }

    public String[] getLlamadas(String Llmadas) {
        List<String> llamadas = new LinkedList<String>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                == PackageManager.PERMISSION_GRANTED) {

            Cursor cur = getContentResolver().query(CallLog.Calls.CONTENT_URI, new
                            String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER}, null, null,
                    null);

            int opcion =0;

            if (cur.moveToFirst()) {



                while (cur.moveToNext()) {




                    switch (Llmadas){
                        case "Todas":
                            opcion = 0;
                            break;
                        case "Entrantes":
                            opcion = CallLog.Calls.INCOMING_TYPE;
                            break;
                        case "Salientes":
                            opcion = CallLog.Calls.OUTGOING_TYPE;
                            break;
                        case "Perdidas":
                            opcion = CallLog.Calls.MISSED_TYPE;
                            break;
                    }

                    int colTelefono = cur.getColumnIndex(CallLog.Calls.NUMBER);
                    int tipoLlamda = cur.getColumnIndex(CallLog.Calls.TYPE);

                    String numeros = cur.getString(colTelefono);
                    String typoLlama = cur.getString(tipoLlamda);


                    if (Integer.parseInt(typoLlama) == opcion) {
                        //llamadas.add("el numero de telefono es " + numeros + "El tipo de llamada es " + typoLlama);
                        llamadas.add(numeros + " " + typoLlama);
                    }else if (opcion == 0){

                        //llamadas.add("el numero de telefono es " + numeros);
                        llamadas.add(numeros + " " + typoLlama);

                    }
                }
            }
        }
        return llamadas.toArray(new String[llamadas.size()]);
    }



}
