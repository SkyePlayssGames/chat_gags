package net.galaxyy.chatgags.client.events

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.galaxyy.chatgags.client.gags.Gags
import net.galaxyy.chatgags.client.interfaces.HasGags
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component

class AllowChatMessageEvent : ClientSendMessageEvents.AllowChat {
    override fun allowSendChatMessage(message: String): Boolean {
        if ((Minecraft.getInstance() as HasGags).`chat_gags$getGag`() != Gags.MUTE) return true

        Minecraft.getInstance().player?.sendOverlayMessage(Component.literal("You aren't allowed to speak!"))
        return false
    }
}