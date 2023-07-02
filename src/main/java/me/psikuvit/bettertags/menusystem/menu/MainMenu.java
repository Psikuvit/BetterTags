package me.psikuvit.bettertags.menusystem.menu;

import me.psikuvit.bettertags.*;
import me.psikuvit.bettertags.datas.Category;
import me.psikuvit.bettertags.datas.PlayerTag;
import me.psikuvit.bettertags.menusystem.PaginatedMenu;
import me.psikuvit.bettertags.menusystem.PlayerMenuUtility;
import me.psikuvit.bettertags.utils.Messages;
import me.psikuvit.bettertags.utils.Utils;
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

public class MainMenu extends PaginatedMenu {

    private final PlayerTag playerTag = PlayerTag.getInstance();
    private final CategoryManager categoryManager = CategoryManager.getInstance();

    public MainMenu(PlayerMenuUtility playerMenuUtility, BetterTags plugin) {
        super(playerMenuUtility, plugin);
    }

    @Override
    public String getMenuName() {
        return Messages.Main_Menu;
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        List<Category> categories = categoryManager.getCategoryList();

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
                if (!((index + 1) >= categories.size())) {
                    page = page + 1;
                    super.open(p);
                } else {
                    p.sendMessage(Messages.Already_Last_Page);
                }
            }
        }
        if (e.getCurrentItem().getType() == Material.COMPARATOR) {
            if (playerTag.isActive(p)) {
                playerTag.toggleActive(p);
                inventory.setItem(51, makeItem(Material.COMPARATOR, Messages.Enable_Tag));
                return;
            } else {
                playerTag.toggleActive(p);
                inventory.setItem(51, makeItem(Material.COMPARATOR, Messages.Disable_Tag));
                return;
            }
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
        String id = pdc.get(new NamespacedKey(BetterTags.getPlugin(), "category"), PersistentDataType.STRING);

        Category category = categoryManager.getCategoryByID(id);
        new TagsMenu(playerMenuUtility, plugin, category).open(p);
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        inventory.setItem(4, Utils.getPlayerSkull(Bukkit.getPlayer(playerMenuUtility.owner())));
        if (playerTag.isActive(Bukkit.getPlayer(playerMenuUtility.owner()))) {
            inventory.setItem(51, makeItem(Material.COMPARATOR, Messages.Disable_Tag));
        } else {
            inventory.setItem(51, makeItem(Material.COMPARATOR, Messages.Enable_Tag));
        }

        List<Category> categories = categoryManager.getCategoryList();

        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if (index >= categories.size()) break;
            if (categories.get(index) != null) {
                ///////////////////////////
                Category category = categories.get(index);

                //Create an item from our collection and place it into the inventory
                ItemStack categoryItem = category.icon();
                ItemMeta categoryMeta = categoryItem.getItemMeta();
                PersistentDataContainer pdc = categoryMeta.getPersistentDataContainer();
                pdc.set(new NamespacedKey(BetterTags.getPlugin(), "category"), PersistentDataType.STRING, category.id());

                categoryMeta.setDisplayName(category.name());
                categoryMeta.setLore(category.lore());
                categoryItem.setItemMeta(categoryMeta);


                inventory.addItem(categoryItem);
            }
        }
    }
}
