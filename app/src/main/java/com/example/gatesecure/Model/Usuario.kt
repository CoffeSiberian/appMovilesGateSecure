package com.example.gatesecure.Model

data class Usuario (
    var id: String,
    var name: String? = null,
    var rfid: String? = null,
    var azlevel: Int? = null,
)