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
import net.galaxyy.chatgags.ChatGags.MOD_ID
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

    val VanillaGags: List<Gag> = listOf(MUTE, RANDOM, CAT, DOG, FOX, BIRD, MUFFLED, SQUEAK)
    var resourcePackGags: ArrayList<Gag> = ArrayList()

    val GAG_TYPES = listOf(
        "create",
        "add",
        "remove",
        "delete",
        "replace"
    )

    fun tryUpdateResourcePackGag(customGag: Gag.CustomGag, namespace: String) {
        if (customGag.name.none { char -> char in 'a'..'z' || char in 'A'..'Z' || char in '1'..'9' || char == '-' || char == '_' || char == ' ' }) {
            LOGGER.error("Name must be fully alphanumeric, extended with _, -, and space, not \"${customGag.name}\"")
            return
        }
        val name = customGag.name
        if (customGag.covers == listOf<String>() && customGag.type != "remove") {
            LOGGER.error("No elements found in words for gag $name")
            return
        }
        if (customGag.type !in GAG_TYPES) {
            LOGGER.error("Invalid type declared for gag $name")
            return
        }

        if (customGag.type == "create") {
            if (namespace in listOf("minecraft", MOD_ID)) {
                LOGGER.error("Cannot create a new gag in the $namespace namespace")
                return
            }
            if (NameToGagMap["$namespace+$name".lowercase().replace(' ', '_')] != null) {
                LOGGER.error("Tried defining gag $namespace+$name twice")
                return
            }
            resourcePackGags.add(Gag("$namespace+$name", customGag.covers))
            return
        }
        if (customGag.type == "add") {
            val toModify = getGagToModify(namespace, name, "add") ?: return
            Gag(toModify.getName(true), toModify.covers + customGag.covers)
            return
        }
        if (customGag.type == "remove") {
            val toModify = getGagToModify(namespace, name, "remove") ?: return
            Gag(toModify.getName(true), toModify.covers - customGag.covers.toSet())
            return
        }
        if (customGag.type == "replace") {
            val toModify = getGagToModify(namespace, name, "replace") ?: return
            Gag(toModify.getName(true), customGag.covers)
            return
        }
        if (customGag.type == "delete") {
            val toModify: Gag? = NameToGagMap.remove((if (namespace == MOD_ID) name else "$namespace+$name").lowercase().replace(' ', '_'))
            if (toModify == null) {
                LOGGER.error("Non-existent or invalid gag \"$namespace+$name\" defined for delete. Make sure your resource packs are ordered correctly")
                return
            }
            return
        }
    }

    private fun getGagToModify(namespace: String, name: String, type: String): Gag? {
        val toModify: Gag? = NameToGagMap[(if (namespace == MOD_ID) name else "$namespace+$name").lowercase().replace(' ', '_')]
        if (toModify == null || toModify is Gag.EmptyGag) {
            LOGGER.error("Non-existent or invalid gag \"$namespace+$name\" defined for $type. Make sure your resource packs are ordered correctly")
            return null
        }
        return toModify
    }
}