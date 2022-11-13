package com.example.gatesecure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.gatesecure.databinding.ActivityProfileUserBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileUser : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityProfileUserBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configuracion de la barra de acciones
        actionBar = supportActionBar!!
        actionBar.title = "Perfil"

        //Verificar si el usuario esta logueado
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, cerrar sesion
        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        //ir al crud
        binding.btnCRUD.setOnClickListener {
            startActivity(Intent(this, CrudActivity::class.java))
        }


    }

    private fun checkUser() {
        //obtener usuario actual
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            //usuario esta logueado
            //obtener email del usuario
            val email = firebaseUser.email
            //setear email en textview
            binding.tvEmail.text = email
        }
        else{
            //usuario no esta logueado, ir a la actividad main
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}