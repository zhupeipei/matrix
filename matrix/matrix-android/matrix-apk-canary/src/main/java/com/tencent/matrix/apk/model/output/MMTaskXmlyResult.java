

package com.tencent.matrix.apk.model.output;

import com.google.gson.JsonObject;
import com.tencent.matrix.apk.model.result.TaskXmlyResult;
import javax.xml.parsers.ParserConfigurationException;

public class MMTaskXmlyResult extends TaskXmlyResult {
    private static final String TAG = MMTaskXmlyResult.class.getSimpleName();

    public MMTaskXmlyResult(int taskType, JsonObject config) throws ParserConfigurationException {
        super(taskType, config);
    }
}
