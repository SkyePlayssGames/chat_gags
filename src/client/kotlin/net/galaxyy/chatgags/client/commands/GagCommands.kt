package net.galaxyy.chatgags.client.commands

import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.galaxyy.chatgags.client.gags.Gag
import net.galaxyy.chatgags.client.gags.Gags
import net.galaxyy.chatgags.client.interfaces.HasGags
import net.minecraft.client.Minecraft
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player

object GagCommands {
    fun setGag(context: CommandContext<FabricClientCommandSource>): Int {
        if (!(Minecraft.getInstance() as HasGags).`chat_gags$canSelfGag`()) {
            context.source.sendError(Component.literal("You aren't allowed to set your own gag!"))
            return 0
        }
        val gag: Gag = context.getArgument("gag", Gag::class.java)
        (Minecraft.getInstance() as HasGags).`chat_gags$setGag`(gag)
        context.source.sendFeedback(Component.literal("You set your gag to " + gag.getName(false)))
        return 1
    }

    fun getGag(context: CommandContext<FabricClientCommandSource>): Int {
        val gag: Gag = (Minecraft.getInstance() as HasGags).`chat_gags$getGag`()
        context.source.sendFeedback(Component.literal("You currently have this gag: " + gag.getName(false)))
        return 1
    }
}