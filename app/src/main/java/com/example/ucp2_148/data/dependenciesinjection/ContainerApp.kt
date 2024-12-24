package com.example.ucp2_148.data.dependenciesinjection

import android.content.Context
import com.example.ucp2_148.data.database.appDatabase
import com.example.ucp2_148.data.repository.LocalRepositoryBrg
import com.example.ucp2_148.data.repository.LocalRepositorySpl
import com.example.ucp2_148.data.repository.RepositoryBrg
import com.example.ucp2_148.data.repository.RepositorySpl

interface InterfaceContainerApp{
    val repositoryBrg: RepositoryBrg
    val repositorySpl: RepositorySpl
}

class ContainerApp(private val context: Context) : InterfaceContainerApp{
    override val repositoryBrg: RepositoryBrg by lazy {
        LocalRepositoryBrg(appDatabase.getDatabase(context).barangDao())
    }
    override val repositorySpl: RepositorySpl by lazy {
        LocalRepositorySpl(appDatabase.getDatabase(context).suplierDao())
    }
}