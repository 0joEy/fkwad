package henrycmoss.tuah.commands;

import henrycmoss.tuah.Tuah;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class TradeAcceptCommand extends AbstractCommand {

    public final String name = "tradeaccept";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("OnlyPlayers can run thus command");
            return true;
        }
        Player player = (Player) sender;

        if(args.length != 1) {
            sender.sendMessage("This command can only be ran on one player");
            return true;
        }

        if(Bukkit.getServer().getPlayerExact(args[0]) == player) {
            player.sendMessage("You cannot run this command on yourself!");
            return true;
        }
        Inventory inv = Bukkit.createInventory((Player) sender, 9, "Trade Menu");
        inv.setItem(1, new ItemStack(Material.APPLE, 1));
        Player target = Bukkit.getServer().getPlayerExact(args[0]);
        PersistentDataContainer data = target.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Tuah.getPlugin(Tuah.class), "tradingwith");
        player.sendMessage("Target data keys: " + data.getKeys());
        player.sendMessage("Target tradingwith key: " + data.get(key, PersistentDataType.STRING));
        player.sendMessage("Target UUID (tostring): " + target.getUniqueId());
        player.sendMessage("target UUID" + target.getUniqueId());
        player.sendMessage("player UUID" + player.getUniqueId());
        if(data.get(key, PersistentDataType.STRING).contains(player.getUniqueId().toString())) {
            player.openInventory(inv);
            target.openInventory(inv);
            data.set(key, PersistentDataType.STRING, data.get(key, PersistentDataType.STRING).replace(player.getUniqueId().toString(), ""));
            sender.sendMessage(player.getUniqueId().toString());
            sender.sendMessage(data.get(key, PersistentDataType.STRING));
            return true;
        }
        player.sendMessage("This player has not requested to trade with you!");
        return true;
    }
}
