package ua.lich.coding.whispermode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WhisperCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> player_names = new ArrayList<>();


        if(args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player_names.add(player.getName());
            }
            Collections.sort(player_names);
            return player_names;
        }
        if (args.length == 2) {
            return Arrays.asList(ChatColor.YELLOW + "message");
        }
        return new ArrayList<>();
    }
}
