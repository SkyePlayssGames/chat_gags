package net.galaxyy.chatgags.client.gags

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.galaxyy.chatgags.ChatGags
import net.galaxyy.chatgags.ChatGags.id
import net.galaxyy.chatgags.client.gags.Gags.NameToGagMap
import net.galaxyy.chatgags.client.gags.Gags.resourcePackGags
import net.minecraft.client.Minecraft
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import kotlin.collections.mapNotNull

class GagReloadListener : SimpleSynchronousResourceReloadListener {
    override fun getFabricId(): Identifier {
        return id("gags")
    }

    override fun onResourceManagerReload(resourceManager: ResourceManager) {
        resourcePackGags.forEach { gag ->
            NameToGagMap.remove(gag.name)
        }
        resourcePackGags.removeAll { _ -> true }

        resourceManager.listResources("gags", { path -> path.toString().endsWith(".json")}).keys.forEach { identifier ->
            val resource: Resource = resourceManager.getResource(identifier).get()

            val gagJson = (Json.Default.decodeFromString(resource.open().readAllBytes().decodeToString()) as JsonObject)
            val name: String? = (gagJson["name"] as JsonPrimitive?)?.content
            val wordsJson: JsonArray? = (gagJson["words"] as JsonArray?)
            val type: String? = (gagJson["type"] as JsonPrimitive?)?.content
            if (name == null || wordsJson == null || type == null) {
                ChatGags.LOGGER.error("Error reloading custom Chat Gags: Missing content field")
                return
            }
            Gags.tryUpdateResourcePackGag(name, wordsJson, type)
        }
    }
}