package com.devrachit.insightloop.data.remote.mapper

import com.devrachit.insightloop.domain.model.Category

fun String.toCategory(): Category = when (this) {
    "Confidence" -> Category.CONFIDENCE
    "Grammar" -> Category.GRAMMAR
    "Fluency And Vocabulary" -> Category.FLUENCY_AND_VOCABULARY
    "Pronunciation" -> Category.PRONUNCIATION
    "Other" -> Category.OTHER
    "Unspecified" -> Category.UNSPECIFIED
    else -> Category.UNSPECIFIED // default or error case
}