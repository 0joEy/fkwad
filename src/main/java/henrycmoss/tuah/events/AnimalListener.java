package henrycmoss.tuah.events;

import henrycmoss.tuah.Tuah;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AnimalListener implements Listener {

    public AnimalListener(Tuah plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHitAnimal(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Animals) {
            Player player = (Player) event.getDamager();
            World world = event.getDamager().getWorld();
            player.sendMessage("okokokokokokokokokokookok");
            event.getEntity().setVelocity(new Vector().setY(2));
            IronGolem golem = (IronGolem) world.spawnEntity(player.getLocation().setDirection(player.getFacing().getDirection()).add(5, 50, 0), EntityType.IRON_GOLEM);
            golem.setCustomName(ChatColor.DARK_RED + "VEGAN DUDE");
            golem.setTarget(player);
            golem.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100000, 2, true, false));
            golem.attack(player);
            player.sendMessage(golem.getTarget().getName());
            player.sendMessage("ok");
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if(entity.getLastDamageCause() instanceof Player && entity instanceof IronGolem) {
            Player player = (Player) entity.getLastDamageCause();
            player.sendMessage("1");
            if(player.getGameMode() != GameMode.CREATIVE) {
                player.sendMessage("2");
                for (int i = 0; i < 200; i++) {
                    player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.POPPY, 5));
                    player.sendMessage("3");
                }
                for (int i = 0; i < 10; i++) {
                    player.damage(0.5d);
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.BLAZE);
                    player.sendMessage("4");
                }
            }
        }
    }
}
