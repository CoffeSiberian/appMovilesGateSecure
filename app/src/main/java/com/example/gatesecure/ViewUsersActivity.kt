package com.example.gatesecure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gatesecure.Adapter.AdapterUsuarios
import com.example.gatesecure.Model.Usuario
import com.example.gatesecure.R
import com.google.firebase.database.*

class ViewUsersActivity : AppCompatActivity() {
    private lateinit var productosRecyclerView: RecyclerView
    private lateinit var adapterProductos: AdapterUsuarios
    private lateinit var listUsuarios: ArrayList<Usuario>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        productosRecyclerView.layoutManager = LinearLayoutManager(this)
        productosRecyclerView.setHasFixedSize(true)

        listUsuarios = arrayListOf<Usuario>()
    }
}