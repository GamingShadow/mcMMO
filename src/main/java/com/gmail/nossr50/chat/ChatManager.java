package com.gmail.nossr50.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.events.chat.McMMOAdminChatEvent;
import com.gmail.nossr50.events.chat.McMMOPartyChatEvent;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.party.Party;
import com.gmail.nossr50.util.Permissions;

public final class ChatManager {
    public ChatManager () {}

    public static void handleAdminChat(Plugin plugin, String playerName, String displayName, String message) {
        McMMOAdminChatEvent chatEvent = new McMMOAdminChatEvent(plugin, playerName, displayName, message);
        mcMMO.p.getServer().getPluginManager().callEvent(chatEvent);

        if (chatEvent.isCancelled()) {
            return;
        }

        if(Config.getInstance().getAdminDisplayNames())
            displayName = chatEvent.getDisplayName();
        else
            displayName = chatEvent.getSender();

        String adminMessage = chatEvent.getMessage();

        for (Player otherPlayer : mcMMO.p.getServer().getOnlinePlayers()) {
            if (Permissions.adminChat(otherPlayer) || otherPlayer.isOp()) {
                otherPlayer.sendMessage(LocaleLoader.getString("Commands.AdminChat.Prefix", displayName) + adminMessage);
            }
        }

        displayName = ChatColor.stripColor(displayName);
        mcMMO.p.getLogger().info("[A]<" + displayName + "> " + adminMessage);
    }

    public static void handlePartyChat(Plugin plugin, Party party, String playerName, String displayName, String message) {
        String partyName = party.getName();

        McMMOPartyChatEvent chatEvent = new McMMOPartyChatEvent(plugin, playerName, displayName, partyName, message);
        mcMMO.p.getServer().getPluginManager().callEvent(chatEvent);

        if (chatEvent.isCancelled()) {
            return;
        }

        if(Config.getInstance().getPartyDisplayNames())
            displayName = chatEvent.getDisplayName();
        else
            displayName = chatEvent.getSender();

        String partyMessage = chatEvent.getMessage();

        for (Player member : party.getOnlineMembers()) {
            member.sendMessage(LocaleLoader.getString("Commands.Party.Chat.Prefix", displayName) + partyMessage);
        }

        displayName = ChatColor.stripColor(displayName);
        mcMMO.p.getLogger().info("[P](" + partyName + ")" + "<" + displayName + "> " + partyMessage);
    }
}
