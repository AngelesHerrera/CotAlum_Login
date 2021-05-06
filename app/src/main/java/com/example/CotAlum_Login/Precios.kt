package com.example.CotAlum_Login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class Precios : AppCompatActivity() {
    lateinit var emailT: TextView
    lateinit var providerTextView: TextView
    lateinit var saveButton: Button
    lateinit var getButton: Button
    lateinit var deleteButton: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_precios)

        emailT = findViewById(R.id.emailTextView2)
        providerTextView = findViewById(R.id.providerTextView2)
        saveButton=findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)
        getButton=findViewById(R.id.getButton)

        //Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

    }

    private fun setup(email: String, provider: String) {
        title = "Precios"
        emailT.text = email
        providerTextView.text = provider

        saveButton.setOnClickListener {
            db.collection("users").document(email).set(
                    hashMapOf("provider" to provider,

                    )
            )
            Toast.makeText(this, "se guardo", Toast.LENGTH_LONG).show()
        }
        getButton.setOnClickListener {
            db.collection("users").document(email).get().addOnSuccessListener {

                Toast.makeText(this, "Datos recuperados", Toast.LENGTH_LONG).show()
            }
        }
        deleteButton.setOnClickListener {
            db.collection("users").document(email).delete()
            Toast.makeText(this, "se elimino", Toast.LENGTH_LONG).show()
        }
    }
}