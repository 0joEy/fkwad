package henrycmoss.tuah.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TaxCommand extends AbstractCommand {

    public final String name = "tax";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "This command can only be run by a player");
            return true;
        }
        if(args.length != 1) {
            sender.sendMessage(ChatColor.RED + "This command can only be ran on tw player");
            return true;
        }
        Player player = (Player) sender;
        player.openInventory(Bukkit.getServer().getPlayerExact(args[0]).getInventory());
        return true;
    }
}
