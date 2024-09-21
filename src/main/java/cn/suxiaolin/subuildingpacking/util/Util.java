package cn.suxiaolin.subuildingpacking.util;

import cn.suxiaolin.subuildingpacking.data.Data;
import cn.suxiaolin.subuildingpacking.edgeline.EdgeLine;
import cn.suxiaolin.subuildingpacking.item.ItemBuild;
import cn.suxiaolin.subuildingpacking.progress.ProgressStart;
import cn.suxiaolin.subuildingpacking.suBuildingPacking;
import cn.suxiaolin.sucore.message.msg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Util {
    private static Map<Player, Location[]> locationMap = new HashMap<>();
    private static suBuildingPacking plugin;

    public Util(suBuildingPacking plugin){
        this.plugin = plugin;
    }

    public static void recordBuild(Player player){
        List<Material> materials = new ArrayList<>();

        Location loca = locationMap.get(player)[0];
        Location locb = locationMap.get(player)[1];

        int ax1 = loca.getBlockX();
        int ay1 = loca.getBlockY();
        int az1 = loca.getBlockZ();
        int bx1 = locb.getBlockX();
        int by1 = locb.getBlockY();
        int bz1 = locb.getBlockZ();

        int xmax = Math.max(ax1, bx1);
        int xmin = Math.min(ax1, bx1);
        int ymax = Math.max(ay1, by1);
        int ymin = Math.min(ay1, by1);
        int zmax = Math.max(az1, bz1);
        int zmin = Math.min(az1, bz1);

        World world = loca.getWorld();

        int xc = xmax - xmin + 1;
        int yc = ymax - ymin + 1;
        int zc = zmax - zmin + 1;

        Data.writeDataToFilexyz(player, xc, yc, zc);

        for (int i = 0; i < zc; i++){
            for (int j = 0; j < yc; j++){
                for (int k = 0; k < xc; k++){
                    Material material = world.getBlockAt(xmin + k, ymin + j, zmin + i).getType();
                    materials.add(material);
                    Location aloc = new Location(world, xmin + k, ymin + j, zmin + i);
                    Block block = aloc.getBlock();
                    Bukkit.getScheduler().runTask(plugin, () -> block.setType(Material.AIR));
                }
            }
        }

        Data.writeDataToFileMaterial(player, materials.toString());
        ItemBuild.createItemToPlayer(player);
        msg.pcommonmsg(suBuildingPacking.getpluginname(), player.getDisplayName(), "建筑打包完成！");

        Data.removeID(player);
        EdgeLine.removeLine(player);
        ProgressStart.removeProgress(player);

    }

    public static void placeBuild(String ID, Location location){
        String var1 = Data.getDataConfig().getString(ID + ".material");
        String[] materialStrings = var1.replaceAll("[\\[\\]]", "").split(",\\s*");
        // 将字符串转换为 Material 枚举
        List<Material> materialList = Arrays.stream(materialStrings)
                                            .map(Material::valueOf)
                                            .collect(Collectors.toList());

        int a = 0;

        int xc = Data.getDataConfig().getInt(ID + ".xc");
        int yc = Data.getDataConfig().getInt(ID + ".yc");
        int zc = Data.getDataConfig().getInt(ID + ".zc");

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int i = 0; i < zc; i++){
            for (int j = 0; j < yc; j++){
                for (int k = 0; k < xc; k++){
                    Location aloc = new Location(location.getWorld(), x + k, y + j, z + i);
                    aloc.getBlock().setType(materialList.get(a));
                    a++;
               }
            }
        }

        FileConfiguration config = Data.getDataConfig();
        config.set(ID, null);
        Data.saveDataToFile(config);
    }

    public static void addLocation(Player player, Location[] locations) {
        locationMap.put(player, locations);
    }
    public static void replaceLocation(Player player, Location[] locations) {
        locationMap.replace(player, locations);
    }
    public static Location[] getLocation(Player player) {
        return locationMap.get(player);
    }
}
