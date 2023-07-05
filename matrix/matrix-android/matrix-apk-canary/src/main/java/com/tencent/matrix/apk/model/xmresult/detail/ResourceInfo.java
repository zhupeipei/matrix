

package com.tencent.matrix.apk.model.xmresult.detail;

import com.android.dexdeps.FieldRef;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceInfo {
    private Map<String, String> totalResource = new HashMap();
    private Map<String, String> unusedResource = new HashMap();
    private Map<String, List<FieldRef>> rFileFieldMap = new HashMap();

    public ResourceInfo() {
    }

    public Map<String, List<FieldRef>> getRFileFieldMap() {
        return this.rFileFieldMap;
    }

    public Map<String, String> getTotalResource() {
        return this.totalResource;
    }

    public Map<String, String> getUnusedResource() {
        return this.unusedResource;
    }
}
