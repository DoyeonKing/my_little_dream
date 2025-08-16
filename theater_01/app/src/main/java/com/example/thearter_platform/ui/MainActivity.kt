package com.example.thearter_platform.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thearter_platform.ui.screens.*
import com.example.thearter_platform.ui.theme.Thearter_platformTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Thearter_platformTheme {
                TheaterPlatformApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheaterPlatformApp() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                val screens = listOf(
                    Screen.Home,
                    Screen.Performances,
                    Screen.Community,
                    Screen.Profile
                )
                
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.Performances.route) {
                TicketsScreen(navController)
            }
            composable(Screen.Community.route) {
                CommunityScreen(navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }
            
            // 简化的路由
            composable("create-post") {
                CreatePostScreen(navController)
            }
            
            composable("search") {
                SearchScreen(navController)
            }
            
            composable("city-selection") {
                CitySelectionScreen(navController)
            }
            
            // 盘票相关路由
            composable("ticket-community") {
                TicketCommunityScreen(navController)
            }
            
            composable("create-ticket-post") {
                CreateTicketPostScreen(navController)
            }
            
            composable("ticket-exchange") {
                TicketExchangeScreen(navController)
            }
            
            // 购票流程路由
            composable("performance-detail/{performanceId}") { backStackEntry ->
                val performanceId = backStackEntry.arguments?.getString("performanceId")
                performanceId?.let { PerformanceDetailScreen(navController, it) }
            }
            
            composable("seat-selection/{performanceId}") { backStackEntry ->
                val performanceId = backStackEntry.arguments?.getString("performanceId")
                performanceId?.let { SeatSelectionScreen(navController, it) }
            }
            
            // 2D和3D选座路由
            composable("2d-seating/{performanceId}") { backStackEntry ->
                val performanceId = backStackEntry.arguments?.getString("performanceId")
                performanceId?.let { Theater2DSeatingScreen(navController, it) }
            }
            
            composable("3d-seating/{performanceId}") { backStackEntry ->
                val performanceId = backStackEntry.arguments?.getString("performanceId")
                performanceId?.let { Theater3DSeatingScreen(navController, it) }
            }
            
            // 支付和确认路由
            composable("payment/{performanceId}/{selectedSeats}") { backStackEntry ->
                val performanceId = backStackEntry.arguments?.getString("performanceId")
                val selectedSeats = backStackEntry.arguments?.getString("selectedSeats")
                if (performanceId != null && selectedSeats != null) {
                    PaymentScreen(navController, performanceId, selectedSeats)
                }
            }
            
            composable("purchase-confirmation/{performanceId}/{selectedSeats}") { backStackEntry ->
                val performanceId = backStackEntry.arguments?.getString("performanceId")
                val selectedSeats = backStackEntry.arguments?.getString("selectedSeats")
                if (performanceId != null && selectedSeats != null) {
                    PurchaseConfirmationScreen(navController, performanceId, selectedSeats)
                }
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "首页", Icons.Default.Home)
    object Performances : Screen("performances", "购票", Icons.Default.ShoppingCart)
    object Community : Screen("community", "社群", Icons.Default.Person)
    object Profile : Screen("profile", "我的", Icons.Default.Person)
}
