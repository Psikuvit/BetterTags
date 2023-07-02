package me.psikuvit.bettertags.menusystem;

import me.psikuvit.bettertags.BetterTags;
import me.psikuvit.bettertags.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class PaginatedMenu extends Menu {

    //Keep track of what page the menu is on
    protected int page = 0;

    //28 is max items because with the border set below,
    //28 empty slots are remaining.
    protected int maxItemsPerPage = 28;

    //the index represents the index of the slot
    //that the loop is on
    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility, BetterTags plugin) {
        super(playerMenuUtility, plugin);
    }

    //Set the border and menu buttons for the menu
    public void addMenuBorder(){
        inventory.setItem(47, makeItem(Material.ARROW, Messages.Left));

        inventory.setItem(48, makeItem(Material.BARRIER, Messages.Close));

        inventory.setItem(49, makeItem(Material.ARROW, Messages.Right));


        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS_10);
            }
        }

        inventory.setItem(2, FILLER_GLASS_8);
        inventory.setItem(3, FILLER_GLASS_8);
        inventory.setItem(5, FILLER_GLASS_8);
        inventory.setItem(6, FILLER_GLASS_8);
        inventory.setItem(17, super.FILLER_GLASS_10);
        inventory.setItem(18, FILLER_GLASS_8);
        inventory.setItem(26, FILLER_GLASS_8);
        inventory.setItem(27, FILLER_GLASS_8);
        inventory.setItem(35, FILLER_GLASS_8);
        inventory.setItem(36, super.FILLER_GLASS_10);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS_10);
            }
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }
}
