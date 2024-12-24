package com.example.ucp2_148.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Barang")
data class Barang(
    @PrimaryKey
    val idbrg:String,
    val namabrg:String,
    val deskripsi:String,
    val harga:Int,
    val stok:Int,
    val namaspl: String
)
