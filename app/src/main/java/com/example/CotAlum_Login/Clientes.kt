package com.example.CotAlum_Login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Clientes : AppCompatActivity() {
    lateinit var emailT: TextView
    lateinit var providerTextView: TextView
    lateinit var buscarTextView: EditText
    lateinit var clienteTextView: EditText
    lateinit var addressTextView: EditText
    lateinit var phoneTextView: EditText
    lateinit var saveButton: Button
    lateinit var buscarButton: Button
    lateinit var getButton: Button
    lateinit var deleteButton: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        emailT = findViewById(R.id.emailTextView2)
        providerTextView = findViewById(R.id.providerTextView2)
        buscarTextView = findViewById(R.id.BuscarTextView)
        clienteTextView = findViewById(R.id.ClienteTextView)
        addressTextView = findViewById(R.id.addresstextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        saveButton=findViewById(R.id.saveButton)
        buscarButton=findViewById(R.id.BuscarButton)
        deleteButton = findViewById(R.id.deleteButton)
        getButton=findViewById(R.id.getButton)

        //Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")
        //guardado de datos

    }

    private fun setup(email: String, provider: String) {
        title = "Registrar Clientes"
        emailT.text = email
        providerTextView.text = provider

        saveButton.setOnClickListener {
            db.collection("Clientes").document(phoneTextView.text.toString()).set(
                hashMapOf("provider" to provider,
                    "cliente" to clienteTextView.text.toString(),
                    "address" to addressTextView.text.toString(),
                    "phone" to phoneTextView.text.toString()
                )
            )
            Toast.makeText(this, "se guardo", Toast.LENGTH_LONG).show()
        }
        getButton.setOnClickListener {
            db.collection("Clientes").document(phoneTextView.text.toString()).get().addOnSuccessListener {
                clienteTextView.setText(it.get("cliente")as String?)
                addressTextView.setText(it.get("address") as String?)
                phoneTextView.setText(it.get("phone") as String?)
                Toast.makeText(this, "Datos modificados", Toast.LENGTH_LONG).show()
            }
        }
        buscarButton.setOnClickListener {
            db.collection("Clientes").document(buscarTextView.text.toString()).get().addOnSuccessListener{
                if(it.exists()){
                    clienteTextView.setText(it.get("cliente")as String?)
                    addressTextView.setText(it.get("address") as String?)
                    phoneTextView.setText(it.get("phone") as String?)
                    Toast.makeText(this, "Datos Encontrados", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Datos no Encontrados", Toast.LENGTH_LONG).show()
                }

            }

        }
        deleteButton.setOnClickListener {
            db.collection("Clientes").document(email).delete()
            Toast.makeText(this, "se elimino", Toast.LENGTH_LONG).show()
        }
    }
}