package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.thearter_platform.ui.models.TicketPost
import com.example.thearter_platform.ui.models.Comment
import com.example.thearter_platform.ui.utils.getTypeColor
import com.example.thearter_platform.ui.utils.getTypeText
import com.example.thearter_platform.ui.utils.getTicketById
import com.example.thearter_platform.ui.utils.getComments

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDetailScreen(navController: NavController, ticketId: String) {
    val ticket = getTicketById(ticketId)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("票务详情") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { /* 分享功能 */ }) {
                        Icon(Icons.Filled.Share, contentDescription = "分享")
                    }
                    IconButton(onClick = { /* 举报功能 */ }) {
                        Icon(Icons.Filled.Info, contentDescription = "举报")
                    }
                }
            )
        },
        bottomBar = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* 收藏功能 */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("收藏")
                    }
                    
                    Button(
                        onClick = { /* 联系用户 */ },
                        modifier = Modifier.weight(2f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getTypeColor(ticket.type)
                        )
                    ) {
                        Icon(Icons.Filled.Email, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            when (ticket.type) {
                                "transfer" -> "联系转让"
                                "purchase" -> "联系求购"
                                "exchange" -> "联系交换"
                                else -> "联系"
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 票务类型标签
            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(getTypeColor(ticket.type).copy(alpha = 0.1f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = getTypeText(ticket.type),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = getTypeColor(ticket.type)
                    )
                }
            }
            
            // 标题
            item {
                Text(
                    text = ticket.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // 用户信息
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(getTypeColor(ticket.type)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ticket.userName.first().toString(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = ticket.userName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "发布于 ${ticket.postTime}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        OutlinedButton(
                            onClick = { /* 查看用户主页 */ }
                        ) {
                            Text("查看主页")
                        }
                    }
                }
            }
            
            // 详细描述
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "详细描述",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = ticket.description,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
            
            // 演出信息
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "演出信息",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        InfoRow(
                            icon = Icons.Filled.Info,
                            label = "演出名称",
                            value = ticket.performanceName
                        )
                        
                        InfoRow(
                            icon = Icons.Filled.DateRange,
                            label = "演出时间",
                            value = ticket.performanceTime
                        )
                        
                        InfoRow(
                            icon = Icons.Filled.LocationOn,
                            label = "演出地点",
                            value = ticket.performanceVenue
                        )
                    }
                }
            }
            
            // 票务详情
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "票务详情",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "价格",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "¥${ticket.price}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "数量",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${ticket.quantity}张",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
            
            // 统计信息
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatisticItem(
                            icon = Icons.Filled.Info,
                            label = "浏览",
                            value = ticket.viewCount.toString()
                        )
                        StatisticItem(
                            icon = Icons.Filled.Email,
                            label = "评论",
                            value = ticket.commentCount.toString()
                        )
                        StatisticItem(
                            icon = Icons.Filled.Favorite,
                            label = "收藏",
                            value = "12"
                        )
                    }
                }
            }
            
            // 评论区
            item {
                Text(
                    text = "评论区",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            
            items(getComments(ticketId)) { comment ->
                CommentCard(comment = comment)
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label：",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun StatisticItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun CommentCard(comment: Comment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = comment.userName.first().toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = comment.userName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = comment.commentTime,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comment.content,
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}

// 模拟数据

// 模拟数据
fun getTicketById(id: String): TicketPost {
    return TicketPost(
        id = id,
        type = "transfer",
        userName = "剧场爱好者",
        postTime = "2小时前",
        title = "转让《哈姆雷特》演出票",
        description = "因临时有事无法观看，转让一张《哈姆雷特》演出票，位置很好，价格可议。这是一场非常精彩的演出，由著名演员主演，舞台效果震撼。票务位置在A区，视野极佳，希望有缘人能够享受这场艺术盛宴。",
        performanceName = "哈姆雷特",
        performanceTime = "2024-12-20 19:30",
        performanceVenue = "国家大剧院",
        price = 380,
        quantity = 1,
        commentCount = 5,
        viewCount = 128
    )
}

fun getComments(ticketId: String): List<Comment> {
    return listOf(
        Comment(
            id = "1",
            userName = "戏剧迷",
            userAvatar = "https://picsum.photos/200/200?random=1",
            commentTime = "1小时前",
            content = "这个位置确实很好，价格也合理，可惜我已经有票了。"
        ),
        Comment(
            id = "2",
            userName = "艺术青年",
            userAvatar = "https://picsum.photos/200/200?random=2",
            commentTime = "30分钟前",
            content = "请问可以面交吗？我在国家大剧院附近。"
        ),
        Comment(
            id = "3",
            userName = "古典爱好者",
            userAvatar = "https://picsum.photos/200/200?random=3",
            commentTime = "15分钟前",
            content = "这个价格比官网便宜很多，很划算！"
        )
    )
}
