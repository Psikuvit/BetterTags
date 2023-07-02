package me.psikuvit.bettertags.utils;

import me.psikuvit.bettertags.CategoryManager;
import me.psikuvit.bettertags.datas.PlayerTag;
import me.psikuvit.bettertags.menusystem.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickEventListener implements Listener {
    private final CategoryManager categoryManager = CategoryManager.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        //If the inventory-holder of the inventory clicked on
        // is an instance of Menu, then gg. The reason that
        // an InventoryHolder can be a Menu is because our Menu
        // class implements InventoryHolder!!
        if (holder instanceof Menu menu) {
            event.setCancelled(true); //prevent them from fucking with the inventory
            if (event.getCurrentItem() == null) { //deal with null exceptions
                return;
            }
            //Since we know our inventory-holder is a menu, get the Menu Object representing
            // the menu we clicked on
            //Call the handleMenu object which takes the event and processes it
            menu.handleMenu(event);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PlayerTag playerTag = PlayerTag.getInstance();
        if (!playerTag.getIsActive().containsKey(e.getPlayer().getUniqueId())) {
            playerTag.getIsActive().put(e.getPlayer().getUniqueId(), true);
        }
    }
}
