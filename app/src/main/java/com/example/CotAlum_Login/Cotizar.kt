package com.example.CotAlum_Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class Cotizar: AppCompatActivity(), AdapterView.OnItemSelectedListener {
        var opciones = arrayOf("Claro","Filtrasol","Reflecta","Tintex","Satin")//definir estructura
        lateinit var spinnerOpciones: Spinner
        var vidrio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizacion)
        spinnerOpciones = findViewById(R.id.spinner)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOpciones.adapter = adaptador
        // para reutilizar el evento.
        spinnerOpciones.onItemSelectedListener = this
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //
        if (position==0){
            vidrio = 450
        Log.v("SpinnerApp","Se seleccionó")}
        else if (position==1)
           vidrio = 740
        else if (position==2)
            vidrio = 1260
        else if (position==3)
            vidrio = 765
        else if (position==4)
            vidrio = 700
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}