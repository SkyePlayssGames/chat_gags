package net.galaxyy.chatgags.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.galaxyy.chatgags.ChatGags.id
import net.galaxyy.chatgags.client.commands.GagArgumentType
import net.galaxyy.chatgags.client.commands.GagCommands
import net.galaxyy.chatgags.client.commands.GagSuggestions
import net.galaxyy.chatgags.client.events.AllowChatMessageEvent
import net.galaxyy.chatgags.client.events.AllowCommandEvent
import net.galaxyy.chatgags.client.events.ModifyChatMessageEvent
import net.galaxyy.chatgags.client.events.ModifyCommandEvent
import net.galaxyy.chatgags.client.events.ReceiveChatMessageEvent
import net.galaxyy.chatgags.client.gags.GagReloadListener
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.commands.synchronization.SingletonArgumentInfo
import net.minecraft.server.packs.PackType

object ChatGagsClient : ClientModInitializer {
	override fun onInitializeClient() {
		ArgumentTypeRegistry.registerArgumentType(
			id("gag"),
			GagArgumentType().javaClass,
			SingletonArgumentInfo.contextFree(::GagArgumentType)
		)
		ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(
			id("custom_gags"), GagReloadListener
		)

		ClientSendMessageEvents.ALLOW_CHAT.register(AllowChatMessageEvent())
		ClientSendMessageEvents.ALLOW_COMMAND.register(AllowCommandEvent())
		ClientSendMessageEvents.MODIFY_CHAT.register(ModifyChatMessageEvent())
		ClientSendMessageEvents.MODIFY_COMMAND.register(ModifyCommandEvent())
		ClientReceiveMessageEvents.ALLOW_CHAT.register(ReceiveChatMessageEvent())

		ClientCommandRegistrationCallback.EVENT.register { dispatcher, context ->
			dispatcher.register(ClientCommands.literal("gag")
				.executes(GagCommands::getGag)
				.then(ClientCommands.literal("set")
					.then(ClientCommands.argument("gag", GagArgumentType())
						.suggests(GagSuggestions())
						.executes(GagCommands::setGag)
					)
				)
			)
		}
	}

	//TODO: Setting for letting others gag you

}