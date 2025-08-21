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
import com.example.thearter_platform.ui.models.ActorKnowledge
import com.example.thearter_platform.ui.components.StatItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorKnowledgeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("演员资料") },
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
                            text = "演员资料库",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "了解优秀演员的成长历程、代表作品和艺术成就",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatItem("演员", "567")
                            StatItem("本月新增", "23")
                        }
                    }
                }
            }

            item {
                Text(
                    text = "知名演员",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(getActorsByCategory("知名")) { actor ->
                ActorCard(
                    actor = actor,
                    onClick = { /* 暂时禁用详情页 */ }
                )
            }
        }
    }
}

@Composable
fun ActorCard(
    actor: ActorKnowledge,
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
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = actor.name.first().toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = actor.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (actor.isVerified) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "认证演员",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Text(
                    text = actor.role,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = actor.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "★ ${actor.rating}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${actor.workCount} 作品",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// 模拟数据
fun getActorsByCategory(category: String): List<ActorKnowledge> {
    return listOf(
        ActorKnowledge(
            id = "1",
            name = "巩俐",
            role = "女演员",
            description = "国际影星，被誉为'巩皇'，以精湛的演技和独特的气质闻名。",
            rating = 4.9f,
            workCount = 45,
            representativeWorks = listOf("红高粱", "大红灯笼高高挂", "霸王别姬"),
            isVerified = true,
            birthYear = 1965,
            nationality = "中国",
            awards = listOf("威尼斯电影节终身成就奖", "金鸡奖最佳女演员"),
            specialties = listOf("电影表演", "舞台表演")
        ),
        ActorKnowledge(
            id = "2",
            name = "陈道明",
            role = "男演员",
            description = "实力派演员，以深沉内敛的表演风格著称，塑造了众多经典角色。",
            rating = 4.8f,
            workCount = 38,
            representativeWorks = listOf("康熙王朝", "围城", "英雄"),
            isVerified = true,
            birthYear = 1955,
            nationality = "中国",
            awards = listOf("金鹰奖最佳男演员", "飞天奖优秀男演员"),
            specialties = listOf("电视剧表演", "电影表演")
        ),
        ActorKnowledge(
            id = "3",
            name = "张艺谋",
            role = "导演",
            description = "著名导演，以独特的视觉风格和深刻的人文关怀闻名于世。",
            rating = 4.7f,
            workCount = 25,
            representativeWorks = listOf("红高粱", "大红灯笼高高挂", "英雄"),
            isVerified = true,
            birthYear = 1951,
            nationality = "中国",
            awards = listOf("柏林电影节金熊奖", "威尼斯电影节金狮奖"),
            specialties = listOf("电影导演", "摄影")
        ),
        ActorKnowledge(
            id = "4",
            name = "章子怡",
            role = "女演员",
            description = "国际知名女演员，以优雅的气质和出色的演技获得广泛认可。",
            rating = 4.6f,
            workCount = 32,
            representativeWorks = listOf("卧虎藏龙", "英雄", "一代宗师"),
            isVerified = true,
            birthYear = 1979,
            nationality = "中国",
            awards = listOf("金马奖最佳女演员", "金像奖最佳女演员"),
            specialties = listOf("电影表演", "舞蹈")
        ),
        ActorKnowledge(
            id = "5",
            name = "梁朝伟",
            role = "男演员",
            description = "香港著名演员，以细腻的表演和深邃的眼神著称。",
            rating = 4.5f,
            workCount = 40,
            representativeWorks = listOf("花样年华", "无间道", "色戒"),
            isVerified = true,
            birthYear = 1962,
            nationality = "中国香港",
            awards = listOf("戛纳电影节最佳男演员", "金像奖最佳男演员"),
            specialties = listOf("电影表演", "电视剧表演")
        )
    )
}
