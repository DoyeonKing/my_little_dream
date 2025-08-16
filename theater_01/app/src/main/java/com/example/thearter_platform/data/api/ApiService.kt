package com.example.thearter_platform.data.api

import com.example.thearter_platform.data.model.*
import retrofit2.http.*

interface ApiService {
    
    // 用户相关API
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
    
    @GET("users/profile")
    suspend fun getUserProfile(): User
    
    @PUT("users/profile")
    suspend fun updateUserProfile(@Body user: User): User
    
    // 剧目相关API
    @GET("plays")
    suspend fun getPlays(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("genre") genre: String? = null,
        @Query("language") language: String? = null,
        @Query("search") search: String? = null
    ): PagedResponse<Play>
    
    @GET("plays/{id}")
    suspend fun getPlayById(@Path("id") id: String): Play
    
    @GET("plays/{id}/characters")
    suspend fun getPlayCharacters(@Path("id") id: String): List<Character>
    
    @GET("plays/{id}/gallery")
    suspend fun getPlayGallery(@Path("id") id: String): List<PlayGallery>
    
    // 演员相关API
    @GET("actors")
    suspend fun getActors(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("search") search: String? = null
    ): PagedResponse<Actor>
    
    @GET("actors/{id}")
    suspend fun getActorById(@Path("id") id: String): Actor
    
    @GET("actors/{id}/plays")
    suspend fun getActorPlays(@Path("id") id: String): List<ActorPlay>
    
    // 剧场相关API
    @GET("theaters")
    suspend fun getTheaters(
        @Query("city") city: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<Theater>
    
    @GET("theaters/{id}")
    suspend fun getTheaterById(@Path("id") id: String): Theater
    
    @GET("theaters/{id}/seating")
    suspend fun getTheaterSeating(@Path("id") id: String): List<SeatingSection>
    
    @GET("theaters/{id}/nearby")
    suspend fun getNearbyAttractions(@Path("id") id: String): List<NearbyAttraction>
    
    // 演出相关API
    @GET("performances")
    suspend fun getPerformances(
        @Query("playId") playId: String? = null,
        @Query("theaterId") theaterId: String? = null,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<Performance>
    
    @GET("performances/{id}")
    suspend fun getPerformanceById(@Path("id") id: String): Performance
    
    @GET("performances/{id}/cast")
    suspend fun getPerformanceCast(@Path("id") id: String): List<PerformanceCast>
    
    @GET("performances/{id}/sd")
    suspend fun getSDInfo(@Path("id") id: String): SDInfo?
    
    // 票务相关API
    @GET("tickets")
    suspend fun getUserTickets(
        @Query("status") status: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<Ticket>
    
    @POST("tickets")
    suspend fun purchaseTicket(@Body request: PurchaseTicketRequest): Ticket
    
    @PUT("tickets/{id}/cancel")
    suspend fun cancelTicket(@Path("id") id: String): Ticket
    
    @GET("performances/{id}/discounts")
    suspend fun getPerformanceDiscounts(@Path("id") id: String): List<Discount>
    
    // 内容发布相关API
    @GET("posts")
    suspend fun getPosts(
        @Query("type") type: String? = null,
        @Query("playId") playId: String? = null,
        @Query("actorId") actorId: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<Post>
    
    @POST("posts")
    suspend fun createPost(@Body post: CreatePostRequest): Post
    
    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: String, @Body post: Post): Post
    
    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: String)
    
    @GET("posts/{id}/comments")
    suspend fun getPostComments(@Path("id") id: String): List<Comment>
    
    @POST("posts/{id}/comments")
    suspend fun createComment(@Path("id") id: String, @Body request: CreateCommentRequest): Comment
    
    @POST("posts/{id}/like")
    suspend fun likePost(@Path("id") id: String)
    
    @DELETE("posts/{id}/like")
    suspend fun unlikePost(@Path("id") id: String)
    
    // 社群相关API
    @GET("communities")
    suspend fun getCommunities(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<Community>
    
    @GET("communities/{id}")
    suspend fun getCommunityById(@Path("id") id: String): Community
    
    @POST("communities")
    suspend fun createCommunity(@Body community: CreateCommunityRequest): Community
    
    @POST("communities/{id}/join")
    suspend fun joinCommunity(@Path("id") id: String)
    
    @DELETE("communities/{id}/leave")
    suspend fun leaveCommunity(@Path("id") id: String)
    
    // 知识图谱相关API
    @GET("knowledge")
    suspend fun getKnowledgeNodes(
        @Query("type") type: String? = null,
        @Query("search") search: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<KnowledgeNode>
    
    @GET("knowledge/{id}")
    suspend fun getKnowledgeNodeById(@Path("id") id: String): KnowledgeNode
    
    @POST("knowledge")
    suspend fun createKnowledgeNode(@Body node: CreateKnowledgeNodeRequest): KnowledgeNode
    
    // 盘票相关API
    @GET("ticket-exchanges")
    suspend fun getTicketExchanges(
        @Query("performanceId") performanceId: String? = null,
        @Query("status") status: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<TicketExchange>
    
    @POST("ticket-exchanges")
    suspend fun createTicketExchange(@Body exchange: CreateTicketExchangeRequest): TicketExchange
    
    @PUT("ticket-exchanges/{id}")
    suspend fun updateTicketExchange(@Path("id") id: String, @Body exchange: TicketExchange): TicketExchange
    
    @DELETE("ticket-exchanges/{id}")
    suspend fun deleteTicketExchange(@Path("id") id: String)
    
    // AI生成相关API
    @POST("ai/generate")
    suspend fun generateAI(@Body request: AIGenerationRequest): AIGeneration
    
    @GET("ai/generations")
    suspend fun getUserGenerations(
        @Query("type") type: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<AIGeneration>
    
    @GET("ai/generations/{id}")
    suspend fun getGenerationById(@Path("id") id: String): AIGeneration
    
    // 个人剧场日志相关API
    @GET("theater-logs")
    suspend fun getUserLogs(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<TheaterLog>
    
    @POST("theater-logs")
    suspend fun createTheaterLog(@Body log: CreateTheaterLogRequest): TheaterLog
    
    @PUT("theater-logs/{id}")
    suspend fun updateTheaterLog(@Path("id") id: String, @Body log: TheaterLog): TheaterLog
    
    @DELETE("theater-logs/{id}")
    suspend fun deleteTheaterLog(@Path("id") id: String)
    
    // 通知相关API
    @GET("notifications")
    suspend fun getNotifications(
        @Query("isRead") isRead: Boolean? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): PagedResponse<Notification>
    
    @PUT("notifications/{id}/read")
    suspend fun markNotificationAsRead(@Path("id") id: String): Notification
    
    @PUT("notifications/read-all")
    suspend fun markAllNotificationsAsRead()
    
    // 搜索相关API
    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): SearchResult
    
    // 文件上传API
    @Multipart
    @POST("upload/image")
    suspend fun uploadImage(@Part file: okhttp3.MultipartBody.Part): UploadResponse
    
    @Multipart
    @POST("upload/video")
    suspend fun uploadVideo(@Part file: okhttp3.MultipartBody.Part): UploadResponse
}

// 请求和响应数据类
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: User
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val token: String,
    val user: User
)

data class PagedResponse<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val currentPage: Int,
    val size: Int
)

data class CreatePostRequest(
    val type: PostType,
    val title: String,
    val content: String,
    val playId: String? = null,
    val performanceId: String? = null,
    val actorId: String? = null,
    val isPublic: Boolean = true,
    val mediaUrls: List<String> = emptyList()
)

data class CreateCommentRequest(
    val content: String,
    val parentId: String? = null
)

data class CreateCommunityRequest(
    val name: String,
    val description: String? = null,
    val isPrivate: Boolean = false,
    val rules: List<String> = emptyList()
)

data class CreateKnowledgeNodeRequest(
    val type: KnowledgeType,
    val title: String,
    val content: String,
    val tags: List<String> = emptyList(),
    val connections: List<String> = emptyList()
)

data class CreateTicketExchangeRequest(
    val performanceId: String,
    val seatSection: String,
    val seatRow: String,
    val seatNumber: String,
    val originalPrice: Double,
    val sellingPrice: Double,
    val description: String? = null,
    val contactInfo: Map<String, String> = emptyMap()
)

data class AIGenerationRequest(
    val type: AIGenerationType,
    val prompt: String
)

data class CreateTheaterLogRequest(
    val performanceId: String,
    val logDate: String,
    val rating: Int? = null,
    val review: String? = null,
    val memories: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val isPublic: Boolean = false,
    val photoUrls: List<String> = emptyList()
)

data class PurchaseTicketRequest(
    val performanceId: String,
    val seatSection: String,
    val seatRow: String,
    val seatNumber: String,
    val paymentMethod: PaymentMethod = PaymentMethod.ONLINE
)

data class UploadResponse(
    val url: String,
    val filename: String,
    val size: Long
)
