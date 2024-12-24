package com.example.ucp2_148.data.repository

import com.example.ucp2_148.data.dao.BarangDao
import com.example.ucp2_148.data.entity.Barang
import kotlinx.coroutines.flow.Flow

class LocalRepositoryBrg(
    private val barangDao: BarangDao
) : RepositoryBrg {

    // Insert barang
    override suspend fun insertBrg(barang: Barang) {
        barangDao.insertBarang(barang)
    }

    // Get all barang
    override fun getAllBrg(): Flow<List<Barang>> {
        return barangDao.getAllBarang()
    }

    // Get barang by ID
    override fun getBrg(id: String): Flow<Barang> {
        return barangDao.getBarang(id)
    }

    // Delete barang
    override suspend fun deleteBrg(barang: Barang) {
        barangDao.deleteBarang(barang)
    }

    // Update barang
    override suspend fun updateBrg(barang: Barang) {
        barangDao.updateBarang(barang)
    }
}
