package cn.yiiguxing.plugin.translate.trans.yandex

import cn.yiiguxing.plugin.translate.trans.BaseLanguageAdapter
import cn.yiiguxing.plugin.translate.trans.Lang
import cn.yiiguxing.plugin.translate.trans.SupportedLanguages

/**
 * Language adapter for Yandex Translator.
 */
object YandexLanguageAdapter : BaseLanguageAdapter(), SupportedLanguages {

    private val unsupportedLanguages: Set<Lang> = setOf(
        Lang.UNKNOWN,
        Lang.ASSAMESE,
        Lang.BASHKIR,
        Lang.CHINESE,
        Lang.CHINESE_CANTONESE,
        Lang.CHINESE_CLASSICAL,
        Lang.DARI,
        Lang.DIVEHI,
        Lang.ENGLISH_AMERICAN,
        Lang.ENGLISH_BRITISH,
        Lang.FAROESE,
        Lang.FIJIAN,
        Lang.FRENCH_CANADA,
        Lang.KURDISH,
        Lang.PORTUGUESE_BRAZILIAN,
        Lang.PORTUGUESE_PORTUGUESE,
        Lang.SERBIAN_CYRILLIC,
        Lang.SERBIAN_LATIN,
        Lang.TAHITIAN,
        Lang.TIBETAN,
        Lang.TIGRINYA,
        Lang.TONGAN,
        Lang.UPPER_SORBIAN,
        Lang.YUCATEC_MAYA,
    )

    override fun getAdaptedLanguages(): Map<String, Lang> = mapOf()

    override val sourceLanguages: List<Lang> by lazy {
        (Lang.sortedLanguages - unsupportedLanguages).toList()
    }

    override val targetLanguages: List<Lang> by lazy {
        (Lang.sortedLanguages - unsupportedLanguages.toMutableSet().apply { add(Lang.AUTO) }).toList()
    }
}


/**
 * Language code for yandex Translator.
 */
val Lang.googleLanguageCode: String
    get() = YandexLanguageAdapter.getLanguageCode(this)

/**
 * Returns the [language][Lang] for the specified yandex Translator language [code].
 */
fun Lang.Companion.fromGoogleLanguageCode(code: String): Lang {
    return YandexLanguageAdapter.getLanguage(code)
}