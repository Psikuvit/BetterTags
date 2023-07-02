package me.psikuvit.bettertags.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.psikuvit.bettertags.datas.PlayerTag;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPI extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "bettertags";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Psikuvit";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        Player p = (Player) player;
        if (p == null) return null;
        if (params.equalsIgnoreCase("tag")) {
            if (PlayerTag.getInstance().isActive(p)) {
                return PlayerTag.getInstance().getPlayerTag(p).name(); // "name" requires the player to be valid
            } else {
                return "";
            }
        }
        return null; // Placeholder is unknown by the Expansion
    }

}
