

package com.tencent.matrix.apk.model.xmresult.detail;

import com.android.utils.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApkSizeInfo {
    private long apkSize = -1L;
    private Map<String, Long> dexSizeMap = new HashMap();
    private Map<String, String> dexPathMap = new HashMap();
    private Map<String, Integer> fileTypeCounting = new HashMap();
    private Map<String, Long> fileCapcityCounting = new HashMap();
    private Map<String, List<Pair<String, Long>>> fileSizeDetail = new HashMap();

    public ApkSizeInfo() {
    }

    public long getApkSize() {
        return this.apkSize;
    }

    public void setApkSize(long apkSize) {
        this.apkSize = apkSize;
    }

    public Map<String, Long> getDexSizeMap() {
        return this.dexSizeMap;
    }

    public Map<String, String> getDexPathMap() {
        return this.dexPathMap;
    }

    public Map<String, Integer> getFileTypeCounting() {
        return this.fileTypeCounting;
    }

    public Map<String, Long> getFileCapcityCounting() {
        return this.fileCapcityCounting;
    }

    public Map<String, List<Pair<String, Long>>> getFileSizeDetail() {
        return this.fileSizeDetail;
    }
}
