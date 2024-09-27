package cn.suxiaolin.subuildingpacking.util;

import cn.suxiaolin.subuildingpacking.data.BuildingDataHandler;
import cn.suxiaolin.subuildingpacking.packing.PackProcessHandler;
import cn.suxiaolin.subuildingpacking.BuildingPackingPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

import static cn.suxiaolin.subuildingpacking.BuildingPackingPlugin.plugin;

public class RecordUtil {

    private static final Map<Player, Location[]> locationMap = new HashMap<>();

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

        BuildingDataHandler.writeDataToFilexyz(player, xc, yc, zc);

        for (int i = 0; i < yc; i++){
            for (int j = 0; j < zc; j++){
                for (int k = 0; k < xc; k++){
                    Material material = world.getBlockAt(xmax - k, ymax - i, zmax - j).getType();
                    materials.add(material);
                    Location aloc = new Location(world, xmax - k, ymax - i, zmax - j);
                    Block block = aloc.getBlock();
                    Bukkit.getScheduler().runTask(plugin, () -> block.setType(Material.AIR));
                }
            }
        }

        BuildingDataHandler.writeDataToFileMaterial(player, materials.toString());
        ItemUtil.createItemToPlayer(player);
        MsgUtil.pcommonmsg(BuildingPackingPlugin.getpluginname(), player.getDisplayName(), "建筑打包完成！");

        BuildingDataHandler.removeID(player);
        EdgeLineUtil.deactivateLine(player);
        PackProcessHandler.removeProgress(player);

    }

    public static void placeBuild(String ID, Location location){
        String var1 = BuildingDataHandler.getDataConfig().getString(ID + ".material");
        String[] materialStrings = var1.replaceAll("[\\[\\]]", "").split(",\\s*");
        // 将字符串转换为 Material 枚举
        List<Material> materialList = Arrays.stream(materialStrings)
                                            .map(Material::valueOf)
                                            .collect(Collectors.toList());

        int a = materialList.size() - 1;

        int xc = BuildingDataHandler.getDataConfig().getInt(ID + ".xc");
        int yc = BuildingDataHandler.getDataConfig().getInt(ID + ".yc");
        int zc = BuildingDataHandler.getDataConfig().getInt(ID + ".zc");

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int i = 0; i < yc; i++){
            for (int j = 0; j < zc; j++){
                for (int k = 0; k < xc; k++){
                    Location aloc = new Location(location.getWorld(), x + k, y + i, z + j);
                    aloc.getBlock().setType(materialList.get(a));
                    a--;
               }
            }
        }

        FileConfiguration config = BuildingDataHandler.getDataConfig();
        config.set(ID, null);
        BuildingDataHandler.saveDataToFile(config);
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
