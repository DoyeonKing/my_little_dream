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
fun SeatSelectionScreen(navController: NavController, performanceId: String, scheduleId: String) {
    println("DEBUG: 进入选座页面，performanceId=$performanceId, scheduleId=$scheduleId")
    
    val performance = when (performanceId) {
        "1" -> "《哈姆雷特》"
        "2" -> "《天鹅湖》"
        "3" -> "《茶花女》"
        else -> "未知演出"
    }
    
    val schedule = when (scheduleId) {
        "1" -> Schedule("1", "2024-01-15 19:30", "歌剧厅", "¥180-1280", "156")
        "2" -> Schedule("2", "2024-01-16 19:30", "歌剧厅", "¥180-1280", "89")
        "3" -> Schedule("3", "2024-01-17 19:30", "歌剧厅", "¥180-1280", "234")
        "4" -> Schedule("4", "2024-01-18 14:30", "歌剧厅", "¥180-1280", "67")
        "5" -> Schedule("5", "2024-01-19 19:30", "歌剧厅", "¥180-1280", "123")
        else -> Schedule("1", "2024-01-15 19:30", "歌剧厅", "¥180-1280", "156")
    }
    
    val seatTypes = listOf(
        SeatType("2D选座", "传统平面选座", Icons.Default.List),
        SeatType("3D选座", "立体沉浸式选座", Icons.Default.Star)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("选择座位") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // 演出信息
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
                        text = performance,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = schedule.dateTime,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "国家大剧院 ${schedule.hall}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 选座方式选择
            Text(
                text = "选择选座方式",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            // 2D选座按钮
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { 
                        println("DEBUG: 点击2D选座")
                        navController.navigate("2d-seating/$performanceId/$scheduleId")
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.List,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "2D选座",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "传统平面选座",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "选择",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 3D选座按钮
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { 
                        println("DEBUG: 点击3D选座")
                        navController.navigate("3d-seating/$performanceId/$scheduleId")
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "3D选座",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "立体沉浸式选座",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "选择",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 座位区域说明
            Text(
                text = "座位区域说明",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SeatAreaInfo("VIP区", "¥1280", "最佳观演位置")
                    SeatAreaInfo("A区", "¥880", "前排中央区域")
                    SeatAreaInfo("B区", "¥580", "中排区域")
                    SeatAreaInfo("C区", "¥380", "后排区域")
                    SeatAreaInfo("D区", "¥180", "侧边区域")
                }
            }
        }
    }
}

@Composable
fun SeatTypeCard(
    seatType: SeatType,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                seatType.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = seatType.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = seatType.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "选择",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SeatAreaInfo(
    area: String,
    price: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = area,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(60.dp)
        )
        
        Text(
            text = price,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(80.dp)
        )
        
        Text(
            text = description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

data class SeatType(
    val name: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
