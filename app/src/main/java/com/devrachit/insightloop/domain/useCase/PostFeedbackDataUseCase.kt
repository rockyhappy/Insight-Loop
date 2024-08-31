package com.devrachit.insightloop.domain.useCase

import com.devrachit.core.common.Resource
import com.devrachit.insightloop.data.remote.dto.Confidence
import com.devrachit.insightloop.data.remote.dto.DidWellGrammar
import com.devrachit.insightloop.data.remote.dto.DidWellConfidence
import com.devrachit.insightloop.data.remote.dto.DidWellFluencyAndVocabulary
import com.devrachit.insightloop.data.remote.dto.DidWellPronunciation
import com.devrachit.insightloop.data.remote.dto.FeedbackRequestDto
import com.devrachit.insightloop.data.remote.dto.FluencyAndVocabulary
import com.devrachit.insightloop.data.remote.dto.Grammar
import com.devrachit.insightloop.data.remote.dto.Pronunciation
import com.devrachit.insightloop.data.remote.dto.ScopeOfImprovementGrammar
import com.devrachit.insightloop.data.remote.dto.ScopeOfImprovementConfidence
import com.devrachit.insightloop.data.remote.dto.ScopeOfImprovementFluencyAndVocabulary
import com.devrachit.insightloop.data.remote.dto.ScopeOfImprovementPronunciation
import com.devrachit.insightloop.data.remote.dto.request
import com.devrachit.insightloop.domain.repository.ServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PostFeedbackDataUseCase @Inject constructor(private val repository: ServicesRepository) {
    operator fun invoke(feedbackRequestDto: FeedbackRequestDto): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            var request = request()
            print(feedbackRequestDto)
            feedbackRequestDto.confidence.let {
                println(it)
                request.confidence = Confidence(
                    DidWellConfidence(
                        eyeContact = it?.didWell?.get("Eye Contact") ?: emptyList(),
                        overallBodyLanguage = it?.didWell?.get("Overall Body Language")
                            ?: emptyList(),
                        useOfFillers = it?.didWell?.get("Use Of Fillers") ?: emptyList(),
                        pausesWhileSpeaking = it?.didWell?.get("Pauses While Speaking")
                            ?: emptyList(),
                    ),
                    ScopeOfImprovementConfidence(
                        eyeContact = it?.scopeOfImprovement?.get("Eye Contact") ?: emptyList(),
                        overallBodyLanguage = it?.scopeOfImprovement?.get("Overall Body Language")
                            ?: emptyList(),
                        useOfFillers = it?.scopeOfImprovement?.get("Use Of Fillers") ?: emptyList(),
                        pausesWhileSpeaking = it?.scopeOfImprovement?.get("Pauses While Speaking")
                            ?: emptyList(),
                    )
                )
            }
            feedbackRequestDto.grammar.let {
                request.grammar = Grammar(
                    DidWellGrammar(
                        subjectVerbAgreement = it?.didWell?.get("Subject Verb Agreement")
                            ?: emptyList(),
                        applicationOfSingularVsPlural = it?.didWell?.get("Application Of Singular Vs Plural")
                            ?: emptyList(),
                        usageOfModalAuxiliaries = it?.didWell?.get("Usage Of Modal Auxiliaries")
                            ?: emptyList(),
                        useOfArticles = it?.didWell?.get("Use Of Articles") ?: emptyList(),
                        useOfTenses = it?.didWell?.get("Use Of Tenses") ?: emptyList(),
                        useOfParticiples = it?.didWell?.get("Use Of Participles") ?: emptyList(),
                        useOfPrepositions = it?.didWell?.get("Use Of Prepositions") ?: emptyList(),
                        usageOfPronouns = it?.didWell?.get("Usage Of Pronouns") ?: emptyList(),
                    ),
                    ScopeOfImprovementGrammar(
                        subjectVerbAgreement = it?.scopeOfImprovement?.get("Subject Verb Agreement")
                            ?: emptyList(),
                        applicationOfSingularVsPlural = it?.scopeOfImprovement?.get("Application Of Singular Vs Plural")
                            ?: emptyList(),
                        usageOfModalAuxiliaries = it?.scopeOfImprovement?.get("Usage Of Modal Auxiliaries")
                            ?: emptyList(),
                        useOfArticles = it?.scopeOfImprovement?.get("Use Of Articles") ?: emptyList(),
                        useOfTenses = it?.scopeOfImprovement?.get("Use Of Tenses") ?: emptyList(),
                        useOfParticiples = it?.scopeOfImprovement?.get("Use Of Participles") ?: emptyList(),
                        useOfPrepositions = it?.scopeOfImprovement?.get("Use Of Prepositions") ?: emptyList(),
                        usageOfPronouns = it?.scopeOfImprovement?.get("Usage Of Pronouns") ?: emptyList(),
                    )
                )
            }

            feedbackRequestDto.fluencyAndVocabulary.let {
                request.fluencyAndVocabulary = FluencyAndVocabulary(
                    DidWellFluencyAndVocabulary(
                        continuity = it?.didWell?.get("Continuity") ?: emptyList(),
                        sentenceFormation = it?.didWell?.get("Sentence Formation") ?: emptyList(),
                        speed = it?.didWell?.get("Speed") ?: emptyList(),
                        sentenceCompletion = it?.didWell?.get("Sentence Completion") ?: emptyList(),
                    ),
                    ScopeOfImprovementFluencyAndVocabulary(
                        continuity = it?.scopeOfImprovement?.get("Continuity") ?: emptyList(),
                        sentenceFormation = it?.scopeOfImprovement?.get("Sentence Formation") ?: emptyList(),
                        speed = it?.scopeOfImprovement?.get("Speed") ?: emptyList(),
                        sentenceCompletion = it?.scopeOfImprovement?.get("Sentence Completion") ?: emptyList(),
                    )
                )
            }
            feedbackRequestDto.pronunciation.let {
                request.pronunciation = Pronunciation(
                    DidWellPronunciation(
                        modulation = it?.didWell?.get("Modulation") ?: emptyList(),
                        intonation = it?.didWell?.get("Intonation") ?: emptyList(),
                        pronunciationOfWordsWithSilentLetters = it?.didWell?.get("Pronunciation Of Words With Silent Letters")
                            ?: emptyList(),
                        speechClarity = it?.didWell?.get("Speech Clarity") ?: emptyList(),
                    ),
                    ScopeOfImprovementPronunciation(
                        modulation = it?.scopeOfImprovement?.get("Modulation") ?: emptyList(),
                        intonation = it?.scopeOfImprovement?.get("Intonation") ?: emptyList(),
                        pronunciationOfWordsWithSilentLetters = it?.scopeOfImprovement?.get("Pronunciation Of Words With Silent Letters")
                            ?: emptyList(),
                        speechClarity = it?.scopeOfImprovement?.get("Speech Clarity") ?: emptyList(),
                    )
                )
            }

            val data = repository.postFeedback(request)
            emit(Resource.Success(data.toString()))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred\nResponse code: ${e.code()}"
                )
            )
            e.printStackTrace()
        } catch (e: Exception) {
            emit(Resource.Error("Couldn't reach the server\nCheck your internet connection"))
            e.printStackTrace()
        }
    }
}