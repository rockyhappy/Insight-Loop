package com.devrachit.insightloop.data.remote.dto

data class DidWellPronunciation(
    var modulation: List<String>?=null,
    var intonation: List<String>?=null,
    var pronunciationOfWordsWithSilentLetters: List<String>?=null,
    var speechClarity: List<String>?=null,
)
