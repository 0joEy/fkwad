package henrycmoss.tuah;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface Punishment {

    void basicPunishment(LivingEntity entity, World world);

    public interface PlayerPunishment {
        void playerPunishment(Player player, World world);
    }
}
