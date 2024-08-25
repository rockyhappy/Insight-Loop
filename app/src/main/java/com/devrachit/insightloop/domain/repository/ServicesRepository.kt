package com.devrachit.insightloop.domain.repository


import com.devrachit.insightloop.data.remote.dto.FeedbackDataDto
import com.devrachit.insightloop.data.remote.dto.FeedbackRequestDto
import retrofit2.Response

interface ServicesRepository {
    suspend fun getFeedbackDetails(): FeedbackDataDto

    suspend fun postFeedback(feedbackRequestDto: FeedbackRequestDto): Any
}