package net.galaxyy.chatgags.client.modmenu

import net.galaxyy.chatgags.ChatGags.id
import net.galaxyy.chatgags.client.interfaces.HasGags
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Button.CreateNarration
import net.minecraft.client.gui.components.toasts.SystemToast
import net.minecraft.client.gui.components.toasts.SystemToast.SystemToastId.PERIODIC_NOTIFICATION
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import java.util.function.Supplier

class ModMenuConfigScreen : Screen(Component.literal("Chat Gags Config")) {
    val selfGagButton: Button = Button.Builder(
        Component.literal("Toggle self-gagging"),
        { _ ->
            val x: Boolean = (Minecraft.getInstance() as HasGags).`chat_gags$toggleSelfGag`()
            Minecraft.getInstance().gui.toastManager().addToast(
                SystemToast(
                    PERIODIC_NOTIFICATION,
                    Component.literal("Updated Self-Gagging"),
                    Component.literal("Set it to: $x")
                )
            )
        }
    ).build()

    val othersGagButton: Button = Button.Builder(
        Component.literal("Toggle others gagging you"),
        { _ ->
            val x: Boolean = (Minecraft.getInstance() as HasGags).`chat_gags$toggleOthersGag`()
            Minecraft.getInstance().gui.toastManager().addToast(
                SystemToast(
                    PERIODIC_NOTIFICATION,
                    Component.literal("Updated Others Gagging"),
                    Component.literal("Set it to: $x")
                )
            )
        }
    ).pos(0, Button.DEFAULT_HEIGHT + 1).build()

    override fun init() {
        super.init()

        addRenderableWidget(selfGagButton)
        addRenderableWidget(othersGagButton)
    }
}