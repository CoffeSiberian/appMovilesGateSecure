package com.example.gatesecure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.gatesecure.databinding.ActivityProfileUserBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileUser : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUserBinding
    private lateinit var actionBar: ActionBar
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Perfil"

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        binding.btnCRUD.setOnClickListener {
            startActivity(Intent(this, CrudActivity::class.java))
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val email = firebaseUser.email
            binding.tvEmail.text = email
        }
        else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}