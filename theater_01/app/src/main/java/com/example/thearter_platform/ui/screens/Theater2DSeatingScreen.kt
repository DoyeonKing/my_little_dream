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
fun Theater2DSeatingScreen(navController: NavController, performanceId: String) {
    var selectedSection by remember { mutableStateOf("A区") }
    var selectedSeats by remember { mutableStateOf(setOf<String>()) }
    
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
                text = "2D平面选座",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            IconButton(onClick = { /* 帮助功能 */ }) {
                Icon(Icons.Default.Info, contentDescription = "帮助")
            }
        }
        
        // 区域选择
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf("VIP区", "A区", "B区", "C区", "D区", "E区")) { section ->
                FilterChip(
                    selected = selectedSection == section,
                    onClick = { selectedSection = section },
                    label = { Text(section) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 2D剧场视图
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
        ) {
            Theater2DSeatingView(
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
        
        // 底部信息栏
        BottomInfoBar2D(
            selectedSeats = selectedSeats,
            onConfirm = {
                // 确认选座
                navController.navigate("seat-confirmation/${performanceId}?seats=${selectedSeats.joinToString(",")}")
            }
        )
    }
}

@Composable
fun Theater2DSeatingView(
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
                        Seat2DView(
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
        
        // 选座提示
        Text(
            text = "2D平面选座 - 点击选择座位",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun Seat2DView(
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
fun BottomInfoBar2D(
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
