package henrycmoss.tuah.events;

import henrycmoss.tuah.Tuah;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class TuahEvents implements Listener {

    private final String invName = "Trade Menu";

    public TuahEvents(Tuah plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void onSnowBallHit(ProjectileHitEvent event) {

        Player player = event.getEntity().getShooter() instanceof Player ? (Player) event.getEntity().getShooter() : null;

        event.getEntity().setCustomName("Poison Bomb");

        World world = event.getEntity().getWorld();

        if(event.getEntity() instanceof Snowball) {
            Snowball ball = (Snowball) event.getEntity();
        }

    }












}
