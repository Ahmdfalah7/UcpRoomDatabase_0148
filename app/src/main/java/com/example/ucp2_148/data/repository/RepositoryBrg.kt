package com.example.ucp2_148.data.repository

import com.example.ucp2_148.data.entity.Barang
import kotlinx.coroutines.flow.Flow

interface RepositoryBrg {
    suspend fun insertBrg(barang: Barang)
    fun getAllBrg() : Flow<List<Barang>>
    //getBrg
    fun getBrg(id : String): Flow<Barang>
    //deleteBrg
    suspend fun deleteBrg(barang: Barang)
    //updateBrg
    suspend fun updateBrg(barang: Barang)
}