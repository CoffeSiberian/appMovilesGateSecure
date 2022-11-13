package com.example.gatesecure

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.gatesecure.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var fireBaseAuth: FirebaseAuth
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Iniciar Secion"

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Cargando...")
        progressDialog.setMessage("Iniciando secion")
        progressDialog.setCanceledOnTouchOutside(false)

        fireBaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnLogin.setOnClickListener {
            validarDatos()
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun validarDatos() {
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Correo invalido"
        } else if (TextUtils.isEmpty(password)){
            binding.etPassword.error = "Ingrese una contraseña"
        }else if (password.length < 6) {
            binding.etPassword.error = "Contraseña debe tener al menos 6 caracteres"
        } else {
            firebaseLogin(email, password)
        }
    }

    private fun firebaseLogin(email: String, password: String) {
        progressDialog.show()
        fireBaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = fireBaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Bienvenido $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileUser::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Error al iniciar sesion con el correo $email", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = fireBaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, ProfileUser::class.java))
            finish()
        }
    }
}