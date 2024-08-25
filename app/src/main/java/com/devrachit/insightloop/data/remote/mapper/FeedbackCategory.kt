package com.devrachit.insightloop.data.remote.mapper

import com.devrachit.insightloop.data.remote.dto.FeedbackCategoryDto
import com.devrachit.insightloop.data.remote.dto.FeedbackCategoryRequestDto
import com.devrachit.insightloop.data.remote.dto.FeedbackDetailsRequestDto
import com.devrachit.insightloop.data.remote.dto.FeedbackRequestDto
import com.devrachit.insightloop.domain.model.FeedbackCategory
import com.devrachit.insightloop.domain.model.Option
import com.devrachit.insightloop.domain.model.getCategoryFromString


//ToDo : Add Mapper for FeedbackCategoryDto
fun FeedbackCategory.toFeedbackRequestDto() =
    FeedbackRequestDto(
        confidence = FeedbackCategoryRequestDto(
            didWell = FeedbackDetailsRequestDto(

            ),
            scopeOfImprovement = FeedbackDetailsRequestDto(

            )
        ),
        grammar = FeedbackCategoryRequestDto(
            didWell = FeedbackDetailsRequestDto(

            ),
            scopeOfImprovement = FeedbackDetailsRequestDto(

            )
        ),
        fluencyAndVocabulary =  FeedbackCategoryRequestDto(
            didWell = FeedbackDetailsRequestDto(

            ),
            scopeOfImprovement = FeedbackDetailsRequestDto(

            )
        ),
        pronunciation =  FeedbackCategoryRequestDto(
            didWell = FeedbackDetailsRequestDto(

            ),
            scopeOfImprovement = FeedbackDetailsRequestDto(

            )
        ),
    )