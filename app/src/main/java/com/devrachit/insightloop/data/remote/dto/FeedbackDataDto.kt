package com.devrachit.insightloop.data.remote.dto

data class FeedbackDataDto(
    val feedbackCategories: List<FeedbackCategoryDto>,
    val statusCode: Int
)
