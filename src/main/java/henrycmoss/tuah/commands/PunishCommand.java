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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.checkerframework.checker.units.qual.C;
import org.joml.Vector2d;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PunishCommand extends AbstractCommand {

    public final String name = "punish";

    private static final String[] punishmentNames = {"lightningStrike", "pillagers", "lavaPit", "rape"};

    public static final Map<String, Punishment.PlayerPunishment> playerPunishmentMap = Stream.of(new Object[][] {
            { punishmentNames[0], (Punishment.PlayerPunishment) (player, world) -> world.strikeLightning(player.getLocation())},
            { punishmentNames[1], (Punishment.PlayerPunishment) (player, world) -> {
                player.setGameMode(GameMode.SURVIVAL);
                for(int i = 0; i < 20; i++) {
                    world.spawnEntity(player.getLocation(), EntityType.PILLAGER);
                }
            }},
            { punishmentNames[2], (Punishment.PlayerPunishment) (player, world) -> {
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
            { punishmentNames[3], (Punishment.PlayerPunishment) (player, world) -> {
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

    public static final Map<String, Punishment> punishmentMap = Stream.of(new Object[][] {
            { punishmentNames[0], (Punishment) (entity, world) -> playerPunishmentMap.get(punishmentNames[0]) },
            { punishmentNames[1], (Punishment) (entity, world) -> {
                for(int i = 0; i < 20; i++) {
                    world.spawnEntity(entity.getLocation(), EntityType.PILLAGER);
                }
            } },
            { punishmentNames[2], (Punishment) (entity, world) -> playerPunishmentMap.get(punishmentNames[0]) },
            { punishmentNames[3], (Punishment) (entity, world) -> playerPunishmentMap.get(punishmentNames[0]) },
    }).collect(Collectors.toMap((data) -> (String) data[0], data -> (Punishment) data[1]));


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = Bukkit.getPlayerExact(args[0]);
        if (player != null) {

            if(args[1].equals("random")) {
                String[] keys = playerPunishmentMap.keySet().toArray(new String[0]);
                playerPunishmentMap.get(keys[new Random().nextInt(keys.length)]).playerPunishment(player, player.getWorld());
            }
            else {
                playerPunishmentMap.get(args[1]).playerPunishment(player, player.getWorld());
            }
        }
        else if(args[0].equalsIgnoreCase("selected")) {
            Cast
            if(args[1].equalsIgnoreCase("random")) {
                String[] keys = punishmentMap.keySet().toArray(new String[0]);
                punishmentMap.get(keys[new Random().nextInt(keys.length)]).basicPunishment(entity, entity.getWorld());
            }
            else punishmentMap.get(args[1]).basicPunishment(entity, entity.getWorld());
        }

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
