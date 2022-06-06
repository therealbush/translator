package me.bush.translator

/**
 * All languages recognized by Google Translate.
 *
 * @author bush
 * @since 1.0.0
 */
enum class Language(val code: String) {
    AUTO("auto"),

    AFRIKAANS("af"),
    ALBANIAN("sq"),
    AMHARIC("am"),
    ARABIC("ar"),
    ARMENIAN("hy"),
    AZERBAIJANI("az"),
    BASQUE("eu"),
    BELARUSIAN("be"),
    BENGALI("bn"),
    BOSNIAN("bs"),
    BULGARIAN("bg"),
    CATALAN("ca"),
    CEBUANO("ceb"),
    CHICHEWA("ny"),
    CHINESE_SIMPLIFIED("zh-cn"),
    CHINESE_TRADITIONAL("zh-tw"),
    CORSICAN("co"),
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESPERANTO("eo"),
    ESTONIAN("et"),
    FILIPINO("tl"),
    FINNISH("fi"),
    FRENCH("fr"),
    FRISIAN("fy"),
    GALICIAN("gl"),
    GEORGIAN("ka"),
    GERMAN("de"),
    GREEK("el"),
    GUJARATI("gu"),
    HATIAN_CREOLE("ht"),
    HAUSA("ha"),
    HAWAIIAN("haw"),
    HEBREW_IW("iw"),
    HEBREW_HE("he"),
    HINDI("hi"),
    HMONG("hm"),
    HUNGARIAN("hu"),
    ICELANDIC("is"),
    IGBO("ig"),
    INDONESIAN("id"),
    IRISH("ga"),
    ITALIAN("it"),
    JAPANESE("ja"),
    JAVANESE("jw"),
    KANNADA("kn"),
    KAZAKH("kk"),
    KHMER("km"),
    KOREAN("ko"),
    KURDISH_KURMANJI("ku"),
    KYRGYZ("ky"),
    LAO("lo"),
    LATIN("la"),
    LATVIAN("lv"),
    LITHUANIAN("lt"),
    LUXEMBOURGISH("lb"),
    MACEDONIAN("mk"),
    MALAGASY("mg"),
    MALAY("ms"),
    MALAYALAM("ml"),
    MALTESE("mt"),
    MAORI("mi"),
    MARATHI("mr"),
    MONGOLIAN("mn"),
    MYANMAR_BURMESE("my"),
    NEPALI("ne"),
    NORWEGIAN("no"),
    ODIA("or"),
    PASHTO("ps"),
    PERSIAN("fa"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    PUNJABI("pa"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SAMOAN("sm"),
    SCOTS_GAELIC("gd"),
    SERBIAN("sr"),
    SESOTHO("st"),
    SHONA("sn"),
    SINDHI("sd"),
    SINHALA("si"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SOMALI("so"),
    SPANISH("es"),
    SUDANESE("su"),
    SWAHILI("sw"),
    SWEDISH("sv"),
    TAJIK("tg"),
    TAMIL("ta"),
    TELUGU("te"),
    THAI("th"),
    TURKISH("tr"),
    UKRAINIAN("uk"),
    URDU("ur"),
    UYGHUR("ug"),
    UZBEK("uz"),
    VIETNAMESE("vi"),
    WELSH("cy"),
    XHOSA("xh"),
    YIDDISH("yi"),
    YORUBA("yo"),
    ZULU("zu");

    init {
        languageToEnum[this.name.lowercase()] = this
        codeToEnum[code] = this
    }

    override fun toString() = name.lowercase().replaceFirstChar { it.uppercase() }
}

private val languageToEnum = mutableMapOf<String, Language>()
private val codeToEnum = mutableMapOf<String, Language>()

/**
 * Attempts to resolve a [Language] from the input string.
 *
 * Valid inputs include "en", "haw", "spanish", "CHINESE_TRA"
 *
 * @param language A language name, code, or part of a
 *                 language name. Case-insensitive.
 *
 * @throws NoSuchElementException If [language] is not a valid language
 *                                name, code, or part of a language name.
 */
fun languageOf(language: String) =
    codeToEnum[language] ?: // If language is a code
    languageToEnum[language.lowercase()] ?: // If language is a string
    languageToEnum[languageToEnum.keys.first { language in it }]!! // Check for contains or throw
