package com.gmail.nossr50.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.utilities.SkillTools;
import com.gmail.nossr50.skills.utilities.SkillType;
import com.gmail.nossr50.util.Permissions;

public final class CommandHelper {
    private CommandHelper() {}

    /**
     * Checks for command permissions.
     *
     * @param sender The command sender
     * @param permission The permission to check
     * @return true if the sender is a player without permissions, false otherwise
     */
    public static boolean noCommandPermissions(CommandSender sender, String permission) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!Permissions.hasPermission(player, permission)) {
                player.sendMessage(LocaleLoader.getString("mcMMO.NoPermission"));
                return true;
            }
        }

        return false;
    }

    public static boolean noCommandPermissions(Player player, String permission) {
        if (!Permissions.hasPermission(player, permission)) {
            player.sendMessage(LocaleLoader.getString("mcMMO.NoPermission"));
            return true;
        }

        return false;
    }

    public static boolean noConsoleUsage(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LocaleLoader.getString("Commands.NoConsole"));
            return true;
        }

        return false;
    }

    /**
     * Print out details on Gathering skills. Only for online players.
     *
     * @param inspect The player to retrieve stats for
     * @param profile The player's profile
     * @param display The sender to display stats to
     */
    public static void printGatheringSkills(Player inspect, PlayerProfile profile, CommandSender display) {
        if (SkillTools.hasGatheringSkills(inspect)) {
            display.sendMessage(LocaleLoader.getString("Stats.Header.Gathering"));

            if (Permissions.excavation(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Excavation.Listener"), profile.getSkillLevel(SkillType.EXCAVATION), profile.getSkillXpLevel(SkillType.EXCAVATION), profile.getXpToLevel(SkillType.EXCAVATION)));
            }

            if (Permissions.fishing(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Fishing.Listener"), profile.getSkillLevel(SkillType.FISHING), profile.getSkillXpLevel(SkillType.FISHING), profile.getXpToLevel(SkillType.FISHING)));
            }

            if (Permissions.herbalism(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Herbalism.Listener"), profile.getSkillLevel(SkillType.HERBALISM), profile.getSkillXpLevel(SkillType.HERBALISM), profile.getXpToLevel(SkillType.HERBALISM)));
            }

            if (Permissions.mining(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Mining.Listener"), profile.getSkillLevel(SkillType.MINING), profile.getSkillXpLevel(SkillType.MINING), profile.getXpToLevel(SkillType.MINING)));
            }

            if (Permissions.woodcutting(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Woodcutting.Listener"), profile.getSkillLevel(SkillType.WOODCUTTING), profile.getSkillXpLevel(SkillType.WOODCUTTING), profile.getXpToLevel(SkillType.WOODCUTTING)));
            }
        }
    }

    public static void printGatheringSkills(Player player, PlayerProfile profile) {
        printGatheringSkills(player, profile, player);
    }

    /**
     * Print out details on Combat skills. Only for online players.
     *
     * @param inspect The player to retrieve stats for
     * @param profile The player's profile
     * @param display The sender to display stats to
     */
    public static void printCombatSkills(Player inspect, PlayerProfile profile, CommandSender display) {
        if (SkillTools.hasCombatSkills(inspect)) {
            display.sendMessage(LocaleLoader.getString("Stats.Header.Combat"));

            if (Permissions.axes(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Axes.Listener"), profile.getSkillLevel(SkillType.AXES), profile.getSkillXpLevel(SkillType.AXES), profile.getXpToLevel(SkillType.AXES)));
            }

            if (Permissions.archery(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Archery.Listener"), profile.getSkillLevel(SkillType.ARCHERY), profile.getSkillXpLevel(SkillType.ARCHERY), profile.getXpToLevel(SkillType.ARCHERY)));
            }

            if (Permissions.swords(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Swords.Listener"), profile.getSkillLevel(SkillType.SWORDS), profile.getSkillXpLevel(SkillType.SWORDS), profile.getXpToLevel(SkillType.SWORDS)));
            }

            if (Permissions.taming(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Taming.Listener"), profile.getSkillLevel(SkillType.TAMING), profile.getSkillXpLevel(SkillType.TAMING), profile.getXpToLevel(SkillType.TAMING)));
            }

            if (Permissions.unarmed(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Unarmed.Listener"), profile.getSkillLevel(SkillType.UNARMED), profile.getSkillXpLevel(SkillType.UNARMED), profile.getXpToLevel(SkillType.UNARMED)));
            }
        }
    }

    public static void printCombatSkills(Player player, PlayerProfile profile) {
        printCombatSkills(player, profile, player);
    }

    /**
     * Print out details on Misc skills. Only for online players.
     *
     * @param inspect The player to retrieve stats for
     * @param profile The player's profile
     * @param display The sender to display stats to
     */
    public static void printMiscSkills(Player inspect, PlayerProfile profile, CommandSender display) {
        if (SkillTools.hasMiscSkills(inspect)) {
            display.sendMessage(LocaleLoader.getString("Stats.Header.Misc"));

            if (Permissions.acrobatics(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Acrobatics.Listener"), profile.getSkillLevel(SkillType.ACROBATICS), profile.getSkillXpLevel(SkillType.ACROBATICS), profile.getXpToLevel(SkillType.ACROBATICS)));
            }

            if (Permissions.repair(inspect)) {
                display.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Repair.Listener"), profile.getSkillLevel(SkillType.REPAIR), profile.getSkillXpLevel(SkillType.REPAIR), profile.getXpToLevel(SkillType.REPAIR)));
            }
        }
    }

    public static void printMiscSkills(Player player, PlayerProfile profile) {
        printMiscSkills(player, profile, player);
    }
}
