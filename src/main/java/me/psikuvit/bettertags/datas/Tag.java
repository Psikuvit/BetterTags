package me.psikuvit.bettertags.datas;

import me.psikuvit.bettertags.utils.Messages;

import java.util.List;

public record Tag(String id, String name, String permission, List<String> lockedLore, List<String> unlockedLore,
                  String category) {

    @Override
    public String name() {
        return Messages.color(name);
    }

    @Override
    public List<String> lockedLore() {
        return Messages.color(lockedLore);
    }

    @Override
    public List<String> unlockedLore() {
        return Messages.color(unlockedLore);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", lockedLore=" + lockedLore +
                ", unlockedLore=" + unlockedLore +
                ", category='" + category + '\'' +
                '}';
    }
}
