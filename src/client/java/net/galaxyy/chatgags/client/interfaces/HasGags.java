package net.galaxyy.chatgags.client.interfaces;

import net.galaxyy.chatgags.client.gags.Gag;

public interface HasGags {
    void chat_gags$setGag(Gag gag);
    Gag chat_gags$getGag();

    boolean chat_gags$canSelfGag();
    boolean chat_gags$toggleSelfGag();

    boolean chat_gags$canOthersGag();
    boolean chat_gags$toggleOthersGag();
}
