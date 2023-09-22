package fr.arthurbr02.pvpstatus.status;

import fr.arthurbr02.pvpstatus.PvPStatus;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import java.util.HashMap;
import java.util.Map;

public class StatusManager {
    public static final Status NEUTRAL = new Status("neutral", Color.YELLOW, "Neutre");
    public static final Status PASSIVE = new Status("passive", Color.GREEN, "Passif");
    public static final Status AGGRESSIVE = new Status("aggressive", Color.RED, "Agressif");

    private static Map<Player, ArmorStand> playerArmorStandMap = new HashMap<>();

    private PvPStatus main;

    public StatusManager(PvPStatus main) {
        this.main = main;
    }

    public static Status getStatus(String status) {
        switch (status) {
            case "neutral":
                return NEUTRAL;
            case "passive":
                return PASSIVE;
            case "aggressive":
                return AGGRESSIVE;
            default:
                return null;
        }
    }

    public static Status getStatus(ChatColor color) {
        switch (color) {
            case YELLOW:
                return NEUTRAL;
            case DARK_GREEN:
                return PASSIVE;
            case DARK_RED:
                return AGGRESSIVE;
            default:
                return null;
        }
    }

    public void setPlayerStatus(String uuid, Status status) {
        main.getConfig().getConfigurationSection(uuid).set( ".pvpStatus", status.getStatus());
        main.saveConfig();

    }

    public static Status getPlayerStatus(String uuid) {
        return getStatus(PvPStatus.getInstance().getConfig().getConfigurationSection(uuid).getString(".pvpStatus"));
    }

    public static Map<Player, ArmorStand> getPlayerArmorStandMap() {
        return playerArmorStandMap;
    }

    public static void createArmorStandForPlayer(Player player) {
        Status status = getPlayerStatus(player.getUniqueId().toString());
        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(status.getColor() + status.getLabel());
        armorStand.setInvulnerable(true);
        armorStand.setSilent(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setArms(false);
        armorStand.setBasePlate(false);
        armorStand.setCollidable(false);
        armorStand.setRemoveWhenFarAway(false);
        armorStand.setBasePlate(false);
        armorStand.setMarker(true);
        armorStand.setBodyPose(new EulerAngle(185, 0, 0));
        armorStand.setLeftLegPose(new EulerAngle(180, 0, 0));
        armorStand.setRightLegPose(new EulerAngle(180, 0, 0));
//        player.addPassenger(armorStand);
        playerArmorStandMap.put(player, armorStand);
    }

    public static void removeArmorStandForPlayer(Player player) {
        playerArmorStandMap.get(player).remove();
        playerArmorStandMap.remove(player);
    }
}
