package net.galaxyy.chatgags.client.events

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.galaxyy.chatgags.client.gags.Gags
import net.galaxyy.chatgags.client.interfaces.HasGags
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component

class AllowCommandEvent : ClientSendMessageEvents.AllowCommand {
    override fun allowSendCommandMessage(command: String): Boolean {
        if (!(command.split(" ")[0] in arrayOf("w", "msg", "tell") &&
            (Minecraft.getInstance() as HasGags).`chat_gags$getGag`() == Gags.MUTE)) return true

        Minecraft.getInstance().player?.sendOverlayMessage(Component.literal("You aren't allowed to speak!"))
        return false
    }
}