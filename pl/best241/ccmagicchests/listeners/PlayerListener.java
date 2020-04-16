// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.listeners;

import org.bukkit.Color;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import pl.best241.ccmagicchests.data.LootItemData;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import pl.best241.ccmagicchests.manager.ItemsLoader;
import org.bukkit.inventory.Inventory;
import org.bukkit.ChatColor;
import pl.best241.ccmagicchests.animation.AnimationManager;
import pl.best241.ccmagicchests.manager.MagicItemManager;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.Random;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener
{
    private static Random random;
    
    @EventHandler
    public static void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
    }
    
    @EventHandler
    public static void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
    }
    
    @EventHandler
    public static void onPlayerKick(final PlayerKickEvent event) {
        final Player player = event.getPlayer();
    }
    
    @EventHandler
    public static void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemInHand = event.getItemInHand();
        if (itemInHand.isSimilar(MagicItemManager.getChestItem())) {
            if (MagicItemManager.hasKey(player)) {
                if (AnimationManager.duringRandoming.containsKey(player.getUniqueId())) {
                    player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.RED + "Aktualnie losujesz... Otwieram podglad!");
                    player.openInventory((Inventory)AnimationManager.duringRandoming.get(player.getUniqueId()));
                    event.setCancelled(true);
                    return;
                }
                MagicItemManager.removeKey(player);
                final Location itemLoc = event.getBlockPlaced().getLocation();
                final ArrayList<LootItemData> randomDrops = ItemsLoader.getRandomDrops(player);
                AnimationManager.runAnimation(player, randomDrops, itemLoc);
                final ItemStack chest = itemInHand.clone();
                chest.setAmount(1);
                player.getInventory().removeItem(new ItemStack[] { chest });
            }
            else {
                player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.RED + "Aby otworzyc skrzynke musisz posiadac magiczny klucz!");
                player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.RED + "Mozesz go kupic TYLKO na stronie www.craftcore.pl!");
            }
            event.setCancelled(true);
        }
        else if (itemInHand.isSimilar(MagicItemManager.getKeyItem())) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.RED + "Nie mozesz tego polozyc!");
            event.setCancelled(true);
        }
    }
    
    public static void firewark(final Location loc) {
        final Firework fw = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        final FireworkMeta fwm = fw.getFireworkMeta();
        final Random r = new Random();
        final int rt = r.nextInt(4) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1) {
            type = FireworkEffect.Type.BALL;
        }
        if (rt == 2) {
            type = FireworkEffect.Type.BALL_LARGE;
        }
        if (rt == 3) {
            type = FireworkEffect.Type.BURST;
        }
        if (rt == 4) {
            type = FireworkEffect.Type.CREEPER;
        }
        if (rt == 5) {
            type = FireworkEffect.Type.STAR;
        }
        final int r1i = r.nextInt(17) + 1;
        final int r2i = r.nextInt(17) + 1;
        final Color c1 = getColor(r1i);
        final Color c2 = getColor(r2i);
        final FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
        fwm.addEffect(effect);
        final int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);
        fw.setFireworkMeta(fwm);
    }
    
    private static Color getColor(final int i) {
        Color c = null;
        if (i == 1) {
            c = Color.AQUA;
        }
        if (i == 2) {
            c = Color.BLACK;
        }
        if (i == 3) {
            c = Color.BLUE;
        }
        if (i == 4) {
            c = Color.FUCHSIA;
        }
        if (i == 5) {
            c = Color.GRAY;
        }
        if (i == 6) {
            c = Color.GREEN;
        }
        if (i == 7) {
            c = Color.LIME;
        }
        if (i == 8) {
            c = Color.MAROON;
        }
        if (i == 9) {
            c = Color.NAVY;
        }
        if (i == 10) {
            c = Color.OLIVE;
        }
        if (i == 11) {
            c = Color.ORANGE;
        }
        if (i == 12) {
            c = Color.PURPLE;
        }
        if (i == 13) {
            c = Color.RED;
        }
        if (i == 14) {
            c = Color.SILVER;
        }
        if (i == 15) {
            c = Color.TEAL;
        }
        if (i == 16) {
            c = Color.WHITE;
        }
        if (i == 17) {
            c = Color.YELLOW;
        }
        return c;
    }
    
    static {
        PlayerListener.random = new Random();
    }
}
