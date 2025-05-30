package henrycmoss.tuah;

import henrycmoss.tuah.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Tuah extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        registerCommands();
        this.getCommand("test").setExecutor(new TestCommand());
        this.getLogger().info("FUCKING NIGGERS");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.getLogger().info("bye nigga");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("die")) {
            if(sender instanceof Player player) {
                player.setHealth(0.0d);
                player.sendMessage("swiss cheesed");
            }
        }

        return true;
    }

    private void registerCommands() {
        List<AbstractCommand> commands = List.of(new PunishCommand(), new TestCommand(), new StickMeCommand());

        commands.forEach(command -> getCommand(command.getName()).setExecutor(command));
    }
}
