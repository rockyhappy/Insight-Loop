package com.devrachit.insightloop.data.remote.dto

data class Confidence(
    var didWell: DidWellConfidence?= null,
    var scopeOfImprovement: ScopeOfImprovementConfidence?= null
)