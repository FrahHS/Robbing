package com.frahhs.robbing.feature.kidnapping;

import com.frahhs.robbing.Robbing;
import com.frahhs.robbing.feature.Feature;
import com.frahhs.robbing.feature.kidnapping.bag.KidnappingBag;
import com.frahhs.robbing.feature.kidnapping.listeners.KidnappingListener;

public class KidnappingFeature extends Feature {
    private final Robbing plugin;

    public KidnappingFeature(Robbing plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new KidnappingListener(), plugin);
    }

    @Override
    protected void registerBags() {
        plugin.getBagManager().registerBags(new KidnappingBag());
    }

    @Override
    protected String getID() {
        return "kidnapping";
    }
}
