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
            feedbackRequestDto.confidence.let {
                request.confidence = Confidence(
                    DidWellConfidence(
                        eyeContact = it?.didWell?.get("eyeContact") ?: emptyList(),
                        overallBodyLanguage = it?.didWell?.get("overallBodyLanguage")
                            ?: emptyList(),
                        useOfFillers = it?.didWell?.get("useOfFillers") ?: emptyList(),
                        pausesWhileSpeaking = it?.didWell?.get("pausesWhileSpeaking")
                            ?: emptyList(),
                    ),
                    ScopeOfImprovementConfidence(
                        eyeContact = it?.didWell?.get("eyeContact") ?: emptyList(),
                        overallBodyLanguage = it?.didWell?.get("overallBodyLanguage")
                            ?: emptyList(),
                        useOfFillers = it?.didWell?.get("useOfFillers") ?: emptyList(),
                        pausesWhileSpeaking = it?.didWell?.get("pausesWhileSpeaking")
                            ?: emptyList(),
                    )
                )
            }
            feedbackRequestDto.grammar.let {
                request.grammar = Grammar(
                    DidWellGrammar(
                        subjectVerbAgreement = it?.didWell?.get("subjectVerbAgreement")
                            ?: emptyList(),
                        applicationOfSingularVsPlural = it?.didWell?.get("applicationOfSingularVsPlural")
                            ?: emptyList(),
                        usageOfModalAuxiliaries = it?.didWell?.get("usageOfModalAuxiliaries")
                            ?: emptyList(),
                        useOfArticles = it?.didWell?.get("useOfArticles") ?: emptyList(),
                        useOfTenses = it?.didWell?.get("useOfTenses") ?: emptyList(),
                        useOfParticiples = it?.didWell?.get("useOfParticiples") ?: emptyList(),
                        useOfPrepositions = it?.didWell?.get("useOfPrepositions") ?: emptyList(),
                        usageOfPronouns = it?.didWell?.get("usageOfPronouns") ?: emptyList(),
                    ),
                    ScopeOfImprovementGrammar(
                        subjectVerbAgreement = it?.didWell?.get("subjectVerbAgreement")
                            ?: emptyList(),
                        applicationOfSingularVsPlural = it?.didWell?.get("applicationOfSingularVsPlural")
                            ?: emptyList(),
                        usageOfModalAuxiliaries = it?.didWell?.get("usageOfModalAuxiliaries")
                            ?: emptyList(),
                        useOfArticles = it?.didWell?.get("useOfArticles") ?: emptyList(),
                        useOfTenses = it?.didWell?.get("useOfTenses") ?: emptyList(),
                        useOfParticiples = it?.didWell?.get("useOfParticiples") ?: emptyList(),
                        useOfPrepositions = it?.didWell?.get("useOfPrepositions") ?: emptyList(),
                        usageOfPronouns = it?.didWell?.get("usageOfPronouns") ?: emptyList(),
                    )
                )
            }

            feedbackRequestDto.fluencyAndVocabulary.let {
                request.fluencyAndVocabulary = FluencyAndVocabulary(
                    DidWellFluencyAndVocabulary(
                        continuity = it?.didWell?.get("continuity") ?: emptyList(),
                        sentenceFormation = it?.didWell?.get("sentenceFormation") ?: emptyList(),
                        speed = it?.didWell?.get("speed") ?: emptyList(),
                        sentenceCompletion = it?.didWell?.get("sentenceCompletion") ?: emptyList(),
                    ),
                    ScopeOfImprovementFluencyAndVocabulary(
                        continuity = it?.didWell?.get("continuity") ?: emptyList(),
                        sentenceFormation = it?.didWell?.get("sentenceFormation") ?: emptyList(),
                        speed = it?.didWell?.get("speed") ?: emptyList(),
                        sentenceCompletion = it?.didWell?.get("sentenceCompletion") ?: emptyList(),
                    )
                )
            }
            feedbackRequestDto.pronunciation.let {
                request.pronunciation = Pronunciation(
                    DidWellPronunciation(
                        modulation = it?.didWell?.get("modulation") ?: emptyList(),
                        intonation = it?.didWell?.get("intonation") ?: emptyList(),
                        pronunciationOfWordsWithSilentLetters = it?.didWell?.get("pronunciationOfWordsWithSilentLetters")
                            ?: emptyList(),
                        speechClarity = it?.didWell?.get("speechClarity") ?: emptyList(),
                    ),
                    ScopeOfImprovementPronunciation(
                        modulation = it?.didWell?.get("modulation") ?: emptyList(),
                        intonation = it?.didWell?.get("intonation") ?: emptyList(),
                        pronunciationOfWordsWithSilentLetters = it?.didWell?.get("pronunciationOfWordsWithSilentLetters")
                            ?: emptyList(),
                        speechClarity = it?.didWell?.get("speechClarity") ?: emptyList(),
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