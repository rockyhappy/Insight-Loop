package com.devrachit.insightloop.data.remote.dto

data class ScopeOfImprovementPronunciation(
    var modulation: List<String>?=null,
    var intonation: List<String>?=null,
    var pronunciationOfWordsWithSilentLetters: List<String>?=null,
    var speechClarity: List<String>?=null,
)
