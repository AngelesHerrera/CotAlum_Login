package com.example.CotAlum_Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var emailT: TextView
    lateinit var providerTextView: TextView
    lateinit var signUp: Button
    lateinit var loginB: Button
    lateinit var emailE: EditText
    lateinit var pass: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        //Splash
        Thread.sleep(2000) // HACK:
        //setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //analytics Event
        signUp = findViewById(R.id.signUpButton)
        loginB = findViewById(R.id.logInButton)
        emailE = findViewById(R.id.emailEditText)
        pass = findViewById(R.id.passwordEditText)
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase completa")
        analytics.logEvent("InitScreen", bundle)
        //setup()
        //if (getSharedPreferences("mis datos", Context.MODE_PRIVATE).toString().isNotEmpty()) {
            //setContentView(R.layout.activity_home)
        //} else {

          setup()
       // }

    }

    //crear usuario
    private fun setup() {
        title = "Autentificacion"
        signUp.setOnClickListener {
            if (emailE.text.isNotEmpty() && pass.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailE.text.toString(), pass.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                                /*val preferencias = getSharedPreferences("mis datos", Context.MODE_PRIVATE)
                                val nuevo = emailE.text.toString()
                                val nuevo1 = pass.text.toString()
                                val editor = preferencias.edit()
                                editor.putString("email", nuevo)
                                editor.putString("contraseña", nuevo1)
                                editor.commit()
                                emailT.text=nuevo
                                providerTextView.text=nuevo1*/
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
        //acceder
        loginB.setOnClickListener {
            if (emailE.text.isNotEmpty() && pass.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailE.text.toString(), pass.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                            /*val preferencias = getSharedPreferences("mis datos", Context.MODE_PRIVATE)
                            val nuevo = emailE.text.toString()
                            val nuevo1 = pass.text.toString()
                            val editor = preferencias.edit()
                            editor.putString("email", nuevo)
                            editor.putString("contraseña", nuevo1)
                            editor.commit()
                            emailT.text=nuevo
                            providerTextView.text=nuevo1*/
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                            Toast.makeText(this, "accedido", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Hubo un error al ingresar", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }


    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}

