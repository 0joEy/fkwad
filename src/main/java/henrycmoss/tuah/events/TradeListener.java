package henrycmoss.tuah.events;

import henrycmoss.tuah.Tuah;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class TradeListener implements Listener {

    public TradeListener(Tuah plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
