package com.devrachit.insightloop.data.remote.dto

data class DidWellConfidence(
    var eyeContact: List<String>? = null,
    var overallBodyLanguage: List<String>? = null,
    var useOfFillers: List<String>? = null,
    var pausesWhileSpeaking: List<String>? = null,
)