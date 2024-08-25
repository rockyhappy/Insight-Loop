package com.devrachit.insightloop.presentation.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.core.common.Resource
import com.devrachit.insightloop.domain.model.FeedbackCategory
import com.devrachit.insightloop.domain.useCase.GetFeedbackDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getFeedbackDataUseCase: GetFeedbackDataUseCase) :
    ViewModel() {

    private val _feedbackCategories: MutableStateFlow<List<FeedbackCategory>> = MutableStateFlow(
        emptyList()
    )
    val feedbackCategories: StateFlow<List<FeedbackCategory>> get() = _feedbackCategories.asStateFlow()

    private val _isLoading: Channel<Boolean> = Channel()
    val isLoading get() = _isLoading.receiveAsFlow()

    private val _error: Channel<String> = Channel()
    val error get() = _error.receiveAsFlow()

    var submitEnabled: Boolean = false
        private set

    init {
        getFeedbackData()
    }

    private fun getFeedbackData() {
        getFeedbackDataUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _isLoading.send(true)
                }

                is Resource.Success -> {
                    _isLoading.send(false)
                    _feedbackCategories.value = it.data!!
                }

                is Resource.Error -> {
                    _isLoading.send(false)
                    _error.send(it.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setSubmitEnabled(enabled: Boolean) {
        submitEnabled = enabled
    }

}