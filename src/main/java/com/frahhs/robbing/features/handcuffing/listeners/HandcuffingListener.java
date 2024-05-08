package com.frahhs.robbing.features.handcuffing.listeners;

import com.frahhs.robbing.features.BaseListener;
import com.frahhs.robbing.features.handcuffing.controllers.HandcuffingController;
import com.frahhs.robbing.features.handcuffing.models.CooldownModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HandcuffingListener extends BaseListener {
    private final HandcuffingController handcuffingController = new HandcuffingController();

    @EventHandler
    public void onIteract(PlayerInteractEntityEvent e) {
        Player handcuffed;
        Player handcuffer = e.getPlayer();

        String message;

        // Check if handcuffs are enabled
        if(!config.getBoolean("handcuffing.enabled"))
            return;

        // Check if handcuffs target is a player
        if(!(e.getRightClicked() instanceof Player))
            return;

        handcuffed = (Player) e.getRightClicked();

        // Check if the player is using cuffs
        if(!handcuffingController.isUsingHandcuffs(handcuffer))
            return;

        // Check if player is using main hand
        if (!e.getHand().equals(EquipmentSlot.HAND))
            return;

        // Check if player have permissions
        if(! handcuffer.hasPermission("robbing.cuff") ) {
            message = messages.getMessage("general.no_permission_item");
            handcuffer.sendMessage(message);
            return;
        }

        // Check if target have permissions to not get handcuffed
        if(handcuffed.hasPermission("robbing.notcuffable")) {
            message = messages.getMessage("handcuffing.not_cuffable");
            handcuffer.sendMessage(message);
            return;
        }

        // Check if the target is already handcuffed, soo remove handcuffs
        if(handcuffingController.isHandcuffed(handcuffed)) {
            handcuffingController.removeHandcuffs(handcuffed);
            return;
        }

        // Check if player have handcuffing cooldown
        if(handcuffingController.haveCooldown(handcuffer)) {
            int handcuffing_cooldown = config.getInt("handcuffing.cooldown");

            CooldownModel cooldown = handcuffingController.getCooldown(handcuffer);
            long waitingTime = cooldown.getCooldown() - ((System.currentTimeMillis() - cooldown.getTimestamp()) / 1000 );
            message = messages.getMessage("general.cooldown").replace("{time}", Long.toString(waitingTime));
            handcuffer.sendMessage(message);
            return;
        }

        // check if is enabled handcuffing escaping
        if(!config.getBoolean("handcuffing.escape.enabled")) {
            // Handcuff the player
            handcuffingController.putHandcuffs(handcuffer, handcuffed);
            return;
        }

        // Handcuffing escaping mechanic
        handcuffingController.escape(handcuffer, handcuffed);

    }

    @EventHandler
    public void onLeash(PlayerLeashEntityEvent e) {
        // Remove vanilla leash events to cuffs
        if(handcuffingController.isUsingHandcuffs(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
