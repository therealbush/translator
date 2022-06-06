package me.bush.translator

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

/**
 * [A translator that uses Google's translate API.](https://github.com/therealbush/translator)
 *
 * @author bush, py-googletrans + contributors
 * @since 1.0.0
 *
 * @property client The HTTP client to use for requests to Google's API.
 *                  This should be kept as default unless you know what you are doing.
 */
class Translator(
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
) {

    /**
     * Translates the given string to the desired language.
     * ```
     * translate("Text to be translated", Language.RUSSIAN)
     * translate("Text to be translated", ALBANIAN, ENGLISH)
     * ```
     * @param text   The text to be translated.
     * @param target The language to translate [text] to.
     * @param source The language of [text]. By default, this is [Language.AUTO].
     *
     * @return A [Translation] containing the translated text and other related data.
     * @throws TranslationException If the HTTP request could not be completed.
     *
     * @see translateCatching
     * @see translateBlocking
     * @see translateBlockingCatching
     *
     * @see Translation
     */
    suspend fun translate(
        text: String,
        target: Language,
        source: Language = Language.AUTO
    ): Translation {
        require(target != Language.AUTO) {
            "The target language cannot be Language.AUTO!"
        }
        val response = client.get("https://translate.googleapis.com/translate_a/single") {
            constantParameters()
            parameter("sl", source.code)
            parameter("tl", target.code)
            parameter("hl", target.code)
            parameter("q", text)
        }
        return Translation(target, text, response.bodyAsText(), response.request.url)
    }

    /**
     * Translates the given string to the desired language,
     * and returns a [Result] containing the [Translation].
     * ```
     * translateCatching("Text to be translated", Language.RUSSIAN)
     * translateCatching("Text to be translated", ALBANIAN, ENGLISH)
     * ```
     * @param text   The text to be translated.
     * @param target The language to translate [text] to.
     * @param source The language of [text]. By default, this is [Language.AUTO].
     *
     * @return A [Result] containing the [Translation], or a [TranslationException]
     *         If the HTTP request could not be completed.
     *
     * @see translate
     * @see translateBlocking
     * @see translateBlockingCatching
     *
     * @see Translation
     */
    suspend fun translateCatching(
        text: String,
        target: Language,
        source: Language = Language.AUTO
    ): Result<Translation> = runCatching { translate(text, target, source) }

    /**
     * Translates the given string to the desired language,
     * blocking the current thread until completion.
     * ```
     * translateBlocking("Text to be translated", Language.RUSSIAN)
     * translateBlocking("Text to be translated", ALBANIAN, ENGLISH)
     * ```
     * @param text   The text to be translated.
     * @param target The language to translate [text] to.
     * @param source The language of [text]. By default, this is [Language.AUTO].
     *
     * @return A [Translation] containing the translated text and other related data.
     * @throws TranslationException If the HTTP request could not be completed.
     *
     * @see translate
     * @see translateCatching
     * @see translateBlockingCatching
     */
    @JvmOverloads
    fun translateBlocking(
        text: String,
        target: Language,
        source: Language = Language.AUTO
    ): Translation = runBlocking { translate(text, target, source) }

    /**
     * Translates the given string to the desired language,
     * blocking the current thread until completion and
     * returning a [Result] containing the [Translation].
     * ```
     * translateBlockingCatching("Text to be translated", Language.RUSSIAN)
     * translateBlockingCatching("Text to be translated", ALBANIAN, ENGLISH)
     * ```
     * @param text   The text to be translated.
     * @param target The language to translate [text] to.
     * @param source The language of [text]. By default, this is [Language.AUTO].
     *
     * @return A [Result] containing the [Translation], or a [TranslationException]
     *         If the HTTP request could not be completed.
     *
     * @see translate
     * @see translateCatching
     * @see translateBlocking
     *
     * @see Translation
     */
    fun translateBlockingCatching(
        text: String,
        target: Language,
        source: Language = Language.AUTO
    ): Result<Translation> = runBlocking { translateCatching(text, target, source) }
}

// I didn't find these myself, check out https://github.com/ssut/py-googletrans
private val dtParams = arrayOf("at", "bd", "ex", "ld", "md", "qca", "rw", "rm", "ss", "t")

// ^^^
private fun HttpRequestBuilder.constantParameters() {
    parameter("client", "gtx")
    dtParams.forEach { parameter("dt", it) }
    parameter("ie", "UTF-8")
    parameter("oe", "UTF-8")
    parameter("otf", 1)
    parameter("ssel", 0)
    parameter("tsel", 0)
    parameter("tk", "bushissocool")
}

/**
 * Indicates an exception/error relating to the translation's HTTP request.
 */
private class TranslationException(message: String, cause: Throwable? = null) : Exception(message, cause)
