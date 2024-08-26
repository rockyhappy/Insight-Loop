package com.devrachit.insightloop.presentation.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.core.common.Resource
import com.devrachit.insightloop.data.remote.dto.FeedbackCategoryRequestDto
import com.devrachit.insightloop.data.remote.dto.FeedbackRequestDto
import com.devrachit.insightloop.domain.model.Category
import com.devrachit.insightloop.domain.model.Feedback
import com.devrachit.insightloop.domain.model.FeedbackCategory
import com.devrachit.insightloop.domain.model.FeedbackItem
import com.devrachit.insightloop.domain.useCase.GetFeedbackDataUseCase
import com.devrachit.insightloop.domain.useCase.PostFeedbackDataUseCase
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
class HomeViewModel @Inject constructor(
    private val getFeedbackDataUseCase: GetFeedbackDataUseCase,
    private val postFeedbackDataUseCase: PostFeedbackDataUseCase
) :
    ViewModel() {

    private val _feedbackCategories: MutableStateFlow<List<FeedbackCategory>> = MutableStateFlow(
        emptyList()
    )
    val feedbackCategories: StateFlow<List<FeedbackCategory>> get() = _feedbackCategories.asStateFlow()

    private var _feedbackRequest: MutableStateFlow<FeedbackRequestDto> = MutableStateFlow(FeedbackRequestDto())
    val feedbackrequest:StateFlow<FeedbackRequestDto> get()=_feedbackRequest.asStateFlow()

    private val _isLoading: Channel<Boolean> = Channel()
    val isLoading get() = _isLoading.receiveAsFlow()

    private val _error: Channel<String> = Channel()
    val error get() = _error.receiveAsFlow()

    var submitEnabled: Boolean = false
        private set

    init {
        getFeedbackData()
    }

    fun makeRequest() {
        val updatedRequest = FeedbackRequestDto(
            confidence = mapToFeedbackCategoryRequestDto(Category.CONFIDENCE),
            grammar = mapToFeedbackCategoryRequestDto(Category.GRAMMAR),
            fluencyAndVocabulary = mapToFeedbackCategoryRequestDto(Category.FLUENCY_AND_VOCABULARY),
            pronunciation = mapToFeedbackCategoryRequestDto(Category.PRONUNCIATION)
        )
        _feedbackRequest.value = updatedRequest
        postFeedback()

    }
    private fun mapToFeedbackCategoryRequestDto(category: Category): FeedbackCategoryRequestDto? {
        val feedbackCategory = _feedbackCategories.value.find { it.category == category } ?: return null

        return FeedbackCategoryRequestDto(
            didWell = mapToFeedbackDetailsRequestDto(feedbackCategory.feedbackItems.filter { it.selectedFeedback == Feedback.DID_WELL }),
            scopeOfImprovement = mapToFeedbackDetailsRequestDto(feedbackCategory.feedbackItems.filter { it.selectedFeedback == Feedback.SCOPE_OF_IMPROVEMENT })
        )
    }
    private fun mapToFeedbackDetailsRequestDto(items: List<FeedbackItem>): Map<String, List<String>> {
        val detailsMap = mutableMapOf<String, List<String>>()

        items.forEach { item ->
            if(item.selectedFeedback.name == Feedback.DID_WELL.name)
                detailsMap.merge(item.aspect, item.didWell.map { it.text }) { old, new -> old + new }
            else if(item.selectedFeedback.name == Feedback.SCOPE_OF_IMPROVEMENT.name)
                detailsMap.merge(item.aspect, item.scopeOfImprovement.map { it.text }) { old, new -> old + new }
        }
        return detailsMap
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
    private fun postFeedback() {
        postFeedbackDataUseCase(feedbackrequest.value).onEach {
            when (it) {
                is Resource.Loading -> {
                    _isLoading.send(true)
                }

                is Resource.Success -> {
                    _isLoading.send(false)
                    println("Success: ${it.data}")
                }

                is Resource.Error -> {
                    _isLoading.send(false)
                    _error.send(it.message!!)
                    println("Error: ${it.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setSubmitEnabled(enabled: Boolean) {
        submitEnabled = enabled
    }

}