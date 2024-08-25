package com.devrachit.insightloop.data.remote.mapper

import com.devrachit.insightloop.data.remote.dto.FeedbackCategoryDto
import com.devrachit.insightloop.domain.model.Option
import com.devrachit.insightloop.domain.model.getCategoryFromString


//fun FeedbackCategoryDto.toFeedbackCategory() =
//    CategoryData(
//        category = getCategoryFromString(categoryName ?: "").toString(),
//        feedbackItems = feedbackItemDtos?.map {
//            FeedbackItem(
//                aspect = it.aspect,
//                didWell = it.didWell?.map { Option(it) } ?: emptyList(),
//                scopeOfImprovement =it.scopeOfImprovement?.map { Option(it) } ?: emptyList()
//            )
//        }?: emptyList()
//    )
