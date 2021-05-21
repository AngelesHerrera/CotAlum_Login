package com.example.CotAlum_Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


enum class ProviderType{
    BASIC,
}
class HomeActivity : AppCompatActivity(){
    lateinit var emailT: TextView
    lateinit var providerTextView: TextView
    lateinit var logOutButton: Button
    lateinit var preciosButton: Button
    lateinit var clientesButton: Button
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        emailT = findViewById(R.id.emailTextView)
        providerTextView = findViewById(R.id.providerTextView)
        logOutButton=findViewById(R.id.logOutButton)
        preciosButton=findViewById(R.id.PreciosButton)
        clientesButton=findViewById(R.id.ClientesButton)
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
//            setContentView(R.layout.activity_precios)
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
}