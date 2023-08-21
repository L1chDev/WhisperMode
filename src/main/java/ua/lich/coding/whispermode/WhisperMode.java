package ua.lich.coding.whispermode;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ua.lich.coding.whispermode.commands.Whisper;
import ua.lich.coding.whispermode.commands.WhisperCompleter;

public final class WhisperMode extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
        }



        setConfig();
        registerCommands();
    }

    private void registerCommands() {
        getCommand("whisper").setExecutor(new Whisper(this));
        getCommand("whisper").setTabCompleter(new WhisperCompleter());
    }
    private void setConfig() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
}
