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
        val translation = translator.translateBlocking( // todo: unescape newlines or deal with them somehow
            """
                Yeezy season approaching
                Fuck whatever y'all been hearing
                Fuck what, fuck whatever y'all been wearing
                A monster about to come alive again
                Soon as I pull up and park the Benz
                We get this bitch shaking like Parkinsons
                Take my number and lock it in
                Indian hair, no moccasins
                It's too many hoes in this house of sin
                Real nigga back in the house again
                Black Timbs all on your couch again
                Black dick all in your spouse again
                And I know she like chocolate men
                She got more niggas off than Cochran, huh?
                On sight, on sight
                How much do I not give a fuck?
                Let me show you right now before you give it up
                How much do I not give a fuck?
                Let me show you right now before you give it up
                He'll give us what we need
                It may not be what we want
                Baby girl tryna get a nut
                And her girl tryna give it up
                Chopped em both down
                Don't judge 'em, Joe Brown
                One last announcement
                No sports bra, let's keep it bouncing
                Everybody wanna live at the top of the mountain
                Took her to the 'Bleau, she tried to sip the fountain
                That when David Grutman kicked her out
                But I got her back in and put my dick in her mouth
                On sight, on sight
                Uh-huh
                Right now, I need right now
                Right now, I need, I need right now
                Right now, I need, I need right now
            """.trimIndent(),
            target = Language.BOSNIAN,
            source = Language.AUTO
        )
        println(translation.url)
        println(translation.translatedText)
        println(translation.sourceText)
        println(translation.target)
        println(translation.source)
        println(translation.pronunciation)
    }
}
