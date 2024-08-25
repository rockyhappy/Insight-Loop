package com.devrachit.insightloop.data.remote.dto

data class FeedbackRequestDto (
    val confidence: FeedbackCategoryRequestDto?=null,
    val grammar: FeedbackCategoryRequestDto?=null,
    val fluencyAndVocabulary: FeedbackCategoryRequestDto?=null,
    val pronunciation: FeedbackCategoryRequestDto?=null
)