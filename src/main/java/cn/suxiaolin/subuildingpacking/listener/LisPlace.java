package cn.suxiaolin.subuildingpacking.listener;

import cn.suxiaolin.subuildingpacking.data.Data;
import cn.suxiaolin.subuildingpacking.suBuildingPacking;
import cn.suxiaolin.subuildingpacking.util.Util;
import cn.suxiaolin.sucore.message.msg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LisPlace implements Listener {
    public LisPlace(suBuildingPacking plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onBlockClick(PlayerInteractEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.PAPER && event.getAction() == Action.RIGHT_CLICK_BLOCK){
            String ID = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().get(0);
            if(ID != null && Data.getDataConfig().contains(ID)){
                Util.placeBuild(ID, event.getClickedBlock().getLocation());
                msg.pcommonmsg(suBuildingPacking.getpluginname(), event.getPlayer().getDisplayName(), "已放置!");
                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
            }
        }
    }
}
