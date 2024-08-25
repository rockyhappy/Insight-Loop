package com.devrachit.insightloop.data.remote.services


import com.devrachit.insightloop.data.remote.dto.FeedbackCategoryDto
import com.devrachit.insightloop.data.remote.dto.FeedbackDataDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @GET("api/rating/getFeedbackData")
    suspend fun getData() : FeedbackDataDto

    @POST("api/rating/submitFeedback")
    suspend fun postFeedback(@Body feedbackSubmitDto: Any) : Response<Unit>
}