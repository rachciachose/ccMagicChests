// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.data;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import org.bukkit.Material;

public class ItemData
{
    private Material material;
    private int amount;
    private short durability;
    private ArrayList<EnchantData> enchantList;
    private String displayName;
    private String[] lore;
    
    public ItemData(final Material material, final int amount, final short durability, final ArrayList<EnchantData> enchantList, final String displayName, final String[] lore) {
        this.material = material;
        this.amount = amount;
        this.durability = durability;
        this.enchantList = enchantList;
        if (displayName != null) {
            this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        }
        else {
            this.displayName = null;
        }
        this.lore = lore;
    }
    
    public static void main(final String... args) throws InterruptedException {
        final int ticks = 140;
        int ticksSum = 0;
        final int speed = 1;
        final ArrayList<Integer> ticksSleep = new ArrayList<Integer>();
        for (int i = 1; i < ticks; i += speed) {
            ticksSleep.add(i);
            ticksSum += i;
            if (ticksSum > ticks) {
                break;
            }
        }
        System.out.println(ticksSleep);
        Thread.sleep(1000L);
        int count = 0;
        for (final int sleep : ticksSleep) {
            ++count;
            Thread.sleep(sleep * 50);
            System.out.println("Losowanie kurwa " + count + "/" + ticksSleep.size());
        }
    }
    
    public ItemStack getItemStack() {
        final ItemStack item = new ItemStack(this.material, this.amount, this.durability);
        final ItemMeta meta = item.getItemMeta();
        if (this.displayName != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));
        }
        if (this.lore != null) {
            final ArrayList<String> loreList = new ArrayList<String>();
            for (final String loreLine : this.lore) {
                if (loreLine != null) {
                    loreList.add(ChatColor.translateAlternateColorCodes('&', loreLine));
                }
            }
            meta.setLore((List)loreList);
        }
        item.setItemMeta(meta);
        if (this.enchantList != null) {
            for (final EnchantData enchant : this.enchantList) {
                final Enchantment enchantment = Enchantment.getById(enchant.getId());
                item.addUnsafeEnchantment(enchantment, enchant.getLevel());
            }
        }
        return item;
    }
    
    public Material getMaterial() {
        return this.material;
    }
    
    public void setMaterial(final Material material) {
        this.material = material;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(final int amount) {
        this.amount = amount;
    }
    
    public short getDurability() {
        return this.durability;
    }
    
    public void setDurability(final short durability) {
        this.durability = durability;
    }
    
    public ArrayList<EnchantData> getEnchantList() {
        return this.enchantList;
    }
    
    public void setEnchantList(final ArrayList<EnchantData> enchantList) {
        this.enchantList = enchantList;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public String[] getLore() {
        return this.lore;
    }
    
    public void setLore(final String[] lore) {
        this.lore = lore;
    }
}
