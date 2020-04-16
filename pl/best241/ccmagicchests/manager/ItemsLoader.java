// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.manager;

import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;
import java.util.HashMap;
import org.bukkit.Material;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import pl.best241.ccmagicchests.data.ManipulatorData;
import org.bukkit.entity.Player;
import java.io.FileWriter;
import net.minecraft.util.org.apache.commons.io.IOUtils;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Reader;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.File;
import pl.best241.ccmagicchests.CcMagicChests;
import java.util.Random;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import pl.best241.ccmagicchests.data.LootItemData;
import java.util.ArrayList;

public class ItemsLoader
{
    public static ArrayList<LootItemData> items;
    private static Gson gson;
    private static Random random;
    
    public static void loadItems(final String fileName) {
        final File file = new File(CcMagicChests.getPlugin().getDataFolder(), fileName);
        if (file.exists() || file.length() != 0L) {
            FileReader reader = null;
            try {
                reader = new FileReader(file);
                ItemsLoader.items = (ArrayList<LootItemData>)ItemsLoader.gson.fromJson((Reader)reader, new TypeToken<ArrayList<LootItemData>>() {}.getType());
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(ItemsLoader.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    reader.close();
                }
                catch (IOException ex2) {
                    Logger.getLogger(ItemsLoader.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }
            finally {
                try {
                    reader.close();
                }
                catch (IOException ex3) {
                    Logger.getLogger(ItemsLoader.class.getName()).log(Level.SEVERE, null, ex3);
                }
            }
        }
        else {
            try {
                file.createNewFile();
                Reader defConfigStream = null;
                try {
                    defConfigStream = new InputStreamReader(CcMagicChests.getPlugin().getResource(fileName), "UTF8");
                    final String json = IOUtils.toString(defConfigStream);
                    try (final FileWriter fw = new FileWriter(file)) {
                        fw.write(json);
                        fw.flush();
                    }
                }
                catch (IOException ex2) {
                    Logger.getLogger(ItemsLoader.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }
            catch (IOException ex4) {
                Logger.getLogger(ItemsLoader.class.getName()).log(Level.SEVERE, null, ex4);
            }
        }
    }
    
    public static ArrayList<LootItemData> getAllLoots() {
        return ItemsLoader.items;
    }
    
    public static ArrayList<LootItemData> getRandomDrops(final Player player) {
        final ManipulatorData manipulatorData = CcMagicChests.getPlugin().getBackend().getManipulator(player.getUniqueId());
        final ArrayList<LootItemData> lootList = new ArrayList<LootItemData>();
        for (final LootItemData lootData : ItemsLoader.items) {
            if (manipulatorData != null) {
                if (lootData.getWorth() >= manipulatorData.getFromWorth()) {
                    for (int number = lootData.getDropChance() * manipulatorData.getMultipler(), i = 0; i < number; ++i) {
                        lootList.add(lootData.clone());
                    }
                }
                else {
                    for (int number = lootData.getDropChance(), i = 0; i < number; ++i) {
                        lootList.add(lootData.clone());
                    }
                }
            }
            else {
                for (int number = lootData.getDropChance(), i = 0; i < number; ++i) {
                    lootList.add(lootData.clone());
                }
            }
        }
        shuffle(lootList);
        return lootList;
    }
    
    public static void shuffle(final ArrayList<LootItemData> array) {
        if (ItemsLoader.random == null) {
            ItemsLoader.random = new Random();
        }
        int i;
        for (int count = i = array.size(); i > 1; --i) {
            swap(array, i - 1, ItemsLoader.random.nextInt(i));
        }
    }
    
    public static void sort(final ArrayList<LootItemData> array) {
        for (int i = 0; i < array.size(); ++i) {
            for (int j = 0; j < array.size() - i - 1; ++j) {
                if (array.get(j).getWorth() > array.get(j + 1).getWorth()) {
                    final LootItemData temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                }
            }
        }
        Collections.reverse(array);
    }
    
    private static void swap(final ArrayList<LootItemData> array, final int i, final int j) {
        final LootItemData temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }
    
    public static ItemStack createItemStack(final Material material, final int amount, final String name, final int data, final HashMap<Enchantment, Integer> enchants) {
        final ItemStack item = new ItemStack(material, amount, (short)data);
        if (name != null) {
            final ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        if (enchants != null) {
            item.addUnsafeEnchantments((Map)enchants);
        }
        return item;
    }
    
    public static ItemStack createItemStack(final Material material, final int amount, final String name, final int data, final Enchantment enchant, final int level) {
        final HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
        enchants.put(enchant, level);
        return createItemStack(material, amount, name, data, enchants);
    }
    
    static {
        ItemsLoader.items = new ArrayList<LootItemData>();
        ItemsLoader.gson = new GsonBuilder().setPrettyPrinting().create();
        ItemsLoader.random = null;
    }
}
