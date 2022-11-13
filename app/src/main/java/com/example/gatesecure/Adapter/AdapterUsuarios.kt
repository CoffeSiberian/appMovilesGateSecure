package com.example.gatesecure.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gatesecure.Model.Usuario
import com.example.gatesecure.R

class AdapterUsuarios (private val usuarios: ArrayList<Usuario>) : RecyclerView.Adapter<AdapterUsuarios.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.users_card, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val usuario = usuarios[position]
            holder.id.text = usuario.id
            holder.name.text = usuario.name
            holder.rfid.text = usuario.rfid
            holder.azlevel.text = usuario.azlevel.toString()
        }

        override fun getItemCount(): Int {
            return usuarios.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name: TextView = itemView.findViewById(R.id.cname)
            val id: TextView = itemView.findViewById(R.id.cid)
            val rfid: TextView = itemView.findViewById(R.id.crfid)
            val azlevel: TextView = itemView.findViewById(R.id.cazlevel)
        }

}