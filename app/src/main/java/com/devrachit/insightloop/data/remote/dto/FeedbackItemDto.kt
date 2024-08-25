package com.devrachit.insightloop.data.remote.dto

import com.devrachit.insightloop.domain.model.FeedbackItem
import com.devrachit.insightloop.domain.model.Option


data class FeedbackItemDto(
    val aspect: String,
    val didWell: List<String>,
    val scopeOfImprovement: List<String>
)

fun FeedbackItemDto.toFeedbackItem() = FeedbackItem(
    aspect = aspect,
    didWell = didWell.map {
        it.toOption()
    },
    scopeOfImprovement = scopeOfImprovement.map {
        it.toOption()
    }
)

fun String.toOption(): Option = Option(this)