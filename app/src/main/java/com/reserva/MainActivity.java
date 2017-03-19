package com.reserva;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnSeekBarChangeListener,
		OnClickListener, OnDateSetListener, OnTimeSetListener {

	EditText nombre; //objeto
	EditText Apellido;
	EditText Edad;
	EditText NumPhone;
	EditText NomHotel;
	EditText Email;
	TextView cuantasPersonas;
	Button fecha, hora; //vista
	SeekBar barraPersonas;

	SimpleDateFormat horaFormato, fechaFormato;

	String nombreReserva = "";
	String numPersonas = "";
	String fechaSel = "", horaSel = "";
	Date fechaConv;
	String cuantasPersonasFormat = "";
	int personas = 1; // Valor por omision, al menos 1 persona tiene que reservar

	Calendar calendario;

	@Override
	protected void onCreate(Bundle savedInstanceState) { //
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bundle datos = new Bundle();
		datos = this.getIntent().getExtras();
		if(datos !=null){

			TextView Muestrame;
			String NombreV ;
			String ApellidoV;
			String EdadV;
			String NumPhoneV;
			String NomHotV;
			String EmailV;
			int PersonasV;
			String FechaV;
			String HoraV;

			Muestrame = (TextView) findViewById(R.id.txtMuestra);
			NombreV = datos.getString("Nombre");
			ApellidoV = datos.getString("Apellido");
			EdadV = datos.getString("Edad");
			NumPhoneV = datos.getString("NumPhone");
			EmailV = datos.getString("Email");
			NomHotV = datos.getString("NomHot");
			PersonasV = datos.getInt("Personas");
			FechaV = datos.getString("Fecha");
			HoraV = datos.getString("Hora");

			Muestrame.setText("Registros Anteriores: " + "\n" +
					"Nombre: " + NombreV +" " + ApellidoV + "\n" +
					"Edad: " + EdadV + "\n" +
					"Numero de Telefono: " + NumPhoneV + "\n" +
					"Email: " + EmailV + "\n" +
					"Nombre del Hotel: " + NomHotV + "\n" +
					"Cantidad de Personas: " + PersonasV + "\n" +
					"Fecha: " + FechaV + "\n" +
					"Hora: " + HoraV + "\n"
			);



		}


		cuantasPersonas = (TextView) findViewById(R.id.cuantasPersonas);
		barraPersonas = (SeekBar) findViewById(R.id.personas);

		fecha = (Button) findViewById(R.id.fecha);
		hora = (Button) findViewById(R.id.hora);

		barraPersonas.setOnSeekBarChangeListener(this);
		nombre = (EditText) findViewById(R.id.nombre);
		Apellido = (EditText) findViewById(R.id.txtApellido);
		Edad = (EditText) findViewById(R.id.txtEdad);
		NumPhone = (EditText) findViewById(R.id.txtNumPhone);
		Email = (EditText) findViewById(R.id.txtEmail);
		NomHotel = (EditText) findViewById(R.id.txtNomHotel);

		cuantasPersonasFormat = cuantasPersonas.getText().toString();
		// cuantasPersonasFormat = "personas: %d";
		cuantasPersonas.setText("Personas: 1"); // condicion inicial

		// Para seleccionar la fecha y la hora
		Calendar fechaSeleccionada = Calendar.getInstance();
		fechaSeleccionada.set(Calendar.HOUR_OF_DAY, 12); // hora inicial
		fechaSeleccionada.clear(Calendar.MINUTE); // 0
		fechaSeleccionada.clear(Calendar.SECOND); // 0

		// formatos de la fecha y hora
		fechaFormato = new SimpleDateFormat(fecha.getText().toString());
		horaFormato = new SimpleDateFormat("HH:mm");
		// horaFormato = new SimpleDateFormat(hora.getText().toString());

		// La primera vez mostramos la fecha actual
		Date fechaReservacion = fechaSeleccionada.getTime();
		fechaSel = fechaFormato.format(fechaReservacion);
		fecha.setText(fechaSel); // fecha en el

		horaSel = horaFormato.format(fechaReservacion);
		// boton
		hora.setText(horaSel); // hora en el boton

		// Otra forma de ocupar los botones
		fecha.setOnClickListener(this);
		hora.setOnClickListener(this);

	}

	@Override
	public void onProgressChanged(SeekBar barra, int progreso,
			boolean delUsuario) {

		numPersonas = String.format(cuantasPersonasFormat,
				barraPersonas.getProgress() + 1);
		personas = barraPersonas.getProgress() + 1; // este es el valor que se
													// guardara en la BD
		// Si no se mueve la barra, enviamos el valor personas = 1
		cuantasPersonas.setText(numPersonas);
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
	}

	@Override
	public void onClick(View v) {
		if (v == fecha) {
			Calendar calendario = parseCalendar(fecha.getText(), fechaFormato);
			new DatePickerDialog(this, this, calendario.get(Calendar.YEAR),
					calendario.get(Calendar.MONTH),
					calendario.get(Calendar.DAY_OF_MONTH)).show();
		} else if (v == hora) {
			Calendar calendario = parseCalendar(hora.getText(), horaFormato);
			new TimePickerDialog(this, this,
					calendario.get(Calendar.HOUR_OF_DAY),
					calendario.get(Calendar.MINUTE), false) // /true = 24 horas
					.show();
		}
	}

	private Calendar parseCalendar(CharSequence text,
			SimpleDateFormat fechaFormat2) {
		try {
			fechaConv = fechaFormat2.parse(text.toString());
		} catch (ParseException e) { // import java.text.ParsedExc
			throw new RuntimeException(e);
		}
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaConv);
		return calendario;
	}

	@Override
	public void onDateSet(DatePicker picker, int anio, int mes, int dia) {
		calendario = Calendar.getInstance();
		calendario.set(Calendar.YEAR, anio);
		calendario.set(Calendar.MONTH, mes);
		calendario.set(Calendar.DAY_OF_MONTH, dia);

		fechaSel = fechaFormato.format(calendario.getTime());
		fecha.setText(fechaSel);

	}

	public void onTimeSet(TimePicker picker, int horas, int minutos) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, horas);
		calendar.set(Calendar.MINUTE, minutos);

		horaSel = horaFormato.format(calendar.getTime());
		hora.setText(horaSel);
	}

	public void reserva(View v) {
		Intent envia = new Intent(this, Actividad2.class);
		Bundle datos = new Bundle();
		datos.putString("Nombre", nombre.getText().toString().trim());
		datos.putString("Apellidos", Apellido.getText().toString().trim());
		datos.putString("Edad", Edad.getText().toString().trim());
		datos.putString("NumPhone", NumPhone.getText().toString().trim());
		datos.putString("Email", Email.getText().toString().trim());
		datos.putString("NomHot", NomHotel.getText().toString().trim());
		datos.putInt("Personas", personas);
		datos.putString("Fecha", fechaSel);
		datos.putString("Hora", horaSel);
		envia.putExtras(datos);
		finish();
		startActivity(envia);
	}
}
