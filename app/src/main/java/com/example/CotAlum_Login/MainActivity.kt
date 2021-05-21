package com.example.CotAlum_Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var authLayout: LinearLayout
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
        authLayout = findViewById(R.id.authLayout)
        signUp = findViewById(R.id.signUpButton)
        loginB = findViewById(R.id.logInButton)
        emailE = findViewById(R.id.emailEditText)
        pass = findViewById(R.id.passwordEditText)
        //analitics event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase completa")
        analytics.logEvent("InitScreen", bundle)
        //setup()


        setup()
        session()

    }

    override fun onStart() {
        super.onStart()
        authLayout.visibility =View.VISIBLE
    }
    private fun session(){
        val prefs  = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email",null)
        val provider= prefs.getString("provider",null)
        if(email != null && provider !=null){
            authLayout.visibility = View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }

    }
    //crear usuario
    private fun setup() {
        title = "Autentificacion"
        signUp.setOnClickListener {
            if (emailE.text.isNotEmpty() && pass.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailE.text.toString(), pass.text.toString()).addOnCompleteListener {
                            if (it.isSuccessful) {
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
                                Toast.makeText(this, "accedido", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this, "Hubo un error al ingresar, verifica tu Conexion", Toast.LENGTH_LONG).show()
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