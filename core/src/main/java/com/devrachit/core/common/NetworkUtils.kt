package com.devrachit.core.common

import com.devrachit.core.common.Constants.Companion.NO_INTERNET_CONNECTION
import com.devrachit.core.common.Constants.Companion.SOMETHING_WENT_WRONG
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


object NetworkUtils {
    fun <T : Any> handleResponse(response: Response<T>): Resource<T> {
        return try {
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                val errorMessage = when {
                    response.errorBody() != null -> parseErrorBody(response.errorBody())
                    else -> SOMETHING_WENT_WRONG
                }
                Resource.Error(errorMessage, null)
            }
        } catch (e: IOException) {
            Resource.Error(e.message ?: NO_INTERNET_CONNECTION, null)
        } catch (e: HttpException) {
            Resource.Error(e.message ?: SOMETHING_WENT_WRONG, null)
        } catch (e: Exception) {
            Resource.Error(e.message ?: SOMETHING_WENT_WRONG, null)
        }
    }

    private fun parseErrorBody(errorBody: ResponseBody?): String {
        return errorBody?.let { body ->
            val contentType = body.contentType()
            val errorBodyString = body.string()
            if (contentType?.toString()?.contains("json", ignoreCase = true) == true) {
                parseJsonError(errorBodyString)
            } else {
                SOMETHING_WENT_WRONG
            }
        } ?: SOMETHING_WENT_WRONG
    }

    private fun parseJsonError(errorBodyString: String): String {
        return try {
            val gson = Gson()
            val errorJson = gson.fromJson(errorBodyString, Map::class.java)
            errorJson["error"]?.let {
                (it as Map<String, String>)["message"]
            } ?: "Something went wrong"
        } catch (e: Exception) {
            e.message.toString()
        }
    }
}