package com.juniorvaldivia86.miplazofijo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText EditTextMontoInicial, EditTextTNA, EditTextLapso;
    TextView TextViewMontoFinal, TextViewInteresesGanados, TextViewInteresesMes;
    Button buttonCalcular, buttonNuevaVentana, buttonSalir;
    RadioButton RadioButtonPesos, RadioButtonDolares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefe1 = getSharedPreferences("datos1", Context.MODE_PRIVATE);

        EditTextMontoInicial = (EditText) findViewById(R.id.editText);
        EditTextMontoInicial.setText(prefe1.getString("inicial","6000"));

        EditTextTNA = (EditText) findViewById(R.id.editText2);
        EditTextTNA.setText(prefe1.getString("tna","19"));

        EditTextLapso = (EditText) findViewById(R.id.editText3);
        EditTextLapso.setText(prefe1.getString("lapso","30"));

        TextViewMontoFinal = (TextView) findViewById(R.id.textView7);
        TextViewInteresesGanados = (TextView) findViewById(R.id.textView8);
        TextViewInteresesMes = (TextView) findViewById(R.id.textView9);

        RadioButtonPesos = (RadioButton) findViewById(R.id.radioButton);
        RadioButtonDolares = (RadioButton) findViewById(R.id.radioButton2);

        buttonCalcular = (Button) findViewById(R.id.button);
        buttonCalcular.setOnClickListener(this);

        buttonNuevaVentana = (Button) findViewById(R.id.button2);
        buttonNuevaVentana.setOnClickListener(this);

        buttonSalir = (Button) findViewById(R.id.button3);
        buttonSalir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            SharedPreferences preferencias1 = getSharedPreferences("datos1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias1.edit();
            editor.putString("inicial", EditTextMontoInicial.getText().toString());
            editor.putString("tna", EditTextTNA.getText().toString());
            editor.putString("lapso", EditTextLapso.getText().toString());
            editor.apply();
        }catch (Exception e) {
            TextViewInteresesMes.setText(e.toString());
        }

        double resu1, resu3, dolar=15.4;
        String total, interes, pormes;

        switch (v.getId()) {
            case R.id.button: // Calcular Plazo Fijo
                try {
                    String valor1 = EditTextMontoInicial.getText().toString();
                    int nro1 = Integer.parseInt(valor1);

                    String valor2 = EditTextTNA.getText().toString();
                    double nro2 = Double.parseDouble(valor2);

                    String valor3 = EditTextLapso.getText().toString();
                    int nro3 = Integer.parseInt(valor3);

                    // ((MontoInicial-5500)*(TNA-20)*(dias-30))/(dato-36500)
                    // String.format("%.2f", (resu3))

                    if (RadioButtonPesos.isChecked()){
                        resu1 = ((nro1  * nro2 * nro3) / 36500);
                        resu3 = ((resu1) / (nro3 / 30));

                        total = String.valueOf(String.format(Locale.ENGLISH, "%.2f", (resu1 + nro1)));
                        interes = String.valueOf(String.format(Locale.ENGLISH, "%.2f", resu1));
                        pormes = String.valueOf(String.format(Locale.ENGLISH, "%.2f", (resu3)));
                    }else{
                        resu1 = ((nro1 * dolar * nro2 * nro3) / 36500);
                        resu3 = ((resu1) / (nro3 / 30));

                        total = String.valueOf(String.format(Locale.ENGLISH, "%.2f", ((resu1 + nro1) * dolar)));
                        interes = String.valueOf(String.format(Locale.ENGLISH, "%.2f", (resu1 * dolar)));
                        pormes = String.valueOf(String.format(Locale.ENGLISH, "%.2f", (resu3 * dolar)));
                    }

                    TextViewMontoFinal.setText(total);
                    TextViewInteresesGanados.setText(interes);
                    TextViewInteresesMes.setText(pormes);

                } catch (Exception e) {
                    TextViewInteresesMes.setText(e.toString());
                }
                break;

            case R.id.button2:
                Ventana();
                break;

            case R.id.button3:
                finish();
                break;
        }
    }

    private void Ventana() {
        Intent i = new Intent(this, CambioDivisa.class );
        startActivity(i);
    }
}
