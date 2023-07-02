package me.psikuvit.bettertags.datas;

import me.psikuvit.bettertags.utils.Messages;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public record Category(String name, List<String> lore, ItemStack icon, String id) {

    @Override
    public String name() {
        return Messages.color(name);
    }

    @Override
    public List<String> lore() {
        return Messages.color(lore);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", lore=" + lore +
                ", icon=" + icon +
                ", id='" + id + '\'' +
                '}';
    }
}
