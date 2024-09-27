package cn.suxiaolin.subuildingpacking.command;

import cn.suxiaolin.subuildingpacking.packing.PackProcessHandler;
import cn.suxiaolin.subuildingpacking.BuildingPackingPlugin;
import cn.suxiaolin.subuildingpacking.packing.BossBarHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class PackingCommand implements CommandExecutor {

    public PackingCommand(BuildingPackingPlugin plugin) {
        plugin.getCommand("packing").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (sender.hasPermission("subuildingpacking.command")) {
            if (command.getName().equalsIgnoreCase("packing") && strings.length == 0) {
                if (PackProcessHandler.getHaveProgress((Player) sender)) {
                    return true;
                }
                PackProcessHandler.addProgress((Player) sender);
                BossBarHandler.createBossBar((Player) sender);
            }
        }
        return true;
    }
}
