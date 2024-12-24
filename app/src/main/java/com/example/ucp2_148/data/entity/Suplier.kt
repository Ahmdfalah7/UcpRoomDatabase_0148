package com.example.ucp2_148.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Suplier")
data class Suplier(
    @PrimaryKey
    val idspl:String,
    val namaspl:String,
    val kontak:String,
    val alamat:String,
)

