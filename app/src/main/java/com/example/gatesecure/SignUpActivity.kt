package com.example.gatesecure

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.gatesecure.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email:String = ""
    private var password:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Crear Cuenta"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor espere")
        progressDialog.setMessage("Creando cuenta")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            validarDatos()
        }
    }

    private fun validarDatos() {
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error = "Email no valido"
        }else if (TextUtils.isEmpty(password)){
            binding.etPassword.error = "Ingrese una contraseña"
        }
        else if(password.length < 6){
            binding.etPassword.error = "Password debe tener al menos 6 caracteres"
        }
        else{
            firebaseRegister(email, password)
        }
    }

    private fun firebaseRegister(email: String, password: String) {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Registrado con el email: $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, crudActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}