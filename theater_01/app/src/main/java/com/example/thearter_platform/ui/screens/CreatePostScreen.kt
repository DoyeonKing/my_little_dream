package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.thearter_platform.ui.components.TagChip
import com.example.thearter_platform.ui.models.MediaType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(navController: NavController) {
    var postTitle by remember { mutableStateOf("") }
    var postContent by remember { mutableStateOf("") }
    var selectedMediaType by remember { mutableStateOf(MediaType.IMAGE) }
    var selectedImages by remember { mutableStateOf(listOf<String>()) }
    var selectedVideo by remember { mutableStateOf<String?>(null) }
    var selectedTags by remember { mutableStateOf(listOf<String>()) }
    var location by remember { mutableStateOf("") }
    var showTagInput by remember { mutableStateOf(false) }
    var newTag by remember { mutableStateOf("") }
    
    val availableTags = listOf(
        "戏剧推荐", "观后感", "幕后花絮", "演员专访", "舞台摄影", 
        "经典剧目", "现代戏剧", "音乐剧", "话剧", "沉浸式戏剧"
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "发布动态", 
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "返回", modifier = Modifier.size(24.dp))
                    }
                },
                actions = {
                    TextButton(
                        onClick = { 
                            // 发布逻辑
                            navController.navigateUp() 
                        },
                        enabled = postContent.isNotBlank() || selectedImages.isNotEmpty() || selectedVideo != null
                    ) {
                        Text(
                            "发布", 
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
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
                .verticalScroll(rememberScrollState())
        ) {
            // 媒体类型选择
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FilterChip(
                    selected = selectedMediaType == MediaType.IMAGE,
                    onClick = { selectedMediaType = MediaType.IMAGE },
                    label = { Text("图片") },
                                         leadingIcon = {
                         Icon(Icons.Filled.Info, "图片", modifier = Modifier.size(18.dp))
                     }
                )
                
                FilterChip(
                    selected = selectedMediaType == MediaType.VIDEO,
                    onClick = { selectedMediaType = MediaType.VIDEO },
                    label = { Text("视频") },
                                         leadingIcon = {
                         Icon(Icons.Filled.Info, "视频", modifier = Modifier.size(18.dp))
                     }
                )
                
                FilterChip(
                    selected = selectedMediaType == MediaType.MIXED,
                    onClick = { selectedMediaType = MediaType.MIXED },
                    label = { Text("混合") },
                                         leadingIcon = {
                         Icon(Icons.Filled.Info, "混合", modifier = Modifier.size(18.dp))
                     }
                )
            }
            
            // 标题输入
            OutlinedTextField(
                value = postTitle,
                onValueChange = { postTitle = it },
                placeholder = { Text("添加标题...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 内容输入
            OutlinedTextField(
                value = postContent,
                onValueChange = { postContent = it },
                placeholder = { Text("分享你的观剧感受...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 媒体上传区域
            when (selectedMediaType) {
                MediaType.IMAGE -> {
                    // 图片上传
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "添加图片",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            if (selectedImages.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.outline,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            // 模拟选择图片
                                            selectedImages = listOf(
                                                "https://picsum.photos/400/600?random=100",
                                                "https://picsum.photos/400/500?random=101"
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            Icons.Filled.Add,
                                            "添加图片",
                                            modifier = Modifier.size(32.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            "点击添加图片",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            } else {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(selectedImages) { imageUrl ->
                                        Box {
                                            AsyncImage(
                                                model = imageUrl,
                                                contentDescription = "选择的图片",
                                                modifier = Modifier
                                                    .size(80.dp)
                                                    .clip(RoundedCornerShape(8.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                            IconButton(
                                                onClick = {
                                                    selectedImages = selectedImages.filter { it != imageUrl }
                                                },
                                                modifier = Modifier
                                                    .align(Alignment.TopEnd)
                                                    .size(24.dp)
                                                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                            ) {
                                                Icon(
                                                    Icons.Rounded.Close,
                                                    "删除",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                MediaType.VIDEO -> {
                    // 视频上传
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "添加视频",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            if (selectedVideo == null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.outline,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            // 模拟选择视频
                                            selectedVideo = "https://sample-videos.com/zip/10/mp4/SampleVideo_1280x720_1mb.mp4"
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            Icons.Filled.Info,
                                            "添加视频",
                                            modifier = Modifier.size(32.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            "点击添加视频",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            } else {
                                Box {
                                    AsyncImage(
                                        model = "https://picsum.photos/400/600?random=200",
                                        contentDescription = "视频封面",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    IconButton(
                                        onClick = { selectedVideo = null },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(8.dp)
                                            .size(32.dp)
                                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                    ) {
                                        Icon(
                                            Icons.Rounded.Close,
                                            "删除",
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                MediaType.MIXED -> {
                    // 混合内容上传
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "添加视频和图片",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // 视频上传
                            if (selectedVideo == null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.outline,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            selectedVideo = "https://sample-videos.com/zip/10/mp4/SampleVideo_1280x720_1mb.mp4"
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Filled.Info,
                                            "添加视频",
                                            modifier = Modifier.size(24.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "添加视频",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // 图片上传
                            if (selectedImages.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.outline,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            selectedImages = listOf(
                                                "https://picsum.photos/400/600?random=300",
                                                "https://picsum.photos/400/500?random=301"
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Filled.Add,
                                            "添加图片",
                                            modifier = Modifier.size(24.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "添加图片",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 标签选择
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "添加标签",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        IconButton(
                            onClick = { showTagInput = !showTagInput }
                        ) {
                            Icon(
                                Icons.Rounded.Add,
                                "添加自定义标签",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // 自定义标签输入
                    if (showTagInput) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = newTag,
                                onValueChange = { newTag = it },
                                placeholder = { Text("输入自定义标签") },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(
                                onClick = {
                                    if (newTag.isNotBlank() && !selectedTags.contains(newTag)) {
                                        selectedTags = selectedTags + newTag
                                        newTag = ""
                                    }
                                }
                            ) {
                                Text("添加")
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    
                    // 标签列表
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(availableTags) { tag ->
                            TagChip(
                                tag = tag,
                                onClick = {
                                    if (selectedTags.contains(tag)) {
                                        selectedTags = selectedTags.filter { it != tag }
                                    } else {
                                        selectedTags = selectedTags + tag
                                    }
                                },
                                modifier = Modifier.background(
                                    if (selectedTags.contains(tag)) 
                                        MaterialTheme.colorScheme.primary 
                                    else 
                                        MaterialTheme.colorScheme.primaryContainer,
                                    RoundedCornerShape(16.dp)
                                )
                            )
                        }
                    }
                    
                    // 已选择的标签
                    if (selectedTags.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "已选择:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(selectedTags) { tag ->
                                TagChip(
                                    tag = tag,
                                    onClick = {
                                        selectedTags = selectedTags.filter { it != tag }
                                    },
                                    modifier = Modifier.background(
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(16.dp)
                                    )
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 位置信息
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                placeholder = { Text("添加位置信息（可选）") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                leadingIcon = {
                    Icon(Icons.Rounded.LocationOn, "位置")
                }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

