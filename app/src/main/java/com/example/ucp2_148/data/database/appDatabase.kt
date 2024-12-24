package com.example.ucp2_148.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2_148.data.dao.BarangDao
import com.example.ucp2_148.data.dao.SuplierDao
import com.example.ucp2_148.data.entity.Barang
import com.example.ucp2_148.data.entity.Suplier

@Database(entities = [Barang::class, Suplier::class], version = 1, exportSchema = false)
abstract class appDatabase : RoomDatabase() {
    abstract fun barangDao(): BarangDao
    abstract fun suplierDao(): SuplierDao

    companion object {
        @Volatile
        private var Instance: appDatabase? = null

        fun getDatabase(context: Context): appDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "appDatabase"
                ).build().also { Instance = it }
            })
        }
    }
}