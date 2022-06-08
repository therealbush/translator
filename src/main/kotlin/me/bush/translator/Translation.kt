package me.bush.translator

import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import java.util.Objects.hash

/**
 * A class containing the results of a translation request.
 *
 * @author bush
 * @since 1.0.0
 */
class Translation internal constructor(

    /**
     * The language translated to.
     */
    val targetLanguage: Language,

    /**
     * The original, untranslated text.
     */
    val sourceText: String,

    /**
     * The raw data received from Google's API.
     */
    val rawData: String,

    /**
     * The url that the translation request was made to.
     */
    val url: Url
) {
    /**
     * The data received from Google's API, as a [JsonArray].
     */
    val jsonData = Json.parseToJsonElement(rawData).jsonArray

    /**
     * The pronunciation of the translated text. This is generally
     * null when the target language uses the Roman/Latin Alphabet.
     */
    val pronunciation = jsonData[0].jsonArray.last().jsonArray[2].string

    /**
     * The language of the translated text. This is useful
     * if the source language was set to [Language.AUTO].
     */
    val sourceLanguage = languageOf(jsonData[2].string!!)!!

    /**
     * The result of the translation.
     */
    val translatedText = buildString {
        // For some reason every sentence/line is separated, so we need to join them back.
        jsonData[0].jsonArray.mapNotNull { it.jsonArray[0].string }.forEach(::append)
    }

    override fun equals(other: Any?) = when {
        other === this -> true
        other !is Translation -> false
        other.hashCode() == hashCode() -> true
        else -> false
    }

    override fun hashCode() = hash(
        targetLanguage,
        sourceLanguage,
        translatedText,
        pronunciation,
        sourceText,
        jsonData,
        rawData,
        url
    )

    override fun toString() =
        "Translation($sourceLanguage -> $targetLanguage, $sourceText -> $translatedText)"
}

// Un-json our text
private val JsonElement.string
    get() = toString().removeSurrounding("\"").replace("\\n", "\n").takeIf { it != "null" }
