package henrycmoss.tuah.events;

import henrycmoss.tuah.Tuah;
import henrycmoss.tuah.commands.StickMeCommand;
import org.bukkit.Particle;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.EntityEffect;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MagicShaftListener implements Listener {

    public MagicShaftListener(Tuah plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onShaftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        ItemStack mainHandItem = player.getInventory().getItemInMainHand();

        player.sendMessage("clicked");

        if(mainHandItem.getType().equals(Material.BLAZE_ROD) && mainHandItem.getItemMeta().getDisplayName().equals(StickMeCommand.MAGIC_WAND_NAME)) {
            player.sendMessage("e");
            if(player.getFacing() == BlockFace.UP) {
                player.sendMessage("a");
                if(player.getPlayerWeather().equals(WeatherType.DOWNFALL)) player.setPlayerWeather(WeatherType.CLEAR);
                else player.setPlayerWeather(WeatherType.DOWNFALL);
                world.spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation().add(0, 5, 0), 25);
            }
        }
    }

    @EventHandler
    public static void shaftHitEntity(EntityDamageByEntityEvent event) {
        Player player = event.getDamager() instanceof Player ? (Player) event.getDamager() : null;
        Entity target = event.getEntity();
        LivingEntity entity = event.getEntity() instanceof LivingEntity ? (LivingEntity) event.getEntity() : null;

        if(player != null && player.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD) && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(StickMeCommand.MAGIC_WAND_NAME)) {
            player.sendMessage("yeah");
            target.setFireTicks(600);
            target.playEffect(EntityEffect.FIREWORK_EXPLODE);
            player.getWorld().playSound(player, Sound.ENTITY_BEE_STING, 10f, 1f);
            if(entity != null) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 2, false, true, true));
                entity.damage(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / 2);
            }
        }
        else {
            Bukkit.broadcastMessage("nah");
        }
    }

    @EventHandler
    public void shaftClickAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        Entity target = event.getRightClicked();

        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        player.sendMessage(String.valueOf(mainHandItem.getData().getData()));
        if(mainHandItem.getType().equals(Material.BLAZE_ROD) && mainHandItem.getItemMeta().getDisplayName().equals(StickMeCommand.MAGIC_WAND_NAME)) {
            LootTable table = new LootTable() {
                @Override
                public Collection<ItemStack> populateLoot(Random random, LootContext context) {
                    List<ItemStack> items = Arrays.asList(
                            new ItemStack(Material.COOKED_BEEF, 30),
                            new ItemStack(Material.COOKED_PORKCHOP, 45),
                            new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 15),
                            new ItemStack(Material.CAKE, 2),
                            new ItemStack(Material.COOKIE, 35)
                    );
                    return items;
                }

                @Override
                public void fillInventory(Inventory inventory, Random random, LootContext context) {

                    List<ItemStack> inv = Arrays.asList(inventory.getContents());

                    for (ItemStack item : inv) {
                        int max = 10;
                        int min = 1;


                        if(random.nextInt(max + 1 - min) + min < 5) inv.remove(item);
                    }

                    inventory.setContents(inv.toArray(new ItemStack[6]));
                }

                @Override
                public NamespacedKey getKey() {
                    return NamespacedKey.fromString("gameplay/feast", Tuah.getPlugin(Tuah.class));
                }
            };

            for (ItemStack stack : table.populateLoot(new Random(), new LootContext.Builder(player.getLocation()).build())) {
                world.dropItem(target.getLocation(), stack);
            }
        }
        else {
            player.sendMessage("failure");
        }
    }
}
