package cn.suxiaolin.subuildingpacking.util;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

import static cn.suxiaolin.subuildingpacking.BuildingPackingPlugin.plugin;

public class EdgeLineUtil {

    private static final Particle.DustOptions DUST = new Particle.DustOptions(Color.fromRGB(72, 209, 204), 0.8f);
    private static final double INTERVAL = 0.25;

    private static final Map<Player, Integer> showLineTasks = new HashMap<>();

    public static void activateLine(Player player) {
        Location[] locations = RecordUtil.getLocation(player);
        Location loaction1 = locations[0];
        Location location2 = locations[1];
        BukkitTask asyncTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            EdgeLineUtil.draw(player, loaction1, location2);
        }, 0L, 20L);
        showLineTasks.put(player, asyncTask.getTaskId());
    }

    public static void deactivateLine(Player player) {
        Bukkit.getScheduler().cancelTask(showLineTasks.get(player));
        showLineTasks.remove(player);
    }

    public static void draw(Player player, Location loc1, Location loc2) {
        double xmin = Math.min(loc1.getBlockX(), loc2.getBlockX());
        double ymin = Math.min(loc1.getBlockY(), loc2.getBlockY());
        double zmin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        double xmax = Math.max(loc1.getBlockX(), loc2.getBlockX()) + 1;
        double ymax = Math.max(loc1.getBlockY(), loc2.getBlockY()) + 1;
        double zmax = Math.max(loc1.getBlockZ(), loc2.getBlockZ()) + 1;
        drawXRtLine(player, xmin, xmax, ymin, zmin);
        drawXRtLine(player, xmin, xmax, ymin, zmax);
        drawXRtLine(player, xmin, xmax, ymax, zmin);
        drawXRtLine(player, xmin, xmax, ymax, zmax);
        drawYRtLine(player, xmin, ymin, ymax, zmin);
        drawYRtLine(player, xmin, ymin, ymax, zmax);
        drawYRtLine(player, xmax, ymin, ymax, zmin);
        drawYRtLine(player, xmax, ymin, ymax, zmax);
        drawZRtLine(player, xmin, ymin, zmin, zmax);
        drawZRtLine(player, xmin, ymax, zmin, zmax);
        drawZRtLine(player, xmax, ymin, zmin, zmax);
        drawZRtLine(player, xmax, ymax, zmin, zmax);
    }

    private static void drawXRtLine(Player player, double x1, double x2, double y, double z) {
        for (double x = x1; x <= x2; x += INTERVAL) {
            player.spawnParticle(Particle.REDSTONE, x, y, z, 1, 0, 0, 0, 0, DUST);
        }
    }

    private static void drawYRtLine(Player player, double x, double y1, double y2, double z) {
        for (double y = y1; y <= y2; y += INTERVAL) {
            player.spawnParticle(Particle.REDSTONE, x, y, z, 1, 0, 0, 0, 0, DUST);
        }
    }

    private static void drawZRtLine(Player player, double x, double y, double z1, double z2) {
        for (double z = z1; z <= z2; z += INTERVAL) {
            player.spawnParticle(Particle.REDSTONE, x, y, z, 1, 0, 0, 0, 0, DUST);
        }
    }
}
