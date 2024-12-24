package com.example.ucp2_148.ui.navigasi


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2_148.ui.view.HomeAppView
import com.example.ucp2_148.ui.view.barang.DetailBrgView
import com.example.ucp2_148.ui.view.barang.HomeBrgView
import com.example.ucp2_148.ui.view.barang.InsertBrgView
import com.example.ucp2_148.ui.view.barang.UpdateBrgView
import com.example.ucp2_148.ui.view.suplier.HomeSplView
import com.example.ucp2_148.ui.view.suplier.InsertSplView


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ) {
        composable(
            route = DestinasiHome.route
        ) {
            HomeAppView(
                onBarangClick =  { navController.navigate(DestinasiHomeBrg.route) },
                onAddBrgClick =  { navController.navigate(DestinasiInsertBrg.route) },
                onSuplierClick = { navController.navigate(DestinasiHomeSpl.route) },
                onAddSplClick = { navController.navigate(DestinasiInsertSpl.route) },
            )
        }
        composable(
            route = DestinasiHomeBrg.route
        ){
            HomeBrgView(
                onDetailBrgClick = {idBrg ->
                    navController.navigate("${DestinasiDetailBrg.route}/$idBrg")
                    println("PengelolaHalaman: idBarang= $idBrg")
                },
                onAddBarang = {navController.navigate(DestinasiInsertBrg.route)},
                onBack = {navController.popBackStack()},
                modifier = Modifier
            )
        }
        composable(
            route = DestinasiHomeSpl.route
        ){
            HomeSplView(
                onBack = {navController.popBackStack()},
                modifier = Modifier
            )
        }
        composable(
            route = DestinasiInsertBrg.route
        ){
            InsertBrgView(
                onBack = {navController.popBackStack()},
                onNavigate = { },
                modifier = Modifier
            )
        }
        composable(
            DestinasiDetailBrg.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailBrg.idBrg){
                    type = NavType.StringType
                }
            )
        ){
            val id = it.arguments?.getString(DestinasiDetailBrg.idBrg)
            id?.let {
                DetailBrgView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateBrg.route}/$it")
                    },
                    modifier = Modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )

            }
        }
        composable(
            DestinasiUpdateBrg.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateBrg.idBrg){
                    type = NavType.StringType
                }
            )
        ){
            UpdateBrgView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = Modifier,
            )
        }
        composable(
            route = DestinasiInsertSpl.route
        ){
            InsertSplView(
                onBack = {navController.popBackStack()},
                onNavigate = { },
                modifier = Modifier
            )
        }
    }
}