package fr.arthurbr02.pvpstatus;

import com.google.common.collect.Lists;
import fr.arthurbr02.pvpstatus.commands.ChangeStatusCommand;
import fr.arthurbr02.pvpstatus.commands.ChangeStatusCompleter;
import fr.arthurbr02.pvpstatus.commands.ParticlesCommand;
import fr.arthurbr02.pvpstatus.commands.ParticlesCompleter;
import fr.arthurbr02.pvpstatus.listeners.PvPStatusListener;
import fr.arthurbr02.pvpstatus.status.Status;
import fr.arthurbr02.pvpstatus.status.StatusManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
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
        getCommand("tagMode").setExecutor(new ChangeStatusCommand(this));
        getCommand("tagMode").setTabCompleter(new ChangeStatusCompleter());
        getCommand("particles").setExecutor(new ParticlesCommand());
        getCommand("particles").setTabCompleter(new ParticlesCompleter());

        List<Player> players = Lists.newArrayList(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            if (this.getConfig().getConfigurationSection(player.getUniqueId().toString()) == null) {
                this.getConfig().createSection(player.getUniqueId().toString());
                this.getConfig().set(player.getUniqueId().toString() + ".pvpStatus", "neutral");
                StatusManager.setParticleStatus(player.getUniqueId().toString(), true);
                this.saveConfig();

            }

        }

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            List<Player> playersTmp = Lists.newArrayList(Bukkit.getOnlinePlayers());
            for (Player player : playersTmp) {
                Status status = StatusManager.getPlayerStatus(player.getUniqueId().toString());
                if (player.isDead() || !StatusManager.getParticleStatus(player.getUniqueId().toString())) continue;

                Particle.DustOptions dustOptions = new Particle.DustOptions(status.getColor(), 0.5F);
                player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(0, 2.8, 0), 25, dustOptions);

            }

        }, 0L, 0L);

    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(task);

    }

    public static PvPStatus getInstance() {
        return getPlugin(PvPStatus.class);
    }
}
