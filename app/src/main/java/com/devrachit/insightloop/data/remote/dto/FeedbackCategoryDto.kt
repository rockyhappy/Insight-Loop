package com.devrachit.insightloop.data.remote.dto

data class FeedbackCategoryDto(
    val categoryName: String,
    val feedbackItemDtos: List<FeedbackItemDto>
)