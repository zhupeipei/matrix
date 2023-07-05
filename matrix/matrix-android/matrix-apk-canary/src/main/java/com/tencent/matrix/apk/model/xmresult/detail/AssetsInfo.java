

package com.tencent.matrix.apk.model.xmresult.detail;

import java.util.HashMap;
import java.util.Map;

public class AssetsInfo {
    private Map<String, Long> assetsFileSize = new HashMap();
    private Map<String, Long> unusedAssetsFileSize = new HashMap();

    public AssetsInfo() {
    }

    public int getAssetsFileNumber() {
        return this.assetsFileSize.size();
    }

    public Map<String, Long> getAssetsFileSize() {
        return this.assetsFileSize;
    }

    public Map<String, Long> getUnusedAssetsFileSize() {
        return this.unusedAssetsFileSize;
    }
}
