package com.devrachit.insightloop.domain.model

data class CategoryData (
    val category: String,
    val feedbackItems: List<FeedbackItem>,
    var isOpen: Boolean = false
)