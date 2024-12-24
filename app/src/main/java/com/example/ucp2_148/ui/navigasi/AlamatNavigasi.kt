package com.example.ucp2_148.ui.navigasi

interface AlamatNavigasi {
    val route :String
}
object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}
object DestinasiHomeSpl: AlamatNavigasi {
    override val route = "homesuplier"
}

object DestinasiHomeBrg: AlamatNavigasi {
    override val route = "homebarang"
}

object DestinasiInsertSpl: AlamatNavigasi {
    override val route = "suplier"
}

object DestinasiInsertBrg: AlamatNavigasi {
    override val route = "barang"
}

object DestinasiDetailSpl: AlamatNavigasi {
    override val route = "detail"
    const val idSpl = "idSpl"
    val routeWithArg = "$route/{$idSpl}"
}

object DestinasiDetailBrg: AlamatNavigasi {
    override val route = "detail"
    const val idBrg = "idBarang"
    val routeWithArg = "$route/{$idBrg}"
}

object DestinasiUpdateBrg: AlamatNavigasi {
    override val route = "update"
    const val idBrg = "idBrg"
    val routeWithArg = "$route/{$idBrg}"
}