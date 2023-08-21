package ua.lich.coding.whispermode.commands;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ua.lich.coding.whispermode.WhisperMode;


public class Whisper implements CommandExecutor {

    private final WhisperMode plugin;

    public Whisper(WhisperMode plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) commandSender;
            if(args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    console.sendMessage("Config reloaded.");
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }


        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            User user = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(player);
            String prefix = user.getCachedData().getMetaData().getPrefix();
            String colored_prefix = "&f";
            if(prefix != null) {
                colored_prefix = ChatColor.translateAlternateColorCodes('&', prefix);
            }

            if (args.length == 0) {
                usage_message(player);
            }
             else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (args.length == 1) {
                    if (target == null) {
                        player.sendMessage(getStringFromConfig("player-no-exist"));
                    } else {
                        usage_message(player);
                    }
                    return true;
                }
                    if (!(target == null)) {



                        //StringBuilder
                        StringBuilder builder = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            builder.append(args[i]).append(" ");
                        }

                        //Ready message
                        String message = builder.toString();

                        //OutgoingMessage
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', getStringFromConfig("outgoing-message")) + colored_prefix + ChatColor.translateAlternateColorCodes('&', getStringFromConfig("player-name-color")) + target.getName() + ChatColor.GOLD + ": " + message);

                        //IncomingMessage
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', getStringFromConfig("incoming-message")) + colored_prefix + ChatColor.translateAlternateColorCodes('&', getStringFromConfig("player-name-color")) + player.getName() + ChatColor.GOLD + ": " + message);
                        target.playSound(target.getLocation(), Sound.ENTITY_WARDEN_TENDRIL_CLICKS, SoundCategory.PLAYERS, 5.0f, 1.0f);

                        } else {
                        player.sendMessage(getStringFromConfig("player-no-exist"));
                    }


                    }
                }


        return true;
    }

    private void usage_message(Player player) {
        player.sendMessage(getStringFromConfig("usage-command"));
    }

    private String getStringFromConfig(String path) {
        String path_config = plugin.getConfig().getString(path);
        if(path_config != null) {
            return ChatColor.translateAlternateColorCodes('&', path_config);
        } else {
            return "Wrong path to String in Plugin.yml";
        }
    }
    private void reloadConfig(CommandSender sender) {
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "Конфигурация перезагружена.");
    }

}
