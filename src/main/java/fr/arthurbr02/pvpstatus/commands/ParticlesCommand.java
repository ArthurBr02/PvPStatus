package fr.arthurbr02.pvpstatus.commands;

import fr.arthurbr02.pvpstatus.status.StatusManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ParticlesCommand implements CommandExecutor {
    private final static String[] COMMANDS = { "on", "off" };
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;

        if (strings.length > 1 || strings.length == 0) {
            commandSender.sendMessage("Commande incorrecte: " + command.getUsage());
            return true;
        }

        if (!Arrays.asList(COMMANDS).contains(strings[0].toLowerCase())) {
            commandSender.sendMessage("Commande incorrecte: " + command.getUsage());
            return true;
        }

        switch (strings[0].toLowerCase()) {
            case "on":
                StatusManager.setParticleStatus(((Player) commandSender).getUniqueId().toString(), true);
                commandSender.sendMessage("Les particules sont maintenant activées.");
                break;
            case "off":
                StatusManager.setParticleStatus(((Player) commandSender).getUniqueId().toString(), false);
                commandSender.sendMessage("Les particules sont maintenant désactivées.");
                break;
        }

        return true;
    }
}
