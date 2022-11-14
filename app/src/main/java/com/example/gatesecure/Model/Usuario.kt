package com.example.gatesecure.Model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Usuario (
    var id: String? = null,
    var name: String? = null,
    var rfid: String? = null,
    var azlevel: String? = null,
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "rfid" to rfid,
            "azlevel" to azlevel,
        )
    }
}