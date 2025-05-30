package henrycmoss.tuah.events;

import henrycmoss.tuah.Tuah;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlockPlaceListener implements Listener {

    public BlockPlaceListener(Tuah plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerPlaceBlock(BlockPlaceEvent event) {
        if(event.getBlockAgainst().getType() == Material.BEE_NEST && event.getBlockPlaced().getType() == Material.PIGLIN_HEAD) {
            World world = event.getPlayer().getWorld();
            int[][] portal = {
                    {0, 0, 0, 0},
                    {0, 1, 1, 0},
                    {0, 1, 1, 0},
                    {0, 1, 1, 0},
                    {0, 0, 0, 0}
            };
            Location blockPos = event.getBlockPlaced().getLocation().add(-1, -1, 0);
            event.getPlayer().sendMessage(blockPos.toString());
            List<Location> list = new ArrayList<>();
            for(int i = portal.length - 1; i >= 0; i--) {
                Location newPos = blockPos.add(0, 1, 0);
                for(int j = 0; j < portal[i].length; j++) {
                    Location newnewPos = newPos.add(1, 0, 0);
                    if(portal[i][j] == 1) {
                        list.add(new Location(world, newnewPos.getX(), newnewPos.getY(), newnewPos.getZ()));
                    }
                    else if(portal[i][j] == 0) {
                        world.setBlockData(newnewPos, Material.BEE_NEST.createBlockData());
                    }
                    else {
                        throw new ArrayIndexOutOfBoundsException("Portal array must be composed of ones or zeroes");
                    }

                    event.getPlayer().sendMessage("" + portal[i][j]);
                }
                newPos.setX(event.getBlockPlaced().getLocation().getX() - 1);
            }
            for(Location l : list) {
                world.setBlockData(l, Material.NETHER_PORTAL.createBlockData());
            }
            event.getPlayer().sendMessage(blockPos.toString());
        }

        if(event.getBlockAgainst().getType() == Material.BEE_NEST && event.getBlockPlaced().getType() == Material.DRAGON_HEAD) {
            Collection<Entity> entities = event.getPlayer().getNearbyEntities(100, 100, 100);
            EnderDragon dragon = null;
            for (Entity e : entities) {
                if(e instanceof EnderDragon) {
                    dragon = (EnderDragon) e;
                    for(int i = 0; i < 50; i++) {
                        Bee bee = (Bee) event.getPlayer().getWorld().spawnEntity(event.getBlockPlaced().getLocation(), EntityType.BEE);
                        bee.setTarget(dragon);
                    }
                    return;
                }
            }

            for(int i = 0; i < 50; i++) {
                Bee bee = (Bee) event.getPlayer().getWorld().spawnEntity(event.getBlockPlaced().getLocation(), EntityType.BEE);
                bee.setTarget(event.getPlayer());

            }
        }
    }

    @EventHandler
    public void lavaPlaceEvent(PlayerBucketEmptyEvent event) {
        if(event.getBucket() == Material.LAVA_BUCKET) {
            event.getPlayer().getWorld().setBlockData(event.getBlock().getLocation(), Material.TORCHFLOWER.createBlockData());
            event.getPlayer().getInventory().setItem(event.getHand(), new ItemStack(Material.BUCKET, 1));
            event.setCancelled(true);
        }
        event.getPlayer().sendMessage(event.getBucket() + ", " + event.getBlock().getLocation());
    }
}
