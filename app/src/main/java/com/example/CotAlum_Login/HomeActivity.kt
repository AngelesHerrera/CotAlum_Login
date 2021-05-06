package com.example.CotAlum_Login

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
    lateinit var usuariosButton: Button
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        emailT = findViewById(R.id.emailTextView)
        providerTextView = findViewById(R.id.providerTextView)
        logOutButton=findViewById(R.id.logOutButton)
        preciosButton=findViewById(R.id.PreciosButton)
        usuariosButton=findViewById(R.id.UsuariosButton)
        //Setup
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider= bundle?.getString("provider")
        setup(email ?:"",provider ?: "")
        //guardado de datos

    }
    private fun setup(email: String, provider: String){
        title ="Inicio"
        emailT.text = email
        providerTextView.text = provider

        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
        usuariosButton.setOnClickListener {
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
        val homeIntent = Intent(this, Usuarios::class.java).apply {
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