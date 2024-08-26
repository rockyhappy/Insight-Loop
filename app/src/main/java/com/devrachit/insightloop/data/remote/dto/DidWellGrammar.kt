package com.devrachit.insightloop.data.remote.dto

data class DidWellGrammar(
    var usageOfModalAuxiliaries: List<String>?=null,
    var useOfArticles: List<String>?=null,
    var useOfPrepositions: List<String>? = null,
    var usageOfPronouns: List<String>? = null,
    var subjectVerbAgreement: List<String>? = null,
    var applicationOfSingularVsPlural: List<String>? = null,
    var useOfParticiples: List<String>? = null,
    var useOfTenses: List<String>? = null,
)