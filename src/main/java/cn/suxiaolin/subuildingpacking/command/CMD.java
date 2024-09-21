package cn.suxiaolin.subuildingpacking.command;

import cn.suxiaolin.subuildingpacking.progress.ProgressStart;
import cn.suxiaolin.subuildingpacking.suBuildingPacking;
import cn.suxiaolin.subuildingpacking.bossbar.Bar;
import cn.suxiaolin.sucore.message.msg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CMD implements CommandExecutor {
    public CMD(suBuildingPacking plugin){
        plugin.getCommand("packing").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender.hasPermission("subuildingpacking.command")){
            if(command.getName().equalsIgnoreCase("packing") && strings.length == 0){
                ProgressStart.addProgress((Player) sender);
                Bar.createBossBar((Player) sender);
            }
        }
        return true;
    }
}
