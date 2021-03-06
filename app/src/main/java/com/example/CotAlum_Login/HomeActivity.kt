package com.example.CotAlum_Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


enum class ProviderType{
    BASIC,
}
class HomeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    //spinner
    var opciones = arrayOf("Ventana3","ventana2","Proyeccion", "Ventana1_1/2")//definir estructura
    lateinit var spinnerOpciones: Spinner
    //botones
    lateinit var emailT: TextView
    lateinit var providerTextView: TextView
    lateinit var logOutButton: Button
    lateinit var preciosButton: Button
    lateinit var clientesButton: Button
    lateinit var cotizarButton: Button
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        spinnerOpciones = findViewById(R.id.spinner)
        emailT = findViewById(R.id.emailTextView)
        providerTextView = findViewById(R.id.providerTextView)
        logOutButton=findViewById(R.id.logOutButton)
        preciosButton=findViewById(R.id.PreciosButton)
        clientesButton=findViewById(R.id.CotizarButton)
        cotizarButton=findViewById(R.id.CotizacionButton)
        //Setup
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider= bundle?.getString("provider")
        setup(email ?:"",provider ?: "")
        //guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider",provider)
        prefs.apply()
        //spinner()
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOpciones.adapter = adaptador
        // para reutilizar el evento.
        spinnerOpciones.onItemSelectedListener = this


    }
    private fun setup(email: String, provider: String){
        title ="Inicio"
        emailT.text = email
        providerTextView.text = provider

        logOutButton.setOnClickListener {
            //Borrado de datos
            val prefs  = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
        clientesButton.setOnClickListener {
            FirebaseAuth.getInstance()
            showHome(email ?: "", ProviderType.BASIC)
        }
        preciosButton.setOnClickListener {
            FirebaseAuth.getInstance()
            showHomePrecios(email ?: "", ProviderType.BASIC)
        }
        cotizarButton.setOnClickListener {
            FirebaseAuth.getInstance()
            showHomeCotizar(email ?: "", ProviderType.BASIC)
        }

    }
    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, Clientes::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
    private fun showHomePrecios(email: String, provider: ProviderType) {
        val Intent = Intent(this, Precios::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(Intent)
    }
    private fun showHomeCotizar(email: String, provider: ProviderType) {
        val Intent = Intent(this, Cotizar::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(Intent)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position==0) {
            // val email = "02angelesherrera@gmail.com"
            //FirebaseAuth.getInstance()
           // showHomeCotizar(email, ProviderType.BASIC)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}