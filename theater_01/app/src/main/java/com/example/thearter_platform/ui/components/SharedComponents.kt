package com.example.thearter_platform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import coil.compose.AsyncImage
import com.example.thearter_platform.ui.models.CommunityPost
import com.example.thearter_platform.ui.models.MediaType

@Composable
fun StatItem(
    label: String,
    value: String,
    icon: ImageVector = Icons.Filled.Info
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

// 视频播放器组件
@Composable
fun VideoPlayer(
    videoUrl: String,
    coverUrl: String?,
    duration: Int?,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0f) }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
            .clickable { isPlaying = !isPlaying }
    ) {
        // 视频封面
        if (coverUrl != null) {
            AsyncImage(
                model = coverUrl,
                contentDescription = "视频封面",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        
        // 播放按钮
        if (!isPlaying) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.PlayArrow,
                    contentDescription = "播放",
                    modifier = Modifier.size(60.dp),
                    tint = Color.White
                )
            }
        }
        
        // 视频时长
        duration?.let { durationSeconds ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
            ) {
                Text(
                    text = formatDuration(durationSeconds),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
        
        // 播放进度条
        if (isPlaying) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                LinearProgressIndicator(
                    progress = currentPosition,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
            }
        }
    }
}

// 图片轮播组件
@Composable
fun ImageCarousel(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    var currentPage by remember { mutableStateOf(0) }
    
    Box(modifier = modifier) {
        // 图片显示
        AsyncImage(
            model = images.getOrNull(currentPage),
            contentDescription = "图片 ${currentPage + 1}",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        
        // 页码指示器
        if (images.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(images.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .size(8.dp)
                            .background(
                                color = if (currentPage == index) Color.White else Color.White.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
        
        // 左右切换按钮
        if (images.size > 1) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { 
                        currentPage = if (currentPage > 0) currentPage - 1 else images.size - 1 
                    }
                ) {
                    Icon(
                        Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "上一张",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            .padding(4.dp)
                    )
                }
                
                IconButton(
                    onClick = { 
                        currentPage = if (currentPage < images.size - 1) currentPage + 1 else 0 
                    }
                ) {
                    Icon(
                        Icons.Filled.KeyboardArrowRight,
                        contentDescription = "下一张",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

// 用户头像组件
@Composable
fun UserAvatar(
    avatarUrl: String,
    isVerified: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "用户头像",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        if (isVerified) {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = "已认证",
                tint = Color(0xFF1DA1F2),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(16.dp)
                    .background(Color.White, CircleShape)
            )
        }
    }
}

// 标签组件
@Composable
fun TagChip(
    tag: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable { onClick() },
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

// 小红书风格的帖子卡片组件
@Composable
fun CommunityPostCard(
    post: CommunityPost,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onCollectClick: () -> Unit = {},
    onUserClick: () -> Unit = {},
    onPostClick: () -> Unit = {},
    onVideoClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onPostClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 用户信息栏
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserAvatar(
                    avatarUrl = post.userAvatar,
                    isVerified = post.isVerified,
                    modifier = Modifier.clickable { onUserClick() }
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = post.userName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
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
                
                IconButton(onClick = { /* 更多选项 */ }) {
                    Icon(Icons.Rounded.MoreVert, "更多")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 标题和内容
            if (post.title.isNotBlank()) {
                Text(
                    text = post.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            if (post.content.isNotBlank()) {
                Text(
                    text = post.content,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // 媒体内容
            when (post.mediaType) {
                MediaType.IMAGE -> {
                    if (post.images.isNotEmpty()) {
                        ImageCarousel(
                            images = post.images,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                MediaType.VIDEO -> {
                    post.videoUrl?.let { videoUrl ->
                        VideoPlayer(
                            videoUrl = videoUrl,
                            coverUrl = post.videoCover,
                            duration = post.videoDuration,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onVideoClick() }
                        )
                    }
                }
                MediaType.MIXED -> {
                    // 混合内容：先显示视频，再显示图片
                    post.videoUrl?.let { videoUrl ->
                        VideoPlayer(
                            videoUrl = videoUrl,
                            coverUrl = post.videoCover,
                            duration = post.videoDuration,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onVideoClick() }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    if (post.images.isNotEmpty()) {
                        ImageCarousel(
                            images = post.images,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            
            // 标签
            if (post.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(post.tags) { tag ->
                        TagChip(tag = tag)
                    }
                }
            }
            
            // 演出信息卡片
            post.performanceInfo?.let { performance ->
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "演出",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = performance.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${performance.venue} · ${performance.date}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = performance.price,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            // 位置信息
            post.location?.let { location ->
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Rounded.LocationOn,
                        contentDescription = "位置",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = location,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 互动按钮栏
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 点赞按钮
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onLikeClick() }
                    ) {
                        Icon(
                            if (post.isLiked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            contentDescription = "点赞",
                            tint = if (post.isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${post.likeCount}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // 评论按钮
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onCommentClick() }
                    ) {
                                                 Icon(
                             Icons.Filled.Email,
                             contentDescription = "评论",
                             modifier = Modifier.size(20.dp),
                             tint = MaterialTheme.colorScheme.onSurfaceVariant
                         )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${post.commentCount}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // 分享按钮
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onShareClick() }
                    ) {
                        Icon(
                            Icons.Rounded.Share,
                            contentDescription = "分享",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${post.shareCount}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // 收藏按钮
                IconButton(onClick = onCollectClick) {
                    Icon(
                        if (post.isCollected) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "收藏",
                        tint = if (post.isCollected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// 工具函数
private fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}
