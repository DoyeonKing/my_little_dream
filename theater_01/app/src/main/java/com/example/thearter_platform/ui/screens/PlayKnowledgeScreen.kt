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
import com.example.thearter_platform.ui.models.PlayKnowledge
import com.example.thearter_platform.ui.components.StatItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayKnowledgeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("剧目信息") },
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
                            text = "剧目知识库",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "探索经典剧目的背景故事、人物关系和艺术价值",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatItem("剧目", "1,234")
                            StatItem("本月新增", "89")
                        }
                    }
                }
            }

            item {
                Text(
                    text = "热门剧目",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(getPlaysByCategory("热门")) { play ->
                PlayCard(
                    play = play,
                    onClick = { /* 暂时禁用详情页 */ }
                )
            }
        }
    }
}

@Composable
fun PlayCard(
    play: PlayKnowledge,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(getPlayCategoryColor(play.category)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = play.title.first().toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = play.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = play.category,
                    fontSize = 12.sp,
                    color = getPlayCategoryColor(play.category)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = play.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "★ ${play.rating}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${play.viewCount} 浏览",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// 辅助函数
fun getPlayCategoryColor(category: String): Color {
    return when (category) {
        "悲剧" -> Color(0xFFE57373)      // 红色
        "喜剧" -> Color(0xFF81C784)      // 绿色
        "历史剧" -> Color(0xFF64B5F6)    // 蓝色
        "现代剧" -> Color(0xFFFFB74D)    // 橙色
        else -> Color(0xFF9E9E9E)        // 灰色
    }
}

// 模拟数据
fun getPlaysByCategory(category: String): List<PlayKnowledge> {
    return listOf(
        PlayKnowledge(
            id = "1",
            title = "哈姆雷特",
            category = "悲剧",
            description = "莎士比亚最著名的悲剧，讲述了丹麦王子哈姆雷特为父报仇的故事。",
            rating = 4.8f,
            viewCount = 12500,
            tags = listOf("莎士比亚", "悲剧", "复仇")
        ),
        PlayKnowledge(
            id = "2",
            title = "罗密欧与朱丽叶",
            category = "悲剧",
            description = "经典爱情悲剧，讲述了两个年轻恋人在家族仇恨中相爱并最终殉情的故事。",
            rating = 4.7f,
            viewCount = 9800,
            tags = listOf("莎士比亚", "爱情", "悲剧")
        ),
        PlayKnowledge(
            id = "3",
            title = "仲夏夜之梦",
            category = "喜剧",
            description = "莎士比亚的浪漫喜剧，讲述了在魔法森林中发生的爱情故事。",
            rating = 4.6f,
            viewCount = 7600,
            tags = listOf("莎士比亚", "喜剧", "魔法")
        ),
        PlayKnowledge(
            id = "4",
            title = "李尔王",
            category = "悲剧",
            description = "莎士比亚四大悲剧之一，讲述了李尔王因轻信谗言而失去一切的故事。",
            rating = 4.5f,
            viewCount = 6500,
            tags = listOf("莎士比亚", "悲剧", "权力")
        ),
        PlayKnowledge(
            id = "5",
            title = "麦克白",
            category = "悲剧",
            description = "莎士比亚的悲剧，讲述了麦克白因野心而走向毁灭的故事。",
            rating = 4.4f,
            viewCount = 5800,
            tags = listOf("莎士比亚", "悲剧", "野心")
        )
    )
}
