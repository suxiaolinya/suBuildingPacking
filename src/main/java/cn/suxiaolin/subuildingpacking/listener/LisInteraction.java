package cn.suxiaolin.subuildingpacking.listener;

import cn.suxiaolin.subuildingpacking.data.Data;
import cn.suxiaolin.subuildingpacking.edgeline.EdgeLine;
import cn.suxiaolin.subuildingpacking.progress.ProgressStart;
import cn.suxiaolin.subuildingpacking.bossbar.Bar;
import cn.suxiaolin.subuildingpacking.suBuildingPacking;
import cn.suxiaolin.subuildingpacking.util.Util;
import cn.suxiaolin.subuildingpacking.util.RandomID;
import cn.suxiaolin.sucore.message.msg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP;

public class LisInteraction implements Listener {

    public LisInteraction(suBuildingPacking plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(ProgressStart.getHaveProgress(player) && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK){
            if(event.getAction() == Action.LEFT_CLICK_BLOCK && ProgressStart.getProgressSteps(player) == 0) {
                event.setCancelled(true);

                Location[] location = new Location[2];
                location[0] = event.getClickedBlock().getLocation();
                Util.addLocation(player, location);

                ProgressStart.setProgressSteps(player, 1);
                Bar.updateBossBar1(player, 1);

                player.playSound(player.getLocation(), ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }else if(event.getAction() == Action.RIGHT_CLICK_BLOCK && ProgressStart.getProgressSteps(player) == 1){
                event.setCancelled(true);

                Location[] location = Util.getLocation(player);
                location[1] = event.getClickedBlock().getLocation();
                Util.replaceLocation(player, location);

                String id = RandomID.generateRandomID();
                Data.addID(player, id);
                msg.pcommonmsg(suBuildingPacking.getpluginname(), player.getDisplayName(), "请输入你想要的建筑名:");

                ProgressStart.setProgressSteps(player, 2);
                Bar.updateBossBar2(player, 2);

                player.playSound(player.getLocation(), ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                EdgeLine.buildline(player);
            }
        }
    }
}
