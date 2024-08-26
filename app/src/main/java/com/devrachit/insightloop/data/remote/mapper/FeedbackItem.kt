package com.devrachit.insightloop.data.remote.mapper

//import com.devrachit.insightloop.data.remote.dto.FeedbackDetailsRequestDto
//import com.devrachit.insightloop.domain.model.Feedback
//import com.devrachit.insightloop.domain.model.FeedbackItem
//import com.devrachit.insightloop.domain.model.Option
//
//fun mapToFeedbackDetailsRequestDto(items: List<FeedbackItem>): FeedbackDetailsRequestDto {
//    val detailsMap = mutableMapOf<String, List<String>>()
//
//    items.forEach { item ->
//        if(item.selectedFeedback.name == Feedback.DID_WELL.name)
//            detailsMap.merge(item.aspect, item.didWell.map { it.text }) { old, new -> old + new }
//        else if(item.selectedFeedback.name == Feedback.SCOPE_OF_IMPROVEMENT.name)
//            detailsMap.merge(item.aspect, item.scopeOfImprovement.map { it.text }) { old, new -> old + new }
//    }
//    return FeedbackDetailsRequestDto(details = detailsMap)
//}
//
