package com.devrachit.insightloop.data.remote.dto

import com.devrachit.insightloop.domain.model.FeedbackCategory
import com.devrachit.insightloop.domain.model.getCategoryFromString

data class FeedbackCategoryDto(
    val categoryName: String,
    val feedbackItems: List<FeedbackItemDto>
)

fun FeedbackCategoryDto.toFeedbackCategory() =
    FeedbackCategory(
        category = getCategoryFromString(categoryName),
        feedbackItems = feedbackItems.map {
            it.toFeedbackItem()
        }
    )
