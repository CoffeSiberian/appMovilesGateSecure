package com.example.gatesecure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import com.example.gatesecure.Adapter.AdapterUsuarios
import com.example.gatesecure.Model.Usuario
import com.example.gatesecure.databinding.ActivityCrudBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.reflect.typeOf

class ViewUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrudBinding
    private lateinit var actionBar: ActionBar
    private lateinit var productosRecyclerView: RecyclerView

    private lateinit var adapterProductos: AdapterUsuarios
    private lateinit var listUsuarios: ArrayList<Usuario>

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Agregar Usuario"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        database = Firebase.database.reference

        var getData = database.child("Usuarios").get()

        getData.addOnSuccessListener {
            var arr = it.children

            for (r in arr){
                Log.i("firebase", "${r.getValue<Usuario>()}")

            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

    }
}