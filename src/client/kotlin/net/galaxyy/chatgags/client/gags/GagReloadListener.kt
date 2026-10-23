package net.galaxyy.chatgags.client.gags

import com.google.gson.Gson
import com.mojang.serialization.JsonOps
import net.galaxyy.chatgags.client.gags.Gags.NameToGagMap
import net.galaxyy.chatgags.client.gags.Gags.VanillaGags
import net.galaxyy.chatgags.client.gags.Gags.resourcePackGags
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.ResourceManagerReloadListener

class GagReloadListener : ResourceManagerReloadListener {
    override fun onResourceManagerReload(resourceManager: ResourceManager) {
        NameToGagMap.clear()
        VanillaGags.forEach { gag -> Gag(gag.getName(true), gag.covers) }
        resourcePackGags.removeAll { _ -> true }

        resourceManager.listResources("gags", { path -> path.toString().endsWith(".json")}).keys.forEach { identifier ->
            val resource: Resource = resourceManager.getResource(identifier).get()
            val resourceJson = Gson().fromJson(resource.open().readAllBytes().decodeToString(), com.google.gson.JsonObject::class.java)
            val customGag = Gag.CustomGag.CODEC.parse(JsonOps.INSTANCE, resourceJson)
            Gags.tryUpdateResourcePackGag(customGag, identifier.namespace)
        }
    }
}