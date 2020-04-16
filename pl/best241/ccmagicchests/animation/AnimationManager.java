// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.animation;

import org.bukkit.plugin.Plugin;
import pl.best241.ccmagicchests.CcMagicChests;
import pl.best241.ccmagicchests.listeners.PlayerListener;
import org.bukkit.Effect;
import pl.best241.ccchat.CcChat;
import pl.best241.ccitemshop.vouchers.VoucherManager;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.best241.ccmagicchests.data.LootItemData;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.UUID;
import java.util.HashMap;
import java.util.Random;

public class AnimationManager
{
    private static final int invSize = 27;
    private static final Random random;
    public static String randomInventoryName;
    public static HashMap<UUID, Inventory> duringRandoming;
    
    public static void runAnimation(final Player player, final ArrayList<LootItemData> loots, final Location itemLoc) {
        final ArrayList<Integer> sleepTimes = getSleepTimes(140, 1);
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, AnimationManager.randomInventoryName);
        final ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "###");
        item.setItemMeta(itemMeta);
        for (int i = 0; i < 27; ++i) {
            inv.setItem(i, item);
        }
        final ItemStack pointer = new ItemStack(Material.TORCH);
        final ItemMeta pointerMeta = pointer.getItemMeta();
        pointerMeta.setDisplayName(ChatColor.BLUE + "WSKAZNIK");
        pointer.setItemMeta(pointerMeta);
        inv.setItem(4, pointer);
        inv.setItem(22, pointer);
        player.openInventory(inv);
        AnimationManager.duringRandoming.put(player.getUniqueId(), inv);
        int predictedItemNumber = sleepTimes.size() + 3;
        if (predictedItemNumber > loots.size()) {
            predictedItemNumber %= loots.size();
        }
        int antiStackOverflow = 0;
        final int maxAntiStackOverflow = 40;
        while (++antiStackOverflow <= maxAntiStackOverflow) {
            final LootItemData itemDropped = loots.get(predictedItemNumber);
            if (!itemDropped.getIsFake()) {
                renderGui(player, inv, itemLoc, loots, sleepTimes, 0);
                return;
            }
            loots.set(predictedItemNumber, loots.get(AnimationManager.random.nextInt(loots.size())));
            System.out.println("Avoided fake item drop!");
        }
        System.out.println("Anti stack overflow! Avoided not allowed item drop");
    }
    
    public static void renderGui(final Player player, final Inventory inv, final Location itemLoc, final ArrayList<LootItemData> loots, final ArrayList<Integer> sleepTimes, final int step) {
        if (sleepTimes.isEmpty()) {
            int lootItem = step + 3;
            if (lootItem > loots.size()) {
                lootItem %= loots.size();
            }
            final LootItemData lootItemData = loots.get(lootItem);
            ItemStack item = null;
            if (lootItemData.getIsItem() != null) {
                item = lootItemData.getIsItem();
            }
            else if (lootItemData.getItemData() != null) {
                item = lootItemData.getItemData().getItemStack();
            }
            else if (lootItemData.getVoucherType() != null) {
                item = VoucherManager.getVoucherItem(VoucherManager.getVoucherData(lootItemData.getVoucherType()));
            }
            player.getWorld().dropItemNaturally(itemLoc, item);
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GREEN + "Gratulacje! Wylosowales: " + ChatColor.RED + lootItemData.getName());
            if (lootItemData.getBroadcastName() != null) {
                CcChat.broadcastMessage(lootItemData.getBroadcastName().replace("%nick", player.getName()));
            }
            player.updateInventory();
            for (int i = 0; i < 9; ++i) {
                player.getWorld().playEffect(itemLoc.add(Math.random(), 1.0 + Math.random(), Math.random()).add(-Math.random(), 0.0, -Math.random()).add(0.5, 0.0, 0.5), Effect.HAPPY_VILLAGER, 30, 30);
                PlayerListener.firewark(itemLoc.add(Math.random(), 1.0 + Math.random(), Math.random()).add(-Math.random(), 0.0, -Math.random()).add(0.5, 0.0, 0.5));
            }
            player.getWorld().strikeLightningEffect(itemLoc);
            AnimationManager.duringRandoming.remove(player.getUniqueId());
            Bukkit.getScheduler().runTaskLater((Plugin)CcMagicChests.getPlugin(), () -> {
                if (player.getOpenInventory() != null && player.getOpenInventory().getTopInventory() != null && player.getOpenInventory().getTopInventory().getTitle().equals(AnimationManager.randomInventoryName) && !AnimationManager.duringRandoming.containsKey(player.getUniqueId())) {
                    player.closeInventory();
                }
            }, 100L);
            return;
        }
        if (!AnimationManager.duringRandoming.containsKey(player.getUniqueId())) {
            AnimationManager.duringRandoming.put(player.getUniqueId(), inv);
        }
        final Integer sleepTime = sleepTimes.get(0);
        sleepTimes.remove(0);
        int j;
        int itemInInv;
        Integer lootItem2;
        LootItemData get;
        ItemStack item2;
        Bukkit.getScheduler().runTaskLater((Plugin)CcMagicChests.getPlugin(), () -> {
            for (j = 0; j < 9; ++j) {
                itemInInv = j + 9;
                lootItem2 = step + j;
                if (lootItem2 >= loots.size()) {
                    lootItem2 %= loots.size();
                }
                get = loots.get(lootItem2);
                item2 = null;
                if (get.getIsItem() != null) {
                    item2 = get.getIsItem();
                }
                else if (get.getItemData() != null) {
                    item2 = get.getItemData().getItemStack();
                }
                else if (get.getVoucherType() != null) {
                    item2 = VoucherManager.getVoucherItem(VoucherManager.getVoucherData(get.getVoucherType()));
                }
                inv.setItem(itemInInv, item2);
            }
            renderGui(player, inv, itemLoc, loots, sleepTimes, step + 1);
        }, (long)sleepTime);
    }
    
    public static ArrayList<Integer> getSleepTimes(final int ticks, final int tickDecreaseSpeed) {
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
        return ticksSleep;
    }
    
    static {
        random = new Random();
        AnimationManager.randomInventoryName = ChatColor.GOLD + "Losowanie przedmiotow...";
        AnimationManager.duringRandoming = new HashMap<UUID, Inventory>();
    }
}
