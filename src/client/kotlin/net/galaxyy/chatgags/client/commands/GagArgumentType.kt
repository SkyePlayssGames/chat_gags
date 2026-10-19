package net.galaxyy.chatgags.client.commands

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.galaxyy.chatgags.client.gags.Gag
import net.galaxyy.chatgags.client.gags.Gags

class GagArgumentType : ArgumentType<Gag> {
    override fun parse(reader: StringReader): Gag {
        try {
            val string: String = reader.readString()

            return Gags.NameToGagMap[string] ?: throw Exception()

        } catch (e: Exception) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException().create(
                "Expected one of the possible gags"
            )
        }
    }
}