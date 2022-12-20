package me.taison.wizardpractice.addons.impl;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.utilities.chat.StringUtils;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.Collections;
import java.util.Objects;

public class MagicChest extends BukkitRunnable {

    private ArmorStand armorStandItem;

    private final ItemStack item;

    private final double probability;

    private final String message;

    private boolean up;
    private int rotate = 0;

    public MagicChest(ItemStack item, double probability, String message){
        this.item = item;

        this.probability = probability;

        this.message = message;

        Objects.requireNonNull(Bukkit.getWorld("world")).getEntities().forEach(entity -> {
            if(entity.getType() == EntityType.ARMOR_STAND){
                entity.remove();
            }
        });

        this.initializeArmorStand(new Location(Bukkit.getWorld("world"), -8.5, 124, -52.5));

        this.runTaskTimer(WizardPractice.getSingleton(), 20, 1);
    }

    @Override
    public void run() {
        this.rotateArmorStand();
    }

    private void rotateArmorStand() {
        EulerAngle headPose;

        if (up) {
            if (rotate >= 540) {
                up = false;
            }
            if (rotate > 500) {
                headPose = new EulerAngle(0, Math.toRadians(rotate += 1), 0);
            } else if (rotate > 470) {
                headPose = new EulerAngle(0, Math.toRadians(rotate += 2), 0);
            } else if (rotate > 450) {
                headPose = new EulerAngle(0, Math.toRadians(rotate += 3), 0);
            } else {
                headPose = new EulerAngle(0, Math.toRadians(rotate += 4), 0);
            }
        } else {
            if (rotate <= 0) {
                up = true;
            }
            if (rotate > 120) {
                headPose = new EulerAngle(0, Math.toRadians(rotate -= 4), 0);
            } else if (rotate > 90) {
                headPose = new EulerAngle(0, Math.toRadians(rotate -= 3), 0);
            } else if (rotate > 70) {
                headPose = new EulerAngle(0, Math.toRadians(rotate -= 2), 0);
            } else {
                headPose = new EulerAngle(0, Math.toRadians(rotate -= 1), 0);
            }

        }
        armorStandItem.setHeadPose(headPose);
    }

    private void initializeArmorStand(Location location){
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);

        armorStand.setRemoveWhenFarAway(false);
        armorStand.setVisible(false);
        armorStand.setCanPickupItems(false);
        armorStand.setArms(false);
        armorStand.setBasePlate(false);
        armorStand.setMarker(true);

        armorStand.setHelmet(new ItemStack(Material.CHEST));
        this.armorStandItem = armorStand;

        WizardPractice.getSingleton().getHologramFactory().addHologram("test",  location.add(0, 2.25, 0));
        WizardPractice.getSingleton().getHologramFactory().updateHologram("test", Collections.singletonList(StringUtils.color("&aBardzo magiczna skrzynka!")));
    }



    public ItemStack getItem() {
        return item;
    }

    public double getProbability() {
        return probability;
    }

    public String getMessage() {
        return message;
    }

}
