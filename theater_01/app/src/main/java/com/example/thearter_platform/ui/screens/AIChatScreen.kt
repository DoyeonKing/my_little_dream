package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import com.example.thearter_platform.ui.components.StatItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIChatScreen(navController: NavController) {
    var userInput by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(getInitialMessages()) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("智能问答") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 欢迎卡片
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "AI剧场助手",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "我是您的专属剧场知识助手，可以为您解答关于剧目、演员、术语等各种问题",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem("已回答", "1,234")
                        StatItem("准确率", "95%")
                    }
                }
            }

            // 快捷问题
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(getQuickQuestions()) { question ->
                    QuickQuestionChip(
                        question = question,
                        onClick = {
                            userInput = question
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 聊天消息列表
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { message ->
                    ChatMessage(message = message)
                }
            }

            // 输入框
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("输入您的问题...") },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                FloatingActionButton(
                    onClick = {
                        if (userInput.isNotBlank()) {
                            val newMessage = ChatMessage(
                                id = (messages.size + 1).toString(),
                                text = userInput,
                                isUser = true,
                                timestamp = "现在"
                            )
                            messages = messages + newMessage
                            
                            // 模拟AI回复
                            val aiResponse = getAIResponse(userInput)
                            val aiMessage = ChatMessage(
                                id = (messages.size + 2).toString(),
                                text = aiResponse,
                                isUser = false,
                                timestamp = "现在"
                            )
                            messages = messages + aiMessage
                            
                            userInput = ""
                            
                            // 滚动到底部
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Filled.Send, contentDescription = "发送")
                }
            }
        }
    }
}

@Composable
fun ChatMessage(message: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isUser) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isUser) 16.dp else 4.dp,
                bottomEnd = if (message.isUser) 4.dp else 16.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.text,
                    fontSize = 14.sp,
                    color = if (message.isUser) 
                        MaterialTheme.colorScheme.onPrimary 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.timestamp,
                    fontSize = 10.sp,
                    color = if (message.isUser) 
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f) 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun QuickQuestionChip(
    question: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = question,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// 数据模型
data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String
)

// 辅助函数
fun getAIResponse(userQuestion: String): String {
    return when {
        userQuestion.contains("哈姆雷特") -> "《哈姆雷特》是莎士比亚最著名的悲剧之一，讲述了丹麦王子哈姆雷特为父报仇的故事。这部剧探讨了复仇、背叛、爱情等永恒主题，被认为是西方文学史上最伟大的作品之一。"
        userQuestion.contains("演员") -> "在剧场中，演员是表演艺术的核心。优秀的演员需要具备扎实的表演技巧、丰富的情感表达能力和对角色深入的理解。许多著名演员如巩俐、陈道明等都曾在舞台上留下经典表演。"
        userQuestion.contains("术语") -> "剧场术语是戏剧艺术的专业语言，包括'台步'、'亮相'、'念白'等。这些术语帮助演员和导演更好地沟通，确保演出的专业性和艺术性。"
        userQuestion.contains("购票") -> "您可以通过我们的购票系统购买演出票，支持在线选座、电子票务等功能。建议提前购票以确保座位，热门演出可能需要提前预订。"
        else -> "感谢您的提问！我是您的AI剧场助手，可以为您解答关于剧目、演员、术语、购票等各种问题。请随时向我提问！"
    }
}

// 模拟数据
fun getInitialMessages(): List<ChatMessage> {
    return listOf(
        ChatMessage(
            id = "1",
            text = "您好！我是您的AI剧场助手，有什么可以帮助您的吗？",
            isUser = false,
            timestamp = "现在"
        )
    )
}

fun getQuickQuestions(): List<String> {
    return listOf(
        "《哈姆雷特》讲了什么？",
        "如何成为演员？",
        "剧场术语有哪些？",
        "怎么购票？"
    )
}
