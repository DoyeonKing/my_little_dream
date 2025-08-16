package com.example.thearter_platform.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thearter_platform.data.model.*
import com.example.thearter_platform.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    
    // 用户状态
    private val _userState = MutableStateFlow<UIState<User>>(UIState())
    val userState: StateFlow<UIState<User>> = _userState.asStateFlow()
    
    // 推荐演出状态
    private val _recommendedPerformances = MutableStateFlow<UIState<List<Performance>>>(UIState())
    val recommendedPerformances: StateFlow<UIState<List<Performance>>> = _recommendedPerformances.asStateFlow()
    
    // 热门剧目状态
    private val _popularPlays = MutableStateFlow<UIState<List<Play>>>(UIState())
    val popularPlays: StateFlow<UIState<List<Play>>> = _popularPlays.asStateFlow()
    
    // 最新动态状态
    private val _latestPosts = MutableStateFlow<UIState<List<Post>>>(UIState())
    val latestPosts: StateFlow<UIState<List<Post>>> = _latestPosts.asStateFlow()
    
    // 知识节点状态
    private val _knowledgeNodes = MutableStateFlow<UIState<List<KnowledgeNode>>>(UIState())
    val knowledgeNodes: StateFlow<UIState<List<KnowledgeNode>>> = _knowledgeNodes.asStateFlow()
    
    init {
        loadHomeData()
    }
    
    private fun loadHomeData() {
        loadRecommendedPerformances()
        loadPopularPlays()
        loadLatestPosts()
        loadKnowledgeNodes()
    }
    
    private fun loadRecommendedPerformances() {
        viewModelScope.launch {
            _recommendedPerformances.value = UIState(isLoading = true)
            try {
                repository.getPerformances(page = 1, size = 5).collect { result ->
                    result.fold(
                        onSuccess = { response ->
                            _recommendedPerformances.value = UIState(data = response.content)
                        },
                        onFailure = { exception ->
                            _recommendedPerformances.value = UIState(error = exception.message)
                        }
                    )
                }
            } catch (e: Exception) {
                _recommendedPerformances.value = UIState(error = e.message)
            }
        }
    }
    
    private fun loadPopularPlays() {
        viewModelScope.launch {
            _popularPlays.value = UIState(isLoading = true)
            try {
                repository.getPlays(page = 1, size = 5).collect { result ->
                    result.fold(
                        onSuccess = { response ->
                            _popularPlays.value = UIState(data = response.content)
                        },
                        onFailure = { exception ->
                            _popularPlays.value = UIState(error = exception.message)
                        }
                    )
                }
            } catch (e: Exception) {
                _popularPlays.value = UIState(error = e.message)
            }
        }
    }
    
    private fun loadLatestPosts() {
        viewModelScope.launch {
            _latestPosts.value = UIState(isLoading = true)
            try {
                repository.getPosts(page = 1, size = 5).collect { result ->
                    result.fold(
                        onSuccess = { response ->
                            _latestPosts.value = UIState(data = response.content)
                        },
                        onFailure = { exception ->
                            _latestPosts.value = UIState(error = exception.message)
                        }
                    )
                }
            } catch (e: Exception) {
                _latestPosts.value = UIState(error = e.message)
            }
        }
    }
    
    private fun loadKnowledgeNodes() {
        viewModelScope.launch {
            _knowledgeNodes.value = UIState(isLoading = true)
            try {
                repository.getKnowledgeNodes(page = 1, size = 5).collect { result ->
                    result.fold(
                        onSuccess = { response ->
                            _knowledgeNodes.value = UIState(data = response.content)
                        },
                        onFailure = { exception ->
                            _knowledgeNodes.value = UIState(error = exception.message)
                        }
                    )
                }
            } catch (e: Exception) {
                _knowledgeNodes.value = UIState(error = e.message)
            }
        }
    }
    
    fun loadUserProfile() {
        viewModelScope.launch {
            _userState.value = UIState(isLoading = true)
            try {
                val result = repository.getUserProfile()
                result.fold(
                    onSuccess = { user ->
                        _userState.value = UIState(data = user)
                    },
                    onFailure = { exception ->
                        _userState.value = UIState(error = exception.message)
                    }
                )
            } catch (e: Exception) {
                _userState.value = UIState(error = e.message)
            }
        }
    }
    
    fun refreshData() {
        loadHomeData()
    }
}
