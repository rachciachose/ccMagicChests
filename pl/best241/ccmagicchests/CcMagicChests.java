// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests;

import pl.best241.ccmagicchests.manager.MagicItemManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import pl.best241.ccmagicchests.manager.ItemsLoader;
import pl.best241.ccmagicchests.backend.RedisBackend;
import pl.best241.ccmagicchests.listeners.InventoryListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import pl.best241.ccmagicchests.listeners.PlayerListener;
import pl.best241.ccmagicchests.backend.Backend;
import org.bukkit.plugin.java.JavaPlugin;

public class CcMagicChests extends JavaPlugin
{
    private Backend backend;
    private static CcMagicChests chests;
    
    public void onEnable() {
        CcMagicChests.chests = this;
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new InventoryListener(), (Plugin)this);
        this.backend = new RedisBackend();
        ItemsLoader.loadItems("config.json");
    }
    
    public Backend getBackend() {
        return this.backend;
    }
    
    public static CcMagicChests getPlugin() {
        return CcMagicChests.chests;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String lable, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            for (int i = 0; i < 64; ++i) {
                player.getInventory().addItem(new ItemStack[] { MagicItemManager.getKeyItem() });
                player.getInventory().addItem(new ItemStack[] { MagicItemManager.getChestItem() });
            }
        }
        return false;
    }
}
