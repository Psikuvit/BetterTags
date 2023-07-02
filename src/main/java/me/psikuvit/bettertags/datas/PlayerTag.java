package me.psikuvit.bettertags.datas;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTag {

    private static PlayerTag INSTANCE;
    private final Map<UUID, Tag> playerTags;
    private final Map<UUID, Boolean> isActive;

    private PlayerTag() {
        this.playerTags = new HashMap<>();
        this.isActive = new HashMap<>();
    }

    public static PlayerTag getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerTag();
        }
        return INSTANCE;
    }

    public void setPlayerTag(Player player, Tag tag) {
        playerTags.put(player.getUniqueId(), tag);
    }

    public Tag getPlayerTag(Player player) {
        return playerTags.get(player.getUniqueId());
    }

    public boolean isActive(Player player) {
        return isActive.get(player.getUniqueId());
    }

    public void toggleActive(Player player) {
        boolean b = isActive.get(player.getUniqueId());// Toggle the state of the button
        isActive.put(player.getUniqueId(), !b);
    }

    public Map<UUID, Boolean> getIsActive() {
        return isActive;
    }

    public Map<UUID, Tag> getPlayerTags() {
        return playerTags;
    }
}