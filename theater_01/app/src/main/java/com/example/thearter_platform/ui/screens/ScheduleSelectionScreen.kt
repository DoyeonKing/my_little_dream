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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleSelectionScreen(navController: NavController, performanceId: String) {
    var selectedSchedule by remember { mutableStateOf<Schedule?>(null) }
    
    val performance = when (performanceId) {
        "1" -> "《哈姆雷特》"
        "2" -> "《天鹅湖》"
        "3" -> "《茶花女》"
        else -> "未知演出"
    }
    
    val schedules = listOf(
        Schedule("1", "2024-01-15 19:30", "歌剧厅", "¥180-1280", "156"),
        Schedule("2", "2024-01-16 19:30", "歌剧厅", "¥180-1280", "89"),
        Schedule("3", "2024-01-17 19:30", "歌剧厅", "¥180-1280", "234"),
        Schedule("4", "2024-01-18 14:30", "歌剧厅", "¥180-1280", "67"),
        Schedule("5", "2024-01-19 19:30", "歌剧厅", "¥180-1280", "123")
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("选择场次", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { 
                                selectedSchedule?.let { schedule ->
                                    navController.navigate("seat-selection/${performanceId}/${schedule.id}")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = selectedSchedule != null
                        ) {
                            Text("选择座位")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.List, contentDescription = null)
                        }
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
                            text = "国家大剧院",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 场次列表
            item {
                Text(
                    text = "选择场次",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            
            items(schedules) { schedule ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedSchedule?.id == schedule.id) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    ),
                    onClick = { selectedSchedule = schedule }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = schedule.dateTime,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedSchedule?.id == schedule.id) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Text(
                                text = schedule.hall,
                                fontSize = 14.sp,
                                color = if (selectedSchedule?.id == schedule.id) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Text(
                                text = "剩余座位：${schedule.remainingSeats}个",
                                fontSize = 12.sp,
                                color = if (selectedSchedule?.id == schedule.id) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = schedule.priceRange,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            if (selectedSchedule?.id == schedule.id) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = "已选择",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            // 购票须知
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "购票须知",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        ScheduleNoticeItem("• 确认场次后将进入选座页面")
                        ScheduleNoticeItem("• 选座时间为15分钟，超时订单将自动取消")
                        ScheduleNoticeItem("• 选座完成后需在10分钟内完成支付")
                        ScheduleNoticeItem("• 演出开始前30分钟停止售票")
                        ScheduleNoticeItem("• 一经售出，概不退换")
                    }
                }
                
                Spacer(modifier = Modifier.height(80.dp)) // 为底部按钮留出空间
            }
        }
    }
}

@Composable
fun ScheduleNoticeItem(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}

data class Schedule(
    val id: String,
    val dateTime: String,
    val hall: String,
    val priceRange: String,
    val remainingSeats: String
)
