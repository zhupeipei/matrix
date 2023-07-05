

package com.tencent.matrix.apk.model.result;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tencent.matrix.apk.model.task.TaskFactory;
import com.tencent.matrix.apk.model.xmresult.XmReport;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;

public class TaskXmlyResult extends TaskResult {
    private static TaskXmlyResult taskResult = null;
    private JsonObject config;
    private JsonObject jsonObject;
    private Map<String, String> result;
    private XmReport report;

    public static TaskXmlyResult getTaskXmlyResult(int taskType, JsonObject config) throws ParserConfigurationException {
        if (null == taskResult) {
            synchronized (TaskXmlyResult.class) {
                if (null == taskResult) {
                    taskResult = new TaskXmlyResult(taskType, config);
                }
            }
        }

        return taskResult;
    }

    public TaskXmlyResult(int taskType, JsonObject config) throws ParserConfigurationException {
        super(taskType);
        this.config = config;
        this.jsonObject = new JsonObject();
        this.jsonObject.addProperty("taskType", taskType);
        this.jsonObject.addProperty("taskDescription", (String) TaskFactory.TaskDescription.get(taskType));
        this.result = new HashMap();
        this.report = new XmReport();
    }

    public XmReport getReport() {
        return this.report;
    }

    public Map getResult() {
        return this.result;
    }

    public void format(TaskJsonResult source) {
        if (null != source) {
            Iterator var2 = source.getResult().entrySet().iterator();

            while (var2.hasNext()) {
                Entry<String, JsonElement> temp = (Entry) var2.next();
                if (null != temp) {
                    this.result.put(temp.getKey(), null == ((JsonElement) temp.getValue()).toString() ? "null" : ((JsonElement) temp.getValue()).toString());
                }
            }

        }
    }
}
