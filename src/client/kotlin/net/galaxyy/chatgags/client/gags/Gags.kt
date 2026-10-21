package net.galaxyy.chatgags.client.gags

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.internal.readJson
import kotlinx.serialization.json.jsonNull
import kotlinx.serialization.json.jsonPrimitive
import net.galaxyy.chatgags.ChatGags
import net.galaxyy.chatgags.ChatGags.LOGGER
import net.galaxyy.chatgags.ChatGags.id
import net.minecraft.client.Minecraft
import java.util.logging.Logger

object Gags {
    val NameToGagMap: HashMap<String, Gag> = HashMap()

    @JvmField
    val EMPTY: Gag = Gag.EmptyGag("None")
    val MUTE: Gag = Gag.EmptyGag("Mute")
    val RANDOM: Gag = Gag.RandomGag("Random")
    val CAT: Gag = Gag("Cat", listOf("hiss", "meow", "purr", "nyaa"))
    val DOG: Gag = Gag("Dog", listOf("woof", "arf", "ruff", "grr"))
    val FOX: Gag = Gag("Fox", listOf("yip", "yipyip", "awawa", "auuu"))
    val BIRD: Gag = Gag("Bird", listOf("chirp", "tweet", "*sing*", "fweet"))
    val MUFFLED: Gag = Gag("Muffled", listOf("mmmph", "mmph", "mmmmmphh"))
    val SQUEAK: Gag = Gag("Squeak", listOf("squeak!"))

    var resourcePackGags: ArrayList<Gag> = ArrayList()

    val GAG_TYPES = listOf(
        "create"
    )

    fun tryUpdateResourcePackGag(name: String, words: List<JsonElement>, type: String, namespace: String) {
        if (name.none { char -> !(char.isLetterOrDigit() || char == '_' || char == '-') }) {
            LOGGER.error("Name must be fully alphanumeric, extended with _ and -")
        }
        val result = validateWords(words)
        if (result.second != "") {
            LOGGER.error(result.second + " for gag $name")
            return
        }
        if (result.first == listOf<String>()) {
            LOGGER.error("No elements found in words for gag $name")
            return
        }
        if (type !in GAG_TYPES) {
            LOGGER.error("Invalid type declared for gag $name")
        }

        if (type == "create") {
            resourcePackGags.add(Gag("$namespace+$name", result.first))
        }
    }

    private fun validateWords(words: List<JsonElement>): Pair<List<String>, String> {
        val result: ArrayList<String> = arrayListOf()
        words.forEach { element ->
            try {
                element.jsonPrimitive
            } catch (e: IllegalArgumentException) {
                return Pair(listOf(), "One of the elements of words is not a String")
            }
            if (!element.jsonPrimitive.isString) {
                return Pair(listOf(), "One of the elements of words is not a String")
            }
            result.add(element.jsonPrimitive.content)
        }
        return Pair(result, "")
    }
}