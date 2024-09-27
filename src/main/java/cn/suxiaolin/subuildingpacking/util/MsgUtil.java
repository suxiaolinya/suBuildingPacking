package cn.suxiaolin.subuildingpacking.util;

import org.bukkit.Bukkit;

public class MsgUtil {

    public static void commonmsg(String pluginname, String msg) {
        Bukkit.getConsoleSender().sendMessage("[" + pluginname + "]§2 " + msg);
    }

    public static void errormsg(String pluginname, String msg) {
        Bukkit.getConsoleSender().sendMessage("[" + pluginname + "]§4 " + msg);
    }

    public static void pcommonmsg(String pluginname, String playername, String msg) {
        Bukkit.getPlayer(playername).sendMessage("[" + pluginname + "]§2 " + msg);
    }

    public static void perrormsg(String pluginname, String playername, String msg) {
        Bukkit.getPlayer(playername).sendMessage("[" + pluginname + "]§4 " + msg);
    }
}
