package com.example.gatesecure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.gatesecure.Model.Usuario
import com.example.gatesecure.databinding.ActivityAddUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var actionBar: ActionBar
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Agregar Usuario"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        database = FirebaseDatabase.getInstance().getReference("Usuarios")

        binding.btnAdd.setOnClickListener {
            val nombre: String = binding.cName.text.toString().trim()
            val rfid: String = binding.crfid.text.toString().trim()
            val azlevel: String = binding.cazlevel.text.toString().trim()

            val id = database.child("Usuarios").push().key

            if (nombre.isEmpty()){
                binding.cName.error = "Ingrese el nombre del usuario"
            }
            if (rfid.isEmpty()){
                binding.crfid.error = "Ingrese el RFID de la tarjeta"
            }
            if (azlevel.isEmpty()){
                binding.cazlevel.error = "Ingrese el nivel de acceso"
            }else{
                val usuario = Usuario(id = id, name = nombre, rfid = rfid, azlevel = azlevel)
                if (id != null) {
                    database.child(id).setValue(usuario).addOnSuccessListener {
                        binding.cName.setText("")
                        binding.crfid.setText("")
                        binding.cazlevel.setText("")
                        Toast.makeText(this, "Usuario agregado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}