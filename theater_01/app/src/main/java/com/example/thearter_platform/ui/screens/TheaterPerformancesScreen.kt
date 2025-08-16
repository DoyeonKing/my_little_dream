package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheaterPerformancesScreen(navController: NavController, theaterId: String) {
    val theaterName = when (theaterId) {
        "1" -> "国家大剧院"
        "2" -> "北京人民艺术剧院"
        "3" -> "保利剧院"
        "4" -> "天桥艺术中心"
        "5" -> "梅兰芳大剧院"
        else -> "未知剧场"
    }
    
    val performances = listOf(
        Performance("1", "《哈姆雷特》", "2024-01-15 19:30", "¥180-580", "莎士比亚经典悲剧", "话剧"),
        Performance("2", "《天鹅湖》", "2024-01-20 19:30", "¥280-880", "柴可夫斯基芭蕾舞剧", "芭蕾舞"),
        Performance("3", "《茶花女》", "2024-01-25 19:30", "¥380-1280", "威尔第歌剧经典", "歌剧"),
        Performance("4", "《罗密欧与朱丽叶》", "2024-01-30 19:30", "¥220-680", "莎士比亚爱情悲剧", "话剧"),
        Performance("5", "《卡门》", "2024-02-05 19:30", "¥420-1580", "比才歌剧经典", "歌剧")
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(theaterName) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("theater-map/$theaterId") }) {
                        Icon(Icons.Default.LocationOn, contentDescription = "剧场地图")
                    }
                    IconButton(onClick = { navController.navigate("3d-seating/$theaterId") }) {
                        Icon(Icons.Default.Star, contentDescription = "3D选座")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                // 剧场信息卡片
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = theaterName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = when (theaterId) {
                                    "1" -> "北京市西城区西长安街2号"
                                    "2" -> "北京市东城区王府井大街22号"
                                    "3" -> "北京市东城区东直门南小街保利大厦"
                                    "4" -> "北京市西城区天桥南大街9号"
                                    "5" -> "北京市西城区平安里西大街32号"
                                    else -> "未知地址"
                                },
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            OutlinedButton(
                                onClick = { navController.navigate("theater-map/$theaterId") },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            ) {
                                Icon(Icons.Default.LocationOn, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("剧场地图")
                            }
                            
                            Button(
                                onClick = { navController.navigate("3d-seating/$theaterId") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    contentColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("3D选座")
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "近期演出",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            items(performances) { performance ->
                PerformanceCard(
                    performance = performance,
                    onClick = { navController.navigate("performance-detail/${performance.id}") }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun PerformanceCard(
    performance: Performance,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = performance.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = performance.type,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Text(
                    text = performance.price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.List,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = performance.dateTime,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = performance.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { /* 收藏功能 */ }
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("收藏")
                }
                
                Button(
                    onClick = { onClick() }
                ) {
                    Text("立即购票")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                }
            }
        }
    }
}

data class Performance(
    val id: String,
    val title: String,
    val dateTime: String,
    val price: String,
    val description: String,
    val type: String
)
