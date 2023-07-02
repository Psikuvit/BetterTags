package me.psikuvit.bettertags;

import me.psikuvit.bettertags.command.Commands;
import me.psikuvit.bettertags.datas.Data;
import me.psikuvit.bettertags.menusystem.PlayerMenuUtility;
import me.psikuvit.bettertags.utils.InventoryClickEventListener;
import me.psikuvit.bettertags.utils.Messages;
import me.psikuvit.bettertags.utils.PAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class BetterTags extends JavaPlugin {


    public static BetterTags plugin;
    private final Map<UUID, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private Data data;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        data = new Data();
        data.loadTags();
        data.loadCategories();
        data.loadPlayersData();

        CategoryManager.getInstance().cacheMap();

        getServer().getPluginManager().registerEvents(new InventoryClickEventListener(), this);

        getCommand("titres").setExecutor(new Commands());
        saveDefaultConfig();

        Messages.loadStrings();


        PAPI papi = new PAPI();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            // PlaceholderAPI is available, so register the expansion
            if (!papi.isRegistered())
                papi.register();
        } else {
            // PlaceholderAPI is not available, handle the situation accordingly
            getLogger().warning("PlaceholderAPI not found. Your custom expansion will not work.");
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        data.savePlayersData();
    }

    public PlayerMenuUtility getPlayerMenuUtility(Player p) {
        return playerMenuUtilityMap.computeIfAbsent(p.getUniqueId(), PlayerMenuUtility::new);
    }

    public static BetterTags getPlugin() {
        return plugin;
    }
}
