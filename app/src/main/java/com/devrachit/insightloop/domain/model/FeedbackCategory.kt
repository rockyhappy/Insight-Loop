package com.devrachit.insightloop.domain.model

data class FeedbackCategory(
    val category: Category,
    val feedbackItems: List<FeedbackItem>,
    var isOpen: Boolean = false
)