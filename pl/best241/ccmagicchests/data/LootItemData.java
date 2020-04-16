// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.data;

import pl.best241.ccitemshop.managers.ItemManager;
import org.bukkit.inventory.ItemStack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import pl.best241.ccitemshop.vouchers.VoucherType;

public class LootItemData implements Cloneable
{
    private final Integer isItemId;
    private final Integer isItemCount;
    private final VoucherType voucherType;
    private final ItemData item;
    private final int worth;
    private final int dropChance;
    private final String name;
    private final String broadcastName;
    private final Boolean isFake;
    
    public LootItemData(final int worth, final int dropChance, final VoucherType voucherType) {
        this.voucherType = voucherType;
        this.worth = worth;
        this.item = null;
        this.dropChance = dropChance;
        this.name = null;
        this.broadcastName = null;
        this.isItemId = null;
        this.isItemCount = null;
        this.isFake = false;
    }
    
    public VoucherType getVoucherType() {
        return this.voucherType;
    }
    
    public LootItemData(final int worth, final int dropChance, final ItemData item, final String name, final String broadcastName) {
        this.worth = worth;
        this.item = item;
        this.dropChance = dropChance;
        this.name = name;
        this.broadcastName = broadcastName;
        this.isItemId = null;
        this.isItemCount = null;
        this.voucherType = null;
        this.isFake = false;
    }
    
    public Boolean getIsFake() {
        if (this.isFake == null) {
            return false;
        }
        return this.isFake;
    }
    
    public int getWorth() {
        return this.worth;
    }
    
    public ItemData getItemData() {
        if (this.isItemId != null) {
            return null;
        }
        return this.item;
    }
    
    public int getDropChance() {
        return this.dropChance;
    }
    
    public String getName() {
        if (this.name == null) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', this.name);
    }
    
    public String getBroadcastName() {
        if (this.broadcastName == null) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', this.broadcastName);
    }
    
    @Override
    public String toString() {
        return "[" + this.getName() + "]";
    }
    
    public LootItemData clone() {
        try {
            return (LootItemData)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            Logger.getLogger(LootItemData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ItemStack getIsItem() {
        if (this.isItemId == null) {
            return null;
        }
        int count;
        if (this.isItemCount == null) {
            count = 1;
        }
        else {
            count = this.isItemCount;
        }
        final ItemStack itemById = ItemManager.getItemById((int)this.isItemId);
        itemById.setAmount(count);
        return itemById;
    }
}
