package com.gmail.nossr50.skills.mining;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.datatypes.McMMOPlayer;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.utilities.AbilityType;
import com.gmail.nossr50.skills.utilities.SkillTools;
import com.gmail.nossr50.util.Misc;

public class RemoteDetonationEventHandler {
    private MiningManager manager;
    private PlayerInteractEvent event;
    private Block block;
    private HashSet<Byte> transparentBlocks = new HashSet<Byte>();

    public RemoteDetonationEventHandler(MiningManager manager, PlayerInteractEvent event) {
        this.manager = manager;
        this.event = event;
        this.block = event.getClickedBlock();
    }

    protected void targetTNT() {
        if (block == null || block.getType() != Material.TNT) {
            generateTransparentBlockList();
            block = manager.getMcMMOPlayer().getPlayer().getTargetBlock(transparentBlocks, BlastMining.MAXIMUM_REMOTE_DETONATION_DISTANCE);
        }
        else {
            event.setCancelled(true); // This is the only way I know to avoid the original TNT to be triggered (in case the player is close to it)
        }
    }

    protected boolean cooldownOver() {
        McMMOPlayer mcMMOPlayer = manager.getMcMMOPlayer();
        Player player = mcMMOPlayer.getPlayer();
        PlayerProfile profile = mcMMOPlayer.getProfile();

        if (!SkillTools.cooldownOver(profile.getSkillDATS(AbilityType.BLAST_MINING) * Misc.TIME_CONVERSION_FACTOR, AbilityType.BLAST_MINING.getCooldown(), player)) {
            player.sendMessage(LocaleLoader.getString("Skills.TooTired", SkillTools.calculateTimeLeft(profile.getSkillDATS(AbilityType.BLAST_MINING) * Misc.TIME_CONVERSION_FACTOR, AbilityType.BLAST_MINING.getCooldown(), player)));

            return false;
        }

        return true;
    }

    protected void sendMessages() {
        Player player = manager.getMcMMOPlayer().getPlayer();

        Misc.sendSkillMessage(player, AbilityType.BLAST_MINING.getAbilityPlayer(player));
        player.sendMessage(LocaleLoader.getString("Mining.Blast.Boom"));
    }

    protected void handleDetonation() {
        Player player = manager.getMcMMOPlayer().getPlayer();
        TNTPrimed tnt = player.getWorld().spawn(block.getLocation(), TNTPrimed.class);

        mcMMO.p.addToTNTTracker(tnt.getEntityId(), player.getName());
        tnt.setFuseTicks(0);
        block.setType(Material.AIR);
    }

    protected void setProfileData() {
        PlayerProfile profile = manager.getMcMMOPlayer().getProfile();

        profile.setSkillDATS(AbilityType.BLAST_MINING, System.currentTimeMillis());
        profile.setAbilityInformed(AbilityType.BLAST_MINING, false);
    }

    private void generateTransparentBlockList() {
        for (Material material : Material.values()) {
            if (material.isTransparent()) {
                transparentBlocks.add((byte) material.getId());
            }
        }
    }

    protected Block getBlock() {
        return block;
    }
}
