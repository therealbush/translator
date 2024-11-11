package me.bush.translator

import org.junit.jupiter.api.Test

/**
 * @author bush
 * @since 6/1/2022
 */
class TranslatorTest {

    @Test
    fun test() {
        val translator = Translator()
        val translation = translator.translateBlocking(
            """
                I go through my day pretty normal, like I'm a normal guy, I'm a swell guy, I'm a nice enough guy, I'm a 
                cool kinda guy. Heh. I'm a pretty groovy guy… But then I get a little SUGAR in me, and I start to go 
                CUCKOO! Doesn't have to be much, this time around it was two 'em–two of Fiber One® Brownies, only 90 
                calories each but they do the job! Do you know what I'm sayin'? They get me goin', they get me riled up!
                A little CUCKOO, a little WACKY! Start gettin' me a little KOOKY, a little–you know–LOOPY!! Ooh-ooh! 
                Hey! Somebody t–somebody put this kid in a padded cell, get 'im a straitjacket, he's goin' a lil… wacky…
                Goin' a lil KOOKY… He's off the walls! Bananas! Loco! You want me to stop. Isn't that wonderful. Well 
                lemme just do whatever you say, because it's your little fairy tale.
            """.trimIndent(),
            target = Language.entries.filter { it != Language.ENGLISH && it != Language.AUTO }.random(),
            source = Language.AUTO
        )
        println(translation.url)
        println(translation.translatedText)
        println(translation.sourceText)
        println(translation.targetLanguage)
        println(translation.sourceLanguage)
        println(translation.pronunciation)
        println(translation.pronunciationSource)
    }
}
