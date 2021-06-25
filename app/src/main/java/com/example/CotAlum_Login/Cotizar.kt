package com.example.CotAlum_Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore

class Cotizar: AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var opciones = arrayOf("Claro", "Filtrasol", "Reflecta", "Tintex", "Satin")//definir estructura
    lateinit var spinnerOpciones: Spinner
    var vidrio = 0
    var totalVidrio = 0
    lateinit var AltoEditText: EditText
    lateinit var ResultadoText: TextView
    lateinit var AnchoEditText: EditText
    lateinit var herrajes: EditText
    lateinit var cotizarButton: Button
    lateinit var emailT: TextView
    lateinit var providerTextView: TextView
    private val db = FirebaseFirestore.getInstance()
    var riel = 0
    var AnchoSob: Double = 0.0
    var AltoSob: Double = 0.0
    var Jamba: Double = 0.0
    var res = 0
    var jamba = 0
    var cercoChapa = 0
    var traslape = 0
    var zoclo = 0
    var cabezal = 0
    var vertical = 0
    var horizontal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizacion)
        spinnerOpciones = findViewById(R.id.spinner)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOpciones.adapter = adaptador
        // para reutilizar el evento.
        spinnerOpciones.onItemSelectedListener = this
        AltoEditText = findViewById(R.id.altoEdit)
        ResultadoText = findViewById(R.id.ResultadoTextView)
        AnchoEditText = findViewById(R.id.anchoEdit)
        herrajes = findViewById(R.id.HerrajesEdit)
        cotizarButton = findViewById(R.id.CotizarButton)
        emailT = findViewById(R.id.emailTextView4)
        providerTextView = findViewById(R.id.providerTextView4)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider: String) {
        title = "Ventana 3"
        emailT.text = email
        providerTextView.text = provider
        cotizarButton.setOnClickListener {
            if (AltoEditText.text.toString().equals("")) {
                Toast.makeText(this, "Fala ingresar la Altura", Toast.LENGTH_LONG).show()
            } else if (AnchoEditText.text.toString().equals("")) {
                Toast.makeText(this, "Fala ingresar el Ancho", Toast.LENGTH_LONG).show()
            } else if(herrajes.text.toString().equals("")) {
                Toast.makeText(this, "Fala ingresar $ los" +
                        " herrajes", Toast.LENGTH_LONG).show()
            }else{
                redondear()
                db.collection("Precios").document(email).get().addOnSuccessListener {
                    riel = (it.get("Riel3") as String?)!!.toInt()
                    jamba = (it.get("Jamba3") as String?)!!.toInt()
                    cercoChapa = (it.get("CercoChapa3") as String?)!!.toInt()
                    traslape = (it.get("Traslape3") as String?)!!.toInt()
                    zoclo = (it.get("Zoclo3") as String?)!!.toInt()
                    cabezal = (it.get("Cabezal3") as String?)!!.toInt()
                    vertical = (it.get("vertical") as String?)!!.toInt()
                    horizontal = (it.get("horizontal") as String?)!!.toInt()
                    res = ((riel * AnchoSob).toInt())
                    var res1 = ((cercoChapa * AltoSob).toInt())
                    var res2 = ((traslape * AltoSob).toInt())
                    var res3 = ((vertical * AltoSob).toInt())
                    var res4 = ((horizontal * AnchoSob).toInt())
                    var res5 = ((zoclo * AnchoSob).toInt())
                    var res6 = ((cabezal * AnchoSob).toInt())
                    var res7 = ((jamba * Jamba).toInt())
                    var herraje = herrajes.text.toString().toInt()
                    totalVidrio = ((((AnchoEditText.text.toString().toDouble()/100) * (AltoEditText.text.toString().toDouble()/100)) * (vidrio)).toInt())
                    var total = (res + res1 + res2 + res3 + res4 + res5 + res6 + res7 + herraje)
                    var total1 = ((total * .65))
                    total1+= total
                    total1+=totalVidrio
                    ResultadoText.text = "$ " + total1.toString()
                }
            }
        }
    }

    private fun redondear() {
        var ancho = AnchoEditText.text.toString().toInt()
        if (ancho <= 50) {
            AnchoSob = 0.5
        } else if ((ancho >= 54) && (ancho <= 100)) {
            AnchoSob = 1.0
        } else if ((ancho >= 104) && (ancho <= 150)) {
            AnchoSob = 1.5
        } else if ((ancho >= 154) && (ancho <= 200)) {
            AnchoSob = 2.0
        } else if ((ancho >= 204) && (ancho <= 250)) {
            AnchoSob = 2.5
        } else if ((ancho >= 254) && (ancho <= 300)) {
            AnchoSob = 3.0
        } else if ((ancho >= 304) && (ancho <= 350)) {
            AnchoSob = 3.5
        } else if ((ancho >= 354) && (ancho <= 400)) {
            AnchoSob = 4.0
        } else if ((ancho >= 404) && (ancho <= 450)) {
            AnchoSob = 4.5
        } else if ((ancho >= 454) && (ancho < 500)) {
            AnchoSob = 5.0
        } else if (ancho >= 500) {
            AnchoSob = 6.0
        }
        var alto = AltoEditText.text.toString().toInt() * 2
        if (alto <= 50) {
            AltoSob = 0.5
        } else if ((alto >= 54) && (alto <= 100)) {
            AltoSob = 1.0
        } else if ((alto >= 104) && (alto <= 150)) {
            AltoSob = 1.5
        } else if ((alto >= 154) && (alto <= 200)) {
            AltoSob = 2.0
        } else if ((alto >= 204) && (alto <= 250)) {
            AltoSob = 2.5
        } else if ((alto >= 254) && (alto <= 300)) {
            AltoSob = 3.0
        } else if ((alto >= 304) && (alto <= 350)) {
            AltoSob = 3.5
        } else if ((alto >= 354) && (alto <= 400)) {
            AltoSob = 4.0
        } else if ((alto >= 404) && (alto <= 450)) {
            AltoSob = 4.5
        } else if ((alto >= 454) && (alto < 500)) {
            AltoSob = 5.0
        } else if (alto >= 500) {
            AltoSob = 6.0
        }
        var jamba = ancho + alto
        if (jamba <= 50) {
            Jamba = 0.5
        } else if ((jamba >= 54) && (jamba <= 100)) {
            Jamba = 1.0
        } else if ((jamba >= 104) && (jamba <= 150)) {
            Jamba = 1.5
        } else if ((jamba >= 154) && (jamba <= 200)) {
            Jamba = 2.0
        } else if ((jamba >= 204) && (jamba <= 250)) {
            Jamba = 2.5
        } else if ((jamba >= 254) && (jamba <= 300)) {
            Jamba = 3.0
        } else if ((jamba >= 304) && (jamba <= 350)) {
            Jamba = 3.5
        } else if ((jamba >= 354) && (jamba <= 400)) {
            Jamba = 4.0
        } else if ((jamba >= 404) && (jamba <= 450)) {
            Jamba = 4.5
        } else if ((jamba >= 454) && (jamba < 500)) {
            Jamba = 5.0
        } else if ((jamba >= 500) && (jamba <= 610)) {
            Jamba = 6.0
        } else if ((jamba >= 610)) {
            Jamba = Jamba
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position==0) {
            vidrio = 450
        }else if (position==1) {
            vidrio = 740
        }else if (position==2) {
            vidrio = 1260
        }else if (position==3) {
            vidrio = 765
        }else if (position==4) {
            vidrio = 700
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}