

package com.tencent.matrix.apk.model.xmresult.detail;

import com.android.utils.Pair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassAndMethodInfo {
    private Map<String, List<Pair<String, String>>> nonXmclassMap = new HashMap();
    private Set<String> nonXmClassGroupSet = new HashSet();
    private Map<String, List<String>> classMap = new HashMap();
    private Map<String, Pair<String, String>> nonXmmethodMap = new HashMap();
    private Map<String, List<String>> methodMap = new HashMap();
    private Map<String, Pair<Integer, Integer>> dexMethodNumberMap = new HashMap();

    public ClassAndMethodInfo() {
    }

    public Map<String, List<Pair<String, String>>> getNonXmclassMap() {
        return this.nonXmclassMap;
    }

    public Set<String> getNonXmClassGroupSet() {
        return this.nonXmClassGroupSet;
    }

    public Map<String, List<String>> getClassMap() {
        return this.classMap;
    }

    public Map<String, Pair<String, String>> getNonXmmethodMap() {
        return this.nonXmmethodMap;
    }

    public Map<String, List<String>> getMethodMap() {
        return this.methodMap;
    }

    public Map<String, Pair<Integer, Integer>> getDexMethodNumberMap() {
        return this.dexMethodNumberMap;
    }
}
