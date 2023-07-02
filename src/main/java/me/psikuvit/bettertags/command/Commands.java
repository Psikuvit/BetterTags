package me.psikuvit.bettertags.command;

import me.psikuvit.bettertags.BetterTags;
import me.psikuvit.bettertags.CategoryManager;
import me.psikuvit.bettertags.menusystem.menu.MainMenu;
import me.psikuvit.bettertags.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class Commands implements CommandExecutor {

    public final BetterTags plugin = BetterTags.getPlugin(BetterTags.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("platania.menu.titres")) {
            sender.sendMessage(Messages.color("&cYou don't have permission to activate this tag!"));
            return true;
        }
        if (args.length == 0) {
            if (sender instanceof Player p) {
                new MainMenu(plugin.getPlayerMenuUtility(p), plugin).open(p);
            }
        }
        if (args.length > 1) {
            if (args[0].equals("reload")) {
                File file = new File(BetterTags.getPlugin(BetterTags.class).getDataFolder(), "categories.yml");
                YamlConfiguration y = YamlConfiguration.loadConfiguration(file);
                try {
                    y.load(file);
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }
}
