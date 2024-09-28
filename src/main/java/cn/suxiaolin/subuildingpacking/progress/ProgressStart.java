package cn.suxiaolin.subuildingpacking.progress;

import cn.suxiaolin.subuildingpacking.bossbar.Bar;
import cn.suxiaolin.subuildingpacking.suBuildingPacking;
import cn.suxiaolin.sucore.message.msg;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ProgressStart {
    private static Map<Player, Integer> progressMap = new HashMap<>();

    public static void addProgress(Player player) {
        if(!progressMap.containsKey(player)){
            progressMap.put(player, 0);
            msg.pcommonmsg(suBuildingPacking.getpluginname() , player.getDisplayName(), "请用木棍开始选取位置");
            Bar.createBossBar(player);
        }else{
            msg.perrormsg(suBuildingPacking.getpluginname(), player.getDisplayName(), "您上一个打包建筑还未结束!");
            msg.perrormsg(suBuildingPacking.getpluginname(), player.getDisplayName(), "请先结束上一个!");
        }
    }

    public static boolean getHaveProgress(Player player) {
        return progressMap.containsKey(player);
    }
    public static int getProgressSteps(Player player) {
        return progressMap.get(player);
    }
    public static void setProgressSteps(Player player, int steps) {
        progressMap.replace(player, steps);
    }
    public static void removeProgress(Player player) {
        progressMap.remove(player);
    }
}
