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
    //precios
    lateinit var riel2: EditText
    lateinit var riel3: EditText
    lateinit var jamba2: EditText
    lateinit var jamba3: EditText
    lateinit var cercoChapa2: EditText
    lateinit var cercoChapa3: EditText
    lateinit var traslape2: EditText
    lateinit var traslape3: EditText
    lateinit var zoclo2: EditText
    lateinit var zoclo3: EditText
    lateinit var cabezal2: EditText
    lateinit var cabezal3: EditText
    lateinit var vertical: EditText
    lateinit var horizontal: EditText
    //Botones
    lateinit var saveButton: Button
    lateinit var getButton: Button
    lateinit var deleteButton: Button
    //instancia a firebase
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_precios)

        emailT = findViewById(R.id.emailTextView3)
        providerTextView = findViewById(R.id.providerTextView3)
        //editText
        riel2 = findViewById(R.id.riel2)
        riel3 = findViewById(R.id.riel3)
        jamba2 = findViewById(R.id.jamba2)
        jamba3 = findViewById(R.id.jamba3)
        cercoChapa2= findViewById(R.id.cercochapa2)
        cercoChapa3= findViewById(R.id.cercochapa3)
        traslape2= findViewById(R.id.traslape2)
        traslape3= findViewById(R.id.traslape3)
        zoclo2= findViewById(R.id.zoclopuerta2)
        zoclo3= findViewById(R.id.zoclopuerta3)
        cabezal2= findViewById(R.id.cabezal2)
        cabezal3= findViewById(R.id.cabezal3)
        vertical= findViewById(R.id.verticalMosquitero)
        horizontal= findViewById(R.id.horizontalMosquitero)
        //botones
        saveButton=findViewById(R.id.saveButton2)
        deleteButton = findViewById(R.id.deleteButton2)
        getButton=findViewById(R.id.getButton2)

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
            db.collection("Precios").document(email).set(
                    hashMapOf("provider" to provider,
                            "Riel2" to riel2.text.toString(),"Riel3" to riel3.text.toString(),
                            "Jamba2" to jamba2.text.toString(),"Jamba3" to jamba3.text.toString(),
                            "CercoChapa2" to cercoChapa2.text.toString(),"CercoChapa3" to cercoChapa3.text.toString(),
                            "Traslape2" to traslape2.text.toString(),"Traslape3" to traslape3.text.toString(),
                            "Zoclo2" to zoclo2.text.toString(),"Zoclo3" to zoclo3.text.toString(),
                            "Cabezal2" to cabezal2.text.toString(),"Cabezal3" to cabezal3.text.toString(),
                            "vertical" to vertical.text.toString(),"horizontal" to horizontal.text.toString()
                    )
            )
            Toast.makeText(this, "se guardo", Toast.LENGTH_LONG).show()
        }
        getButton.setOnClickListener {
            db.collection("Precios").document(email).get().addOnSuccessListener {
                riel2.setText(it.get("Riel2") as String?)
                riel3.setText(it.get("Riel3") as String?)
                jamba2.setText(it.get("Jamba2") as String?)
                jamba3.setText(it.get("Jamba3") as String?)
                cercoChapa2.setText(it.get("CercoChapa2") as String?)
                cercoChapa3.setText(it.get("CercoChapa3") as String?)
                traslape2.setText(it.get("Traslape2") as String?)
                traslape3.setText(it.get("Traslape3") as String?)
                zoclo2.setText(it.get("Zoclo2") as String?)
                zoclo3.setText(it.get("Zoclo3") as String?)
                cabezal2.setText(it.get("Cabezal2") as String?)
                cabezal3.setText(it.get("Cabezal3") as String?)
                vertical.setText(it.get("vertical") as String?)
                horizontal.setText(it.get("horizontal") as String?)
                Toast.makeText(this, "Datos recuperados", Toast.LENGTH_LONG).show()
            }
        }
        deleteButton.setOnClickListener {
            db.collection("Precios").document(email).delete()
            Toast.makeText(this, "se elimino", Toast.LENGTH_LONG).show()
        }
    }
}