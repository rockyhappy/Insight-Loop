package com.devrachit.insightloop.data.remote.dto

data class FeedbackDetailsDto(
    val feedbackCategories: List<FeedbackCategoryDto>,
    val statusCode: Int
)