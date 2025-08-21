package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.thearter_platform.ui.components.UserAvatar
import com.example.thearter_platform.ui.models.CommunityPost
import com.example.thearter_platform.ui.models.Comment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDetailScreen(navController: NavController, postId: String?) {
    // 模拟视频帖子数据
    val post = remember {
        CommunityPost(
            id = postId ?: "1",
            userId = "user1",
            userName = "剧场达人小王",
            userAvatar = "https://picsum.photos/200/200?random=1",
            isVerified = true,
            postTime = "2小时前",
            title = "《哈姆雷特》精彩片段分享",
            content = "莎翁的经典之作，演员的表演真是绝了！特别是那段独白，听得我鸡皮疙瘩都起来了。强烈推荐大家去看！",
            mediaType = com.example.thearter_platform.ui.models.MediaType.VIDEO,
            videoUrl = "https://sample-videos.com/zip/10/mp4/SampleVideo_1280x720_1mb.mp4",
            videoCover = "https://picsum.photos/400/600?random=10",
            videoDuration = 120,
            tags = listOf("哈姆雷特", "莎士比亚", "经典戏剧", "推荐"),
            location = "国家大剧院",
            likeCount = 128,
            commentCount = 32,
            shareCount = 15,
            performanceInfo = com.example.thearter_platform.ui.models.PerformanceInfo(
                id = "perf1",
                name = "哈姆雷特",
                venue = "国家大剧院",
                date = "2024-01-15",
                price = "¥280起"
            )
        )
    }
    
    // 模拟评论数据
    val comments = remember {
        listOf(
            Comment(
                id = "1",
                userName = "戏剧爱好者",
                userAvatar = "https://picsum.photos/200/200?random=10",
                commentTime = "1小时前",
                content = "太震撼了！演员的表演真的很棒！",
                likeCount = 12
            ),
            Comment(
                id = "2",
                userName = "莎翁迷",
                userAvatar = "https://picsum.photos/200/200?random=11",
                commentTime = "30分钟前",
                content = "经典就是经典，百看不厌！",
                likeCount = 8
            ),
            Comment(
                id = "3",
                userName = "剧场新人",
                userAvatar = "https://picsum.photos/200/200?random=12",
                commentTime = "15分钟前",
                content = "第一次看戏剧，被深深吸引了！",
                likeCount = 5
            )
        )
    }
    
    var isLiked by remember { mutableStateOf(false) }
    var isCollected by remember { mutableStateOf(false) }
    var showComments by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack, 
                            "返回", 
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* 分享 */ }) {
                        Icon(
                            Icons.Rounded.Share, 
                            "分享", 
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* 更多 */ }) {
                        Icon(
                            Icons.Rounded.MoreVert, 
                            "更多", 
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 视频播放区域
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.Black)
                ) {
                    // 视频封面
                                         AsyncImage(
                         model = post.videoCover,
                         contentDescription = "视频封面",
                         modifier = Modifier.fillMaxSize(),
                         contentScale = ContentScale.Crop
                     )
                    
                    // 播放按钮
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.PlayArrow,
                            contentDescription = "播放",
                            modifier = Modifier.size(80.dp),
                            tint = Color.White
                        )
                    }
                    
                    // 视频时长
                    post.videoDuration?.let { duration ->
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = formatDuration(duration),
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
            
            // 帖子信息
            item {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // 用户信息
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UserAvatar(
                            avatarUrl = post.userAvatar,
                            isVerified = post.isVerified
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = post.userName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                if (post.isVerified) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                                                     Icon(
                                     Icons.Filled.CheckCircle,
                                     contentDescription = "已认证",
                                     tint = Color(0xFF1DA1F2),
                                     modifier = Modifier.size(16.dp)
                                 )
                                }
                            }
                            Text(
                                text = post.postTime,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        TextButton(
                            onClick = { /* 关注用户 */ }
                        ) {
                            Text("关注")
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // 标题和内容
                    if (post.title.isNotBlank()) {
                        Text(
                            text = post.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    if (post.content.isNotBlank()) {
                        Text(
                            text = post.content,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    // 标签
                    if (post.tags.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            post.tags.forEach { tag ->
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ) {
                                    Text(
                                        text = "#$tag",
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    // 演出信息
                    post.performanceInfo?.let { performance ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                                                 Icon(
                                     Icons.Filled.Info,
                                     contentDescription = "演出",
                                     tint = MaterialTheme.colorScheme.primary
                                 )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = performance.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${performance.venue} · ${performance.date}",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = performance.price,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    // 互动按钮
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            // 点赞
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { isLiked = !isLiked }
                            ) {
                                Icon(
                                    if (isLiked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                                    contentDescription = "点赞",
                                    tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${if (isLiked) post.likeCount + 1 else post.likeCount}",
                                    fontSize = 16.sp
                                )
                            }
                            
                            // 评论
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { showComments = !showComments }
                            ) {
                                                                 Icon(
                                     Icons.Filled.Email,
                                     contentDescription = "评论",
                                     modifier = Modifier.size(24.dp),
                                     tint = MaterialTheme.colorScheme.onSurfaceVariant
                                 )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${post.commentCount}",
                                    fontSize = 16.sp
                                )
                            }
                            
                            // 分享
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Rounded.Share,
                                    contentDescription = "分享",
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${post.shareCount}",
                                    fontSize = 16.sp
                                )
                            }
                        }
                        
                        // 收藏
                        IconButton(
                            onClick = { isCollected = !isCollected }
                        ) {
                                                         Icon(
                                 if (isCollected) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                 contentDescription = "收藏",
                                 tint = if (isCollected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                 modifier = Modifier.size(24.dp)
                             )
                        }
                    }
                }
            }
            
            // 评论区域
            if (showComments) {
                item {
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    Text(
                        text = "评论 (${comments.size})",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                
                items(comments) { comment ->
                    CommentItem(comment = comment)
                }
                
                item {
                    // 添加评论输入框
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UserAvatar(
                            avatarUrl = "https://picsum.photos/200/200?random=99",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                                                 OutlinedTextField(
                             value = "",
                             onValueChange = { },
                             placeholder = { Text("添加评论...") },
                             modifier = Modifier.weight(1f)
                         )
                    }
                }
            }
        }
    }
}

@Composable
private fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        UserAvatar(
            avatarUrl = comment.userAvatar,
            modifier = Modifier.size(32.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = comment.userName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = comment.commentTime,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = comment.content,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.FavoriteBorder,
                    contentDescription = "点赞",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${comment.likeCount}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "回复",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

private fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}
