package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.thearter_platform.ui.models.TimelineEvent
import com.example.thearter_platform.ui.components.StatItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("时间线") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "剧场发展历程",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "探索剧场艺术的发展历程和重要时刻",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatItem("事件", "1,234")
                            StatItem("时间跨度", "50年")
                        }
                    }
                }
            }
            
            items(getTimelineEvents()) { event ->
                TimelineEventCard(event = event)
            }
        }
    }
}

@Composable
fun TimelineEventCard(event: TimelineEvent) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // 时间线连接线
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(getEventTypeColor(event.type))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.date,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = getEventTypeColor(event.type)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(getEventTypeColor(event.type))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = getEventTypeText(event.type),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = event.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = event.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

// 辅助函数
fun getEventTypeColor(type: String): Color {
    return when (type) {
        "play" -> Color(0xFF1976D2)      // 蓝色 - 剧目
        "actor" -> Color(0xFF388E3C)     // 绿色 - 演员
        "award" -> Color(0xFFFFC107)     // 黄色 - 奖项
        "premiere" -> Color(0xFF9C27B0)  // 紫色 - 首演
        else -> Color(0xFF757575)        // 灰色
    }
}

fun getEventTypeText(type: String): String {
    return when (type) {
        "play" -> "剧目"
        "actor" -> "演员"
        "award" -> "奖项"
        "premiere" -> "首演"
        else -> "其他"
    }
}

// 模拟数据
fun getTimelineEvents(): List<TimelineEvent> {
    return listOf(
        TimelineEvent(
            id = "1",
            date = "2024-12-15",
            title = "《哈姆雷特》数字版首演",
            description = "国家大剧院推出《哈姆雷特》数字版本，通过VR技术让观众体验全新的观剧方式。这是传统戏剧与现代技术结合的里程碑事件。",
            type = "premiere"
        ),
        TimelineEvent(
            id = "2",
            date = "2024-12-10",
            title = "张艺谋获得终身成就奖",
            description = "著名导演张艺谋在第XX届戏剧奖颁奖典礼上获得终身成就奖，表彰其在戏剧艺术领域的杰出贡献。",
            type = "award"
        ),
        TimelineEvent(
            id = "3",
            date = "2024-12-08",
            title = "巩俐回归舞台",
            description = "国际影星巩俐宣布回归舞台，将在明年主演经典剧目《茶花女》，这是她时隔十年后的舞台回归。",
            type = "actor"
        ),
        TimelineEvent(
            id = "4",
            date = "2024-12-05",
            title = "《罗密欧与朱丽叶》音乐剧版首演",
            description = "经典爱情悲剧《罗密欧与朱丽叶》音乐剧版本在北京首演，获得观众和评论界的一致好评。",
            type = "play"
        ),
        TimelineEvent(
            id = "5",
            date = "2024-12-01",
            title = "上海大剧院成立30周年",
            description = "上海大剧院迎来成立30周年庆典，举办了为期一个月的纪念演出季，回顾了30年来的艺术成就。",
            type = "premiere"
        )
    )
}
