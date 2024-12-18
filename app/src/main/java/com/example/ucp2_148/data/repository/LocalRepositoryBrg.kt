package com.example.ucp2_148.data.repository

import com.example.ucp2_148.data.dao.BarangDao
import com.example.ucp2_148.data.entity.Barang
import kotlinx.coroutines.flow.Flow

class LocalRepositoryBrg {
    private val barangDao: BarangDao
    ) : RepositoryBrg
    {
        override suspend fun insertBrg(barang: Barang) {
            barangDao.insertBarang(barang)
        }

        //getAllBrg
        override fun getAllBrg(): Flow<List<Barang>> {
            return barangDao.getAllBarang()
        }

        //getBrg
        override fun getBarang(id: String): Flow<Barang> {
            return barangDao.getBarang(id)
        }

        //deleteBrg
        override suspend fun deleteBrg(barang: Barang) {
            barangDao.deleteBarang(barang)
        }

        //updateBrg
        override suspend fun updateBrg(barang: Barang) {
            barangDao.updateBarang(barang)
        }
    }
}