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
            
            // 知识图谱相关路由
            composable("knowledge-graph") {
                KnowledgeGraphScreen(navController)
            }
            
            // 剧目信息路由
            composable("play-knowledge") {
                PlayKnowledgeScreen(navController)
            }
            
            // 演员资料路由
            composable("actor-knowledge") {
                ActorKnowledgeScreen(navController)
            }
            
            // 行话科普路由
            composable("terminology") {
                TerminologyScreen(navController)
            }
            
            // 新出娱乐路由
            composable("news") {
                NewsScreen(navController)
            }
            
            // 时间线路由
            composable("timeline") {
                TimelineScreen(navController)
            }
            
            // 智能问答路由
            composable("ai-chat") {
                AIChatScreen(navController)
            }
            
            // 其他功能路由
            composable("ai-generation") {
                AIGenerationScreen(navController)
            }
            composable("tickets") {
                TicketScreen(navController)
            }
            composable("ticket-search") {
                TicketSearchScreen(navController)
            }
            composable("create-log") {
                CreateLogScreen(navController)
            }
            
            // 盘票相关路由
            composable("ticket-exchange") {
                TicketExchangeScreen(navController)
            }
            composable("create-ticket-post") {
                CreateTicketPostScreen(navController)
            }
            composable("ticket-detail/{ticketId}") { backStackEntry ->
                val ticketId = backStackEntry.arguments?.getString("ticketId") ?: "1"
                TicketDetailScreen(navController, ticketId)
            }
            composable("ticket-community") {
                TicketExchangeScreen(navController)
            }
            
            // 视频详情路由
            composable("video-detail/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")
                VideoDetailScreen(navController, postId)
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
