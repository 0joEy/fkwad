package henrycmoss.tuah.commands;

import henrycmoss.tuah.Punishment;
import henrycmoss.util.BlockPos;
import henrycmoss.util.Circle;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import org.joml.Vector2d;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PunishCommand extends AbstractCommand {

    public final String name = "punish";

    public static final Map<String, Punishment.PlayerPunishment> playerPunishmentMap = Stream.of(new Object[][] {
            { "lightningStrike", (Punishment.PlayerPunishment) (player, world) -> world.strikeLightning(player.getLocation())},
            { "pillagers", (Punishment.PlayerPunishment) (player, world) -> {
                player.setGameMode(GameMode.SURVIVAL);
                for(int i = 0; i < 20; i++) {
                    world.spawnEntity(player.getLocation(), EntityType.PILLAGER);
                }
            }},
            { "lavaPit", (Punishment.PlayerPunishment) (player, world) -> {
                int y = player.getLocation().getBlockY();
                Location loc = new Location(world, player.getLocation().getBlockX() + 0.5d, y, player.getLocation().getBlockZ() + 0.5d);
                player.teleport(loc);

                if(y >= -50) {
                    for(int i = y; i > -60; i--) {
                        Location loc1 = new Location(loc.getWorld(), loc.getBlockX(), i, loc.getBlockZ());
                        Iterable<BlockPos> blocks = new BlockPos(loc1).betweenClosed(loc1, loc1.add(1, 1, 1));
                        blocks.forEach(pos -> world.setBlockData(new Location(world, pos.getX(), pos.getY(), pos.getZ()), Material.AIR.createBlockData()));
                        if(i == -59) {
                            blocks.forEach(pos -> world.setBlockData(new Location(world, pos.getX(), pos.getY(), pos.getZ()), Material.LAVA.createBlockData()));
                        }
                        else if(i == -58 || i== -57) world.setBlockData(player.getLocation().getBlockX(), i, player.getLocation().getBlockZ(), Material.COBWEB.createBlockData());
                    }
                }
            }},
            { "rape", (Punishment.PlayerPunishment) (player, world) -> {
                Location l = player.getLocation();
                int x;
                int z;

                Circle circle = new Circle(3, player.getLocation().getBlockX(), player.getLocation().getBlockZ());

                for(Vector2d v : circle.getPoints(50)) {
                    player.sendMessage(v.x + ", " + v.y);
                    IronGolem golem = (IronGolem) world.spawnEntity(new Location(world, v.x, player.getLocation().getY(), v.y),
                            EntityType.IRON_GOLEM);
                    golem.setHealth(500.0d);
                    golem.setTarget(player);
                }

                player.sendMessage();
            }}
            /*{ "wolfpack", (Punishment) (player, world) -> {
                player.setGameMode(GameMode.SURVIVAL);
                for(int i = 0; i < 20; i++) {
                    Wolf wolf = (Wolf) world.spawnEntity(player.getLocation(), EntityType.WOLF);
                    wolf.setAngry(true);
                    wolf.setTarget(player);
                }
            }}*/,

            }).collect(Collectors.toMap(data -> (String) data[0], data -> (Punishment.PlayerPunishment) data[1]));


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        if(args[1].equals("random")) {
            String[] keys = playerPunishmentMap.keySet().toArray(new String[0]);
            playerPunishmentMap.get(keys[new Random().nextInt(keys.length)]).playerPunishment((Player) sender, ((Player) sender).getWorld());
        }
        else {
            playerPunishmentMap.get(args[1]).playerPunishment((Player) sender, ((Player) sender).getWorld());
        }
        return true;
    }

    public String getName() {
        return name;
    }
}
