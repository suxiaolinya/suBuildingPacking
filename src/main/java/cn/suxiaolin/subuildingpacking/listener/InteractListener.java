package cn.suxiaolin.subuildingpacking.listener;

import cn.suxiaolin.subuildingpacking.data.BuildingDataHandler;
import cn.suxiaolin.subuildingpacking.BuildingPackingPlugin;
import cn.suxiaolin.subuildingpacking.packing.BossBarHandler;
import cn.suxiaolin.subuildingpacking.packing.PackProcessHandler;
import cn.suxiaolin.subuildingpacking.util.EdgeLineUtil;
import cn.suxiaolin.subuildingpacking.util.MsgUtil;
import cn.suxiaolin.subuildingpacking.util.RandomID;
import cn.suxiaolin.subuildingpacking.util.RecordUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP;

public class InteractListener implements Listener {

    public InteractListener(BuildingPackingPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void buildingPlace(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.PAPER && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            String ID = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().get(0);
            if (ID != null && BuildingDataHandler.getDataConfig().contains(ID)) {
                RecordUtil.placeBuild(ID, event.getClickedBlock().getLocation());
                MsgUtil.pcommonmsg(BuildingPackingPlugin.getpluginname(), event.getPlayer().getDisplayName(), "已放置!");
                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
            }
        }
    }

    @EventHandler
    public void regionSelect(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.STICK || !PackProcessHandler.getHaveProgress(player)) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && PackProcessHandler.getProgressSteps(player) == 0) {
            event.setCancelled(true);

            Location[] location = new Location[2];
            location[0] = event.getClickedBlock().getLocation();
            RecordUtil.addLocation(player, location);

            PackProcessHandler.setProgressSteps(player, 1);
            BossBarHandler.updateBossBar1(player, 1);

            player.playSound(player.getLocation(), ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && PackProcessHandler.getProgressSteps(player) == 1) {
            event.setCancelled(true);

            Location[] location = RecordUtil.getLocation(player);
            location[1] = event.getClickedBlock().getLocation();
            RecordUtil.replaceLocation(player, location);

            String id = RandomID.generateRandomID();
            BuildingDataHandler.addID(player, id);
            MsgUtil.pcommonmsg(BuildingPackingPlugin.getpluginname(), player.getDisplayName(), "请输入你想要的建筑名:");

            PackProcessHandler.setProgressSteps(player, 2);
            BossBarHandler.updateBossBar2(player, 2);

            player.playSound(player.getLocation(), ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            EdgeLineUtil.activateLine(player);
        }
    }
}