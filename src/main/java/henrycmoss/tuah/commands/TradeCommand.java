package henrycmoss.tuah.commands;

import henrycmoss.tuah.Tuah;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class TradeCommand extends AbstractCommand {

    public final String name = "trade";

    public List<String> list = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage("Command must be run on another player!");
            return true;
        }

        Bukkit.getServer().getPlayerExact(args[0]).sendMessage(((Player) sender).getDisplayName()
        + "has requested to trade with you? use /tradeaccept to accept or /tradedeny to deny the trade");
        list.add(Bukkit.getServer().getPlayerExact(args[0]).getUniqueId().toString());
        sender.sendMessage("Target UUID: " + Bukkit.getServer().getPlayerExact(args[0]).getUniqueId());
        Player player = (Player) sender;
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Tuah.getPlugin(Tuah.class), "tradingwith");
        data.set(key, PersistentDataType.STRING, String.join("\n", list));
        sender.sendMessage("Player persistentdata: " + data.get(key, PersistentDataType.STRING));
        return true;
    }
}
