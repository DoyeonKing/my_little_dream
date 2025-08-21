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
import com.example.thearter_platform.ui.models.Terminology
import com.example.thearter_platform.ui.components.StatItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminologyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("行话科普") },
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
                            text = "剧场术语库",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "了解剧场专业术语，深入理解戏剧艺术的专业语言",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatItem("术语", "890")
                            StatItem("本月新增", "45")
                        }
                    }
                }
            }

            item {
                Text(
                    text = "常用术语",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(getTermsByCategory("常用")) { term ->
                TermCard(
                    term = term,
                    onClick = { /* 暂时禁用详情页 */ }
                )
            }
        }
    }
}

@Composable
fun TermCard(
    term: Terminology,
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
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(getTermCategoryColor(term.category)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    getCategoryIcon(term.category),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = term.term,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = term.category,
                    fontSize = 12.sp,
                    color = getTermCategoryColor(term.category)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = term.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${term.viewCount} 浏览",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (term.relatedTerms.isNotEmpty()) {
                        Text(
                            text = "相关: ${term.relatedTerms.take(2).joinToString(", ")}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

// 辅助函数
fun getTermCategoryColor(category: String): Color {
    return when (category) {
        "表演" -> Color(0xFFE57373)      // 红色
        "舞台" -> Color(0xFF81C784)      // 绿色
        "灯光" -> Color(0xFF64B5F6)      // 蓝色
        "音响" -> Color(0xFFFFB74D)      // 橙色
        "服装" -> Color(0xFFBA68C8)      // 紫色
        else -> Color(0xFF9E9E9E)        // 灰色
    }
}

fun getCategoryIcon(category: String) = Icons.Filled.Info

// 模拟数据
fun getTermsByCategory(category: String): List<Terminology> {
    return listOf(
        Terminology(
            id = "1",
            term = "台步",
            category = "表演",
            description = "演员在舞台上的行走姿态和步伐，是表演艺术的重要组成部分。",
            detailedDescription = "台步是演员在舞台上移动的基本技巧，包括走路的姿态、步伐的大小、速度的控制等。好的台步能够体现角色的身份、性格和情绪状态。",
            usageExamples = listOf("演员的台步要稳重大方", "这个角色的台步要轻快活泼"),
            relatedTerms = listOf("亮相", "身段", "手势"),
            viewCount = 8500
        ),
        Terminology(
            id = "2",
            term = "亮相",
            category = "表演",
            description = "演员在舞台上展示自己形象和角色的重要时刻。",
            detailedDescription = "亮相是演员在舞台上第一次出现或重要转折点的展示，要求演员通过姿态、表情、服装等全面展现角色特征。",
            usageExamples = listOf("主角的亮相非常震撼", "这个亮相设计得很巧妙"),
            relatedTerms = listOf("台步", "身段", "表情"),
            viewCount = 7200
        ),
        Terminology(
            id = "3",
            term = "念白",
            category = "表演",
            description = "演员在舞台上的台词表达，包括对白、独白等。",
            detailedDescription = "念白是演员通过语言表达角色情感和推动剧情发展的重要手段，要求发音清晰、语调准确、情感真挚。",
            usageExamples = listOf("这段念白很有感染力", "演员的念白功底很深"),
            relatedTerms = listOf("对白", "独白", "旁白"),
            viewCount = 6800
        ),
        Terminology(
            id = "4",
            term = "布景",
            category = "舞台",
            description = "舞台上用于营造环境和氛围的装饰和道具。",
            detailedDescription = "布景是舞台美术的重要组成部分，通过背景、道具、装饰等元素营造戏剧所需的环境和氛围。",
            usageExamples = listOf("这个布景设计很精美", "布景转换很流畅"),
            relatedTerms = listOf("道具", "背景", "装饰"),
            viewCount = 5600
        ),
        Terminology(
            id = "5",
            term = "追光",
            category = "灯光",
            description = "跟随演员移动的聚光灯，突出主要演员。",
            detailedDescription = "追光是舞台灯光的重要技术，通过跟随演员移动的光束突出主要演员，增强舞台效果。",
            usageExamples = listOf("追光要跟紧演员", "这个追光效果很好"),
            relatedTerms = listOf("聚光灯", "面光", "侧光"),
            viewCount = 4800
        )
    )
}
