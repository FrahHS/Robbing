package com.frahhs.robbing.feature.kidnapping.controllers;

import com.frahhs.robbing.feature.BaseController;
import com.frahhs.robbing.feature.kidnapping.models.Kidnapping;
import org.bukkit.entity.Player;

/**
 * Controller class for managing kidnapping operations.
 */
public class KidnappingController extends BaseController {
    /**
     * Initiates a kidnapping.
     *
     * @param kidnapper The player who is the kidnapper.
     * @param kidnapped The player who is kidnapped.
     */
    public void kidnap(Player kidnapper, Player kidnapped) {
        Kidnapping kidnapping = new Kidnapping(kidnapper, kidnapped);
        kidnapping.kidnap();
    }

    /**
     * Frees a kidnapped player.
     *
     * @param kidnapped The player who is kidnapped.
     */
    public void free(Player kidnapped) {
        if(!Kidnapping.isKidnapped(kidnapped))
            return;

        Kidnapping.getFromKidnapped(kidnapped).free();
    }

    /**
     * Retrieves the kidnapper of a kidnapped player.
     *
     * @param kidnapped The player who is kidnapped.
     * @return The player who is the kidnapper, or null if not found.
     */
    public Player getKidnapper(Player kidnapped) {
        if(!Kidnapping.isKidnapped(kidnapped))
            return null;

        return Kidnapping.getFromKidnapped(kidnapped).getKidnapper();
    }

    /**
     * Retrieves the kidnapped player of a kidnapper.
     *
     * @param kidnapper The player who is the kidnapper.
     * @return The player who is kidnapped, or null if not found.
     */
    public Player getKidnapped(Player kidnapper) {
        if(!Kidnapping.isKidnapper(kidnapper))
            return null;

        return Kidnapping.getFromKidnapper(kidnapper).getKidnapped();
    }
}
