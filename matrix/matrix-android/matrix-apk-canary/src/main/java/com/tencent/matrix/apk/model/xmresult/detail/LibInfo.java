

package com.tencent.matrix.apk.model.xmresult.detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibInfo {
    private List<String> libDir = new ArrayList();
    private List<String> stlLibs = new ArrayList();
    private List<String> unstrippedSo = new ArrayList();
    private Map<String, Long> libSize = new HashMap();

    public LibInfo() {
    }

    public List<String> getLibDir() {
        return this.libDir;
    }

    public List<String> getStlLibs() {
        return this.stlLibs;
    }

    public List<String> getUnstrippedSo() {
        return this.unstrippedSo;
    }

    public Map<String, Long> getLibSize() {
        return this.libSize;
    }
}
