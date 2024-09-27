package cn.suxiaolin.subuildingpacking.util;

import cn.suxiaolin.subuildingpacking.data.BuildingDataHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public static void createItemToPlayer(Player player) {
        ItemStack paper = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = paper.getItemMeta();
        String id = BuildingDataHandler.getID(player);
        String name = BuildingDataHandler.getDataConfig().getString(id + ".name");
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(id);
        meta.setLore(lore);
        paper.setItemMeta(meta);
        player.getInventory().addItem(paper);
    }
}