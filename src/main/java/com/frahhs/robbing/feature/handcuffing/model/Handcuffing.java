package com.frahhs.robbing.feature.handcuffing.model;

import com.frahhs.robbing.Robbing;
import com.frahhs.robbing.feature.Model;
import com.frahhs.robbing.feature.handcuffing.provider.HandcuffingProvider;
import com.frahhs.robbing.provider.ConfigProvider;
import com.frahhs.robbing.util.Cooldown;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

/**
 * Model class representing data and operations related to handcuffing.
 */
public class Handcuffing extends Model {
    private final Player handcuffer;
    private final Player handcuffed;

    private final HandcuffingProvider provider;

    /**
     * Constructs a Handcuffing instance.
     *
     * @param handcuffer The player who handcuffed.
     * @param handcuffed The player who is handcuffed.
     */
    public Handcuffing(Player handcuffer, Player handcuffed) {
        this.handcuffed = handcuffed;
        this.handcuffer = handcuffer;
        this.provider = new HandcuffingProvider();
    }

    /**
     * Retrieves the handcuffer.
     *
     * @return The player who applied the handcuffs.
     */
    public Player getHandcuffer() {
        return handcuffer;
    }

    /**
     * Retrieves the handcuffed player.
     *
     * @return The player who is handcuffed.
     */
    public Player getHandcuffed() {
        return handcuffed;
    }

    /**
     * Retrieves the timestamp of the handcuffing event.
     *
     * @return The timestamp of the handcuffing event.
     */
    public Timestamp getTimestamp() {
        return provider.getTimestamp(handcuffed);
    }

    /**
     * Saves the handcuffing event to the database.
     */
    public void save() {
        provider.saveHandcuffing(handcuffer, handcuffed);
    }

    /**
     * Removes the handcuffing event from the database.
     */
    public void remove() {
        provider.deleteHandcuffing(handcuffed);
    }

    /**
     * Checks if a player is currently handcuffed.
     *
     * @param handcuffed The player to check.
     * @return True if the player is handcuffed, false otherwise.
     */
    public static boolean isHandcuffed(Player handcuffed) {
        HandcuffingProvider provider = new HandcuffingProvider();
        return provider.isHandcuffed(handcuffed);
    }

    /**
     * Retrieves a Handcuffing instance based on the handcuffed player.
     *
     * @param handcuffed The handcuffed player.
     * @return The Handcuffing instance, or null if not found.
     */
    public static Handcuffing getFromHandcuffed(Player handcuffed) {
        HandcuffingProvider provider = new HandcuffingProvider();
        return provider.getHandcuffing(handcuffed);
    }

    /**
     * Checks if a player is currently under a handcuffing cooldown.
     *
     * @param handcuffer The player to check.
     * @return True if the player is under cooldown, otherwise false.
     */
    public static boolean haveCooldown(Player handcuffer) {
        HandcuffingProvider provider = new HandcuffingProvider();
        return provider.haveCooldown(handcuffer);
    }

    /**
     * Retrieves the cooldown timestamp for a player.
     *
     * @param handcuffer The player to check.
     * @return The cooldown object containing the timestamp and duration.
     */
    public static Cooldown getCooldown(Player handcuffer) {
        HandcuffingProvider provider = new HandcuffingProvider();
        return provider.getCooldown(handcuffer);
    }

    /**
     * Sets the cooldown for the handcuffing action.
     *
     * @param handcuffer The player who applied the handcuffs.
     * @param time       The duration of the cooldown in seconds.
     */
    public static void setCooldown(Player handcuffer, int time) {
        HandcuffingProvider provider = new HandcuffingProvider();

        new Thread(() -> {
            try {
                Cooldown cooldown = new Cooldown(System.currentTimeMillis(), time);
                provider.saveCooldown(handcuffer, cooldown);
                Thread.sleep(time * 1000L);
                provider.removeCooldown(handcuffer);
            } catch (InterruptedException v) {
                System.out.println(v.getMessage());
            }
        }).start();
    }

    /**
     * Sets the default cooldown for the handcuffing action.
     *
     * @param handcuffer The player who applied the handcuffs.
     */
    public static void setCooldown(Player handcuffer) {
        ConfigProvider config = Robbing.getInstance().getConfigProvider();
        setCooldown(handcuffer, config.getInt("handcuffing.cooldown"));
    }
}