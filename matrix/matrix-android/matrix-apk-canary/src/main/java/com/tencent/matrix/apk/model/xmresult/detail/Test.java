

package com.tencent.matrix.apk.model.xmresult.detail;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private JsonObject jsonObject;
    private List<String> unknow = new ArrayList();

    public Test() {
    }

    public JsonObject getJsonObject() {
        return this.jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public List<String> getUnknow() {
        return this.unknow;
    }
}
