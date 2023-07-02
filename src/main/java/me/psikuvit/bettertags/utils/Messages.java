package me.psikuvit.bettertags.utils;

import me.psikuvit.bettertags.BetterTags;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class Messages {

    public static String Main_Menu;
    public static String Tag_Menu;
    public static String Close;
    public static String Return_Home;
    public static String Right;
    public static String Left;
    public static String Locked_Tags;
    public static String Unlocked_Tags;
    public static String Select_Tag;
    public static String Already_First_Page;
    public static String Already_Last_Page;
    public static String Enable_Tag;
    public static String Disable_Tag;

    private static final BetterTags plugin = BetterTags.getPlugin(BetterTags.class);

    public static void loadStrings() {
        Main_Menu = color(plugin.getConfig().getString("Main-Menu"));
        Tag_Menu = color(plugin.getConfig().getString("Tag-Menu"));
        Close = color(plugin.getConfig().getString("Close"));
        Return_Home = color(plugin.getConfig().getString("Return-Home"));
        Right = color(plugin.getConfig().getString("Right"));
        Left = color(plugin.getConfig().getString("Left"));
        Locked_Tags = color(plugin.getConfig().getString("Locked-Tags"));
        Unlocked_Tags = color(plugin.getConfig().getString("Unlocked-Tags"));
        Select_Tag = color(plugin.getConfig().getString("Select-Tag"));
        Already_First_Page = color(plugin.getConfig().getString("Already-First-Page"));
        Already_Last_Page = color(plugin.getConfig().getString("Already-Last-Page"));
        Enable_Tag = color(plugin.getConfig().getString("Enable-Tag"));
        Disable_Tag = color(plugin.getConfig().getString("Disable-Tag"));
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static List<String> color(List<String> strings) {
        return strings.stream().map(Messages::color).collect(Collectors.toList());
    }
}
