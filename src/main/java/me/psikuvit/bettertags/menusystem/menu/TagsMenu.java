package me.psikuvit.bettertags.menusystem.menu;

import me.psikuvit.bettertags.BetterTags;
import me.psikuvit.bettertags.CategoryManager;
import me.psikuvit.bettertags.datas.Category;
import me.psikuvit.bettertags.datas.PlayerTag;
import me.psikuvit.bettertags.datas.Tag;
import me.psikuvit.bettertags.menusystem.PaginatedMenu;
import me.psikuvit.bettertags.menusystem.PlayerMenuUtility;
import me.psikuvit.bettertags.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class TagsMenu extends PaginatedMenu {

    public final Category category;
    private final PlayerTag playerTag = PlayerTag.getInstance();
    private final CategoryManager categoryManager = CategoryManager.getInstance();

    public TagsMenu(PlayerMenuUtility playerMenuUtility, BetterTags plugin, Category category) {
        super(playerMenuUtility, plugin);
        this.category = category;
    }

    @Override
    public String getMenuName() {
        return Messages.Tag_Menu.replaceAll("%collection%", category.name());
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        List<Tag> tags = categoryManager.getCategoryTags(category);
        if (e.getCurrentItem().getType() == Material.BARRIER) {
            p.closeInventory();
        } else if (e.getCurrentItem().getType() == Material.ARROW) {
            if (e.getSlot() == 47) {
                if (page == 0) {
                    p.sendMessage(Messages.Already_First_Page);
                } else {
                    page = page - 1;
                    super.open(p);
                }
            } else if (e.getSlot() == 49) {
                if (!((index + 1) >= tags.size())) {
                    page = page + 1;
                    super.open(p);
                } else {
                    p.sendMessage(Messages.Already_Last_Page);
                }
            }
        }
        if (e.getCurrentItem().getType() == Material.DIAMOND) {
            new MainMenu(playerMenuUtility, plugin).open(p);
            return;
        }
        if (e.getSlot() == 18) {
            reorder("locked");
            return;
        } else if (e.getSlot() == 27) {
            reorder("unlocked");
            return;
        }
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null) {
            return;
        }
        if (!categoryManager.getMaterials().contains(clickedItem.getType())) {
            return;
        }
        ItemMeta clickMeta = clickedItem.getItemMeta();
        PersistentDataContainer pdc = clickMeta.getPersistentDataContainer();
        String id = pdc.get(new NamespacedKey(BetterTags.getPlugin(), "tag"), PersistentDataType.STRING);
        Tag tag = categoryManager.getTagByID(id);
        if (!p.hasPermission(tag.permission())) {
            p.sendMessage(Messages.color("&cYou don't have permission to activate this tag!"));
            return;
        }
        playerTag.setPlayerTag(p, tag);
        p.sendMessage(Messages.Select_Tag.replaceAll("%tag%", tag.name()));
    }

    @Override
    public void setMenuItems() {
        reorder("unlocked");
    }

    public void reorder(String order) {
        inventory.clear();
        addMenuBorder();
        inventory.setItem(51, makeItem(Material.DIAMOND, "&7Return Home"));
        inventory.setItem(18, makeItem(new ItemStack(Material.RED_WOOL, 1), "&eLocked Tags"));
        inventory.setItem(27, makeItem(new ItemStack(Material.GREEN_WOOL, 1), "&eUnlocked Tags"));

        List<Tag> tags = categoryManager.getCategoryTags(category);

        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if (index >= tags.size()) break;
            if (tags.get(index) != null) {
                ///////////////////////////
                Tag tag = tags.get(index);
                if (order.equals("locked")) {
                    if (Bukkit.getPlayer(playerMenuUtility.owner()).hasPermission(tag.permission())) {
                        continue;
                    }
                } else if (order.equals("unlocked")) {
                    if (!Bukkit.getPlayer(playerMenuUtility.owner()).hasPermission(tag.permission())) {
                        continue;
                    }
                }
                //Create an item from our collection and place it into the inventory
                ItemStack tagItem = new ItemStack(Material.NAME_TAG);
                ItemMeta tagMeta = tagItem.getItemMeta();
                PersistentDataContainer pdc = tagMeta.getPersistentDataContainer();
                pdc.set(new NamespacedKey(BetterTags.getPlugin(), "tag"), PersistentDataType.STRING, tag.id());

                tagMeta.setDisplayName(tag.name());
                if (Bukkit.getPlayer(playerMenuUtility.owner()).hasPermission(tag.permission())) {
                    tagMeta.setLore(tag.unlockedLore());
                } else {
                    tagMeta.setLore(tag.lockedLore());
                }
                tagItem.setItemMeta(tagMeta);


                inventory.addItem(tagItem);
            }
        }
    }
}
