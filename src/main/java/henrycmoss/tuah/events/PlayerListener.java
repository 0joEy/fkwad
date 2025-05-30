package henrycmoss.tuah.events;

import henrycmoss.tuah.Tuah;
import henrycmoss.tuah.commands.StickMeCommand;
import henrycmoss.util.TriConsumer;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class PlayerListener implements Listener {

    private final Map<String, TriConsumer<World, Player, Entity>> interactAtEntityMap = new HashMap<>();

    public PlayerListener(Tuah plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        interactAtEntityMap.put(StickMeCommand.LIGHTNING_STICK_NAME, ((world, player, victim) -> {
            LightningStrike lightning = world.strikeLightning(victim.getLocation());
            player.sendMessage("okok");
            Bukkit.broadcastMessage("wtf is going on");
        }));
        interactAtEntityMap.put(StickMeCommand.PRISON_STICK_NAME, ((world, player, victim) -> {
            victim.teleport(StickMeCommand.getPrisonLoc().toLocation(world));
            if (victim instanceof Player) {
                Player pVictim = (Player) victim;
                pVictim.setGameMode(GameMode.ADVENTURE);
                Bukkit.broadcastMessage(ChatColor.GREEN + pVictim.getDisplayName() + ChatColor.YELLOW + " has been sentenced to prison by " +
                        ChatColor.LIGHT_PURPLE + player.getDisplayName());
            } else {
                Bukkit.broadcastMessage(ChatColor.GREEN + victim.getType().name() + ChatColor.YELLOW + " has been sentenced to prison by " +
                        ChatColor.LIGHT_PURPLE + player.getDisplayName());
            }
        }));
        interactAtEntityMap.put("things", (world, player, victim) -> {
            Inventory stuff = Bukkit.createInventory(null, 36, "ThingamaboTuah");
            ItemStack gyattt = new ItemStack(Material.SNOWBALL, 1);
            gyattt.getItemMeta().setLocalizedName("bomb");
            gyattt.getItemMeta().setDisplayName(ChatColor.DARK_GREEN + "Poison Bomb");
            gyattt.serialize().put("ee", 1);
            player.serialize().put("bomb", 1);
            stuff.setItem(1, gyattt);
            player.openInventory(stuff);
            player.sendMessage("oadad");
        });
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity victim = event.getRightClicked();
        World world = event.getPlayer().getWorld();

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if(itemInHand.getType().equals(Material.BLAZE_ROD)) {
            ItemMeta meta = itemInHand.getItemMeta();
            MaterialData cookieData = new MaterialData(Material.COOKIE);
            cookieData.setData((byte) 8);
            itemInHand.setData(cookieData);
            //player.sendMessage(itemMeta.getDisplayName()+ ",    " + StickMeCommand.lStickName);
            String metaName = meta.getDisplayName();

            if(interactAtEntityMap.containsKey(metaName)) interactAtEntityMap.get(metaName).accept(world, player, victim);
        }
        else if (player.getInventory().getItemInMainHand().getType().equals(Material.COOKIE)) {
            ItemStack cookie = player.getInventory().getItemInMainHand();
            cookie.getItemMeta().addAttributeModifier(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS, AttributeModifier.deserialize(cookie.serialize()));
            //player.sendMessage(cookie.getItemMeta().getAttributeModifiers().get(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).toString());
            player.sendMessage("if this sends im fucking done");
        }
        else {
            player.sendMessage("eeee");
        }
        if (victim instanceof Enderman) {
            player.sendMessage("Before inventory init");
            Inventory endermanGUI = Bukkit.createInventory(null, 36, "Enderman Inventory");
            player.sendMessage("before inventory loop");
            for (int i = 22; i < 23; i++) {
                endermanGUI.setItem(i, new ItemStack(Material.ENDER_PEARL, 64));
                player.sendMessage("" + i);
            }
            player.sendMessage("after inventory loop");
            player.openInventory(endermanGUI);
            ((Enderman) victim).damage(1000);
        }
        player.sendMessage("interacted with " + victim.getType().name());
    }

    @EventHandler
    public void onPlayerRunCommand(PlayerCommandPreprocessEvent event) {
        if(event.getMessage().equals("/help")) {
            Player player = event.getPlayer();
            Arrow arrow = player.launchProjectile(Arrow.class);
            arrow.setVelocity(player.getLocation().getDirection().multiply(5));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerCraftItem(CraftItemEvent event) {
        ItemMeta resultMeta = event.getCurrentItem().getItemMeta();
        ItemStack result = event.getCurrentItem();
        if(resultMeta instanceof Damageable) {
            Damageable meta = (Damageable) resultMeta;
            meta.setDamage(result.getData().getItemType().getMaxDurability() * 2);
            result.setItemMeta(meta);
        }
        event.getWhoClicked().sendMessage("adaw");
    }


    public static void playerTickEvent(Player player) {
        if(player.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD) && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(StickMeCommand.MAGIC_WAND_NAME)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 5, 4, true, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3600, 2, true, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 3600, 0, true, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3600, 2, true, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 3600, 0, true, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 5, 0, true, false, true));
        }
    }
}
