package com.example.CotAlum_Login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    //private val GOOGLE_SIGN_IN = 100

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

        //setup
        setup()
        //session()
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
                        } else {
                            showAlert()
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
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autentificando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}
    //override fun onStart() {
    //    super.onStart()
    //   authLayout.visibility = View.VISIBLE
    //}

    // private fun session() {
    //   val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
    //  val email = prefs.getString("email", null)
    //  val provider = prefs.getString("provider", null)

    // if (email != null && provider != null) {
    //    authLayout.visibility = View.INVISIBLE
    //    showHome(email, ProviderType.valueOf(provider))
    // }

    // }

