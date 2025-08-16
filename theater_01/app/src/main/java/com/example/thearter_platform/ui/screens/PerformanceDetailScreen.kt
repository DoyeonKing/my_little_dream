package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
fun PerformanceDetailScreen(navController: NavController, performanceId: String) {
    val performance = when (performanceId) {
        "1" -> PerformanceDetail(
            "1", "《哈姆雷特》", "莎士比亚经典悲剧", "2024-01-15 19:30", "国家大剧院",
            "¥180-580", "话剧", "约3小时", "英语演出，中文字幕",
            "丹麦王子哈姆雷特在得知父亲被叔叔克劳狄斯谋杀后，决定为父报仇。在复仇的过程中，他陷入了深深的矛盾和痛苦之中...",
            listOf("主演：汤姆·希德勒斯顿", "导演：肯尼思·布拉纳", "编剧：威廉·莎士比亚"),
            listOf("2024-01-15 19:30", "2024-01-16 19:30", "2024-01-17 19:30")
        )
        "2" -> PerformanceDetail(
            "2", "《天鹅湖》", "柴可夫斯基芭蕾舞剧", "2024-01-20 19:30", "国家大剧院",
            "¥280-880", "芭蕾舞", "约2.5小时", "现场伴奏",
            "王子齐格弗里德在湖边遇到被施了魔法的天鹅公主奥杰塔，两人相爱。但邪恶的魔法师罗斯巴特从中作梗...",
            listOf("主演：娜塔莉娅·奥西波娃", "编舞：马吕斯·彼季帕", "作曲：彼得·柴可夫斯基"),
            listOf("2024-01-20 19:30", "2024-01-21 19:30", "2024-01-22 19:30")
        )
        else -> PerformanceDetail(
            "3", "《茶花女》", "威尔第歌剧经典", "2024-01-25 19:30", "国家大剧院",
            "¥380-1280", "歌剧", "约3小时", "意大利语演出，中文字幕",
            "巴黎名妓薇奥莱塔与青年阿尔弗雷多相爱，但阿尔弗雷多的父亲反对这段感情...",
            listOf("主演：安娜·奈瑞贝科", "指挥：里卡多·穆蒂", "作曲：朱塞佩·威尔第"),
            listOf("2024-01-25 19:30", "2024-01-26 19:30", "2024-01-27 19:30")
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(performance.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { /* 分享功能 */ }) {
                        Icon(Icons.Default.Share, contentDescription = "分享")
                    }
                    IconButton(onClick = { /* 收藏功能 */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "收藏")
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
                            onClick = { navController.navigate("seat-selection/${performance.id}") },
                            modifier = Modifier.fillMaxWidth()
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
            // 演出海报区域
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
                            text = performance.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = performance.subtitle,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "价格",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = performance.price,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            
                            Column {
                                Text(
                                    text = "时长",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = performance.duration,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            
                            Column {
                                Text(
                                    text = "类型",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = performance.type,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 演出时间选择
            item {
                Text(
                    text = "选择场次",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(performance.schedules) { schedule ->
                        Card(
                            modifier = Modifier.width(120.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = schedule.substring(0, 10),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    text = schedule.substring(11),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 演出介绍
            item {
                Text(
                    text = "演出介绍",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Text(
                    text = performance.description,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 演出信息
            item {
                Text(
                    text = "演出信息",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        InfoRow("演出地点", performance.venue)
                        InfoRow("演出时间", performance.dateTime)
                        InfoRow("演出语言", performance.language)
                        InfoRow("演出时长", performance.duration)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 主创团队
            item {
                Text(
                    text = "主创团队",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        performance.crew.forEach { crewMember ->
                            Text(
                                text = crewMember,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 购票须知
            item {
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
                        NoticeItem("1. 请提前30分钟到达剧场")
                        NoticeItem("2. 演出开始后禁止入场")
                        NoticeItem("3. 剧场内禁止拍照摄像")
                        NoticeItem("4. 请将手机调至静音模式")
                        NoticeItem("5. 儿童需购票入场")
                    }
                }
                
                Spacer(modifier = Modifier.height(80.dp)) // 为底部按钮留出空间
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun NoticeItem(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

data class PerformanceDetail(
    val id: String,
    val title: String,
    val subtitle: String,
    val dateTime: String,
    val venue: String,
    val price: String,
    val type: String,
    val duration: String,
    val language: String,
    val description: String,
    val crew: List<String>,
    val schedules: List<String>
)
