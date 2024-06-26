package com.frahhs.robbing.dependencies;

import com.frahhs.robbing.dependencies.worldguard.WorldGuardManager;
import org.bukkit.Bukkit;

public class DependenciesManager {
    public void init() {
        if(haveDependency(Dependency.WORLDGUARD)) {
            WorldGuardManager.registerFlags();
        }
    }

    public static boolean haveDependency(Dependency dependency) {
        switch (dependency) {
            case WORLDGUARD:
                return Bukkit.getPluginManager().getPlugin("WorldGuard") != null;
        }

        throw new RuntimeException("Flag not handled!");
    }
}
