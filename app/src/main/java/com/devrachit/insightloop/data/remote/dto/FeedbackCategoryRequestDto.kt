package com.devrachit.insightloop.data.remote.dto

data class FeedbackCategoryRequestDto(
    val didWell: Map<String, List<String>> = emptyMap(),
    val scopeOfImprovement:  Map<String, List<String>> = emptyMap()
)