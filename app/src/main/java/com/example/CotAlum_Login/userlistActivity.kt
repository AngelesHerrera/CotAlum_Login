package com.example.CotAlum_Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class userlistActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private  lateinit var userRecyclerView : RecyclerView
    private  lateinit var userArraylist : ArrayList<User>
    private val bd : CollectionReference
    init {
        FirebaseApp.initializeApp(this)
        bd = FirebaseFirestore.getInstance().collection("Clientes")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        userRecyclerView =findViewById(R.id.userList)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userArraylist = arrayListOf<User>()
       // getUserData()


    }

    private fun getUserData() {

      dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(User::class.java)
                        userArraylist.add(user!!)

                    }

                    userRecyclerView.adapter = MyAdapter(userArraylist)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}