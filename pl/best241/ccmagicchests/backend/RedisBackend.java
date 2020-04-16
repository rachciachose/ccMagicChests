// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.backend;

import redis.clients.jedis.Jedis;
import pl.best241.rdbplugin.JedisFactory;
import pl.best241.ccmagicchests.data.ManipulatorData;
import java.util.UUID;

public class RedisBackend implements Backend
{
    @Override
    public ManipulatorData getManipulator(final UUID uuid) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        final String valueWorth = jedis.hget("ccMagicChests.fromWorth", uuid.toString());
        final String valueMultipler = jedis.hget("ccMagicChests.multipler", uuid.toString());
        JedisFactory.getInstance().returnJedis(jedis);
        if (valueWorth == null || valueMultipler == null) {
            return null;
        }
        return new ManipulatorData(Integer.parseInt(valueMultipler), Integer.parseInt(valueWorth));
    }
    
    @Override
    public void setManipulator(final UUID uuid, final ManipulatorData data) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.hset("ccMagicChests.fromWorth", uuid.toString(), String.valueOf(data.getFromWorth()));
        jedis.hset("ccMagicChests.multipler", uuid.toString(), String.valueOf(data.getMultipler()));
        JedisFactory.getInstance().returnJedis(jedis);
    }
}
