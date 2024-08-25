package com.devrachit.insightloop.domain.repository

import com.devrachit.insightloop.data.remote.dto.FeedbackDetailsDto
import retrofit2.Response

interface ServicesRepository {
    suspend fun getFeedbackDetails(): Response<FeedbackDetailsDto>
}