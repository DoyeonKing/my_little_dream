package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
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
import com.example.thearter_platform.ui.models.TicketPost
import com.example.thearter_platform.ui.utils.getTypeColor
import com.example.thearter_platform.ui.utils.getTypeText
import com.example.thearter_platform.ui.utils.getTicketPosts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketExchangeScreen(navController: NavController) {
    var selectedFilter by remember { mutableStateOf("全部") }
    var searchQuery by remember { mutableStateOf("") }
    val filters = listOf("全部", "转让", "求购", "交换")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎭 盘票社区") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { /* 搜索功能 */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "搜索")
                    }
                    IconButton(onClick = { /* 筛选功能 */ }) {
                        Icon(Icons.Filled.Info, contentDescription = "筛选")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create-ticket-post") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "发布票务")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 筛选标签
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
            
            // 票务列表
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getTicketPosts(selectedFilter)) { post ->
                    TicketPostCard(
                        post = post,
                        onClick = { navController.navigate("ticket-detail/${post.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun TicketPostCard(
    post: TicketPost,
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
            // 头部信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(getTypeColor(post.type)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = getTypeText(post.type).first().toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = post.userName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = post.postTime,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(getTypeColor(post.type).copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = getTypeText(post.type),
                        fontSize = 12.sp,
                        color = getTypeColor(post.type),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 票务信息
            Text(
                text = post.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = post.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 票务详情
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "演出：${post.performanceName}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "时间：${post.performanceTime}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "地点：${post.performanceVenue}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "¥${post.price}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${post.quantity}张",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 底部操作
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💬 ${post.commentCount}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "👁️ ${post.viewCount}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Button(
                    onClick = { /* 联系用户 */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getTypeColor(post.type)
                    )
                ) {
                    Text(
                        text = when (post.type) {
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
}

// 模拟数据

// 模拟数据
fun getTicketPosts(filter: String): List<TicketPost> {
    val allPosts = listOf(
        TicketPost(
            id = "1",
            type = "transfer",
            userName = "剧场爱好者",
            postTime = "2小时前",
            title = "转让《哈姆雷特》演出票",
            description = "因临时有事无法观看，转让一张《哈姆雷特》演出票，位置很好，价格可议。",
            performanceName = "哈姆雷特",
            performanceTime = "2024-12-20 19:30",
            performanceVenue = "国家大剧院",
            price = 380,
            quantity = 1,
            commentCount = 5,
            viewCount = 128
        ),
        TicketPost(
            id = "2",
            type = "purchase",
            userName = "音乐迷",
            postTime = "4小时前",
            title = "求购《罗密欧与朱丽叶》票",
            description = "非常想看这场演出，求购两张连座票，价格可以商量。",
            performanceName = "罗密欧与朱丽叶",
            performanceTime = "2024-12-25 19:30",
            performanceVenue = "上海大剧院",
            price = 500,
            quantity = 2,
            commentCount = 3,
            viewCount = 89
        ),
        TicketPost(
            id = "3",
            type = "exchange",
            userName = "戏剧达人",
            postTime = "6小时前",
            title = "交换《李尔王》票",
            description = "有一张12月22日的《李尔王》票，想换12月23日的，有意者联系。",
            performanceName = "李尔王",
            performanceTime = "2024-12-22 19:30",
            performanceVenue = "广州大剧院",
            price = 0,
            quantity = 1,
            commentCount = 8,
            viewCount = 156
        ),
        TicketPost(
            id = "4",
            type = "transfer",
            userName = "艺术青年",
            postTime = "8小时前",
            title = "转让《仲夏夜之梦》票",
            description = "转让两张《仲夏夜之梦》演出票，位置在A区，价格优惠。",
            performanceName = "仲夏夜之梦",
            performanceTime = "2024-12-28 19:30",
            performanceVenue = "深圳音乐厅",
            price = 280,
            quantity = 2,
            commentCount = 12,
            viewCount = 234
        ),
        TicketPost(
            id = "5",
            type = "purchase",
            userName = "古典爱好者",
            postTime = "10小时前",
            title = "求购《麦克白》票",
            description = "求购一张《麦克白》演出票，任何位置都可以，价格好说。",
            performanceName = "麦克白",
            performanceTime = "2024-12-30 19:30",
            performanceVenue = "杭州大剧院",
            price = 400,
            quantity = 1,
            commentCount = 6,
            viewCount = 167
        )
    )
    
    return if (filter == "全部") {
        allPosts
    } else {
        allPosts.filter { getTypeText(it.type) == filter }
    }
}
