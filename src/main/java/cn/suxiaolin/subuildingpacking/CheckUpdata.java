package cn.suxiaolin.subuildingpacking;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;

public class CheckUpdata {
    public static void CheckUpdates(String version) {
        try {
            URL url = new URL("https://api.github.com/repos/suxiaolinya/checkupdatesuBuildingPacking/releases/latest");
            Scanner scanner = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A");
            String json = scanner.hasNext()? scanner.next() : "";
            scanner.close();

            String latestVersion = json.contains("\"tag_name\":") ? json.split("\"tag_name\":")[1].split(",")[0].replace("\"", "").trim() : null;

            if (latestVersion != null && !latestVersion.equalsIgnoreCase(version)) {
                // 有新版本可用，发送提示消息
                Bukkit.getConsoleSender().sendMessage("[suBuildingPacking]§4 有新版本可用v" + latestVersion);
            }else{
                Bukkit.getConsoleSender().sendMessage("[suBuildingPacking]§2 当前已是最新版!");
            }
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("[suBuildingPacking]§4 检查更新失败!" + e.getMessage());
        }
    }
}
