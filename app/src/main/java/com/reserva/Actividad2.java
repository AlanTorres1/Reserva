package com.reserva;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Actividad2 extends Activity {

	String nombre = "", fecha = "", hora = "", Apellido ="", Edad ="", NumPhone="", NomHot="", Email="";
	int personas = 0;
	TextView muestraDatos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad2);

		muestraDatos = (TextView) findViewById(R.id.muestraDatos);

		Bundle recibe = new Bundle();
		recibe = this.getIntent().getExtras();

		nombre = recibe.getString("Nombre");
		Apellido = recibe.getString("Apellidos");
		Edad = recibe.getString("Edad");
		NumPhone = recibe.getString("NumPhone");
		Email = recibe.getString("Email");
		NomHot = recibe.getString("NomHot");
		fecha = recibe.getString("Fecha");
		hora = recibe.getString("Hora");
		personas = recibe.getInt("Personas");

		muestraDatos.setText("Reservacion a nombre de :"+ nombre + Apellido + "\n" +
				"Edad: " + Edad + "\n" +
				"Número de Telefono: " + NumPhone + "\n" +
				"Email: " + Email + "\n" +
				"Nombre del Hotel: " + NomHot + "\n" +
				personas + " personas\n" +
				"Fecha: " + fecha + "\n" +
				"Hora: " + hora + "\n");
	}

    public void hacerOtraReserva(View v) {
        Intent envia = new Intent(this, MainActivity.class);
		Bundle datos = new Bundle();
		datos.putString("Nombre", nombre);
		datos.putString("Apellido", Apellido);
		datos.putString("Edad", Edad);
		datos.putString("NumPhone", NumPhone);
		datos.putString("Email", Email);
		datos.putString("NomHot", NomHot);
		datos.putInt("Personas", personas);
		datos.putString("Fecha", fecha);
		datos.putString("Hora", hora);
		envia.putExtras(datos);
		finish();
        startActivity(envia);
    }



}
