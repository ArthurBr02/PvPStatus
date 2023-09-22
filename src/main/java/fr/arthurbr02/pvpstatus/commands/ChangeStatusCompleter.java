package fr.arthurbr02.pvpstatus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChangeStatusCompleter implements TabCompleter {
    private static final String[] COMMANDS = { "passif", "neutre", "agressif" };
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(strings[0], Arrays.asList(COMMANDS), completions);
        Collections.sort(completions);
        return completions;
    }
}
