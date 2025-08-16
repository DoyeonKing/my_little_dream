package com.example.thearter_platform.data.repository

import com.example.thearter_platform.data.api.*
import com.example.thearter_platform.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File

class Repository(private val apiService: ApiService) {
    
    // 用户相关操作
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun register(username: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = apiService.register(RegisterRequest(username, email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserProfile(): Result<User> {
        return try {
            val user = apiService.getUserProfile()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateUserProfile(user: User): Result<User> {
        return try {
            val updatedUser = apiService.updateUserProfile(user)
            Result.success(updatedUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 剧目相关操作
    fun getPlays(
        page: Int = 1,
        size: Int = 20,
        genre: String? = null,
        language: String? = null,
        search: String? = null
    ): Flow<Result<PagedResponse<Play>>> = flow {
        try {
            val response = apiService.getPlays(page, size, genre, language, search)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getPlayById(id: String): Result<Play> {
        return try {
            val play = apiService.getPlayById(id)
            Result.success(play)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPlayCharacters(id: String): Result<List<Character>> {
        return try {
            val characters = apiService.getPlayCharacters(id)
            Result.success(characters)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPlayGallery(id: String): Result<List<PlayGallery>> {
        return try {
            val gallery = apiService.getPlayGallery(id)
            Result.success(gallery)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 演员相关操作
    fun getActors(page: Int = 1, size: Int = 20, search: String? = null): Flow<Result<PagedResponse<Actor>>> = flow {
        try {
            val response = apiService.getActors(page, size, search)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getActorById(id: String): Result<Actor> {
        return try {
            val actor = apiService.getActorById(id)
            Result.success(actor)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getActorPlays(id: String): Result<List<ActorPlay>> {
        return try {
            val plays = apiService.getActorPlays(id)
            Result.success(plays)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 剧场相关操作
    fun getTheaters(city: String? = null, page: Int = 1, size: Int = 20): Flow<Result<PagedResponse<Theater>>> = flow {
        try {
            val response = apiService.getTheaters(city, page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getTheaterById(id: String): Result<Theater> {
        return try {
            val theater = apiService.getTheaterById(id)
            Result.success(theater)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTheaterSeating(id: String): Result<List<SeatingSection>> {
        return try {
            val seating = apiService.getTheaterSeating(id)
            Result.success(seating)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getNearbyAttractions(id: String): Result<List<NearbyAttraction>> {
        return try {
            val attractions = apiService.getNearbyAttractions(id)
            Result.success(attractions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 演出相关操作
    fun getPerformances(
        playId: String? = null,
        theaterId: String? = null,
        dateFrom: String? = null,
        dateTo: String? = null,
        page: Int = 1,
        size: Int = 20
    ): Flow<Result<PagedResponse<Performance>>> = flow {
        try {
            val response = apiService.getPerformances(playId, theaterId, dateFrom, dateTo, page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getPerformanceById(id: String): Result<Performance> {
        return try {
            val performance = apiService.getPerformanceById(id)
            Result.success(performance)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPerformanceCast(id: String): Result<List<PerformanceCast>> {
        return try {
            val cast = apiService.getPerformanceCast(id)
            Result.success(cast)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getSDInfo(id: String): Result<SDInfo?> {
        return try {
            val sdInfo = apiService.getSDInfo(id)
            Result.success(sdInfo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 票务相关操作
    fun getUserTickets(status: String? = null, page: Int = 1, size: Int = 20): Flow<Result<PagedResponse<Ticket>>> = flow {
        try {
            val response = apiService.getUserTickets(status, page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun purchaseTicket(request: PurchaseTicketRequest): Result<Ticket> {
        return try {
            val ticket = apiService.purchaseTicket(request)
            Result.success(ticket)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun cancelTicket(id: String): Result<Ticket> {
        return try {
            val ticket = apiService.cancelTicket(id)
            Result.success(ticket)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPerformanceDiscounts(id: String): Result<List<Discount>> {
        return try {
            val discounts = apiService.getPerformanceDiscounts(id)
            Result.success(discounts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 内容发布相关操作
    fun getPosts(
        type: String? = null,
        playId: String? = null,
        actorId: String? = null,
        page: Int = 1,
        size: Int = 20
    ): Flow<Result<PagedResponse<Post>>> = flow {
        try {
            val response = apiService.getPosts(type, playId, actorId, page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun createPost(request: CreatePostRequest): Result<Post> {
        return try {
            val post = apiService.createPost(request)
            Result.success(post)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updatePost(id: String, post: Post): Result<Post> {
        return try {
            val updatedPost = apiService.updatePost(id, post)
            Result.success(updatedPost)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deletePost(id: String): Result<Unit> {
        return try {
            apiService.deletePost(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPostComments(id: String): Result<List<Comment>> {
        return try {
            val comments = apiService.getPostComments(id)
            Result.success(comments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createComment(id: String, request: CreateCommentRequest): Result<Comment> {
        return try {
            val comment = apiService.createComment(id, request)
            Result.success(comment)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun likePost(id: String): Result<Unit> {
        return try {
            apiService.likePost(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun unlikePost(id: String): Result<Unit> {
        return try {
            apiService.unlikePost(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 社群相关操作
    fun getCommunities(page: Int = 1, size: Int = 20): Flow<Result<PagedResponse<Community>>> = flow {
        try {
            val response = apiService.getCommunities(page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getCommunityById(id: String): Result<Community> {
        return try {
            val community = apiService.getCommunityById(id)
            Result.success(community)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createCommunity(request: CreateCommunityRequest): Result<Community> {
        return try {
            val community = apiService.createCommunity(request)
            Result.success(community)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun joinCommunity(id: String): Result<Unit> {
        return try {
            apiService.joinCommunity(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun leaveCommunity(id: String): Result<Unit> {
        return try {
            apiService.leaveCommunity(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 知识图谱相关操作
    fun getKnowledgeNodes(
        type: String? = null,
        search: String? = null,
        page: Int = 1,
        size: Int = 20
    ): Flow<Result<PagedResponse<KnowledgeNode>>> = flow {
        try {
            val response = apiService.getKnowledgeNodes(type, search, page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getKnowledgeNodeById(id: String): Result<KnowledgeNode> {
        return try {
            val node = apiService.getKnowledgeNodeById(id)
            Result.success(node)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createKnowledgeNode(request: CreateKnowledgeNodeRequest): Result<KnowledgeNode> {
        return try {
            val node = apiService.createKnowledgeNode(request)
            Result.success(node)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 盘票相关操作
    fun getTicketExchanges(
        performanceId: String? = null,
        status: String? = null,
        page: Int = 1,
        size: Int = 20
    ): Flow<Result<PagedResponse<TicketExchange>>> = flow {
        try {
            val response = apiService.getTicketExchanges(performanceId, status, page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun createTicketExchange(request: CreateTicketExchangeRequest): Result<TicketExchange> {
        return try {
            val exchange = apiService.createTicketExchange(request)
            Result.success(exchange)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateTicketExchange(id: String, exchange: TicketExchange): Result<TicketExchange> {
        return try {
            val updatedExchange = apiService.updateTicketExchange(id, exchange)
            Result.success(updatedExchange)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteTicketExchange(id: String): Result<Unit> {
        return try {
            apiService.deleteTicketExchange(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // AI生成相关操作
    suspend fun generateAI(request: AIGenerationRequest): Result<AIGeneration> {
        return try {
            val generation = apiService.generateAI(request)
            Result.success(generation)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getUserGenerations(
        type: String? = null,
        page: Int = 1,
        size: Int = 20
    ): Flow<Result<PagedResponse<AIGeneration>>> = flow {
        try {
            val response = apiService.getUserGenerations(type, page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getGenerationById(id: String): Result<AIGeneration> {
        return try {
            val generation = apiService.getGenerationById(id)
            Result.success(generation)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 个人剧场日志相关操作
    fun getUserLogs(page: Int = 1, size: Int = 20): Flow<Result<PagedResponse<TheaterLog>>> = flow {
        try {
            val response = apiService.getUserLogs(page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun createTheaterLog(request: CreateTheaterLogRequest): Result<TheaterLog> {
        return try {
            val log = apiService.createTheaterLog(request)
            Result.success(log)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateTheaterLog(id: String, log: TheaterLog): Result<TheaterLog> {
        return try {
            val updatedLog = apiService.updateTheaterLog(id, log)
            Result.success(updatedLog)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteTheaterLog(id: String): Result<Unit> {
        return try {
            apiService.deleteTheaterLog(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 通知相关操作
    fun getNotifications(
        isRead: Boolean? = null,
        page: Int = 1,
        size: Int = 20
    ): Flow<Result<PagedResponse<Notification>>> = flow {
        try {
            val response = apiService.getNotifications(isRead, page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun markNotificationAsRead(id: String): Result<Notification> {
        return try {
            val notification = apiService.markNotificationAsRead(id)
            Result.success(notification)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun markAllNotificationsAsRead(): Result<Unit> {
        return try {
            apiService.markAllNotificationsAsRead()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 搜索相关操作
    suspend fun search(query: String, type: String? = null, page: Int = 1, size: Int = 20): Result<SearchResult> {
        return try {
            val result = apiService.search(query, type, page, size)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 文件上传相关操作
    suspend fun uploadImage(file: File): Result<UploadResponse> {
        return try {
            val requestBody = okhttp3.RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val part = okhttp3.MultipartBody.Part.createFormData("file", file.name, requestBody)
            val response = apiService.uploadImage(part)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun uploadVideo(file: File): Result<UploadResponse> {
        return try {
            val requestBody = okhttp3.RequestBody.create("video/*".toMediaTypeOrNull(), file)
            val part = okhttp3.MultipartBody.Part.createFormData("file", file.name, requestBody)
            val response = apiService.uploadVideo(part)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
