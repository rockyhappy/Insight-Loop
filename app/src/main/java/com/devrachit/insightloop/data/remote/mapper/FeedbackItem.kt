package com.devrachit.insightloop.data.remote.mapper

import com.devrachit.insightloop.data.remote.dto.FeedbackItemDto
import com.devrachit.insightloop.domain.model.FeedbackItem
import com.devrachit.insightloop.domain.model.Option

fun FeedbackItemDto.toFeedbackItem() = FeedbackItem(
    aspect = aspect,
    didWell = didWell.map {Option(it)},
    scopeOfImprovement = scopeOfImprovement.map {Option(it) }
)

