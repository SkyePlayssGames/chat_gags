package net.galaxyy.chatgags.client.gags

import net.minecraft.util.RandomSource
import java.util.Locale
import java.util.Locale.getDefault

open class Gag(val name: String, val covers: List<String>) {
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

    class RandomGag(name: String) : Gag(name, listOf()) {
        override fun convertMessage(message: String, random: RandomSource): String {
            val gags: ArrayList<Gag> = ArrayList()
            Gags.NameToGagMap.values.forEach { gag ->
                if (gag !is RandomGag && gag !is EmptyGag) gags.add(gag)
            }
            return gags[random.nextInt(gags.size)].convertMessage(message, random)
        }
    }

    class EmptyGag(name: String) : Gag(name, listOf()) {
        override fun convertMessage(message: String, random: RandomSource): String {
            return message
        }
    }
}