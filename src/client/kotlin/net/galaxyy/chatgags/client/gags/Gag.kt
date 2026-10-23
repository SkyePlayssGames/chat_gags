package net.galaxyy.chatgags.client.gags

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.galaxyy.chatgags.client.gags.Gag.CustomGag
import net.minecraft.util.RandomSource
import java.util.Locale
import java.util.Locale.getDefault

open class Gag(private val name: String, val covers: List<String>) {
    var PUNCTUATION: List<Char>

    init {
        Gags.NameToGagMap[name.lowercase(getDefault()).replace(" ", "_")] = this
        PUNCTUATION = listOf('?', '!', '.', ',', '~', '-', '"', '\'', ';', '<', '>', '@', '#')
    }

    open fun convertMessage(message: String, random: RandomSource): String {
        if (this == Gags.EMPTY) return message

        var newMessage: String = ""
        message.split(" ").forEach { string ->
            newMessage += " "
            var done = false
            string.forEach { char ->
                if (char in PUNCTUATION && !done) {
                    newMessage += char
                } else {
                    done = true
                }
            }
            if (string.filterNot { char -> char in PUNCTUATION }.isNotEmpty()) {
                newMessage += covers[random.nextInt(covers.size)]
                done = false
                string.reversed().forEach { char ->
                    if (char in PUNCTUATION && !done) {
                        newMessage += char
                    } else {
                        done = true
                    }
                }
            }
        }
        return if (newMessage.length > 255) newMessage.substring(0, 256).trim() else newMessage.trim()
    }

    fun getName(hasNameSpace: Boolean): String {
        return if (hasNameSpace) name
        else name.substring(name.indexOf('+') + 1)
    }

    class RandomGag(name: String) : EmptyGag(name) {
        override fun convertMessage(message: String, random: RandomSource): String {
            val gags: ArrayList<Gag> = ArrayList()
            Gags.NameToGagMap.values.forEach { gag ->
                if (gag !is EmptyGag) gags.add(gag)
            }
            return gags[random.nextInt(gags.size)].convertMessage(message, random)
        }
    }

    open class EmptyGag(name: String) : Gag(name, listOf()) {
        override fun convertMessage(message: String, random: RandomSource): String {
            return message
        }
    }

    data class CustomGag(val name: String, val covers: List<String>, val type: String) {
        companion object {
            val CODEC: Codec<CustomGag> = RecordCodecBuilder.create { builder -> builder.group(
                Codec.STRING.fieldOf("name").forGetter(CustomGag::name),
                Codec.list(Codec.STRING).fieldOf("words").forGetter(CustomGag::covers),
                Codec.STRING.fieldOf("type").forGetter(CustomGag::type)
            ).apply(builder, ::CustomGag) }
        }
    }
}