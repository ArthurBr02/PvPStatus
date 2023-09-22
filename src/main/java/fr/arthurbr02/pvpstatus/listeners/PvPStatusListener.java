package fr.arthurbr02.pvpstatus.listeners;

import fr.arthurbr02.pvpstatus.PvPStatus;
import fr.arthurbr02.pvpstatus.status.Status;
import fr.arthurbr02.pvpstatus.status.StatusManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PvPStatusListener implements Listener {

    private final PvPStatus main;
    public PvPStatusListener(PvPStatus main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (main.getConfig().getConfigurationSection(player.getUniqueId().toString()) == null) {
            main.getConfig().createSection(player.getUniqueId().toString());
            main.getConfig().set(player.getUniqueId().toString() + ".pvpStatus", "neutral");
            main.saveConfig();

        }

    }

    @EventHandler
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (player.equals(damager)) {
            return;
        }

        Status playerStatus = StatusManager.getPlayerStatus(player.getUniqueId().toString());
        Status damagerStatus = StatusManager.getPlayerStatus(damager.getUniqueId().toString());

        if ((playerStatus == StatusManager.NEUTRAL || playerStatus == StatusManager.AGGRESSIVE) && (damagerStatus == StatusManager.NEUTRAL || damagerStatus == StatusManager.AGGRESSIVE)) {
            return;
        }

        if (playerStatus == StatusManager.PASSIVE || damagerStatus == StatusManager.PASSIVE) {
            event.setDamage(event.getDamage() * 0.05);

            if (player.getHealth() < event.getDamage()) {
                player.setHealth(event.getDamage() * 2);

            }
        }

    }

}
