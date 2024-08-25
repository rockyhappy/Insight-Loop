package com.devrachit.insightloop.domain.useCase

import com.devrachit.core.common.Resource
import com.devrachit.insightloop.data.remote.dto.toFeedbackCategory
import com.devrachit.insightloop.domain.model.Category
import com.devrachit.insightloop.domain.model.FeedbackCategory
import com.devrachit.insightloop.domain.repository.ServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetFeedbackDataUseCase @Inject constructor(private val repository: ServicesRepository) {

    operator fun invoke(): Flow<Resource<List<FeedbackCategory>>> = flow {
        emit(Resource.Loading())
        try {
            val data = repository.getFeedbackDetails().feedbackCategories.map { it.toFeedbackCategory() }.toMutableList()
            data.add(FeedbackCategory(Category.OTHER, emptyList()))
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred\nResponse code: ${e.code()}"
                )
            )
            e.printStackTrace()
        } catch (e: Exception) {
            emit(Resource.Error("Couldn't reach the server\nCheck your internet connection"))
            e.printStackTrace()
        }
    }

}