// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.listeners;

import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import pl.best241.ccmagicchests.animation.AnimationManager;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.Listener;

public class InventoryListener implements Listener
{
    @EventHandler
    public static void onInventoryClose(final InventoryCloseEvent event) {
        final HumanEntity player = event.getPlayer();
        if (event.getView().getTopInventory().getTitle().equals(AnimationManager.randomInventoryName)) {
            final Player player2 = (Player)player;
        }
    }
    
    @EventHandler
    public static void onInventoryClick(final InventoryClickEvent event) {
        final Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && event.getView().getTopInventory().getTitle().equals(AnimationManager.randomInventoryName)) {
            event.setCancelled(true);
            event.getView().setCursor((ItemStack)null);
            event.setResult(Event.Result.DENY);
        }
    }
    
    @EventHandler
    public static void onInventoryDrag(final InventoryDragEvent event) {
        if (event.getView().getTopInventory().getTitle().equals(AnimationManager.randomInventoryName)) {
            event.setCancelled(true);
            event.getView().setCursor((ItemStack)null);
            event.setResult(Event.Result.DENY);
        }
    }
}
