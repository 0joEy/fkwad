package henrycmoss.tuah.events;

import henrycmoss.tuah.Tuah;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class ItemListener implements Listener {

    public ItemListener(Tuah plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemBreak(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        if(item.getItemMeta() instanceof Damageable) {
            Damageable damageable = (Damageable) item.getItemMeta();
            if(damageable.getDamage() > item.getData().getItemType().getMaxDurability()) {
                damageable.setDamage(damageable.getDamage() - 1);
                item.setItemMeta(damageable);
                event.getPlayer().sendMessage("" + damageable.getDamage());
                event.getPlayer().sendMessage("" + item.getData().getItemType().getMaxDurability());
                event.setCancelled(true);
            }
            event.getPlayer().sendMessage("" + damageable.getDamage());
            event.getPlayer().sendMessage("" + item.getData().getItemType().getMaxDurability());
        }
    }
}
