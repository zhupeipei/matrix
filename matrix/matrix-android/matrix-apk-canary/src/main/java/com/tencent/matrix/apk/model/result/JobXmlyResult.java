package com.tencent.matrix.apk.model.result;

import com.android.dexdeps.FieldRef;
import com.android.utils.Pair;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tencent.matrix.apk.model.job.JobConfig;
import com.tencent.matrix.apk.model.job.JobConstants;
import com.tencent.matrix.apk.util.FileUtil;
import com.tencent.matrix.apk.util.RequestUtil;
import com.tencent.matrix.javalib.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JobXmlyResult extends JobResult {
    private static final String TAG = "JobXmlyResult";
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private JobConfig config;
    private File outputDir;
    private final List<String> classDetailList;
    private final List<String> methodDetailList;
    private static final String HEAD_PART = "MainApp_v";
    private static final String TRAIL_PART = "_c";

    public JobXmlyResult(String format, JobConfig config) {
        this.format = format;
        this.config = config;
        this.resultList = new ArrayList();
        this.classDetailList = new ArrayList();
        this.methodDetailList = new ArrayList();
    }

    public void output() {
        try {
            this.createOutputDirectory();
            if (null == this.outputDir) {
                return;
            }

            TaskXmlyResult taskResult = (TaskXmlyResult) TaskResultFactory.factory(0, TaskResultFactory.TASK_RESULT_TYPE_XMLY, this.config);
            JsonObject reportInfoObject = this.createReportInfoObject();
            File reportInfo = new File(this.outputDir, "reportInfo.json");
            reportInfo.createNewFile();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportInfo)));
            writer.write(reportInfoObject.toString());
            writer.flush();
            writer.close();
            JsonObject apkInfoObject = this.createApkInfoObject(taskResult);
            File apkInfo = new File(this.outputDir, "apkInfo.json");
            apkInfo.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(apkInfo)));
            writer.write(apkInfoObject.toString());
            writer.flush();
            writer.close();
            this.outputFileSizeDetailInfo(taskResult, this.outputDir);
            JsonObject authorityInfoJsonObject = this.createAuthorityInfoObject(taskResult);
            File authorityInfo = new File(this.outputDir, "authorityInfo.json");
            apkInfo.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(authorityInfo)));
            writer.write(authorityInfoJsonObject.toString());
            writer.flush();
            writer.close();
            JsonObject fileInfoObject = this.createFileInfoObject(taskResult);
            File fileInfo = new File(this.outputDir, "fileInfo.json");
            fileInfo.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileInfo)));
            writer.write(fileInfoObject.toString());
            writer.flush();
            writer.close();
            JsonObject redundantFileObject = this.createRedundantFileInfoObject(taskResult);
            File redundantFile = new File(this.outputDir, "redundant.json");
            redundantFile.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(redundantFile)));
            writer.write(redundantFileObject.toString());
            writer.flush();
            writer.close();
            JsonObject imageInfoObject = this.createImageInfoObject(taskResult);
            File imageInfo = new File(this.outputDir, "imageInfo.json");
            imageInfo.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(imageInfo)));
            writer.write(imageInfoObject.toString());
            writer.flush();
            writer.close();
            this.outputClassDetailInfo(taskResult, this.outputDir);
            JsonObject classInfoObject = this.createClassInfoObject(taskResult);
            File classInfo = new File(this.outputDir, "classInfo.json");
            classInfo.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classInfo)));
            writer.write(classInfoObject.toString());
            writer.flush();
            writer.close();
            this.outputMethodDetailInfo(taskResult, this.outputDir);
            JsonObject methodInfoObject = this.createMethodInfoObject(taskResult);
            File methodInfo = new File(this.outputDir, "methodInfo.json");
            methodInfo.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(methodInfo)));
            writer.write(methodInfoObject.toString());
            writer.flush();
            writer.close();
            JsonArray unsedResourceDetailObject = this.createUnusedResourceDetailInfo(taskResult);
            File unsedResourceDetail = new File(this.outputDir, "unusedResourceDetail.json");
            unsedResourceDetail.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(unsedResourceDetail)));
            writer.write(unsedResourceDetailObject.toString());
            writer.flush();
            writer.close();
            JsonObject unusedInfoObject = this.createUnusedInfoObject(taskResult);
            File unusedInfo = new File(this.outputDir, "unusedInfo.json");
            unusedInfo.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(unusedInfo)));
            writer.write(unusedInfoObject.toString());
            writer.flush();
            writer.close();
            JsonObject libInfoObject = this.createLibInfoObject(taskResult);
            File libInfo = new File(this.outputDir, "libInfo.json");
            libInfo.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(libInfo)));
            writer.write(libInfoObject.toString());
            writer.flush();
            writer.close();
            this.uploadResult(this.outputDir);
        } catch (Exception var25) {
        }

    }

    private void uploadResult(File outputDir) {
        String targetUrl = this.config.getTargetUrl();
        Log.i("JobXmlyResult", null == targetUrl ? "emptyUrl" : targetUrl, new Object[0]);
        if (null != targetUrl) {
            File file = FileUtil.zipDirectory(outputDir);
            if (null != file) {
                System.out.println("The Size of " + file.getName() + " is " + file.length());
            } else {
                System.out.println("The file " + file.getName() + " doesn't exist.");
            }

            Map<String, String> param = new HashMap();
            String pipeLineHistoryId = null == this.config.getPipeLineHistoryId() ? "" : this.config.getPipeLineHistoryId();
            System.out.println("pipeLineHistoryId: " + pipeLineHistoryId);
            param.put("pipelineHistoryId", pipeLineHistoryId);
            String appVersion = "";
            if (null != this.config.getApkPath()) {
                String apkName = (new File(this.config.getApkPath())).getName();
                if (apkName.contains("MainApp_v") && apkName.contains("_c")) {
                    appVersion = apkName.substring(apkName.indexOf("MainApp_v") + "MainApp_v".length(), apkName.indexOf("_c"));
                }
            }

            System.out.println("appVersion: " + appVersion);
            param.put("appVersion", appVersion);
            Map<String, File> fileMap = new HashMap();
            fileMap.put("file", file);
            System.out.println("TargetUrl: " + targetUrl);
            String reportResult = RequestUtil.sendPostRequestWithFile(targetUrl, param, fileMap);
            System.out.println();
            System.out.println("Report Result: " + reportResult);
            System.out.println("*********************************");
            System.out.println();
        }

    }

    private void createOutputDirectory() {
        File apkPath = new File(this.config.getApkPath());
        if (apkPath.exists()) {
            try {
                this.outputDir = new File(apkPath.getParentFile(), "CheckerReport");
                FileUtil.deleteDirectory(this.outputDir);
                if (!this.outputDir.mkdirs()) {
                    this.outputDir = null;
                }
            } catch (Exception var3) {
            }
        }

    }

    private JsonObject createReportInfoObject() {
        JsonObject reportInfoObject = new JsonObject();
        long time = System.currentTimeMillis();
        reportInfoObject.addProperty("report_create_time", time);
        reportInfoObject.addProperty("report_create_format_time", TIME_FORMAT.format(new Date(time)));
        File apk = new File(this.config.getApkPath());
        if (apk.exists()) {
            reportInfoObject.addProperty("target_apk", apk.getName());
        }

        return reportInfoObject;
    }

    private JsonObject createApkInfoObject(TaskXmlyResult result) {
        List<String> keys = new ArrayList();
        JsonObject apkInfoObject = new JsonObject();
        apkInfoObject.addProperty("apk_size", result.getReport().getApkSizeInfo().getApkSize());
        JsonArray dexJsonArray = new JsonArray();
        JsonArray dexDetailJsonArray = new JsonArray();
        keys.clear();
        keys.addAll(result.getReport().getApkSizeInfo().getDexSizeMap().keySet());
        Collections.sort(keys);
        Iterator var6 = keys.iterator();

        while (var6.hasNext()) {
            String temp = (String) var6.next();
            String dexName = temp;
            if (temp.contains("/")) {
                dexName = temp.substring(temp.lastIndexOf("/") + 1);
            }

            JsonObject tempObject = new JsonObject();
            tempObject.addProperty("name", dexName);
            tempObject.addProperty("size", (Long) result.getReport().getApkSizeInfo().getDexSizeMap().get(temp));
            tempObject.addProperty("path", (String) result.getReport().getApkSizeInfo().getDexPathMap().get(temp));
            dexDetailJsonArray.add(tempObject);
            dexJsonArray.add(dexName);
        }

        apkInfoObject.add("dex_list", dexJsonArray);
        apkInfoObject.add("dex_detail", dexDetailJsonArray);
        JsonArray suffixArray = new JsonArray();
        JsonArray suffixDetailArray = new JsonArray();
        keys.clear();
        keys.addAll(result.getReport().getApkSizeInfo().getFileTypeCounting().keySet());
        Collections.sort(keys);
        Iterator var13 = keys.iterator();

        while (var13.hasNext()) {
            String temp = (String) var13.next();
            JsonObject tempObject = new JsonObject();
            tempObject.addProperty("type", temp);
            tempObject.addProperty("file_amount", (Number) result.getReport().getApkSizeInfo().getFileTypeCounting().get(temp));
            tempObject.addProperty("total_size", (Number) result.getReport().getApkSizeInfo().getFileCapcityCounting().get(temp));
            suffixDetailArray.add(tempObject);
            suffixArray.add(temp);
        }

        apkInfoObject.add("suffix_list", suffixArray);
        apkInfoObject.add("suffix_detail", suffixDetailArray);
        return apkInfoObject;
    }

    private JsonObject createAuthorityInfoObject(TaskXmlyResult result) {
        JsonObject authorityInfoObject = new JsonObject();
        JsonArray highLevelPermissionArray = new JsonArray();
        JsonArray permissionArray = new JsonArray();
        HashSet<String> removeRedundantPermissionHelper = new HashSet();
        removeRedundantPermissionHelper.addAll(result.getReport().getSecurityInfo().getUses_permission());
        removeRedundantPermissionHelper.addAll(result.getReport().getSecurityInfo().getPermission());
        Iterator var6 = removeRedundantPermissionHelper.iterator();

        while (var6.hasNext()) {
            String usesPermission = (String) var6.next();
            if (JobConstants.SENSITIVE_PERMISSION.contains(usesPermission)) {
                highLevelPermissionArray.add(usesPermission);
            } else {
                permissionArray.add(usesPermission);
            }
        }

        JsonArray serviceArray = new JsonArray();
        Iterator var11 = result.getReport().getSecurityInfo().getService().keySet().iterator();

        while (var11.hasNext()) {
            String service = (String) var11.next();
            JsonObject serviceItem = new JsonObject();
            serviceItem.addProperty("service_name", service);
            serviceItem.addProperty("service_process", (String) result.getReport().getSecurityInfo().getService().get(service));
            serviceArray.add(serviceItem);
        }

        authorityInfoObject.addProperty("high_level_permission_amount", highLevelPermissionArray.size());
        authorityInfoObject.add("high_level_permission_list", highLevelPermissionArray);
        authorityInfoObject.addProperty("permission_amount", permissionArray.size());
        authorityInfoObject.add("permission_list", permissionArray);
        authorityInfoObject.addProperty("service_amount", result.getReport().getSecurityInfo().getService().size());
        authorityInfoObject.add("service_list", serviceArray);
        return authorityInfoObject;
    }

    private JsonObject createFileInfoObject(TaskXmlyResult result) {
        JsonObject fileInfoObject = new JsonObject();
        JsonArray fileArray = new JsonArray();
        Iterator var4 = result.getReport().getFileInfo().getMd5Map().keySet().iterator();

        while (var4.hasNext()) {
            String md5 = (String) var4.next();
            Iterator var6 = ((List) result.getReport().getFileInfo().getMd5Map().get(md5)).iterator();

            while (var6.hasNext()) {
                String fileName = (String) var6.next();
                JsonObject temp = new JsonObject();
                temp.addProperty("MD5", md5);
                temp.addProperty("file_name", fileName);
                fileArray.add(temp);
            }
        }

        JsonArray uncompressedArray = new JsonArray();
        Iterator var10 = result.getReport().getFileInfo().getUncompressedFile().keySet().iterator();

        while (var10.hasNext()) {
            String fileName = (String) var10.next();
            JsonObject temp = new JsonObject();
            temp.addProperty("uncompressed_file_name", fileName);
            temp.addProperty("file_size", (Number) result.getReport().getFileInfo().getUncompressedFile().get(fileName));
            uncompressedArray.add(temp);
        }

        fileInfoObject.add("file_list", fileArray);
        fileInfoObject.add("uncompressed_list", uncompressedArray);
        return fileInfoObject;
    }

    private JsonObject createRedundantFileInfoObject(TaskXmlyResult result) {
        JsonObject redundantFileObject = new JsonObject();
        long totalRedundantSize = 0L;
        Map<String, Long> typeSizeMap = new HashMap();
        String noSuffix = "NoSuffix";
        JsonArray redundantArray = new JsonArray();
        Iterator var8 = result.getReport().getFileInfo().getRedundentFile().keySet().iterator();

        while (var8.hasNext()) {
            Pair<String, Long> commen = (Pair) var8.next();
            Iterator var10 = ((List) result.getReport().getFileInfo().getRedundentFile().get(commen)).iterator();

            while (var10.hasNext()) {
                String fileName = (String) var10.next();
                totalRedundantSize += (Long) commen.getSecond();
                String pureName = fileName;
                if (fileName.contains("/")) {
                    pureName = fileName.substring(fileName.lastIndexOf("/") + 1);
                }

                String suffix = this.getSuffix(pureName);
                if (null == suffix) {
                    suffix = "NoSuffix";
                }

                if (!typeSizeMap.containsKey(suffix)) {
                    typeSizeMap.put(suffix, 0L);
                }

                long size = (Long) typeSizeMap.get(suffix);
                size += (Long) commen.getSecond();
                typeSizeMap.put(suffix, size);
                JsonObject temp = new JsonObject();
                temp.addProperty("MD5", (String) commen.getFirst());
                temp.addProperty("file_size", (Number) commen.getSecond());
                temp.addProperty("file_name", fileName);
                temp.addProperty("file_type", suffix);
                redundantArray.add(temp);
            }
        }

        JsonArray typeSizeArray = new JsonArray();
        Iterator var18 = typeSizeMap.keySet().iterator();

        while (var18.hasNext()) {
            String type = (String) var18.next();
            JsonObject temp = new JsonObject();
            temp.addProperty("type_name", type);
            temp.addProperty("type_total_size", (Number) typeSizeMap.get(type));
            typeSizeArray.add(temp);
        }

        redundantFileObject.addProperty("total_size", totalRedundantSize);
        redundantFileObject.add("size_map", typeSizeArray);
        redundantFileObject.add("redundant_list", redundantArray);
        return redundantFileObject;
    }

    private JsonObject createImageInfoObject(TaskXmlyResult result) {
        JsonObject imageInfoObject = new JsonObject();
        JsonArray suffixSizeArray = new JsonArray();
        Iterator var4 = result.getReport().getImageInfo().getImageSuffixSizeMap().keySet().iterator();

        while (var4.hasNext()) {
            String suffix = (String) var4.next();
            JsonObject suffixSizeObject = new JsonObject();
            suffixSizeObject.addProperty("suffix_name", suffix);
            suffixSizeObject.addProperty("total_size", (Number) result.getReport().getImageInfo().getImageSuffixSizeMap().get(suffix));
            suffixSizeArray.add(suffixSizeObject);
        }

        JsonObject suffixItemObject = new JsonObject();
        Iterator var12 = result.getReport().getImageInfo().getImageSuffixItemMap().keySet().iterator();

        while (var12.hasNext()) {
            String suffix = (String) var12.next();
            JsonArray tempArray = new JsonArray();
            Iterator var8 = ((List) result.getReport().getImageInfo().getImageSuffixItemMap().get(suffix)).iterator();

            while (var8.hasNext()) {
                String item = (String) var8.next();
                JsonObject tempItem = new JsonObject();
                tempItem.addProperty("image_name", item);
                tempItem.addProperty("image_size", (Number) result.getReport().getImageInfo().getImageSizeMap().get(item));
                tempArray.add(tempItem);
            }

            suffixItemObject.add(suffix, tempArray);
        }

        JsonArray nonAlphaPNGArray = new JsonArray();
        Iterator var15 = result.getReport().getImageInfo().getNoneAlphaPng().iterator();

        while (var15.hasNext()) {
            Pair<String, Long> nonAlpha = (Pair) var15.next();
            JsonObject temp = new JsonObject();
            temp.addProperty("png_name", (String) nonAlpha.getFirst());
            temp.addProperty("png_size", (Number) nonAlpha.getSecond());
            nonAlphaPNGArray.add(temp);
        }

        imageInfoObject.add("suffix_total_size", suffixSizeArray);
        imageInfoObject.add("image_list", suffixItemObject);
        imageInfoObject.add("nonAlpha_png_image", nonAlphaPNGArray);
        return imageInfoObject;
    }

    private JsonObject createClassInfoObject(TaskXmlyResult result) {
        JsonObject classInfoObject = new JsonObject();
        int totalClassAmount = 0;
        JsonArray nonXmGroupArray = new JsonArray();
        Iterator var5 = result.getReport().getClassAndMethodInfo().getNonXmClassGroupSet().iterator();

        while (var5.hasNext()) {
            String nonXmGroup = (String) var5.next();
            nonXmGroupArray.add(nonXmGroup);
        }

        JsonArray dexInfoArray = new JsonArray();
        List<String> orderedList = new ArrayList();
        orderedList.addAll(result.getReport().getClassAndMethodInfo().getClassMap().keySet());
        Collections.sort(orderedList);

        for (int i = 0; i < orderedList.size(); ++i) {
            JsonObject tempObject = new JsonObject();
            tempObject.addProperty("dex_name", (String) orderedList.get(i));
            tempObject.addProperty("class_amount", ((List) result.getReport().getClassAndMethodInfo().getClassMap().get(orderedList.get(i))).size());
            if (i < this.classDetailList.size()) {
                tempObject.addProperty("detail_class_file_path", (String) this.classDetailList.get(i));
            }

            dexInfoArray.add(tempObject);
            totalClassAmount += ((List) result.getReport().getClassAndMethodInfo().getClassMap().get(orderedList.get(i))).size();
        }

        classInfoObject.addProperty("total_class_amount", totalClassAmount);
        classInfoObject.add("class_in_dex", dexInfoArray);
        classInfoObject.add("nonXm_class_group", nonXmGroupArray);
        return classInfoObject;
    }

    private void outputClassDetailInfo(TaskXmlyResult result, File outputDir) {
        this.classDetailList.clear();
        File classDetailDir = new File(outputDir, "ClassDetail");
        classDetailDir.mkdirs();
        List<String> orderedList = new ArrayList();
        orderedList.addAll(result.getReport().getClassAndMethodInfo().getClassMap().keySet());
        Collections.sort(orderedList);

        for (int i = 0; i < orderedList.size(); ++i) {
            String dexName = (String) orderedList.get(i);
            if (dexName.contains(".")) {
                dexName = dexName.substring(0, dexName.indexOf("."));
            }

            File dexFile = new File(classDetailDir, dexName + ".json");
            dexName = (String) orderedList.get(i);

            try {
                dexFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dexFile)));
                JsonObject classDetailObject = new JsonObject();
                classDetailObject.addProperty("dex_file_name", dexName);
                JsonArray classArray = new JsonArray();
                Iterator var11 = ((List) result.getReport().getClassAndMethodInfo().getClassMap().get(dexName)).iterator();

                while (var11.hasNext()) {
                    String fullClassName = (String) var11.next();
                    classArray.add(fullClassName);
                }

                classDetailObject.add("class_list", classArray);
                writer.write(classDetailObject.toString());
                writer.flush();
                writer.close();
                String relativePath = dexFile.getAbsolutePath().substring(dexFile.getAbsolutePath().indexOf(outputDir.getAbsolutePath() + 1));
                this.classDetailList.add(relativePath);
            } catch (Exception var13) {
            }
        }

    }

    private JsonObject createMethodInfoObject(TaskXmlyResult result) {
        JsonObject methodInfoObject = new JsonObject();
        int totalMethodAmount = 0;
        JsonArray dexInfoArray = new JsonArray();
        List<String> orderedList = new ArrayList();
        orderedList.addAll(result.getReport().getClassAndMethodInfo().getMethodMap().keySet());
        Collections.sort(orderedList);

        for (int i = 0; i < orderedList.size(); ++i) {
            JsonObject tempObject = new JsonObject();
            tempObject.addProperty("dex_name", (String) orderedList.get(i));
            tempObject.addProperty("method_amount", ((List) result.getReport().getClassAndMethodInfo().getMethodMap().get(orderedList.get(i))).size());
            tempObject.addProperty("internal_method_amount", (Number) ((Pair) result.getReport().getClassAndMethodInfo().getDexMethodNumberMap().get(orderedList.get(i))).getFirst());
            tempObject.addProperty("external_method_amount", (Number) ((Pair) result.getReport().getClassAndMethodInfo().getDexMethodNumberMap().get(orderedList.get(i))).getSecond());
            if (i < this.methodDetailList.size()) {
                tempObject.addProperty("detail_method_file_path", (String) this.classDetailList.get(i));
            }

            dexInfoArray.add(tempObject);
            totalMethodAmount += ((List) result.getReport().getClassAndMethodInfo().getMethodMap().get(orderedList.get(i))).size();
        }

        methodInfoObject.addProperty("total_method_amount", totalMethodAmount);
        methodInfoObject.add("method_in_dex", dexInfoArray);
        return methodInfoObject;
    }

    private void outputMethodDetailInfo(TaskXmlyResult result, File outputDir) {
        this.methodDetailList.clear();
        File methodDetailDir = new File(outputDir, "MethodDetail");
        methodDetailDir.mkdirs();
        List<String> orderedList = new ArrayList();
        orderedList.addAll(result.getReport().getClassAndMethodInfo().getMethodMap().keySet());
        Collections.sort(orderedList);

        for (int i = 0; i < orderedList.size(); ++i) {
            String dexName = (String) orderedList.get(i);
            if (dexName.contains(".")) {
                dexName = dexName.substring(0, dexName.indexOf("."));
            }

            File dexFile = new File(methodDetailDir, dexName + ".json");
            dexName = (String) orderedList.get(i);

            try {
                dexFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dexFile)));
                JsonObject classDetailObject = new JsonObject();
                classDetailObject.addProperty("dex_file_name", dexName);
                JsonArray classArray = new JsonArray();
                Iterator var11 = ((List) result.getReport().getClassAndMethodInfo().getMethodMap().get(dexName)).iterator();

                while (var11.hasNext()) {
                    String fullMethodName = (String) var11.next();
                    classArray.add(fullMethodName);
                }

                classDetailObject.add("method_list", classArray);
                writer.write(classDetailObject.toString());
                writer.flush();
                writer.close();
                String relativePath = dexFile.getAbsolutePath().substring(dexFile.getAbsolutePath().indexOf(outputDir.getAbsolutePath() + 1));
                this.methodDetailList.add(relativePath);
            } catch (Exception var13) {
            }
        }

    }

    private JsonObject createUnusedInfoObject(TaskXmlyResult result) {
        JsonObject unusedInfoObject = new JsonObject();
        JsonArray unusedAssetArray = new JsonArray();
        Iterator var4 = result.getReport().getAssetsInfo().getUnusedAssetsFileSize().keySet().iterator();

        while (var4.hasNext()) {
            String asset = (String) var4.next();
            JsonObject assetObject = new JsonObject();
            assetObject.addProperty("asset_name", asset);
            assetObject.addProperty("asset_size", (Number) result.getReport().getAssetsInfo().getAssetsFileSize().get(asset));
            unusedAssetArray.add(assetObject);
        }

        JsonArray unusedResourceArray = new JsonArray();
        Iterator var9 = result.getReport().getResourceInfo().getUnusedResource().keySet().iterator();

        while (var9.hasNext()) {
            String resource = (String) var9.next();
            JsonObject resourceObject = new JsonObject();
            resourceObject.addProperty("resource_name", resource);
            resourceObject.addProperty("resource_id", (String) result.getReport().getResourceInfo().getUnusedResource().get(resource));
            unusedResourceArray.add(resourceObject);
        }

        unusedInfoObject.addProperty("unused_asset_amount", result.getReport().getAssetsInfo().getUnusedAssetsFileSize().size());
        unusedInfoObject.addProperty("unused_resource_amount", result.getReport().getResourceInfo().getUnusedResource().size());
        unusedInfoObject.add("unused_asset_list", unusedAssetArray);
        unusedInfoObject.add("unused_resource_list", unusedResourceArray);
        return unusedInfoObject;
    }

    private JsonArray createUnusedResourceDetailInfo(TaskXmlyResult result) {
        JsonArray unusedResourceDetailArray = new JsonArray();
        Map<String, List<String>> unusedResourceLayoutMap = new HashMap();
        Map<String, List<String>> unusedResourceDrawableMap = new HashMap();
        Map<String, List<String>> unusedResourceOtherMap = new HashMap();
        Iterator var6 = JobConstants.MODULE_NAME.iterator();

        String resource;
        while (var6.hasNext()) {
            resource = (String) var6.next();
            unusedResourceLayoutMap.put(resource, new ArrayList());
            unusedResourceDrawableMap.put(resource, new ArrayList());
            unusedResourceOtherMap.put(resource, new ArrayList());
        }

        var6 = result.getReport().getResourceInfo().getUnusedResource().keySet().iterator();

        while (true) {
            while (true) {
                String forePart;
                String pureName;
                String resourceType;
                String bundleName;
                do {
                    do {
                        if (!var6.hasNext()) {
                            var6 = JobConstants.MODULE_NAME.iterator();

                            while (true) {
                                do {
                                    if (!var6.hasNext()) {
                                        return unusedResourceDetailArray;
                                    }

                                    resource = (String) var6.next();
                                } while (((List) unusedResourceLayoutMap.get(resource)).size() == 0 && ((List) unusedResourceDrawableMap.get(resource)).size() == 0 && ((List) unusedResourceOtherMap.get(resource)).size() == 0);

                                JsonObject bundleDetailObject = new JsonObject();
                                bundleDetailObject.addProperty("bundle_name", resource);
                                StringBuffer stringBuffer = new StringBuffer();
                                Iterator var16 = ((List) unusedResourceLayoutMap.get(resource)).iterator();

                                while (var16.hasNext()) {
                                    bundleName = (String) var16.next();
                                    stringBuffer.append(bundleName).append(",");
                                }

                                resourceType = stringBuffer.toString();
                                if (null != resourceType && resourceType.contains(",")) {
                                    resourceType = resourceType.substring(0, resourceType.length() - 1);
                                }

                                bundleDetailObject.addProperty("resource_layout_list", resourceType);
                                stringBuffer = new StringBuffer();
                                Iterator var18 = ((List) unusedResourceDrawableMap.get(resource)).iterator();

                                String otherResourceArray;
                                while (var18.hasNext()) {
                                    otherResourceArray = (String) var18.next();
                                    stringBuffer.append(otherResourceArray).append(",");
                                }

                                bundleName = stringBuffer.toString();
                                if (null != bundleName && bundleName.contains(",")) {
                                    bundleName = bundleName.substring(0, bundleName.length() - 1);
                                }

                                bundleDetailObject.addProperty("resource_drawable_list", bundleName);
                                stringBuffer = new StringBuffer();
                                Iterator var17 = ((List) unusedResourceOtherMap.get(resource)).iterator();

                                while (var17.hasNext()) {
                                    String resourceName = (String) var17.next();
                                    stringBuffer.append(resourceName).append(",");
                                }

                                otherResourceArray = stringBuffer.toString();
                                if (null != otherResourceArray && otherResourceArray.contains(",")) {
                                    otherResourceArray = otherResourceArray.substring(0, otherResourceArray.length() - 1);
                                }

                                bundleDetailObject.addProperty("resource_other_list", otherResourceArray);
                                unusedResourceDetailArray.add(bundleDetailObject);
                            }
                        }

                        resource = (String) var6.next();
                    } while (!resource.contains("."));

                    forePart = resource.substring(0, resource.lastIndexOf("."));
                    pureName = resource.substring(resource.lastIndexOf(".") + 1);
                } while (!forePart.contains("."));

                resourceType = forePart.substring(forePart.lastIndexOf(".") + 1);
                if (pureName.contains("_")) {
                    bundleName = pureName.substring(0, pureName.indexOf("_")).toLowerCase();
                    if ("component".equals(bundleName)) {
                        bundleName = "host";
                    }

                    if (bundleName.toLowerCase().startsWith("live")) {
                        bundleName = "live";
                    }

                    if (JobConstants.MODULE_NAME.contains(bundleName)) {
                        if (resourceType.toLowerCase().equals("layout")) {
                            ((List) unusedResourceLayoutMap.get(bundleName)).add(resource);
                        } else if (resourceType.toLowerCase().equals("drawable")) {
                            ((List) unusedResourceDrawableMap.get(bundleName)).add(resource);
                        } else {
                            ((List) unusedResourceOtherMap.get(bundleName)).add(resource);
                        }
                        continue;
                    }
                }

                if (resourceType.toLowerCase().equals("layout")) {
                    ((List) unusedResourceLayoutMap.get("UnKnown")).add(resource);
                } else if (resourceType.toLowerCase().equals("drawable")) {
                    ((List) unusedResourceDrawableMap.get("UnKnown")).add(resource);
                } else {
                    ((List) unusedResourceOtherMap.get("UnKnown")).add(resource);
                }
            }
        }
    }

    private JsonObject createRFileInfoObject(TaskXmlyResult result) {
        JsonObject rFileInfoObject = new JsonObject();
        JsonArray rFileArray = new JsonArray();
        Iterator var4 = result.getReport().getResourceInfo().getRFileFieldMap().keySet().iterator();

        while (var4.hasNext()) {
            String rFile = (String) var4.next();
            JsonObject rFileObject = new JsonObject();
            rFileObject.addProperty("r_file_name", rFile);
            rFileObject.addProperty("field_amount", ((List) result.getReport().getResourceInfo().getRFileFieldMap().get(rFile)).size());
            JsonArray fieldArray = new JsonArray();
            Iterator var8 = ((List) result.getReport().getResourceInfo().getRFileFieldMap().get(rFile)).iterator();

            while (var8.hasNext()) {
                FieldRef fieldRef = (FieldRef) var8.next();
                JsonObject fieldObject = new JsonObject();
                fieldObject.addProperty("field_name", fieldRef.getName());
                fieldObject.addProperty("field_type", fieldRef.getTypeName());
                fieldArray.add(fieldObject);
            }

            rFileObject.add("field_list", fieldArray);
            rFileArray.add(rFileObject);
        }

        rFileInfoObject.addProperty("r_file_amount", result.getReport().getResourceInfo().getRFileFieldMap().size());
        rFileInfoObject.add("r_file_list", rFileArray);
        return rFileInfoObject;
    }

    private JsonObject createLibInfoObject(TaskXmlyResult result) {
        JsonObject libInfoObject = new JsonObject();
        JsonArray libArray = new JsonArray();
        Iterator var4 = result.getReport().getLibInfo().getLibDir().iterator();

        while (var4.hasNext()) {
            String libName = (String) var4.next();
            JsonObject tempObject = new JsonObject();
            tempObject.addProperty("lib_name", libName);
            tempObject.addProperty("lib_size", (Number) result.getReport().getLibInfo().getLibSize().get(libName));
            libArray.add(tempObject);
        }

        JsonArray stlLibArray = new JsonArray();
        Iterator var10 = result.getReport().getLibInfo().getStlLibs().iterator();

        while (var10.hasNext()) {
            String stlLib = (String) var10.next();
            JsonObject tempObject = new JsonObject();
            tempObject.addProperty("lib_name", stlLib);
            tempObject.addProperty("lib_size", (Number) result.getReport().getLibInfo().getLibSize().get(stlLib));
            stlLibArray.add(tempObject);
        }

        JsonArray unstrippedSoArray = new JsonArray();
        Iterator var13 = result.getReport().getLibInfo().getUnstrippedSo().iterator();

        while (var13.hasNext()) {
            String unstrippedSo = (String) var13.next();
            JsonObject tempObject = new JsonObject();
            tempObject.addProperty("lib_name", unstrippedSo);
            tempObject.addProperty("lib_size", (Number) result.getReport().getLibInfo().getLibSize().get(unstrippedSo));
            unstrippedSoArray.add(tempObject);
        }

        libInfoObject.addProperty("lib_amount", result.getReport().getLibInfo().getLibDir().size());
        libInfoObject.add("lib_list", libArray);
        libInfoObject.addProperty("stl_lib_amount", result.getReport().getLibInfo().getStlLibs().size());
        libInfoObject.add("stl_lib_list", stlLibArray);
        libInfoObject.addProperty("unstripped_So_amount", result.getReport().getLibInfo().getUnstrippedSo().size());
        libInfoObject.add("unstripped_So_list", unstrippedSoArray);
        return libInfoObject;
    }

    private String createReportContent(TaskXmlyResult result) {
        if (null == result) {
            return "";
        } else {
            List<String> keys = new ArrayList();
            JsonObject reportContent = new JsonObject();
            File apk = new File(this.config.getApkPath());
            if (apk.exists()) {
                reportContent.addProperty("apkName", apk.getName());
            }

            reportContent.addProperty("bundleId", "com.ximalaya.android.ting");
            long time = System.currentTimeMillis();
            reportContent.addProperty("reportTime", TIME_FORMAT.format(new Date(time)));
            reportContent.addProperty("totalSize", result.getReport().getApkSizeInfo().getApkSize());
            String tempString = this.config.getBranch();
            reportContent.addProperty("brunch", null == tempString ? "" : tempString);
            tempString = this.config.getBuildNumber();
            reportContent.addProperty("buildNum", null == tempString ? "" : tempString);
            tempString = this.config.getBundleVersion();
            reportContent.addProperty("bundleVersion", null == tempString ? "" : tempString);
            JsonArray dexJsonArray = new JsonArray();
            keys.clear();
            keys.addAll(result.getReport().getApkSizeInfo().getDexSizeMap().keySet());
            Collections.sort(keys);
            Iterator var9 = keys.iterator();

            String temp;
            JsonObject tempObject;
            while (var9.hasNext()) {
                temp = (String) var9.next();
                if (temp.contains("/")) {
                    temp = temp.substring(temp.lastIndexOf("/") + 1);
                }

                tempObject = new JsonObject();
                tempObject.addProperty("name", temp);
                tempObject.addProperty("size", (Long) result.getReport().getApkSizeInfo().getDexSizeMap().get(temp));
                tempObject.addProperty("path", (String) result.getReport().getApkSizeInfo().getDexPathMap().get(temp));
                dexJsonArray.add(tempObject);
            }

            reportContent.add("dexList", dexJsonArray);
            JsonArray typeJsonArray = new JsonArray();
            keys.clear();
            keys.addAll(result.getReport().getApkSizeInfo().getFileTypeCounting().keySet());
            Collections.sort(keys);
            Iterator var28 = keys.iterator();

            while (var28.hasNext()) {
                temp = (String) var28.next();
                tempObject = new JsonObject();
                tempObject.addProperty("type", temp);
                tempObject.addProperty("amount", (Number) result.getReport().getApkSizeInfo().getFileTypeCounting().get(temp));
                tempObject.addProperty("size", (Number) result.getReport().getApkSizeInfo().getFileCapcityCounting().get(temp));
                typeJsonArray.add(tempObject);
            }

            reportContent.add("typeList", typeJsonArray);
            JsonArray permissionJsonArray = new JsonArray();
            HashSet<String> removeRedundantPermissionHelper = new HashSet();
            removeRedundantPermissionHelper.addAll(result.getReport().getSecurityInfo().getUses_permission());
            removeRedundantPermissionHelper.addAll(result.getReport().getSecurityInfo().getPermission());

            for (Iterator var31 = removeRedundantPermissionHelper.iterator(); var31.hasNext(); permissionJsonArray.add(tempObject)) {
                String usesPermission = (String) var31.next();
                tempObject = new JsonObject();
                tempObject.addProperty("name", usesPermission);
                if (JobConstants.SENSITIVE_PERMISSION.contains(usesPermission)) {
                    tempObject.addProperty("level", 10);
                } else {
                    tempObject.addProperty("level", 11);
                }
            }

            reportContent.add("permissionList", permissionJsonArray);
            JsonArray serviceJsonArray = new JsonArray();
            Iterator var33 = result.getReport().getSecurityInfo().getService().keySet().iterator();

            String noSuffix;
            while (var33.hasNext()) {
                noSuffix = (String) var33.next();
                JsonObject serviceItem = new JsonObject();
                serviceItem.addProperty("name", noSuffix);
                serviceItem.addProperty("process", (String) result.getReport().getSecurityInfo().getService().get(noSuffix));
                serviceJsonArray.add(serviceItem);
            }

            reportContent.add("serviceList", serviceJsonArray);
            Map<String, Long> typeSizeMap = new HashMap();
            noSuffix = "NoSuffix";
            long totalRedundantSize = 0L;
            JsonArray redundantJsonArray = new JsonArray();
            Iterator var18 = result.getReport().getFileInfo().getRedundentFile().keySet().iterator();

            Iterator var20;
            String fileName;
            String resource;
            while (var18.hasNext()) {
                Pair<String, Long> commen = (Pair) var18.next();
                var20 = ((List) result.getReport().getFileInfo().getRedundentFile().get(commen)).iterator();

                while (var20.hasNext()) {
                    fileName = (String) var20.next();
                    totalRedundantSize += (Long) commen.getSecond();
                    resource = fileName;
                    if (fileName.contains("/")) {
                        resource = fileName.substring(fileName.lastIndexOf("/") + 1);
                    }

                    String suffix = this.getSuffix(resource);
                    if (null == suffix) {
                        suffix = "NoSuffix";
                    }

                    if (!typeSizeMap.containsKey(suffix)) {
                        typeSizeMap.put(suffix, 0L);
                    }

                    long size = (Long) typeSizeMap.get(suffix);
                    size += (Long) commen.getSecond();
                    typeSizeMap.put(suffix, size);
                    JsonObject temp1 = new JsonObject();
                    temp1.addProperty("name", fileName);
                    temp1.addProperty("type", suffix);
                    temp1.addProperty("size", size);
                    redundantJsonArray.add(temp1);
                }
            }

            JsonArray redundantTypeJsonArray = new JsonArray();
            Iterator var38 = typeSizeMap.keySet().iterator();

            while (var38.hasNext()) {
                String type = (String) var38.next();
                JsonObject temp1 = new JsonObject();
                temp1.addProperty("name", type);
                temp1.addProperty("size", (Number) typeSizeMap.get(type));
                redundantTypeJsonArray.add(temp1);
            }

            reportContent.addProperty("redundantSize", totalRedundantSize);
            reportContent.add("redundantTypeList", redundantTypeJsonArray);
            reportContent.add("redundantList", redundantJsonArray);
            JsonArray unusedAssetJsonArray = new JsonArray();
            var20 = result.getReport().getAssetsInfo().getUnusedAssetsFileSize().keySet().iterator();

            while (var20.hasNext()) {
                fileName = (String) var20.next();
                unusedAssetJsonArray.add(fileName);
            }

            reportContent.addProperty("unusedAssetAmount", result.getReport().getAssetsInfo().getUnusedAssetsFileSize().size());
            reportContent.add("unusedAssetList", unusedAssetJsonArray);
            JsonArray unusedResourceJsonArray = new JsonArray();
            Iterator var43 = result.getReport().getResourceInfo().getUnusedResource().keySet().iterator();

            while (var43.hasNext()) {
                resource = (String) var43.next();
                JsonObject resourceObject = new JsonObject();
                resourceObject.addProperty("name", resource);
                resourceObject.addProperty("id", (String) result.getReport().getResourceInfo().getUnusedResource().get(resource));
                unusedResourceJsonArray.add(resourceObject);
            }

            reportContent.addProperty("unusedResourceAmount", result.getReport().getResourceInfo().getUnusedResource().size());
            reportContent.add("unusedResourceList", unusedResourceJsonArray);
            return reportContent.toString();
        }
    }

    private JsonObject createToDeleteDrawableListObject(TaskXmlyResult result) {
        JsonObject toDeleteDrawableListObject = new JsonObject();
        return toDeleteDrawableListObject;
    }

    private void outputFileSizeDetailInfo(TaskXmlyResult result, File outputDir) {
        List<Pair<String, Long>> fileSizeDetailList = new ArrayList();
        File methodDetailDir = new File(outputDir, "FileSizeDetail");
        methodDetailDir.mkdirs();
        List<String> orderedList = new ArrayList();
        orderedList.addAll(result.getReport().getApkSizeInfo().getFileSizeDetail().keySet());
        Collections.sort(orderedList);

        for (int i = 0; i < orderedList.size(); ++i) {
            String suffix = (String) orderedList.get(i);
            File suffixDetialFile = new File(methodDetailDir, "suffix_" + suffix + ".json");
            fileSizeDetailList.clear();
            fileSizeDetailList.addAll((Collection) result.getReport().getApkSizeInfo().getFileSizeDetail().get(suffix));

            try {
                suffixDetialFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(suffixDetialFile)));
                JsonObject classDetailObject = new JsonObject();
                classDetailObject.addProperty("suffix_name", suffix);
                JsonArray classArray = new JsonArray();
                Iterator var12 = fileSizeDetailList.iterator();

                while (var12.hasNext()) {
                    Pair<String, Long> sizeDetail = (Pair) var12.next();
                    JsonObject temp = new JsonObject();
                    temp.addProperty("file_name", (String) sizeDetail.getFirst());
                    temp.addProperty("file_size", (Number) sizeDetail.getSecond());
                    classArray.add(temp);
                }

                classDetailObject.add("size_info_list", classArray);
                writer.write(classDetailObject.toString());
                writer.flush();
                writer.close();
            } catch (Exception var15) {
            }
        }

    }

    private String getSuffix(String pureFileName) {
        return null != pureFileName && pureFileName.contains(".") ? pureFileName.substring(pureFileName.lastIndexOf(".") + 1) : null;
    }
}
