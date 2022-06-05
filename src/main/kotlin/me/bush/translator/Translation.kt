package me.bush.translator

import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray

/**
 * @author bush
 * @since 5/27/2022
 */
class Translation internal constructor(
    val target: Language,
    val rawData: String,
    val url: Url
) {
    val jsonData = Json.parseToJsonElement(rawData).jsonArray
    val translatedText = getTranslatedText(jsonData)
    val sourceText = getSourceText(jsonData)
    val pronunciation = getPronunciation(jsonData)
    val source = languageOf(jsonData[2].string!!)
}

private fun getTranslatedText(data: JsonArray) = StringBuffer().apply {
    data[0].jsonArray.forEach {
        it.jsonArray[0].string?.let(::append)
    }
}.toString()

private fun getSourceText(data: JsonArray) = StringBuffer().apply {
    var first = true
    data[0].jsonArray.forEach {
        it.jsonArray[1].string?.let { string ->
            // For some reason only the source text
            // strings have trailing spaces removed
            if (!first) append(" ")
            append(string)
            first = false
        }
    }
}.toString()

private fun getPronunciation(data: JsonArray) = data[0].jsonArray.last().jsonArray[2].string

private val JsonElement.string
    get() = toString().removeSurrounding("\"").let { if (it == "null") null else it }
