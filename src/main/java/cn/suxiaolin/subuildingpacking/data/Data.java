package cn.suxiaolin.subuildingpacking.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Data {
    private File dataFolder;
    private static File dataFile;
    private static FileConfiguration dataConfig;

    private static Map<Player, String> ID = new HashMap<>();

    public Data(){
        createDataFolder();
        loadConfig();
    }
    public static void writeDataToFileBlockData(Player player, String blockdata) {
        String id = ID.get(player);
        // 向 data.yml 文件写入内容
        dataConfig.set(id + ".blockdata", blockdata);

        // 保存数据到文件
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadConfig();
    }
    public static void writeDataToFilename(Player player, String name) {
        String id = ID.get(player);
        // 向 data.yml 文件写入内容
        dataConfig.set(id + ".name", name);

        // 保存数据到文件
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadConfig();
    }
    public static void writeDataToFilexyz(Player player, int xc, int yc, int zc) {
        String id = ID.get(player);
        // 向 data.yml 文件写入内容
        dataConfig.set(id + ".xc", xc);
        dataConfig.set(id + ".yc", yc);
        dataConfig.set(id + ".zc", zc);

        // 保存数据到文件
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadConfig();
    }
    public static void addID(Player player, String id) {
        ID.put(player, id);
    }
    public static boolean haveID(Player player) {
        return ID.containsKey(player);
    }
    public static String getID(Player player) {
        return ID.get(player);
    }
    public static void removeID(Player player) {
        ID.remove(player);
    }
    private static void loadConfig() {
        // 加载 data.yml 文件
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public static FileConfiguration getDataConfig() {
        return dataConfig;
    }
    public static void saveDataToFile(FileConfiguration file) {
        try {
            file.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createDataFolder() {
        // 获取插件名称并创建文件夹
        String pluginName = "suBuildingPacking";
        dataFolder = new File(Bukkit.getServer().getPluginManager().getPlugin(pluginName).getDataFolder().getPath());
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        createDataFile();
    }
    private void createDataFile() {
        dataFile = new File(dataFolder, "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
