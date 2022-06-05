package me.bush.translator

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

/**
 * @author bush, googletrans-py + contributors
 * @since 5/26/2022
 */
class Translator {
    private val client: HttpClient = HttpClient(CIO) {
        install(UserAgent) {
            agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
        }
        HttpResponseValidator {
            // This is run for all responses
            validateResponse { response ->
                if (!response.status.isSuccess()) {
                    throw TranslationException("Error caught from HTTP request: ${response.status}")
                }
            }
            // This is run only when an exception is thrown, including our custom ones
            handleResponseExceptionWithRequest { cause, _ ->
                if (cause !is TranslationException) {
                    throw TranslationException("Exception caught from HTTP request", cause)
                }
            }
        }
    }

    suspend fun translate(
        text: String,
        target: Language = Language.ENGLISH,
        source: Language = Language.AUTO
    ): Translation {
        val response = client.get("https://translate.googleapis.com/translate_a/single") {
            constantParameters()
            parameter("sl", source.code)
            parameter("tl", target.code)
            parameter("hl", target.code)
            parameter("q", text)
        }
        return Translation(target, response.bodyAsText(), response.request.url)
    }

    suspend fun translateCatching(
        text: String,
        target: Language = Language.ENGLISH,
        source: Language = Language.AUTO
    ): Result<Translation> = runCatching { translate(text, target, source) }

    @JvmOverloads
    fun translateBlocking(
        text: String,
        target: Language = Language.ENGLISH,
        source: Language = Language.AUTO
    ): Translation = runBlocking { translate(text, target, source) }

    fun translateBlockingCatching(
        text: String,
        target: Language = Language.ENGLISH,
        source: Language = Language.AUTO
    ): Result<Translation> = runBlocking { translateCatching(text, target, source) }
}

private val DT_PARAMS = arrayOf("at", "bd", "ex", "ld", "md", "qca", "rw", "rm", "ss", "t")

private fun HttpRequestBuilder.constantParameters() {
    parameter("client", "gtx")
    DT_PARAMS.forEach { parameter("dt", it) }
    parameter("ie", "UTF-8")
    parameter("oe", "UTF-8")
    parameter("otf", 1)
    parameter("ssel", 0)
    parameter("tsel", 0)
    parameter("tk", "bushissocool")
}

class TranslationException(message: String, cause: Throwable? = null) : Exception(message, cause)
