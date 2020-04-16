// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccmagicchests.backend;

import pl.best241.ccmagicchests.data.ManipulatorData;
import java.util.UUID;

public interface Backend
{
    ManipulatorData getManipulator(final UUID p0);
    
    void setManipulator(final UUID p0, final ManipulatorData p1);
}
