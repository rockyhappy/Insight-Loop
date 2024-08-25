package com.devrachit.insightloop.domain.model

sealed class Category(val value:String){
object CONFIDENCE: Category("Confidence")
object GRAMMAR: Category("Grammar")
object FLUENCY_AND_VOCABULARY: Category("Fluency And Vocabulary")
object PRONUNCIATION: Category("Pronunciation")
object OTHER: Category("Other")
object UNSPECIFIED: Category("Unspecified")
}

fun getCategoryFromString(value: String): Category {
    return when (value.lowercase()) {
        Category.CONFIDENCE.value.lowercase() -> Category.CONFIDENCE
        Category.GRAMMAR.value.lowercase() -> Category.GRAMMAR
        Category.FLUENCY_AND_VOCABULARY.value.lowercase() -> Category.FLUENCY_AND_VOCABULARY
        Category.PRONUNCIATION.value.lowercase() -> Category.PRONUNCIATION
        Category.OTHER.value.lowercase() -> Category.OTHER
        else -> Category.UNSPECIFIED
    }
}