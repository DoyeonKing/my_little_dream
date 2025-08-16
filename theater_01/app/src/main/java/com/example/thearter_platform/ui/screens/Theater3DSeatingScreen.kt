package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
fun Theater3DSeatingScreen(navController: NavController, performanceId: String, scheduleId: String) {
    var selectedView by remember { mutableStateOf("3D") }
    var selectedSection by remember { mutableStateOf("A区") }
    var selectedSeats by remember { mutableStateOf(setOf<String>()) }
    var zoomLevel by remember { mutableStateOf(1.0f) }
    
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
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 顶部栏
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "返回")
            }
            
            Text(
                text = "3D选座 - ${performance}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Row {
                IconButton(onClick = { zoomLevel = (zoomLevel - 0.1f).coerceAtLeast(0.5f) }) {
                    Icon(Icons.Default.Star, contentDescription = "缩小")
                }
                IconButton(onClick = { zoomLevel = (zoomLevel + 0.1f).coerceAtMost(2.0f) }) {
                    Icon(Icons.Default.Add, contentDescription = "放大")
                }
            }
        }
        
        // 视角切换
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                selected = selectedView == "3D",
                onClick = { selectedView = "3D" },
                label = { Text("3D视角") }
            )
            FilterChip(
                selected = selectedView == "2D",
                onClick = { selectedView = "2D" },
                label = { Text("2D平面") }
            )
            FilterChip(
                selected = selectedView == "俯视",
                onClick = { selectedView = "俯视" },
                label = { Text("俯视角度") }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 区域选择
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf("A区", "B区", "C区", "D区", "E区", "VIP区")) { section ->
                FilterChip(
                    selected = selectedSection == section,
                    onClick = { selectedSection = section },
                    label = { Text(section) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 3D剧场视图
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
        ) {
            when (selectedView) {
                "3D" -> Theater3DView(
                    theaterId = performanceId,
                    selectedSection = selectedSection,
                    selectedSeats = selectedSeats,
                    onSeatClick = { seatId ->
                        selectedSeats = if (selectedSeats.contains(seatId)) {
                            selectedSeats - seatId
                        } else {
                            selectedSeats + seatId
                        }
                    },
                    zoomLevel = zoomLevel
                )
                "2D" -> Theater2DView(
                    theaterId = performanceId,
                    selectedSection = selectedSection,
                    selectedSeats = selectedSeats,
                    onSeatClick = { seatId ->
                        selectedSeats = if (selectedSeats.contains(seatId)) {
                            selectedSeats - seatId
                        } else {
                            selectedSeats + seatId
                        }
                    }
                )
                "俯视" -> TheaterTopView(
                    theaterId = performanceId,
                    selectedSection = selectedSection,
                    selectedSeats = selectedSeats,
                    onSeatClick = { seatId ->
                        selectedSeats = if (selectedSeats.contains(seatId)) {
                            selectedSeats - seatId
                        } else {
                            selectedSeats + seatId
                        }
                    }
                )
            }
        }
        
        // 底部信息栏
        BottomInfoBar(
            selectedSeats = selectedSeats,
            onConfirm = {
                // 直接导航到支付页面
                if (selectedSeats.isNotEmpty()) {
                    val seatsParam = selectedSeats.joinToString(",")
                    // 先保存座位信息到SavedStateHandle
                    navController.currentBackStackEntry?.savedStateHandle?.set("selectedSeats", seatsParam)
                    val route = "payment/$performanceId/$scheduleId/placeholder"
                    println("DEBUG: 3D选座导航到支付页面: $route")
                    navController.navigate(route)
                }
            }
        )
    }
}

@Composable
fun Theater3DView(
    theaterId: String,
    selectedSection: String,
    selectedSeats: Set<String>,
    onSeatClick: (String) -> Unit,
    zoomLevel: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .clip(RoundedCornerShape(12.dp))
    ) {
        // 舞台
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(200.dp, 40.dp)
                .background(Color(0xFF8B4513))
                .border(2.dp, Color(0xFFDAA520))
        ) {
            Text(
                text = "舞台",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // 座位区域
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // A区
            if (selectedSection == "A区") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(8) { col ->
                        Seat3D(
                            seatId = "A${col + 1}",
                            isSelected = selectedSeats.contains("A${col + 1}"),
                            onClick = { onSeatClick("A${col + 1}") }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // B区
            if (selectedSection == "B区") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(10) { col ->
                        Seat3D(
                            seatId = "B${col + 1}",
                            isSelected = selectedSeats.contains("B${col + 1}"),
                            onClick = { onSeatClick("B${col + 1}") }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // C区
            if (selectedSection == "C区") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(12) { col ->
                        Seat3D(
                            seatId = "C${col + 1}",
                            isSelected = selectedSeats.contains("C${col + 1}"),
                            onClick = { onSeatClick("C${col + 1}") }
                        )
                    }
                }
            }
        }
        
        // 视角控制提示
        Text(
            text = "3D视角 - 可拖拽旋转",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(8.dp),
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
fun Seat3D(
    seatId: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(
                color = if (isSelected) Color(0xFF4CAF50) else Color(0xFF666666),
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF2E7D32) else Color(0xFF444444),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = seatId,
            color = Color.White,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Theater2DView(
    theaterId: String,
    selectedSection: String,
    selectedSeats: Set<String>,
    onSeatClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .clip(RoundedCornerShape(12.dp))
    ) {
        // 舞台
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(300.dp, 20.dp)
                .background(Color(0xFF8B4513))
                .border(1.dp, Color(0xFFDAA520))
        ) {
            Text(
                text = "舞台",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // 座位区域
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 多排座位
            repeat(8) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(12) { col ->
                        Seat2D(
                            seatId = "${('A' + row)}${col + 1}",
                            isSelected = selectedSeats.contains("${('A' + row)}${col + 1}"),
                            isAvailable = true,
                            onClick = { onSeatClick("${('A' + row)}${col + 1}") }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun Seat2D(
    seatId: String,
    isSelected: Boolean,
    isAvailable: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                color = when {
                    isSelected -> Color(0xFF4CAF50)
                    isAvailable -> Color(0xFF2196F3)
                    else -> Color(0xFF9E9E9E)
                },
                shape = CircleShape
            )
            .clickable(enabled = isAvailable) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = seatId,
            color = Color.White,
            fontSize = 6.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TheaterTopView(
    theaterId: String,
    selectedSection: String,
    selectedSeats: Set<String>,
    onSeatClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E8))
            .clip(RoundedCornerShape(12.dp))
    ) {
        // 舞台
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(200.dp, 60.dp)
                .background(Color(0xFF8B4513))
                .border(2.dp, Color(0xFFDAA520))
        ) {
            Text(
                text = "舞台",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // 座位区域 - 俯视角度
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 多排座位，从后往前
            repeat(10) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(15) { col ->
                        SeatTop(
                            seatId = "${(10 - row)}${col + 1}",
                            isSelected = selectedSeats.contains("${(10 - row)}${col + 1}"),
                            onClick = { onSeatClick("${(10 - row)}${col + 1}") }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

@Composable
fun SeatTop(
    seatId: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(
                color = if (isSelected) Color(0xFF4CAF50) else Color(0xFF2196F3),
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = seatId,
            color = Color.White,
            fontSize = 5.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BottomInfoBar(
    selectedSeats: Set<String>,
    onConfirm: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "已选座位：${selectedSeats.size}个",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (selectedSeats.isNotEmpty()) {
                        Text(
                            text = selectedSeats.joinToString(", "),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Button(
                    onClick = onConfirm,
                    enabled = selectedSeats.isNotEmpty()
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("确认选座")
                }
            }
            
            // 座位状态说明
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF2196F3), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("可选", fontSize = 12.sp)
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF4CAF50), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("已选", fontSize = 12.sp)
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF9E9E9E), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("已售", fontSize = 12.sp)
                }
            }
        }
    }
}
