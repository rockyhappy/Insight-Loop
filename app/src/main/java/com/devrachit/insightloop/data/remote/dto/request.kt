package com.devrachit.insightloop.data.remote.dto

data class request(
    var confidence: Confidence? = null,
    var grammar: Grammar?= null,
    var fluencyAndVocabulary: FluencyAndVocabulary?= null,
    var pronunciation: Pronunciation?= null,
)