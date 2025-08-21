package com.example.thearter_platform.ui.models

// 剧目信息数据模型
data class PlayKnowledge(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val rating: Float,
    val viewCount: Int,
    val tags: List<String>
)

// 演员信息数据模型
data class ActorKnowledge(
    val id: String,
    val name: String,
    val role: String,
    val description: String,
    val rating: Float,
    val workCount: Int,
    val representativeWorks: List<String>,
    val isVerified: Boolean = false,
    val birthYear: Int? = null,
    val nationality: String? = null,
    val awards: List<String> = emptyList(),
    val specialties: List<String> = emptyList()
)

// 术语数据模型
data class Terminology(
    val id: String,
    val term: String,
    val category: String,
    val description: String,
    val detailedDescription: String,
    val usageExamples: List<String>,
    val relatedTerms: List<String>,
    val viewCount: Int
)

// 角色数据模型
data class Character(
    val id: String,
    val name: String,
    val description: String,
    val playId: String
)

// 时间线事件数据模型
data class TimelineEvent(
    val id: String,
    val date: String,
    val title: String,
    val description: String,
    val type: String // "play", "actor", "award", "premiere"
)

// 新闻资讯数据模型
data class NewsItem(
    val id: String,
    val title: String,
    val summary: String,
    val date: String,
    val type: String, // "industry", "performance", "award", "technology"
    val viewCount: Int
)

// 票务帖子数据模型
data class TicketPost(
    val id: String,
    val type: String, // "transfer", "purchase", "exchange"
    val userName: String,
    val postTime: String,
    val title: String,
    val description: String,
    val performanceName: String,
    val performanceTime: String,
    val performanceVenue: String,
    val price: Int,
    val quantity: Int,
    val commentCount: Int,
    val viewCount: Int
)

// 小红书风格的社区帖子数据模型
data class CommunityPost(
    val id: String,
    val userId: String,
    val userName: String,
    val userAvatar: String,
    val isVerified: Boolean = false,
    val postTime: String,
    val title: String,
    val content: String,
    val mediaType: MediaType, // IMAGE, VIDEO, MIXED
    val images: List<String> = emptyList(),
    val videoUrl: String? = null,
    val videoCover: String? = null,
    val videoDuration: Int? = null, // 秒
    val tags: List<String> = emptyList(),
    val location: String? = null,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val shareCount: Int = 0,
    val isLiked: Boolean = false,
    val isCollected: Boolean = false,
    val performanceInfo: PerformanceInfo? = null // 关联的演出信息
)

// 媒体类型
enum class MediaType {
    IMAGE, VIDEO, MIXED
}

// 演出信息
data class PerformanceInfo(
    val id: String,
    val name: String,
    val venue: String,
    val date: String,
    val price: String
)

// 用户信息
data class UserProfile(
    val id: String,
    val userName: String,
    val avatar: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val isVerified: Boolean = false,
    val isFollowing: Boolean = false
)

// 评论数据模型（扩展版）
data class Comment(
    val id: String,
    val userName: String,
    val userAvatar: String,
    val commentTime: String,
    val content: String,
    val likeCount: Int = 0,
    val isLiked: Boolean = false,
    val replies: List<Comment> = emptyList()
)
