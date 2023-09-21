package fr.arthurbr02.pvpstatus;

import com.google.common.collect.Lists;
import fr.arthurbr02.pvpstatus.commands.ChangeStatusCommand;
import fr.arthurbr02.pvpstatus.listeners.PvPStatusListener;
import fr.arthurbr02.pvpstatus.status.StatusManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class PvPStatus extends JavaPlugin {

    private int task = 0;

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    @Override
    public void onEnable() {
        this.saveConfig();

        getServer().getPluginManager().registerEvents(new PvPStatusListener(this), this);
        this.getCommand("tagMode").setExecutor(new ChangeStatusCommand(this));

        List<Player> players = Lists.newArrayList(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            if (this.getConfig().getConfigurationSection(player.getUniqueId().toString()) == null) {
                this.getConfig().createSection(player.getUniqueId().toString());
                this.getConfig().set(player.getUniqueId().toString() + ".pvpStatus", "neutral");
                this.saveConfig();

            }

            StatusManager.createArmorStandForPlayer(player);

        }

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            List<Player> playersTmp = Lists.newArrayList(Bukkit.getOnlinePlayers());
            for (Player player : playersTmp) {
                ArmorStand armorStand = StatusManager.getPlayerArmorStandMap().get(player);
                Location location = player.getLocation();
                armorStand.teleport(new Location(location.getWorld(), location.getX(), location.getY() + 2.2, location.getZ()));
            }
        }, 1L, 0L);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(task);

        List<Player> players = Lists.newArrayList(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            StatusManager.removeArmorStandForPlayer(player);
        }
    }

    public static PvPStatus getInstance() {
        return getPlugin(PvPStatus.class);
    }
}
