package com.devrachit.insightloop.domain.model


sealed class Feedback {
    object DidWell : Feedback()
    object ScopeOfImprovement : Feedback()
    object None : Feedback()
}
