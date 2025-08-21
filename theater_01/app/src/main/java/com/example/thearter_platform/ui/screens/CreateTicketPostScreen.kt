package com.example.thearter_platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTicketPostScreen(navController: NavController) {
    var selectedType by remember { mutableStateOf("transfer") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var performanceName by remember { mutableStateOf("") }
    var performanceTime by remember { mutableStateOf("") }
    var performanceVenue by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    var contactInfo by remember { mutableStateOf("") }
    
    val types = listOf(
        "transfer" to "转让",
        "purchase" to "求购", 
        "exchange" to "交换"
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📝 发布票务") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            // 发布逻辑
                            navController.navigateUp()
                        },
                        enabled = title.isNotBlank() && description.isNotBlank()
                    ) {
                        Icon(Icons.Filled.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("发布")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // 票务类型选择
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "票务类型",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        types.forEach { (type, label) ->
                            FilterChip(
                                selected = selectedType == type,
                                onClick = { selectedType = type },
                                label = { Text(label) },
                                modifier = Modifier.weight(1f),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
            
            // 基本信息
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "基本信息",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("标题") },
                        placeholder = { Text("请输入票务标题") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("详细描述") },
                        placeholder = { Text("请详细描述票务信息，如转让原因、求购要求等") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )
                }
            }
            
            // 演出信息
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                    
                    OutlinedTextField(
                        value = performanceName,
                        onValueChange = { performanceName = it },
                        label = { Text("演出名称") },
                        placeholder = { Text("如：《哈姆雷特》") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedTextField(
                        value = performanceTime,
                        onValueChange = { performanceTime = it },
                        label = { Text("演出时间") },
                        placeholder = { Text("如：2024-12-20 19:30") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Filled.DateRange, contentDescription = null)
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedTextField(
                        value = performanceVenue,
                        onValueChange = { performanceVenue = it },
                        label = { Text("演出地点") },
                        placeholder = { Text("如：国家大剧院") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Filled.LocationOn, contentDescription = null)
                        }
                    )
                }
            }
            
            // 票务详情
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = price,
                            onValueChange = { price = it },
                            label = { Text("价格") },
                            placeholder = { Text("0") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            prefix = { Text("¥") }
                        )
                        
                        OutlinedTextField(
                            value = quantity,
                            onValueChange = { quantity = it },
                            label = { Text("数量") },
                            placeholder = { Text("1") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            suffix = { Text("张") }
                        )
                    }
                    
                    if (selectedType == "exchange") {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "💡 交换类型票务价格设为0",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // 联系方式
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "联系方式",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    OutlinedTextField(
                        value = contactInfo,
                        onValueChange = { contactInfo = it },
                        label = { Text("联系方式") },
                        placeholder = { Text("微信号、手机号或其他联系方式") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "💡 建议留下微信号或手机号，方便其他用户联系",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // 发布按钮
            Button(
                onClick = {
                    // 发布逻辑
                    navController.navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = title.isNotBlank() && description.isNotBlank() && performanceName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Filled.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("发布票务")
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
