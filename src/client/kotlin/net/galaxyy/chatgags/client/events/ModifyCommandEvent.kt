package net.galaxyy.chatgags.client.events

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.galaxyy.chatgags.client.gags.Gag
import net.galaxyy.chatgags.client.gags.Gags
import net.galaxyy.chatgags.client.interfaces.HasGags
import net.minecraft.client.Minecraft

class ModifyCommandEvent : ClientSendMessageEvents.ModifyCommand {
    override fun modifySendCommandMessage(command: String): String {
        val cmd: List<String> = command.split(" ")
        val gag: Gag = (Minecraft.getInstance() as HasGags).`chat_gags$getGag`()
        if (cmd[0] !in arrayOf("w", "msg", "tell") || cmd.size < 3 || gag == Gags.EMPTY) return command


        return cmd[0] + " " + cmd[1] + " " + gag.convertMessage(
            cmd.subList(2, cmd.size).joinToString(" "), Minecraft.getInstance().player!!.random
        )
    }
}