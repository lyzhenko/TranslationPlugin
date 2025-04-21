package cn.yiiguxing.plugin.translate.trans.yandex

import cn.yiiguxing.plugin.translate.trans.*
import cn.yiiguxing.plugin.translate.trans.google.GSentence
import com.intellij.openapi.diagnostic.Logger
import org.jsoup.nodes.Document
import javax.swing.Icon
import cn.yiiguxing.plugin.translate.ui.settings.TranslationEngine.YANDEX
import cn.yiiguxing.plugin.translate.util.i
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object YandexTranslator : AbstractTranslator(), DocumentationTranslator {

    private const val TRANSLATE_API_PATH = ""
    private const val DOCUMENTATION_TRANSLATION_API_PATH = ""
    private val logger: Logger = Logger.getInstance(YandexTranslator::class.java)


    override fun doTranslate(text: String, srcLang: Lang, targetLang: Lang): Translation {
        TODO("Not yet implemented")
    }

    override val id: String = YANDEX.id
    override val name: String = YANDEX.translatorName
    override val icon: Icon = YANDEX.icon
    override val primaryLanguage: Lang get() = YANDEX.primaryLanguage
    override val supportedSourceLanguages: List<Lang> = YandexLanguageAdapter.sourceLanguages
    override val supportedTargetLanguages: List<Lang> = YandexLanguageAdapter.targetLanguages
    override val intervalLimit: Int = YANDEX.intervalLimit
    override val contentLengthLimit: Int = YANDEX.contentLengthLimit


    override fun translateDocumentation(
        documentation: Document,
        srcLang: Lang,
        targetLang: Lang
    ): Document = checkError {
        documentation.translateBody { bodyHTML ->
            translateDocumentation(bodyHTML, srcLang, targetLang)
        }
    }


    private fun translateDocumentation(documentation: String, srcLang: Lang, targetLang: Lang): String {
        val client = SimpleTranslateClient(
            this, { _, _, _ -> call(documentation, srcLang, targetLang, true) }, YandexTranslation::parseDocTranslation
        )
        client.updateCacheKey { it.update("DOCUMENTATION".toByteArray()) }
        return client.execute(documentation, srcLang, targetLang).translation ?: ""
    }

    private fun parseTranslation(
        translation: String,
        original: String,
        srcLang: Lang,
        targetLang: Lang,
    ): Translation {
        YandexTranslator.logger.i("Translate result: $translation")

        if (translation.isBlank()) {
            return Translation(original, original, srcLang, targetLang, listOf(srcLang))
        }

        return gson.fromJson(translation, YandexTranslation::class.java).apply {
            this.original = original
            target = targetLang
        }.toTranslation()
    }


}