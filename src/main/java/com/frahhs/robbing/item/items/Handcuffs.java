package com.frahhs.robbing.item.items;

import com.frahhs.robbing.Robbing;
import com.frahhs.robbing.item.RobbingItem;
import com.frahhs.robbing.item.RobbingMaterial;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

public class Handcuffs extends RobbingItem {
    public Handcuffs() {
        super();
    }

    @Override
    public ShapedRecipe getShapedRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(getNamespacedKey(), getItemStack());

        shapedRecipe.shape("   ", "BIB", "   ");
        shapedRecipe.setIngredient('I', Material.IRON_INGOT);
        shapedRecipe.setIngredient('B', Material.IRON_BLOCK);

        return shapedRecipe;
    }

    @Override
    public @NotNull NamespacedKey getNamespacedKey() {
        return new NamespacedKey(Robbing.getInstance(), "Handcuffs");
    }

    @Override
    public @NotNull String getItemName() {
        return "handcuffs";
    }

    @Override
    public @NotNull RobbingMaterial getRBMaterial() {
        return RobbingMaterial.HANDCUFFS;
    }

    @Override
    public boolean isCraftable() {
        return configProvider.getBoolean("handcuffing.enable_crafting");
    }

    @Override
    public @NotNull Material getVanillaMaterial() {
        return Material.STICK;
    }

    @Override
    public int getCustomModelData() {
        return 5456;
    }
}