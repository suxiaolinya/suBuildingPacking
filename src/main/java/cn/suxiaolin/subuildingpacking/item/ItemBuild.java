package cn.suxiaolin.subuildingpacking.item;

import cn.suxiaolin.subuildingpacking.data.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuild {
    public static void createItemToPlayer(Player player) {
        ItemStack paper = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = paper.getItemMeta();
        String id = Data.getID(player);
        String name = Data.getDataConfig().getString(id + ".name");
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(id);
        meta.setLore(lore);
        paper.setItemMeta(meta);
        player.getInventory().addItem(paper);
    }

}
