package com.sanisidro.testriego;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;


public class MyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * llamado por el boton btCalcular :
     * efectua todos los calculos y pone los textFields correspondientes
     */
    public void calculaEficiencia(View view) {

        try {
            /** Suponemos que los olivos riegan a razon de 16 litros por hora */
            final int litrosPorOlivo = 16;

            /** calculamos primero las horas que han pasado ( p.e. 2,13267 horas**/
            final Calendar horaInicial = Calendar.getInstance();
            final EditText txHoraInicial = (EditText) findViewById(R.id.txHoraInicial);
            final EditText txMinutoInicial = (EditText) findViewById(R.id.txMinutoInicial);
            horaInicial.setTime(Time.valueOf(txHoraInicial.getText().toString()+":"+txMinutoInicial.getText().toString() + ":00"));
            final Calendar horaFinal = Calendar.getInstance();
            final EditText txHoraFinal = (EditText) findViewById(R.id.txHoraFinal);
            final EditText txMinutoFinal = (EditText) findViewById(R.id.txMinutoFinal);
            horaFinal.setTime(Time.valueOf(txHoraFinal.getText().toString()+":"+txMinutoFinal.getText().toString()  + ":00"));
            final double horas = (horaFinal.getTimeInMillis() - horaInicial.getTimeInMillis()) / (3600.0 * 1000.0);
            /** calculamos los m3Consumidos de la dif. entre los dos valores del contador */
            final EditText txMedidaIncial = (EditText) findViewById(R.id.txMedidaInicial);
            final EditText txMedidaFinal = (EditText) findViewById(R.id.txMedidaFinal);

            final long m3Consumidos = Long.valueOf(txMedidaFinal.getText().toString()) -
                    Long.valueOf(txMedidaIncial.getText().toString());

            /** los olivos que se estan regando */
            final EditText txNumeroOlivos = (EditText) findViewById(R.id.txNumeroOlivos);
            final long olivos = Long.valueOf(txNumeroOlivos.getText().toString());

            /** calculamos los tres valores que necesitamos */
            final TextView lbConsumoReal = (TextView) findViewById(R.id.lbConsumoReal);
            lbConsumoReal.setText("REAL      :" + String.valueOf(m3Consumidos));

            final long m3Teoricos = Math.round(horas * litrosPorOlivo * olivos / 1000.0);

            final TextView lbConsumoTerorico = (TextView) findViewById(R.id.lbConsumoTeorico);
            lbConsumoTerorico.setText("TEORICO  : " + String.valueOf(m3Teoricos));

            final TextView lbEficiencia = (TextView) findViewById(R.id.lbEficiencia);
            final double eficiencia = ((double) m3Teoricos) / ((double) m3Consumidos) * 100;
            /* color android:textColor=
                "#FF0E12FF" azul
                "#FFFF7F09" naranja
                "#FFFF0B0B" rojo
                    */
            String color = new String();
            if (eficiencia < 50) {
                color = "#FFFF0B0B";
            } else {
                if (eficiencia < 75) {
                    color = "#FFFF7F09";
                } else {
                    color = "#FF0E12FF";
                }
            }

            lbEficiencia.setTextColor(Color.parseColor(color));
            lbEficiencia.setText("EFICIENCIA :" + String.valueOf(Math.round(eficiencia)) + "%");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR EN DATOS!!", Toast.LENGTH_SHORT).show();
        }
    }
}
