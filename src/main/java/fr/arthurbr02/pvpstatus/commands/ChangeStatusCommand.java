package fr.arthurbr02.pvpstatus.commands;

import fr.arthurbr02.pvpstatus.PvPStatus;
import fr.arthurbr02.pvpstatus.status.Status;
import fr.arthurbr02.pvpstatus.status.StatusManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.List;

public class ChangeStatusCommand implements CommandExecutor {

    private final PvPStatus main;
    public ChangeStatusCommand(PvPStatus main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) { // If the sender is not a player
            sender.sendMessage("You must be a player to use this command");
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 1) {
            player.sendMessage("Commande incorrecte: " + command.getUsage());
            return true;

        }

        if (args.length == 0) {
            player.sendMessage("Votre statut PvP est le suivant: " + main.getConfig().getString(player.getUniqueId() + ".pvpStatus"));
            return true;

        } else {
            switch (args[0]) {
                case "neutre":
                    main.getConfig().getConfigurationSection(player.getUniqueId().toString()).set(".pvpStatus", "neutral");
                    main.saveConfig();
                    player.sendMessage("Votre statut PvP est maintenant neutre.\nVous infligerez 5% des dégâts totaux aux joueurs passifs.");
                    break;
                case "passif":
                    main.getConfig().getConfigurationSection(player.getUniqueId().toString()).set(".pvpStatus", "passive");
                    main.saveConfig();
                    player.sendMessage("Votre statut PvP est maintenant passif.\nVous infligerez 5% des dégâts totaux aux joueurs.\nVous subirez 5% des dégâts infligés par les autres joueurs.");
                    break;
                case "agressif":
                    main.getConfig().getConfigurationSection(player.getUniqueId().toString()).set(".pvpStatus", "aggressive");
                    main.saveConfig();
                    player.sendMessage("Votre statut PvP est maintenant agressif.\nVous infligerez 5% des dégâts totaux aux joueurs passifs.");
                    break;
                default:
                    player.sendMessage("Commande incorrecte: " + command.getUsage());
                    return true;
            }

        }

        return true;
    }

}
