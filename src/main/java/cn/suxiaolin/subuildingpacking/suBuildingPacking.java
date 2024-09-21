package cn.suxiaolin.subuildingpacking;

import cn.suxiaolin.subuildingpacking.bossbar.Bar;
import cn.suxiaolin.subuildingpacking.command.CMD;
import cn.suxiaolin.subuildingpacking.data.Data;
import cn.suxiaolin.subuildingpacking.edgeline.EdgeLine;
import cn.suxiaolin.subuildingpacking.interaction.Interaction;
import cn.suxiaolin.subuildingpacking.listener.LisChat;
import cn.suxiaolin.subuildingpacking.listener.LisPlace;
import cn.suxiaolin.subuildingpacking.util.Util;
import cn.suxiaolin.sucore.message.msg;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class suBuildingPacking extends JavaPlugin {
    private static String pluginname = "suBuildingPacking";
    private final String pluginversion = getDescription().getVersion();
    @Override
    public void onEnable() {
        new EdgeLine(this);
        new Interaction(this);
        new LisChat(this);
        new LisPlace(this);
        new CMD(this);
        new Data();
        new Util(this);

        if(Bukkit.getPluginManager().isPluginEnabled("suCore")){
            msg.commonmsg(pluginname, "插件启用成功");
        }else{
            msg.errormsg(pluginname, "前置插件suCore未找到!");
        }

        CheckUpdata.CheckUpdates(pluginversion);
        new Metrics(this, 23435);
    }

    @Override
    public void onDisable() {
        Bar.removeallBossBar();
        msg.errormsg(pluginname, "插件已卸载！");
    }

    public static String getpluginname(){
        return pluginname;
    }
}
