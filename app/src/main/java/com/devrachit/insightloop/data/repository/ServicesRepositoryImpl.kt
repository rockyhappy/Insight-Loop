package com.devrachit.insightloop.data.repository

import com.devrachit.insightloop.data.remote.dto.FeedbackDataDto
import com.devrachit.insightloop.data.remote.dto.FeedbackRequestDto
import com.devrachit.insightloop.data.remote.services.ApiServices
import com.devrachit.insightloop.domain.repository.ServicesRepository
import retrofit2.Response
import javax.inject.Inject

class ServicesRepositoryImpl @Inject constructor(private val apiServices: ApiServices) : ServicesRepository {
    override suspend fun getFeedbackDetails(): FeedbackDataDto {
        return apiServices.getData()
    }

    override suspend fun postFeedback(feedbackRequestDto: Any): Any {
        return apiServices.postFeedback(feedbackSubmitDto = feedbackRequestDto)
    }
}