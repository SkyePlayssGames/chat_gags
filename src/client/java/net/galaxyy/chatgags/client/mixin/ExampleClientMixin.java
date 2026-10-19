package net.galaxyy.chatgags.client.mixin;

import net.galaxyy.chatgags.client.gags.Gag;
import net.galaxyy.chatgags.client.gags.Gags;
import net.galaxyy.chatgags.client.interfaces.HasGags;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ExampleClientMixin implements HasGags {
	@Unique
    private Gag gag = Gags.EMPTY;
	@Unique
	private boolean selfGag = true;
	@Unique
	private boolean othersGag = true;



	@Override
	public void chat_gags$setGag(Gag gag) {
		this.gag = gag;
	}

	@Override
	public Gag chat_gags$getGag() {
		return this.gag;
	}

	@Override
	public boolean chat_gags$canSelfGag() {
		return selfGag;
	}

	@Override
	public boolean chat_gags$toggleSelfGag() {
		selfGag = !selfGag;
		return selfGag;
	}

	@Override
	public boolean chat_gags$canOthersGag() {
		return othersGag;
	}

	@Override
	public boolean chat_gags$toggleOthersGag() {
		othersGag = !othersGag;
		return othersGag;
	}
}