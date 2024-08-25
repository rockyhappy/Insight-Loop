package com.devrachit.insightloop.data.remote.dto

data class FeedbackItemDto(
    val aspect: String,
    val didWell: List<String>,
    val scopeOfImprovement: List<String>
)