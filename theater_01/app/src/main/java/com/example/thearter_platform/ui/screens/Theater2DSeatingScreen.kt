package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Theater2DSeatingScreen(navController: NavController, performanceId: String, scheduleId: String) {
    var selectedSeats by remember { mutableStateOf(mutableSetOf<String>()) }
    
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
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("2D选座", fontWeight = FontWeight.Bold)
                        Text("已选座位: ${selectedSeats.size}个", fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    TextButton(
                        onClick = { 
                            println("DEBUG: 点击确认选座按钮")
                            println("DEBUG: selectedSeats = $selectedSeats")
                            println("DEBUG: performanceId = $performanceId")
                            println("DEBUG: scheduleId = $scheduleId")
                            if (selectedSeats.isNotEmpty()) {
                                val seatsParam = selectedSeats.joinToString(",")
                                // 先保存座位信息到SavedStateHandle
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedSeats", seatsParam)
                                val route = "payment/$performanceId/$scheduleId/placeholder"
                                println("DEBUG: 导航到路由: $route")
                                try {
                                    navController.navigate(route)
                                    println("DEBUG: 导航成功")
                                } catch (e: Exception) {
                                    println("DEBUG: 导航失败: ${e.message}")
                                }
                            } else {
                                println("DEBUG: 没有选择座位，无法导航")
                            }
                        },
                        enabled = selectedSeats.isNotEmpty()
                    ) {
                        Text("确认选座", fontWeight = FontWeight.Bold)
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
            // 演出信息
            item {
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
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "国家大剧院 ${schedule.hall}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "已选座位：${selectedSeats.size}个",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        if (selectedSeats.isNotEmpty()) {
                            Text(
                                text = "座位：${selectedSeats.joinToString(", ")}",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 舞台指示
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "舞台",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 座位图 - 简化的10x20布局
            items((1..10).toList()) { row ->
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 行号
                    item {
                        Box(
                            modifier = Modifier
                                .width(30.dp)
                                .height(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ('A' + row - 1).toString(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    // 座位
                    items((1..20).toList()) { column ->
                        val seatId = "${('A' + row - 1)}$column"
                        val isSelected = selectedSeats.contains(seatId)
                        val isSold = setOf("A1", "A2", "B5", "C10", "D15", "E8", "F12", "G3", "H7", "I14", "J20").contains(seatId)
                        
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    when {
                                        isSelected -> MaterialTheme.colorScheme.primary
                                        isSold -> Color.Gray
                                        else -> MaterialTheme.colorScheme.surface
                                    }
                                )
                                .border(
                                    width = 1.dp,
                                    color = when {
                                        isSelected -> MaterialTheme.colorScheme.primary
                                        isSold -> Color.Gray
                                        else -> MaterialTheme.colorScheme.outline
                                    },
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable(enabled = !isSold) {
                                    println("DEBUG: 点击座位 $seatId")
                                    if (isSelected) {
                                        selectedSeats = selectedSeats.toMutableSet().apply { remove(seatId) }
                                        println("DEBUG: 取消选择座位 $seatId，当前已选: $selectedSeats")
                                    } else {
                                        selectedSeats = selectedSeats.toMutableSet().apply { add(seatId) }
                                        println("DEBUG: 选择座位 $seatId，当前已选: $selectedSeats")
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = column.toString(),
                                fontSize = 10.sp,
                                color = when {
                                    isSelected -> MaterialTheme.colorScheme.onPrimary
                                    isSold -> Color.White
                                    else -> MaterialTheme.colorScheme.onSurface
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
            }
            
            // 座位说明
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "座位说明",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surface,
                                        RoundedCornerShape(2.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("可选座位", fontSize = 12.sp)
                        }
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(2.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("已选座位", fontSize = 12.sp)
                        }
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        Color.Gray,
                                        RoundedCornerShape(2.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("已售座位", fontSize = 12.sp)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
