package com.example.thearter_platform.ui.utils

import androidx.compose.ui.graphics.Color
import com.example.thearter_platform.ui.models.TicketPost
import com.example.thearter_platform.ui.models.Comment

// 获取票务类型颜色
fun getTypeColor(type: String): Color {
    return when (type) {
        "transfer" -> Color(0xFFE57373)      // 红色 - 转让
        "purchase" -> Color(0xFF81C784)      // 绿色 - 求购
        "exchange" -> Color(0xFF64B5F6)      // 蓝色 - 交换
        else -> Color(0xFF9E9E9E)            // 灰色
    }
}

// 获取票务类型文本
fun getTypeText(type: String): String {
    return when (type) {
        "transfer" -> "转让"
        "purchase" -> "求购"
        "exchange" -> "交换"
        else -> "其他"
    }
}

// 模拟数据 - 获取票务帖子列表
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

// 模拟数据 - 根据ID获取票务详情
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

// 模拟数据 - 获取评论列表
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
