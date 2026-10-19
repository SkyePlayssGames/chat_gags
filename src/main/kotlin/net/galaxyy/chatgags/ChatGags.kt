package net.galaxyy.chatgags

import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier
import org.slf4j.LoggerFactory

object ChatGags : ModInitializer {
	const val MOD_ID: String = "chat_gags"

	val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
	}

	fun id(path: String): Identifier
		= Identifier.fromNamespaceAndPath(MOD_ID, path)
}
