package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import com.example.thearter_platform.ui.models.NewsItem
import com.example.thearter_platform.ui.components.StatItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("新出娱乐") },
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
                            text = "行业动态",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "了解剧场行业最新动态和热点资讯",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatItem("资讯", "2,345")
                            StatItem("本月新增", "156")
                        }
                    }
                }
            }
            
            items(getNewsList()) { news ->
                NewsCard(
                    news = news,
                    onClick = { /* 暂时禁用详情页 */ }
                )
            }
        }
    }
}

@Composable
fun NewsCard(
    news: NewsItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(getNewsTypeColor(news.type)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getNewsTypeText(news.type).first().toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = news.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = news.date,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = "查看详情",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = news.summary,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(getNewsTypeColor(news.type))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = getNewsTypeText(news.type),
                    fontSize = 12.sp,
                    color = getNewsTypeColor(news.type)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${news.viewCount} 阅读",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// 辅助函数
fun getNewsTypeColor(type: String): Color {
    return when (type) {
        "industry" -> Color(0xFF1976D2)    // 蓝色 - 行业
        "performance" -> Color(0xFF388E3C)  // 绿色 - 演出
        "award" -> Color(0xFFFFC107)        // 黄色 - 奖项
        "technology" -> Color(0xFF9C27B0)   // 紫色 - 技术
        else -> Color(0xFF757575)           // 灰色
    }
}

fun getNewsTypeText(type: String): String {
    return when (type) {
        "industry" -> "行业"
        "performance" -> "演出"
        "award" -> "奖项"
        "technology" -> "技术"
        else -> "其他"
    }
}

// 模拟数据
fun getNewsList(): List<NewsItem> {
    return listOf(
        NewsItem(
            id = "1",
            title = "国家大剧院推出全新数字剧场体验",
            summary = "国家大剧院宣布推出全新的数字剧场体验项目，通过VR技术让观众在家中也能享受沉浸式剧场体验。该项目将首先应用于经典剧目《哈姆雷特》的数字化呈现。",
            date = "2024-12-15",
            type = "technology",
            viewCount = 12500
        ),
        NewsItem(
            id = "2",
            title = "2024年度戏剧奖颁奖典礼圆满举行",
            summary = "第XX届戏剧奖颁奖典礼在北京举行，张艺谋导演凭借《红高粱》获得最佳导演奖，巩俐获得最佳女演员奖。本届颁奖典礼吸引了来自全国各地的戏剧界人士参加。",
            date = "2024-12-10",
            type = "award",
            viewCount = 8900
        ),
        NewsItem(
            id = "3",
            title = "上海大剧院推出青年艺术家扶持计划",
            summary = "上海大剧院宣布启动青年艺术家扶持计划，将为优秀的青年导演、演员和编剧提供资金支持和演出机会，助力中国戏剧事业的发展。",
            date = "2024-12-08",
            type = "industry",
            viewCount = 6700
        ),
        NewsItem(
            id = "4",
            title = "《罗密欧与朱丽叶》音乐剧版票房破亿",
            summary = "经典爱情悲剧《罗密欧与朱丽叶》音乐剧版本在全国巡演中取得巨大成功，累计票房突破1亿元，成为今年最受欢迎的戏剧作品之一。",
            date = "2024-12-05",
            type = "performance",
            viewCount = 15400
        ),
        NewsItem(
            id = "5",
            title = "人工智能技术在戏剧创作中的应用",
            summary = "多家知名剧院开始尝试将人工智能技术应用于戏剧创作，从剧本创作到舞台设计，AI技术正在为传统戏剧艺术带来新的可能性。",
            date = "2024-12-01",
            type = "technology",
            viewCount = 9200
        )
    )
}
