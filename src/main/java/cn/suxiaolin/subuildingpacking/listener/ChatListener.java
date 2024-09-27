package cn.suxiaolin.subuildingpacking.listener;

import cn.suxiaolin.subuildingpacking.packing.BossBarHandler;
import cn.suxiaolin.subuildingpacking.data.BuildingDataHandler;
import cn.suxiaolin.subuildingpacking.BuildingPackingPlugin;
import cn.suxiaolin.subuildingpacking.util.MsgUtil;
import cn.suxiaolin.subuildingpacking.util.RecordUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener(BuildingPackingPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (BuildingDataHandler.haveID(event.getPlayer())) {
            event.setCancelled(true);
            String message = event.getMessage();
            BuildingDataHandler.writeDataToFilename(event.getPlayer(), message);
            BossBarHandler.removeBossBar(event.getPlayer());
            MsgUtil.pcommonmsg(BuildingPackingPlugin.getpluginname(), event.getPlayer().getDisplayName(), "命名成功！");
            MsgUtil.pcommonmsg(BuildingPackingPlugin.getpluginname(), event.getPlayer().getDisplayName(), "请等待建筑收集完成...");
            RecordUtil.recordBuild(event.getPlayer());
        }
    }
}