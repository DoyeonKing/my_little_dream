package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thearter_platform.ui.components.CommunityPostCard
import com.example.thearter_platform.ui.models.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("推荐", "关注", "同城", "热门")
    
    // 模拟数据
    val posts = remember {
        listOf(
            CommunityPost(
                id = "1",
                userId = "user1",
                userName = "剧场达人小王",
                userAvatar = "https://picsum.photos/200/200?random=1",
                isVerified = true,
                postTime = "2小时前",
                title = "今晚的《哈姆雷特》太震撼了！",
                content = "莎翁的经典之作，演员的表演真是绝了！特别是那段独白，听得我鸡皮疙瘩都起来了。强烈推荐大家去看！",
                mediaType = MediaType.IMAGE,
                images = listOf(
                    "https://picsum.photos/400/600?random=10",
                    "https://picsum.photos/400/500?random=11",
                    "https://picsum.photos/400/700?random=12"
                ),
                tags = listOf("哈姆雷特", "莎士比亚", "经典戏剧", "推荐"),
                location = "国家大剧院",
                likeCount = 128,
                commentCount = 32,
                shareCount = 15,
                performanceInfo = PerformanceInfo(
                    id = "perf1",
                    name = "哈姆雷特",
                    venue = "国家大剧院",
                    date = "2024-01-15",
                    price = "¥280起"
                )
            ),
            CommunityPost(
                id = "2",
                userId = "user2",
                userName = "音乐剧爱好者",
                userAvatar = "https://picsum.photos/200/200?random=2",
                postTime = "4小时前",
                title = "《歌剧魅影》幕后花絮",
                content = "有幸参观了《歌剧魅影》的排练现场，演员们真的太敬业了！",
                mediaType = MediaType.VIDEO,
                videoUrl = "https://sample-videos.com/zip/10/mp4/SampleVideo_1280x720_1mb.mp4",
                videoCover = "https://picsum.photos/400/600?random=20",
                videoDuration = 45,
                tags = listOf("歌剧魅影", "幕后", "音乐剧"),
                location = "上海大剧院",
                likeCount = 89,
                commentCount = 18,
                shareCount = 7
            ),
            CommunityPost(
                id = "3",
                userId = "user3",
                userName = "戏剧评论家",
                userAvatar = "https://picsum.photos/200/200?random=3",
                isVerified = true,
                postTime = "6小时前",
                title = "现代戏剧的新趋势",
                content = "近年来，沉浸式戏剧越来越受欢迎，观众不再是被动的观看者，而是故事的参与者。",
                mediaType = MediaType.MIXED,
                images = listOf(
                    "https://picsum.photos/400/800?random=30",
                    "https://picsum.photos/400/600?random=31"
                ),
                videoUrl = "https://sample-videos.com/zip/10/mp4/SampleVideo_1280x720_2mb.mp4",
                videoCover = "https://picsum.photos/400/600?random=32",
                videoDuration = 120,
                tags = listOf("沉浸式戏剧", "现代戏剧", "戏剧评论"),
                location = "北京人艺",
                likeCount = 256,
                commentCount = 45,
                shareCount = 23
            ),
            CommunityPost(
                id = "4",
                userId = "user4",
                userName = "舞台摄影师",
                userAvatar = "https://picsum.photos/200/200?random=4",
                postTime = "8小时前",
                title = "舞台光影的艺术",
                content = "分享一些舞台摄影的技巧，如何捕捉最美的瞬间。",
                mediaType = MediaType.IMAGE,
                images = listOf(
                    "https://picsum.photos/400/900?random=40",
                    "https://picsum.photos/400/700?random=41",
                    "https://picsum.photos/400/600?random=42",
                    "https://picsum.photos/400/800?random=43"
                ),
                tags = listOf("舞台摄影", "光影艺术", "摄影技巧"),
                location = "广州大剧院",
                likeCount = 167,
                commentCount = 28,
                shareCount = 12
            ),
            CommunityPost(
                id = "5",
                userId = "user5",
                userName = "戏剧学生",
                userAvatar = "https://picsum.photos/200/200?random=5",
                postTime = "10小时前",
                title = "我的第一次登台演出",
                content = "紧张又兴奋！虽然只是一个小角色，但站在舞台上的感觉真的太棒了！",
                mediaType = MediaType.VIDEO,
                videoUrl = "https://sample-videos.com/zip/10/mp4/SampleVideo_1280x720_1mb.mp4",
                videoCover = "https://picsum.photos/400/600?random=50",
                videoDuration = 30,
                tags = listOf("首次登台", "戏剧学生", "舞台表演"),
                location = "中央戏剧学院",
                likeCount = 342,
                commentCount = 67,
                shareCount = 34
            )
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "🎭 剧场社区",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ) 
                },
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Rounded.Search, "搜索", modifier = Modifier.size(24.dp))
                    }
                    IconButton(onClick = { navController.navigate("create-post") }) {
                        Icon(Icons.Rounded.Add, "发布", modifier = Modifier.size(24.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 标签栏
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { 
                            Text(
                                title, 
                                fontSize = 16.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            ) 
                        }
                    )
                }
            }
            
            // 内容区域 - 使用瀑布流布局
            LazyVerticalStaggeredGrid(
                columns = androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells.Fixed(1),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(posts) { post ->
                    CommunityPostCard(
                        post = post,
                        onLikeClick = {
                            // 处理点赞逻辑
                        },
                        onCommentClick = {
                            // 处理评论逻辑
                        },
                        onShareClick = {
                            // 处理分享逻辑
                        },
                        onCollectClick = {
                            // 处理收藏逻辑
                        },
                        onUserClick = {
                            // 处理用户点击逻辑
                        },
                        onPostClick = {
                            // 处理帖子点击逻辑
                        },
                        onVideoClick = {
                            // 跳转到视频详情页面
                            navController.navigate("video-detail/${post.id}")
                        }
                    )
                }
            }
        }
    }
}
