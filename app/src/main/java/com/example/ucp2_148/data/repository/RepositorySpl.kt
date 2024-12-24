package com.example.ucp2_148.data.repository

import com.example.ucp2_148.data.entity.Suplier
import kotlinx.coroutines.flow.Flow

interface RepositorySpl {
    suspend fun insertSpl(suplier: Suplier)
    fun getAllSpl() : Flow<List<Suplier>>
    //getSpl
    fun getSpl(id : String): Flow<Suplier>
}