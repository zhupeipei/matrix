

package com.tencent.matrix.apk.model.xmresult.detail;

import com.android.utils.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInfo {
    private Map<String, Long> uncompressedFile = new HashMap();
    private Map<Pair<String, Long>, List<String>> redundentFile = new HashMap();
    private Map<String, Pair<String, Long>> redundentIndex = new HashMap();
    private Map<String, List<String>> md5Map = new HashMap();

    public FileInfo() {
    }

    public Map<String, Long> getUncompressedFile() {
        return this.uncompressedFile;
    }

    public Map<Pair<String, Long>, List<String>> getRedundentFile() {
        return this.redundentFile;
    }

    public Map<String, Pair<String, Long>> getRedundentIndex() {
        return this.redundentIndex;
    }

    public Map<String, List<String>> getMd5Map() {
        return this.md5Map;
    }
}
