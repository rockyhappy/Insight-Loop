package com.devrachit.insightloop.presentation.feedback

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.core.common.NetworkUtils.handleResponse
import com.devrachit.core.common.Resource
import com.devrachit.insightloop.data.remote.mapper.toFeedbackCategory
import com.devrachit.insightloop.domain.model.CategoryData
import com.devrachit.insightloop.domain.repository.ServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val ServicesRepository: ServicesRepository
) : ViewModel() {

    private val _feedbackCategories = MutableStateFlow<List<CategoryData>>(emptyList())
    val feedbackCategories: StateFlow<List<CategoryData>> = _feedbackCategories.asStateFlow()

    private val _error = MutableSharedFlow<String>(replay = 1)
    val error: SharedFlow<String> = _error.asSharedFlow()

    private val _submitEnabled = MutableStateFlow(false)
    val submitEnabled: StateFlow<Boolean> = _submitEnabled.asStateFlow()

    private val _isLoading = MutableSharedFlow<Boolean>(replay = 1)
    val isLoading: SharedFlow<Boolean> = _isLoading.asSharedFlow()

    init {
        getDetails()
    }

    fun getDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)
            try {
                val response = ServicesRepository.getFeedbackDetails()
                val resource = handleResponse(response)
                when (resource) {
                    is Resource.Success -> {
                        _isLoading.emit(false)
                        resource.data?.let { data ->
                            _feedbackCategories.emit(
                                data.feedbackCategories.map { it.toFeedbackCategory() }
                            )
                        } ?: run {
                            _feedbackCategories.emit(emptyList())
                        }
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                        _error.emit(resource.message ?: "An unexpected error occurred")
                    }

                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                }
            } catch (e: HttpException) {
                _isLoading.emit(false)
                _error.emit("An unexpected error occurred\nResponse code: ${e.code()}")
            } catch (e: Exception) {
                _isLoading.emit(false)
                _error.emit("Couldn't reach the server\nCheck your internet connection")
            }
        }

    }
    fun setEnabled(enabled: Boolean) {
        _submitEnabled.value = enabled
    }

}