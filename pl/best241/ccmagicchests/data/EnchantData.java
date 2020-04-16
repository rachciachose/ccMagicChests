// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.data;

import org.bukkit.enchantments.Enchantment;

public class EnchantData
{
    private int id;
    private int level;
    
    public EnchantData(final Enchantment enchant, final int level) {
        this.id = enchant.getId();
        this.level = level;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getLevel() {
        return this.level;
    }
}
