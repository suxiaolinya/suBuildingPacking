package cn.suxiaolin.subuildingpacking;

import cn.suxiaolin.subuildingpacking.packing.BossBarHandler;
import cn.suxiaolin.subuildingpacking.command.PackingCommand;
import cn.suxiaolin.subuildingpacking.data.BuildingDataHandler;
import cn.suxiaolin.subuildingpacking.listener.ChatListener;
import cn.suxiaolin.subuildingpacking.listener.InteractListener;
import cn.suxiaolin.subuildingpacking.util.MsgUtil;
import cn.suxiaolin.subuildingpacking.util.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

public final class BuildingPackingPlugin extends JavaPlugin {

    public static BuildingPackingPlugin plugin;
    private static String pluginname = "suBuildingPacking";
    private final String pluginversion = getDescription().getVersion();

    @Override
    public void onEnable() {
        plugin = this;
        new ChatListener(this);
        new InteractListener(this);
        new PackingCommand(this);
        BuildingDataHandler.init();
        UpdateChecker.CheckUpdates(pluginversion);
        new Metrics(this, 23435);
    }

    @Override
    public void onDisable() {
        BossBarHandler.removeallBossBar();
        MsgUtil.errormsg(pluginname, "插件已卸载！");
    }

    public static String getpluginname() {
        return pluginname;
    }
}
