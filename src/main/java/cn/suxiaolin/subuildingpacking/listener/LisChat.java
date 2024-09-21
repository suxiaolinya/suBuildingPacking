package cn.suxiaolin.subuildingpacking.listener;

import cn.suxiaolin.subuildingpacking.bossbar.Bar;
import cn.suxiaolin.subuildingpacking.data.Data;
import cn.suxiaolin.subuildingpacking.suBuildingPacking;
import cn.suxiaolin.subuildingpacking.util.Util;
import cn.suxiaolin.sucore.message.msg;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class LisChat implements Listener {
    public LisChat(suBuildingPacking plugin)
    {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(Data.haveID(event.getPlayer())){
            event.setCancelled(true);
            String message = event.getMessage();
            Data.writeDataToFilename(event.getPlayer(), message);
            Bar.removeBossBar(event.getPlayer());
            msg.pcommonmsg(suBuildingPacking.getpluginname(), event.getPlayer().getDisplayName(), "命名成功！");
            msg.pcommonmsg(suBuildingPacking.getpluginname(), event.getPlayer().getDisplayName(), "请等待建筑收集完成...");
            Util.recordBuild(event.getPlayer());
        }
    }
}
