package com.devrachit.insightloop.data.remote.dto

data class FeedbackCategoryRequestDto (
    val didWell: FeedbackDetailsRequestDto?=null,
    val scopeOfImprovement: FeedbackDetailsRequestDto?=null
)