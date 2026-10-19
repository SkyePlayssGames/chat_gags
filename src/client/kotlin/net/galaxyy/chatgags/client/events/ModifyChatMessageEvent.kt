package net.galaxyy.chatgags.client.events

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.galaxyy.chatgags.client.interfaces.HasGags
import net.minecraft.client.Minecraft
import net.minecraft.util.RandomSource

class ModifyChatMessageEvent : ClientSendMessageEvents.ModifyChat {
    override fun modifySendChatMessage(message: String): String {
        val random: RandomSource = Minecraft.getInstance().player!!.random
        return (Minecraft.getInstance() as HasGags).`chat_gags$getGag`().convertMessage(message, random)
    }
}