package com.frahhs.robbing.item.items;

import com.frahhs.robbing.Robbing;
import com.frahhs.robbing.item.RobbingItem;
import com.frahhs.robbing.item.RobbingMaterial;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

public class PanelNumberCancel extends RobbingItem {
    public PanelNumberCancel(Robbing plugin) {
        super(plugin);
    }

    @Override
    public ShapedRecipe getShapedRecipe() {
        return null;
    }

    @Override
    public @NotNull NamespacedKey getNamespacedKey() {
        return new NamespacedKey(plugin, "PanelNumberCancel");
    }

    @Override
    public @NotNull RobbingMaterial getRobbingMaterial() {
        return RobbingMaterial.PANEL_NUMBER_CANCEL;
    }

    @Override
    public boolean isCraftable() {
        return false;
    }

    @Override
    public @NotNull Material getVanillaMaterial() {
        return Material.STICK;
    }

    @Override
    public int getCustomModelData() {
        return 5470;
    }
}