package cn.suxiaolin.subuildingpacking.packing;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BossBarHandler {

    private static final Map<Player, BossBar> bossBarMap = new HashMap<>();

    public static void createBossBar(Player player) {
        BossBar bossBar = Bukkit.createBossBar("当前步骤: 0/2 ,请左键点击第一个点.", BarColor.GREEN, BarStyle.SOLID);
        bossBar.setProgress(0.0);
        bossBarMap.put(player, bossBar);
        bossBar.addPlayer(player);
    }

    public static void updateBossBar1(Player player, int progress) {
        BossBar bossBar = bossBarMap.get(player);
        bossBar.setTitle("当前步骤: " + progress + "/2 ,请右键点击第二个点.");
        bossBar.setProgress((double) progress / 2);
        bossBarMap.replace(player, bossBar);
    }

    public static void updateBossBar2(Player player, int progress) {
        BossBar bossBar = bossBarMap.get(player);
        bossBar.setTitle("当前步骤: " + progress + "/2 ,请输入你想要的建筑名.");
        bossBar.setProgress((double) progress / 2);
        bossBarMap.replace(player, bossBar);
    }

    public static void removeBossBar(Player player) {
        if (!bossBarMap.containsKey(player)) {
            return;
        }
        BossBar bossBar = bossBarMap.get(player);
        bossBar.removePlayer(player);
        bossBarMap.remove(player);
    }

    public static void removeallBossBar() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            removeBossBar(player);
        }
    }
}