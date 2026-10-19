package net.galaxyy.chatgags.client.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.galaxyy.chatgags.client.gags.Gag
import net.galaxyy.chatgags.client.gags.Gags
import java.util.concurrent.CompletableFuture

class GagSuggestions : SuggestionProvider<FabricClientCommandSource> {
    override fun getSuggestions(
        context: CommandContext<FabricClientCommandSource>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        Gags.NameToGagMap.keys.forEach { string -> builder.suggest(string) }

        return builder.buildFuture()
    }
}