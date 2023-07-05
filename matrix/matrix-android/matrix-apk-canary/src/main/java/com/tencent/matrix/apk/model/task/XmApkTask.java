package com.tencent.matrix.apk.model.task;

import com.android.utils.Pair;
import com.tencent.matrix.apk.model.exception.TaskExecuteException;
import com.tencent.matrix.apk.model.exception.TaskInitException;
import com.tencent.matrix.apk.model.job.JobConfig;
import com.tencent.matrix.apk.model.result.TaskResult;
import com.tencent.matrix.apk.model.result.TaskResultFactory;
import com.tencent.matrix.apk.model.result.TaskXmlyResult;
import com.tencent.matrix.javalib.util.Log;
import com.tencent.matrix.javalib.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tencent.matrix.apk.model.result.TaskResultFactory.TASK_RESULT_TYPE_XMLY;

public class XmApkTask extends ApkTask {
    private static final String TAG = XmApkTask.class.getSimpleName();
    private File apkFile;
    private Map<String, Pair<Long, Long>> entrySizeMap;
    private Map<String, String> entryNameMap;
    private Pair a;

    public XmApkTask(JobConfig config, Map<String, String> params) {
        super(config, params);
        this.type = TaskFactory.TASK_TYPE_XMAPK;
    }

    public void init() throws TaskInitException {
        super.init();
        if (Util.isNullOrNil(this.config.getApkPath())) {
            throw new TaskInitException(TAG + "---APK-FILE-PATH can not be null!");
        } else {
            Log.i(TAG, "inputPath:%s", new Object[]{this.config.getApkPath()});
            this.apkFile = new File(this.config.getApkPath());
            if (!this.apkFile.exists()) {
                throw new TaskInitException(TAG + "---'" + this.config.getApkPath() + "' is not exist!");
            } else {
                this.entrySizeMap = this.config.getEntrySizeMap();
                this.entryNameMap = this.config.getEntryNameMap();
            }
        }
    }

    public TaskResult call() throws TaskExecuteException {
        TaskXmlyResult result = null;

        try {
            result = (TaskXmlyResult) TaskResultFactory.factory(this.getType(), TASK_RESULT_TYPE_XMLY, this.config);
            result.getReport().getApkSizeInfo().setApkSize(this.apkFile.length());
            for (Map.Entry<String, String> entry : entryNameMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                Pair<Long, Long> pair = entrySizeMap.get(value);
                if (pair.getFirst() == pair.getSecond()) {
                    result.getReport().getFileInfo().getUncompressedFile().put(value, pair.getFirst());
                }

                String suffix = this.getSuffix(value);
                if ("dex".equals(suffix)) {
                    result.getReport().getApkSizeInfo().getDexSizeMap().put(value, pair.getSecond());
                    result.getReport().getApkSizeInfo().getDexPathMap().put(value, key);
                }

                if (!result.getReport().getApkSizeInfo().getFileTypeCounting().containsKey(suffix)) {
                    result.getReport().getApkSizeInfo().getFileTypeCounting().put(suffix, 1);
                    result.getReport().getApkSizeInfo().getFileCapcityCounting().put(suffix, pair.getSecond());
                    List<Pair<String, Long>> detailSizeList = new ArrayList();
                    detailSizeList.add(Pair.of(value, pair.getSecond()));
                    result.getReport().getApkSizeInfo().getFileSizeDetail().put(suffix, detailSizeList);
                } else {
                    result.getReport().getApkSizeInfo().getFileTypeCounting().replace(suffix, (Integer) result.getReport().getApkSizeInfo().getFileTypeCounting().get(suffix) + 1);
                    result.getReport().getApkSizeInfo().getFileCapcityCounting().replace(suffix, (Long) result.getReport().getApkSizeInfo().getFileCapcityCounting().get(suffix) + (Long) ((Pair) this.entrySizeMap.get(this.entryNameMap.get(key))).getSecond());
                    ((List) result.getReport().getApkSizeInfo().getFileSizeDetail().get(suffix)).add(Pair.of(this.entryNameMap.get(key), ((Pair) this.entrySizeMap.get(this.entryNameMap.get(key))).getSecond()));
                }
            }
        } catch (Exception var6) {
        }
        return result;
    }

    private String getSuffix(String name) {
        int index = name.lastIndexOf(46);
        return index >= 0 && index < name.length() - 1 ? name.substring(index + 1) : "NoSuffix";
    }
}
