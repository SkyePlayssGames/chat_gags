package net.galaxyy.chatgags.client.gags

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.internal.readJson
import net.galaxyy.chatgags.ChatGags
import net.galaxyy.chatgags.ChatGags.id
import net.minecraft.client.Minecraft

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

}