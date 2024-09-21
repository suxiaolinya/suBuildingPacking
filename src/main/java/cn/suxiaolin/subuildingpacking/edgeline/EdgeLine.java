package cn.suxiaolin.subuildingpacking.edgeline;

import cn.suxiaolin.subuildingpacking.suBuildingPacking;
import cn.suxiaolin.subuildingpacking.util.Util;
import cn.suxiaolin.sucore.util.UtilEdgeLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class EdgeLine {
    private static Map<Player, Integer> taskid= new HashMap<>();
    private static suBuildingPacking plugin;

    public EdgeLine(suBuildingPacking plugin){
        this.plugin = plugin;
    }
    public static void buildline(Player player){
        Location[] locations = Util.getLocation(player);
        Location loaction1 = locations[0];
        Location location2 = locations[1];

        BukkitTask asyncTask = new BukkitRunnable() {
            @Override
            public void run() {
                UtilEdgeLine.buildCubicLine(player, loaction1, location2, 0.3, (float)72, (float)209, (float)204);
            }
        }.runTaskTimerAsynchronously(plugin, 20L, 20L);

        taskid.put(player, asyncTask.getTaskId());
    }
    public static void removeLine(Player player){
        Bukkit.getScheduler().cancelTask(taskid.get(player));
    }
}
