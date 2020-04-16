// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.data;

public class ManipulatorData
{
    private final int multipler;
    private final int fromWorth;
    
    public ManipulatorData(final int multipler, final int fromWorth) {
        this.multipler = multipler;
        this.fromWorth = fromWorth;
    }
    
    public int getMultipler() {
        return this.multipler;
    }
    
    public int getFromWorth() {
        return this.fromWorth;
    }
}
