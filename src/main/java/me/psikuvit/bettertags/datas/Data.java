package me.psikuvit.bettertags.datas;

import me.psikuvit.bettertags.BetterTags;
import me.psikuvit.bettertags.CategoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Data {

    private final BetterTags plugin = BetterTags.getPlugin(BetterTags.class);
    private final CategoryManager categoryManager = CategoryManager.getInstance();

    public void createCategoriesFile() {
        File directory = plugin.getDataFolder();
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(plugin.getDataFolder(), "categories.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getLogger().info("Created file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getLogger().info("File exists");
        }
    }

    public void createTagsFile() {
        File directory = plugin.getDataFolder();
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(plugin.getDataFolder(), "tags.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getLogger().info("Created file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getLogger().info("File exists");
        }
    }

    public void createPlayerData() {
        File directory = plugin.getDataFolder();
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(plugin.getDataFolder(), "PlayersData.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getLogger().info("Created file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getLogger().info("File exists");
        }
    }

    public void loadPlayersData() {
        createPlayerData();
        File file = new File(plugin.getDataFolder(), "PlayersData.yml");
        if (file.length() == 0) {
            return;
        }
        YamlConfiguration y = YamlConfiguration.loadConfiguration(file);

        PlayerTag playerTag = PlayerTag.getInstance();

        for (String s : y.getKeys(false)) {
            String id = y.getString(s + ".Tag");
            Tag tag = categoryManager.getTagByID(id);
            boolean isActive = y.getBoolean(s + ".isActive");
            playerTag.getPlayerTags().put(UUID.fromString(s), tag);
            playerTag.getIsActive().put(UUID.fromString(s), isActive);
        }
    }
    public void savePlayersData() {
        File file = new File(plugin.getDataFolder(), "PlayersData.yml");
        YamlConfiguration y = YamlConfiguration.loadConfiguration(file);

        PlayerTag playerTag = PlayerTag.getInstance();

        Map<UUID, Boolean> isActive = playerTag.getIsActive();
        Map<UUID, Tag> playersTag = playerTag.getPlayerTags();

        for (UUID uuid : playersTag.keySet()) {
            y.set(uuid.toString() + ".Tag", playersTag.get(uuid).id());
            y.set(uuid + ".isActive", isActive.get(uuid));

        }
        try {
            y.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().info("Couldn't save players data: " + e.getMessage());
        }
    }

    public void loadCategories() {
        createCategoriesFile();
        File file = new File(plugin.getDataFolder(), "categories.yml");
        if (file.length() == 0) {
            return;
        }
        YamlConfiguration y = YamlConfiguration.loadConfiguration(file);
        for (String s : y.getKeys(false)) {
            String name = y.getString(s + ".Name");
            List<String> lore = y.getStringList(s + ".Lore");
            Material material = Material.getMaterial(y.getString(s + ".Item"));
            Category category = new Category(name, lore, new ItemStack(material), s);
            categoryManager.cacheCategory(category);
            categoryManager.getMaterials().add(material);
        }
    }

    public void loadTags() {
        createTagsFile();
        File file = new File(plugin.getDataFolder(), "tags.yml");
        if (file.length() == 0) {
            return;
        }
        YamlConfiguration y = YamlConfiguration.loadConfiguration(file);
        for (String s : y.getKeys(false)) {
            String name = y.getString(s + ".Name");
            String permission = y.getString(s + ".Permission");
            String category = y.getString(s + ".Collection");
            List<String> unlockedLore = y.getStringList(s + ".UnlockedLore");
            List<String> lockedLore = y.getStringList(s + ".LockedLore");

            Tag tag = new Tag(s, name, permission, lockedLore, unlockedLore, category);
            categoryManager.cacheTag(tag);
        }
    }
}
