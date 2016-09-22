package com.juniorvaldivia86.miplazofijo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

public class CambioDivisa extends AppCompatActivity implements View.OnClickListener {

    TextView TextViewDolares,TextViewResultado;
    EditText EditTextDatos, cdolresperu, cdolaresargentina, vdolaresperu, vdolaresargentina;
    Button buttonCerrar;
    RadioButton radioButtonPesos, radioButtonSoles, radioButtonDolares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_divisa);

        radioButtonSoles = (RadioButton) findViewById(R.id.radioButton3);
        radioButtonSoles.setOnClickListener(this);

        radioButtonPesos = (RadioButton) findViewById(R.id.radioButton4);
        radioButtonPesos.setOnClickListener(this);

        radioButtonDolares = (RadioButton) findViewById(R.id.radioButton5);
        radioButtonDolares.setOnClickListener(this);

        EditTextDatos = (EditText)findViewById(R.id.editText7);
        SharedPreferences prefe = getSharedPreferences("datos2", Context.MODE_PRIVATE);
        EditTextDatos.setText(prefe.getString("monto","100"));

        TextViewDolares = (TextView) findViewById(R.id.textView10);
        TextViewResultado = (TextView) findViewById(R.id.textView11);

        buttonCerrar = (Button) findViewById(R.id.button10);
        /* buttonCerrar.setOnClickListener(this); */

        cdolresperu = (EditText) findViewById(R.id.editText8);
        cdolresperu.setText(prefe.getString("cdp","3.388"));
        vdolaresperu = (EditText) findViewById(R.id.editText9);
        vdolaresperu.setText(prefe.getString("vdp","3.396"));

        cdolaresargentina  = (EditText) findViewById(R.id.editText10);
        cdolaresargentina.setText(prefe.getString("cda","14.90"));
        vdolaresargentina= (EditText) findViewById(R.id.editText11);
        vdolaresargentina.setText(prefe.getString("vda","15.40"));

        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        try {
            SharedPreferences preferencias = getSharedPreferences("datos2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("monto", EditTextDatos.getText().toString());
            editor.putString("cdp", cdolresperu.getText().toString());
            editor.putString("vdp", vdolaresperu.getText().toString());
            editor.putString("cda", cdolaresargentina.getText().toString());
            editor.putString("vda", vdolaresargentina.getText().toString());
            editor.apply();
            //finish();
        }catch (Exception e) {
            TextViewResultado.setText(e.toString());
        }

        double pre; /* dolarPEcomp=3.33, dolarPEvent=3.337, dolarARcomp=14.77, dolarARvent=15.13, pre; */
        String dolares, resultado;

        String valor1 = EditTextDatos.getText().toString();
        double nro1 = Double.parseDouble(valor1);

        String cam1 = cdolresperu.getText().toString();
        double vcdolresperu = Double.parseDouble(cam1);

        String cam2 = cdolaresargentina.getText().toString();
        double vcdolaresargentina = Double.parseDouble(cam2);

        String cam3 = vdolaresperu.getText().toString();
        double vvdolaresperu = Double.parseDouble(cam3);

        String cam4 = vdolaresargentina.getText().toString();
        double vvdolaresargentina = Double.parseDouble(cam4);


        switch (v.getId()) {
            case R.id.radioButton3:
                try {
                    /* cambiamos de soles a pesos */
                    pre = (nro1 / vvdolaresperu);
                    dolares = String.valueOf(String.format(Locale.ENGLISH, "%.2f", pre )) + " Dolares";
                    resultado = String.valueOf(String.format(Locale.ENGLISH, "%.2f", (pre * vcdolaresargentina))) + " Pesos";

                    TextViewDolares.setText(dolares);
                    TextViewResultado.setText(resultado);
                }catch (Exception e) {
                    TextViewResultado.setText(e.toString());
                }
                break;

            case R.id.radioButton4:
                try {
                    /* cambiamos de pesos a soles */
                    pre = (nro1 / vvdolaresargentina);
                    dolares = String.valueOf(String.format(Locale.ENGLISH, "%.2f", pre)) + " Dolares";
                    resultado = String.valueOf(String.format(Locale.ENGLISH, "%.2f", (pre * vcdolresperu))) + " Soles";

                    TextViewDolares.setText(dolares);
                    TextViewResultado.setText(resultado);
                }catch (Exception e) {
                    TextViewResultado.setText(e.toString());
                }
                break;

            case R.id.radioButton5:
                try {
                    /* cambiamos de Dolares a soles y pesos */
                    //dolares = String.valueOf(String.format(Locale.ENGLISH, "%.2f", (nro1 * vcdolresperu))) + " Soles compra";
                    dolares = String.valueOf(String.format(Locale.ENGLISH, "%.2f", (nro1 * vcdolresperu))) + " Soles Compra \r\n" + String.valueOf(String.format(Locale.ENGLISH, "%.2f", (nro1 * vvdolaresperu))) + " Soles Venta";
                    resultado = String.valueOf(String.format(Locale.ENGLISH, "%.2f", (nro1 * vcdolaresargentina))) + " Pesos Compra \r\n" + String.valueOf(String.format(Locale.ENGLISH, "%.2f", (nro1 * vvdolaresargentina))) + " Pesos Venta";

                    TextViewDolares.setText(dolares);
                    TextViewResultado.setText(resultado);
                }catch (Exception e) {
                    TextViewResultado.setText(e.toString());
                }
                break;
        }
    }
}
