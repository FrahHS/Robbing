package com.frahhs.robbing.features.rob.controllers;

import com.frahhs.robbing.Robbing;
import com.frahhs.robbing.features.BaseController;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing caught players.
 */
public class CatchController extends BaseController {
    /** List to store caught players. */
    public static List<Player> caughtList = new ArrayList<>();

    /**
     * Handles catching a robber.
     * TODO: add cooldown on the action bar
     *
     * @param robber The robber who was caught.
     * @param robbed The player who caught the robber.
     */
    public void catchRobber(Player robber, Player robbed) {
        RobController robController = new RobController();
        robController.stopRobbing(robber);

        int caught_robber_time = config.getInt("rob.caught_robber.time");
        int caught_robber_slow_power = config.getInt("rob.caught_robber.slow_power");
        robber.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * caught_robber_time, caught_robber_slow_power));

        // Send message
        robber.sendMessage(messages.getMessage("robbing.caught_robber").replace("{player}", robbed.getDisplayName()));
        robbed.sendMessage(messages.getMessage("robbing.to_catcher").replace("{player}", robber.getDisplayName()));

        // Add to the caught list for the configured time
        new Thread(() -> {
            try {
                addCaught(robber);
                Thread.sleep(caught_robber_time * 1000L);
                removeCaught(robber);
            } catch(InterruptedException v) {
                Robbing.getInstance().getRBLogger().error("Unexpected error, send the following stacktrace to our staff: https://discord.gg/Hh9zMQnWvW.");
                Robbing.getInstance().getRBLogger().error(v.toString());
            }
        }).start();
    }

    /**
     * Adds a player to the list of caught players.
     *
     * @param player The player to add.
     */
    public void addCaught(Player player) {
        caughtList.add(player);
    }

    /**
     * Removes a player from the list of caught players.
     *
     * @param player The player to remove.
     */
    public void removeCaught(Player player) {
        caughtList.remove(player);
    }

    /**
     * Checks if a player is in the list of caught players.
     *
     * @param player The player to check.
     * @return True if the player is caught, false otherwise.
     */
    public boolean isCaught(Player player) {
        return caughtList.contains(player);
    }
}
