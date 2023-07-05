

package com.tencent.matrix.apk.model.xmresult.detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityInfo {
    private int minSDK = -1;
    private int targetSDK = -1;
    private List<String> activity = new ArrayList();
    private Map<String, String> service = new HashMap();
    private List<String> usesPermission = new ArrayList();
    private List<String> permission = new ArrayList();

    public SecurityInfo() {
    }

    public int getMinSDK() {
        return this.minSDK;
    }

    public void setMinSDK(int minSDK) {
        this.minSDK = minSDK;
    }

    public int getTargetSDK() {
        return this.targetSDK;
    }

    public void setTargetSDK(int targetSDK) {
        this.targetSDK = targetSDK;
    }

    public List<String> getActivity() {
        return this.activity;
    }

    public Map<String, String> getService() {
        return this.service;
    }

    public List<String> getUses_permission() {
        return this.usesPermission;
    }

    public List<String> getPermission() {
        return this.permission;
    }
}
