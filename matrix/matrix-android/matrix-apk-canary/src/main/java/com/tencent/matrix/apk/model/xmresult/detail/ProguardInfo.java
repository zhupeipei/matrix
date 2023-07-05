

package com.tencent.matrix.apk.model.xmresult.detail;

import java.util.HashMap;
import java.util.Map;

public class ProguardInfo {
    private Map<String, String> proguardClassMap = new HashMap();
    private Map<String, String> resproguardClassMap = new HashMap();

    public ProguardInfo() {
    }

    public Map<String, String> getProguardClassMap() {
        return this.proguardClassMap;
    }

    public void setProguardClassMap(Map<String, String> proguardClassMap) {
        this.proguardClassMap = new HashMap();
        this.proguardClassMap.putAll(proguardClassMap);
    }

    public Map<String, String> getResproguardClassMap() {
        return this.resproguardClassMap;
    }

    public void setResproguardClassMap(Map<String, String> resproguardClassMap) {
        this.resproguardClassMap = new HashMap();
        this.resproguardClassMap.putAll(resproguardClassMap);
    }
}
