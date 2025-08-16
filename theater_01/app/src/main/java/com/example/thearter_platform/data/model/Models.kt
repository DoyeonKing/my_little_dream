package com.example.thearter_platform.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import java.util.Date

// 用户相关模型
@Parcelize
data class User(
    val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val bio: String? = null,
    val role: UserRole = UserRole.USER,
    val isVerified: Boolean = false,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable

enum class UserRole {
    USER, ACTOR, ADMIN
}

@Parcelize
data class UserProfile(
    val id: String,
    val userId: String,
    val phone: String? = null,
    val birthDate: Date? = null,
    val gender: Gender? = null,
    val location: String? = null,
    val preferences: Map<String, String>? = null
) : Parcelable

enum class Gender {
    MALE, FEMALE, OTHER
}

// 剧目相关模型
@Parcelize
data class Play(
    val id: String,
    val title: String,
    val originalTitle: String? = null,
    val description: String,
    val durationMinutes: Int,
    val genre: List<String>,
    val language: String,
    val ageRating: String,
    val posterUrl: String? = null,
    val backgroundStory: String? = null,
    val status: PlayStatus = PlayStatus.DRAFT,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable

enum class PlayStatus {
    DRAFT, PUBLISHED, ARCHIVED
}

@Parcelize
data class Character(
    val id: String,
    val playId: String,
    val name: String,
    val description: String? = null,
    val imageUrl: String? = null
) : Parcelable

@Parcelize
data class PlayGallery(
    val id: String,
    val playId: String,
    val imageUrl: String,
    val caption: String? = null,
    val sortOrder: Int = 0
) : Parcelable

// 演员相关模型
@Parcelize
data class Actor(
    val id: String,
    val userId: String? = null,
    val name: String,
    val englishName: String? = null,
    val avatarUrl: String? = null,
    val bio: String? = null,
    val birthDate: Date? = null,
    val nationality: String? = null,
    val education: String? = null,
    val awards: List<String>? = null,
    val socialMedia: Map<String, String>? = null,
    val isVerified: Boolean = false
) : Parcelable

@Parcelize
data class ActorPlay(
    val id: String,
    val actorId: String,
    val playId: String,
    val characterId: String? = null,
    val roleType: RoleType = RoleType.SUPPORTING
) : Parcelable

enum class RoleType {
    LEAD, SUPPORTING, ENSEMBLE
}

// 剧场相关模型
@Parcelize
data class Theater(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val description: String? = null,
    val capacity: Int,
    val facilities: List<String>? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val contactPhone: String? = null,
    val contactEmail: String? = null,
    val websiteUrl: String? = null,
    val status: TheaterStatus = TheaterStatus.ACTIVE
) : Parcelable

enum class TheaterStatus {
    ACTIVE, INACTIVE, MAINTENANCE
}

@Parcelize
data class TheaterImage(
    val id: String,
    val theaterId: String,
    val imageUrl: String,
    val caption: String? = null,
    val sortOrder: Int = 0
) : Parcelable

@Parcelize
data class SeatingSection(
    val id: String,
    val theaterId: String,
    val name: String,
    val description: String? = null,
    val priceMin: Double,
    val priceMax: Double,
    val seatCount: Int
) : Parcelable

@Parcelize
data class NearbyAttraction(
    val id: String,
    val theaterId: String,
    val name: String,
    val type: AttractionType,
    val distanceMeters: Int,
    val description: String? = null,
    val rating: Double? = null,
    val address: String? = null,
    val contactPhone: String? = null
) : Parcelable

enum class AttractionType {
    RESTAURANT, HOTEL, SHOPPING, ENTERTAINMENT
}

// 演出相关模型
@Parcelize
data class Performance(
    val id: String,
    val playId: String,
    val theaterId: String,
    val performanceDate: Date,
    val performanceTime: String,
    val status: PerformanceStatus = PerformanceStatus.SCHEDULED,
    val totalSeats: Int,
    val availableSeats: Int,
    val priceMin: Double,
    val priceMax: Double,
    val saleStartDate: Date? = null,
    val saleEndDate: Date? = null
) : Parcelable

enum class PerformanceStatus {
    SCHEDULED, ONGOING, COMPLETED, CANCELLED
}

@Parcelize
data class PerformanceCast(
    val id: String,
    val performanceId: String,
    val actorId: String,
    val characterId: String,
    val roleType: RoleType = RoleType.SUPPORTING
) : Parcelable

@Parcelize
data class SDInfo(
    val id: String,
    val performanceId: String,
    val hasSD: Boolean = false,
    val location: String? = null,
    val sdTime: String? = null,
    val requirements: List<String>? = null,
    val maxParticipants: Int? = null
) : Parcelable

// 票务相关模型
@Parcelize
data class Ticket(
    val id: String,
    val performanceId: String,
    val userId: String,
    val seatSection: String,
    val seatRow: String,
    val seatNumber: String,
    val price: Double,
    val status: TicketStatus = TicketStatus.RESERVED,
    val paymentMethod: PaymentMethod = PaymentMethod.ONLINE,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val transactionId: String? = null,
    val purchasedAt: Date = Date(),
    val usedAt: Date? = null
) : Parcelable

enum class TicketStatus {
    RESERVED, PAID, USED, CANCELLED
}

enum class PaymentMethod {
    ONLINE, OFFLINE
}

enum class PaymentStatus {
    PENDING, COMPLETED, FAILED, REFUNDED
}

@Parcelize
data class Discount(
    val id: String,
    val performanceId: String,
    val name: String,
    val type: DiscountType,
    val value: Double,
    val conditions: List<String>? = null,
    val validFrom: Date,
    val validUntil: Date,
    val maxUses: Int? = null,
    val usedCount: Int = 0,
    val isActive: Boolean = true
) : Parcelable

enum class DiscountType {
    PERCENTAGE, FIXED
}

// 内容发布相关模型
@Parcelize
data class Post(
    val id: String,
    val authorId: String,
    val type: PostType,
    val title: String,
    val content: String,
    val playId: String? = null,
    val performanceId: String? = null,
    val actorId: String? = null,
    val isPublic: Boolean = true,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val status: PostStatus = PostStatus.PUBLISHED,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable

enum class PostType {
    REVIEW, PHOTO, VIDEO, ARTICLE
}

enum class PostStatus {
    DRAFT, PUBLISHED, HIDDEN
}

@Parcelize
data class PostMedia(
    val id: String,
    val postId: String,
    val mediaType: MediaType,
    val mediaUrl: String,
    val thumbnailUrl: String? = null,
    val durationSeconds: Int? = null,
    val fileSize: Long? = null,
    val sortOrder: Int = 0
) : Parcelable

enum class MediaType {
    IMAGE, VIDEO
}

@Parcelize
data class Comment(
    val id: String,
    val postId: String,
    val authorId: String,
    val parentId: String? = null,
    val content: String,
    val likeCount: Int = 0,
    val status: CommentStatus = CommentStatus.ACTIVE,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable

enum class CommentStatus {
    ACTIVE, HIDDEN, DELETED
}

// 社群相关模型
@Parcelize
data class Community(
    val id: String,
    val name: String,
    val description: String? = null,
    val avatarUrl: String? = null,
    val coverImageUrl: String? = null,
    val isPrivate: Boolean = false,
    val memberCount: Int = 0,
    val postCount: Int = 0,
    val rules: List<String>? = null,
    val status: CommunityStatus = CommunityStatus.ACTIVE,
    val createdBy: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable

enum class CommunityStatus {
    ACTIVE, INACTIVE, BANNED
}

@Parcelize
data class CommunityMember(
    val id: String,
    val communityId: String,
    val userId: String,
    val role: CommunityRole = CommunityRole.MEMBER,
    val joinedAt: Date = Date()
) : Parcelable

enum class CommunityRole {
    MEMBER, MODERATOR, ADMIN
}

// 知识图谱相关模型
@Parcelize
data class KnowledgeNode(
    val id: String,
    val type: KnowledgeType,
    val title: String,
    val content: String,
    val tags: List<String>? = null,
    val connections: List<String>? = null,
    val viewCount: Int = 0,
    val isFeatured: Boolean = false,
    val createdBy: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable

enum class KnowledgeType {
    PLAY, ACTOR, TERM, NEWS, TIMELINE
}

// 盘票相关模型
data class TicketExchange(
    val id: String,
    val sellerId: String,
    val performanceId: String,
    val seatSection: String,
    val seatRow: String,
    val seatNumber: String,
    val originalPrice: Double,
    val sellingPrice: Double,
    val status: ExchangeStatus = ExchangeStatus.AVAILABLE,
    val description: String? = null,
    val contactInfo: Map<String, String>? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class ExchangeStatus {
    AVAILABLE, RESERVED, SOLD
}

// AI生成相关模型
data class AIGeneration(
    val id: String,
    val userId: String,
    val type: AIGenerationType,
    val prompt: String,
    val resultUrl: String? = null,
    val status: AIGenerationStatus = AIGenerationStatus.PENDING,
    val errorMessage: String? = null,
    val createdAt: Date = Date(),
    val completedAt: Date? = null
)

enum class AIGenerationType {
    CHARACTER, PHOTO, CONTENT
}

enum class AIGenerationStatus {
    PENDING, PROCESSING, COMPLETED, FAILED
}

// 个人剧场日志模型
data class TheaterLog(
    val id: String,
    val userId: String,
    val performanceId: String,
    val logDate: Date,
    val rating: Int? = null,
    val review: String? = null,
    val memories: List<String>? = null,
    val tags: List<String>? = null,
    val isPublic: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

@Parcelize
data class LogPhoto(
    val id: String,
    val logId: String,
    val photoUrl: String,
    val caption: String? = null,
    val sortOrder: Int = 0
) : Parcelable

// 通知相关模型
data class Notification(
    val id: String,
    val userId: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val data: Map<String, String>? = null,
    val isRead: Boolean = false,
    val createdAt: Date = Date()
)

enum class NotificationType {
    SYSTEM, PERFORMANCE, COMMUNITY, TICKET
}

// UI状态模型
data class UIState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)

// 搜索结果模型
data class SearchResult(
    val plays: List<Play> = emptyList(),
    val actors: List<Actor> = emptyList(),
    val performances: List<Performance> = emptyList(),
    val posts: List<Post> = emptyList()
)
