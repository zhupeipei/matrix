

package com.tencent.matrix.apk.model.xmresult.detail;

import com.android.utils.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageInfo {
    private Map<String, Long> imageSizeMap = new HashMap();
    private List<Pair<String, Long>> noneAlphaPng = new ArrayList();
    private Map<String, List<String>> imageSuffixItemMap = new HashMap();
    private Map<String, Long> imageSuffixSizeMap = new HashMap();

    public ImageInfo() {
    }

    public Map<String, Long> getImageSizeMap() {
        return this.imageSizeMap;
    }

    public List<Pair<String, Long>> getNoneAlphaPng() {
        return this.noneAlphaPng;
    }

    public Map<String, List<String>> getImageSuffixItemMap() {
        return this.imageSuffixItemMap;
    }

    public Map<String, Long> getImageSuffixSizeMap() {
        return this.imageSuffixSizeMap;
    }
}
