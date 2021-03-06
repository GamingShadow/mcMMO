package com.gmail.nossr50.skills.mining;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.events.fake.FakePlayerAnimationEvent;
import com.gmail.nossr50.mods.ModChecks;
import com.gmail.nossr50.skills.utilities.SkillTools;
import com.gmail.nossr50.util.Misc;

public class SuperBreakerEventHandler {
    private MiningManager manager;
    private Block block;
    private Material blockType;
    private boolean customBlock;
    private ItemStack heldItem;
    private int tier;
    private int durabilityLoss;
    private FakePlayerAnimationEvent armswing;

    protected SuperBreakerEventHandler (MiningManager manager, Block block) {
        this.manager = manager;
        this.block = block;
        this.blockType = block.getType();
        this.customBlock = ModChecks.isCustomMiningBlock(block);
        Player player = manager.getMcMMOPlayer().getPlayer();
        this.heldItem = player.getItemInHand();
        this.tier = Misc.getTier(heldItem);
        this.armswing = new FakePlayerAnimationEvent(player);

        calculateDurabilityLoss();
    }

    protected void callFakeArmswing() {
        mcMMO.p.getServer().getPluginManager().callEvent(armswing);
    }

    protected void processDurabilityLoss() {
        SkillTools.abilityDurabilityLoss(heldItem, durabilityLoss);
    }

    protected void processDropsAndXP() {
        manager.miningBlockCheck(block);
    }

    protected void playSound() {
        manager.getMcMMOPlayer().getPlayer().playSound(block.getLocation(), Sound.ITEM_PICKUP, Misc.POP_VOLUME, Misc.POP_PITCH);
    }

    /**
     * Check for the proper tier of item for use with Super Breaker.
     *
     * @return True if the item is the required tier or higher, false otherwise
     */
    protected boolean tierCheck() {
        if (customBlock) {
            if (ModChecks.getCustomBlock(block).getTier() < tier) {
                return false;
            }

            return true;
        }

        switch (blockType) {
        case OBSIDIAN:
            if (tier < Mining.DIAMOND_TOOL_TIER) {
                return false;
            }
            /* FALL THROUGH */

        case DIAMOND_ORE:
        case GLOWING_REDSTONE_ORE:
        case GOLD_ORE:
        case LAPIS_ORE:
        case REDSTONE_ORE:
        case EMERALD_ORE:
            if (tier < Mining.IRON_TOOL_TIER) {
                return false;
            }
            /* FALL THROUGH */

        case IRON_ORE:
            if (tier < Mining.STONE_TOOL_TIER) {
                return false;
            }
            /* FALL THROUGH */

        case COAL_ORE:
        case ENDER_STONE:
        case GLOWSTONE:
        case MOSSY_COBBLESTONE:
        case NETHERRACK:
        case SANDSTONE:
        case STONE:
            return true;

        default:
            return false;
        }
    }

    private void calculateDurabilityLoss() {
        this.durabilityLoss = Misc.toolDurabilityLoss;

        if (blockType.equals(Material.OBSIDIAN)) {
            durabilityLoss = durabilityLoss * 5;
        }
    }
}
