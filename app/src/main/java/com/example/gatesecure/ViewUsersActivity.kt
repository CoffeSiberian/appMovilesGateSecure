package com.example.gatesecure

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gatesecure.Adapter.AdapterUsuarios
import com.example.gatesecure.Model.Usuario
import com.example.gatesecure.databinding.ActivityCrudBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ViewUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrudBinding
    private lateinit var actionBar: ActionBar
    private lateinit var usuariosRecyclerView: RecyclerView
    private lateinit var adapterUsuarios: AdapterUsuarios
    private lateinit var database: DatabaseReference
    private var listUsuarios = ArrayList<Usuario?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuariosRecyclerView = binding.rvUsuarios

        usuariosRecyclerView.layoutManager = LinearLayoutManager(this)
        usuariosRecyclerView.setHasFixedSize(true)

        actionBar = supportActionBar!!
        actionBar.title = "Usuarios"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        getUsers(usuariosRecyclerView)

        binding.bottomBar.setOnClickListener{
            startActivity(Intent(this, AddUserActivity::class.java))
        }
    }

    private fun getUsers(rview: RecyclerView) {
        database = Firebase.database.reference
        val getData = database.child("Usuarios")
        dataChange(getData, rview)
        loadData(getData, rview)
    }

    private fun delUser(database: DatabaseReference, name: String){
        delDialog(this, database, name)
    }

    private fun dataChange(database: DatabaseReference, rview: RecyclerView){
        val usersListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadData(database, rview)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Cancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(usersListener)
    }

    private fun loadData(database: DatabaseReference, rview: RecyclerView){
        database.get().addOnSuccessListener {
            val arr = it.children
            listUsuarios.clear()
            for (r in arr){
                listUsuarios.add(r.getValue<Usuario>())
            }
            viewLoad(rview)
        }.addOnFailureListener{
            Toast.makeText(this, "Error al recuperar los datos: $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewLoad(rview: RecyclerView){
        val adapterUsuarios = AdapterUsuarios(listUsuarios)
        val context = this
        rview.adapter = adapterUsuarios
        adapterUsuarios.setOnItemClickListener(object : AdapterUsuarios.onItemClickListener{
            override fun onItemClick(position: Int, id: String, nameClick: String, name: String) {
                if (nameClick == "cedit"){
                    val refEdit = database.child("Usuarios")
                    Log.w("dev", nameClick)
                    editDialog(context, refEdit, name, id)
                }
                if (nameClick == "cdelete"){
                    val refDel = database.child("Usuarios").child(id)
                    delUser(refDel, name)
                }
            }
        })
    }

    private fun editDialog(context: Context, database: DatabaseReference, name: String, id: String){
        val view: View = View.inflate(this, R.layout.activity_edit_usuario, null)
        MaterialAlertDialogBuilder(context)
            .setTitle("Estas editando al usuario $name")
            .setPositiveButton(resources.getString(R.string.modalEditAccept)) { dialog, which ->
                val newName: EditText = view.findViewById(R.id.edname)
                val newLevel: EditText = view.findViewById(R.id.edlevel)
                val newRfid: EditText = view.findViewById(R.id.edrfid)

                if (newName.text.toString().trim().isEmpty()){
                    newName.error = "Ingrese el nombre del usuario"
                }
                else if (newLevel.text.toString().trim().isEmpty()){
                    newLevel.error = "Ingrese un nivel de acceso"
                }
                else if (newRfid.text.toString().trim().isEmpty()){
                    newRfid.error = "Ingrese el RFID de la tarjeta"
                }else {
                    val userUpdate = Usuario(
                        id,
                        newName.text.toString().trim(),
                        newRfid.text.toString().trim(),
                        newLevel.text.toString().trim(),
                    )
                    val userUpdateValue = userUpdate.toMap()

                    val childUpdates = hashMapOf<String, Any>(
                        "/$id" to userUpdateValue,
                    )
                    database.updateChildren(childUpdates)
                    dialog.cancel()
                }
            }
            .setNegativeButton(resources.getString(R.string.modalDelCancel)) { dialog, which ->
                dialog.cancel()
            }
            .setView(view)
            .show()
    }

    private fun delDialog(context: Context, database: DatabaseReference, name: String){
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.modalDelTitle))
            .setMessage("Estas intentando borrar al usuario $name. Recuerda que esta acción es permanente")
            .setPositiveButton(resources.getString(R.string.modalDelAccept)) { dialog, which ->
                database.removeValue().addOnSuccessListener {
                    Toast.makeText(this, "Usuario borrado", Toast.LENGTH_SHORT).show()
                }
                dialog.cancel()
            }
            .setNegativeButton(resources.getString(R.string.modalDelCancel)) { dialog, which ->
                dialog.cancel()
            }
            .show()
    }

}