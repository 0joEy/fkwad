package henrycmoss.tuah.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class StickMeCommand extends AbstractCommand {

    public final String name = "stickme";

    private static final Vector prisonLoc = new Vector(-2, 82, -8);

    public static final String lightningStickLocal = "lstick";
    public static final String prisonStickLocal = "pstick";
    public static final String magicShaftLocal = "schlong";
    public static final String stuffLocal = "thingStick";
    public static final String LIGHTNING_STICK_NAME = ChatColor.BLUE + "" + ChatColor.BOLD + "Lightning Stick";
    public static final String PRISON_STICK_NAME = ChatColor.GRAY + "" + ChatColor.BOLD + "Prison Stick";
    public static final String MAGIC_WAND_NAME = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Magic Shaft";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only a player can run this command");
            return true;
        }
        if(args.length >= 2) {
            sender.sendMessage(ChatColor.RED + "This command only accepts one argument. Use " + ChatColor.YELLOW + "/stickme " + ChatColor.BLUE +
                    "help " + ChatColor.RED + "to view the arguments.");
            return true;
        }
        Player player = (Player) sender;
        if(player.getInventory().firstEmpty() == -1) {
            sender.sendMessage("your inventory is full");
            return true;
        }
        ItemStack stick = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta stickMeta = stick.getItemMeta();


        if(args.length == 0) {
            stickMeta.setDisplayName("things");
            stick.getItemMeta().setLocalizedName(stuffLocal);
        }
        else if(args[0].equals("lightning")) {
            stickMeta.setDisplayName(LIGHTNING_STICK_NAME);
            stick.getItemMeta().setLocalizedName(lightningStickLocal);
        }

        else if(args[0].equals("prison")) {
            stickMeta.setDisplayName(PRISON_STICK_NAME);
            stick.getItemMeta().setLocalizedName(prisonStickLocal);
        }
        else if(args[0].equals("magic")) {
            stickMeta.setDisplayName(MAGIC_WAND_NAME);
            stick.getItemMeta().setLocalizedName(magicShaftLocal);
            MaterialData matData = new MaterialData(Material.LEGACY_BLAZE_ROD);
            matData.setData((byte) 8);
            stick.setData(matData);
        }
        stick.setItemMeta(stickMeta);
        player.getInventory().addItem(stick);
        return true;
    }

    public static Vector getPrisonLoc() {
        return prisonLoc;
    }

}
