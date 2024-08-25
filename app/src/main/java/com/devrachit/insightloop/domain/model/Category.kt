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
    for (category in Category::class.java.enumConstants as Array<Category>) {
        if (category.value.equals(value, ignoreCase = true)) {
            return category
        }
    }
    return Category.UNSPECIFIED
}