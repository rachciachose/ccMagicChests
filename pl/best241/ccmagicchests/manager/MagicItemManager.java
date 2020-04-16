// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MagicItemManager
{
    public static ItemStack getChestItem() {
        final ItemStack item = new ItemStack(Material.CHEST);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Magiczna skrzynia");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.BLUE + "Aby otworzyc skrzynke musisz posiadac klucz");
        lore.add(ChatColor.RED + "Skrzynki i klucze mozesz kupic na");
        lore.add(ChatColor.RED + "stronie www.craftcore.pl!");
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }
    
    public static ItemStack getKeyItem() {
        final ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Magiczny klucz");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.BLUE + "Tym kluczem mozesz otwierac magiczne skrzynki");
        lore.add(ChatColor.RED + "Skrzynki i klucze mozesz kupic na");
        lore.add(ChatColor.RED + "stronie www.craftcore.pl!");
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }
    
    public static boolean hasKey(final Player player) {
        for (final ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                if (item.isSimilar(getKeyItem())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void removeKey(final Player player) {
        for (final ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                if (item.isSimilar(getKeyItem())) {
                    final ItemStack key = item.clone();
                    key.setAmount(1);
                    player.getInventory().removeItem(new ItemStack[] { key });
                    return;
                }
            }
        }
    }
}
