package net.galaxyy.chatgags.client.events

import com.mojang.authlib.GameProfile
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.galaxyy.chatgags.ChatGags
import net.galaxyy.chatgags.client.gags.Gag
import net.galaxyy.chatgags.client.gags.Gags
import net.galaxyy.chatgags.client.interfaces.HasGags
import net.minecraft.client.Minecraft
import net.minecraft.commands.Commands
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.PlayerChatMessage
import net.minecraft.network.chat.TextColor
import java.time.Instant
import java.util.Locale
import java.util.Locale.getDefault
import java.util.UUID
import kotlin.text.lowercase
import kotlin.uuid.Uuid

class ReceiveChatMessageEvent : ClientReceiveMessageEvents.AllowChat {
    override fun allowReceiveChatMessage(
        message: Component,
        playerChatMessage: PlayerChatMessage?,
        sender: GameProfile?,
        boundChatType: ChatType.Bound,
        timeStamp: Instant
    ): Boolean {
        val yourID: UUID = Minecraft.getInstance().player!!.uuid
        if (!(message.style.color == TextColor.GRAY && message.style.isItalic)) return true

        val msg: List<String> = message.string.split(" ")
        if (msg.size < 3) return true
        if (msg[msg.size-2].lowercase() == "gag" && msg[msg.size-1].lowercase() == "get" && sender?.id != yourID) return sendGags(sender)
        if (Gags.NameToGagMap.containsKey(msg[msg.size - 1].lowercase()) && msg[msg.size-2].lowercase() == "gag" && sender?.id != yourID) return getGagged(msg)
        if (Gags.NameToGagMap.containsKey(msg[msg.size - 1].lowercase()) && msg[msg.size-3].lowercase() == "gag" && msg[msg.size-2] == "both") return getGagged(msg)
        return true
    }

    private fun getGagged(msg: List<String>): Boolean {
        if (!(Minecraft.getInstance() as HasGags).`chat_gags$canOthersGag`()) return true

        val gag: Gag = Gags.NameToGagMap[msg[msg.size-1].lowercase(getDefault())]!!

        (Minecraft.getInstance() as HasGags).`chat_gags$setGag`(gag)
        Minecraft.getInstance().player?.sendOverlayMessage(Component.literal("Your gag was changed to " + gag.getName(false)))

        return false
    }

    private fun sendGags(sender: GameProfile?): Boolean {
        if (sender == null) return true
        val msg: String = Gags.NameToGagMap.keys.joinToString("; ")
        Minecraft.getInstance().connection?.sendCommand("msg ${sender.name} $msg")
        return false
    }
}