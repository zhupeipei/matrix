

package com.tencent.matrix.apk.model.xmresult.configbuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.tencent.matrix.apk.ApkChecker.CheckerPath;
import com.tencent.matrix.apk.model.exception.ConfigCreateException;
import com.tencent.matrix.apk.model.job.JobConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ConfigBuilder {
    private static final String CONFIG_NAME = "config.json";

    public ConfigBuilder() {
    }

    public static void prepareMoudleName(String modulePath) throws FileNotFoundException, IOException {
        JobConstants.MODULE_NAME.clear();
        JobConstants.MODULE_NAME.add("UnKnown");
        System.out.println("*********************************");
        if (null == modulePath) {
            System.out.println("No Target Module Name is given");
            System.out.println("*********************************");
        } else {
            File moduleNameFile = new File(modulePath);
            if (moduleNameFile.exists() && moduleNameFile.isFile() && modulePath.toLowerCase().endsWith(".json")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(moduleNameFile)));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;

                while (null != (line = reader.readLine())) {
                    stringBuffer.append(line);
                }

                JsonArray moduleNames = (new JsonParser()).parse(stringBuffer.toString()).getAsJsonArray();

                for (int i = 0; i < moduleNames.size(); ++i) {
                    JobConstants.MODULE_NAME.add(moduleNames.get(i).getAsString().toLowerCase());
                }

                System.out.println("The given Target Modules are: " + JobConstants.MODULE_NAME);
            } else {
                System.out.println("No Target Module Name is given");
            }

            System.out.println("*********************************");
        }
    }

    public static File buildConfig(String apk, String mappingTxt, String resMapping, String rTxt, String nmTool, CheckerPath path) throws ConfigCreateException, IOException {
        return buildConfig(null == apk ? null : new File(apk), null == mappingTxt ? null : new File(mappingTxt), null == resMapping ? null : new File(resMapping), null == rTxt ? null : new File(rTxt), null == nmTool ? null : new File(nmTool), path);
    }

    public static File buildConfig(File apk, File mappingTxt, File resMappingTxt, File rTxt, File nmTool, CheckerPath path) throws ConfigCreateException, IOException {
        if (apk.exists() && apk.isFile() && apk.getName().toLowerCase().endsWith(".apk")) {
            System.out.println("*********************************");
            if (null != mappingTxt && mappingTxt.exists() && mappingTxt.isFile()) {
                System.out.println("mapping.txt is valid.");
            } else {
                System.out.println("mapping.txt is invalid.");
            }

            if (null != resMappingTxt && resMappingTxt.exists() && resMappingTxt.isFile()) {
                System.out.println("resMapping.txt is valid.");
            } else {
                System.out.println("resMapping.txt is invalid.");
            }

            if (null != rTxt && rTxt.exists() && rTxt.isFile()) {
                System.out.println("R.txt is valid.");
            } else {
                System.out.println("R.txt is invalid.");
            }

            if (null != nmTool && nmTool.exists() && nmTool.isFile()) {
                System.out.println("nmTool is valid.");
            } else {
                System.out.println("nmTool is invalid.");
            }

            System.out.println("*********************************");
            String content = createConfigContent(apk, mappingTxt, resMappingTxt, rTxt, nmTool, path);
            if (null == content) {
                throw new ConfigCreateException("Config is null Exception.");
            } else if (content.length() <= 0) {
                throw new ConfigCreateException("Config' length is zero Exception.");
            } else {
                File config = writeContentIntoConfig(apk, content);
                if (null == config) {
                    throw new ConfigCreateException("Config file is null after write Exception.");
                } else {
                    return config;
                }
            }
        } else {
            throw new ConfigCreateException("The apk Path is invalid Exception.");
        }
    }

    private static String createConfigContent(File apk, File mappingTxt, File resMappingTxt, File rTxt, File nmtool, CheckerPath path) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"--apk\":\"").append(apk.getAbsolutePath()).append("\",");
        if (null != mappingTxt && mappingTxt.exists() && mappingTxt.isFile()) {
            builder.append("\"--mappingTxt\":\"").append(mappingTxt.getAbsolutePath()).append("\",");
        }

        if (null != resMappingTxt && resMappingTxt.exists() && resMappingTxt.isFile()) {
            builder.append("\"--resMappingTxt\":\"").append(resMappingTxt.getAbsolutePath()).append("\",");
        }

        builder.append("\"--output\":\"").append(apk.getParent()).append("/\",");
//        builder.append("\"--format\":\"mm.xmly\",");
        builder.append("\"--format\":\"mm.json\",");
        if (null != path && null != path.url) {
            builder.append("\"--targetUrl\":\"").append(path.url).append("\",");
        }

        if (null != path && null != path.branch) {
            builder.append("\"--branch\":\"").append(path.branch).append("\",");
        }

        if (null != path && null != path.buildNumber) {
            builder.append("\"--buildNum\":\"").append(path.buildNumber).append("\",");
        }

        if (null != path && null != path.bundleVersion) {
            builder.append("\"--bundleVersion\":\"").append(path.bundleVersion).append("\",");
        }

        if (null != path && null != path.pipeLineHistoryId) {
            builder.append("\"--pipeLineHistoryId\":\"").append(path.pipeLineHistoryId).append("\",");
        }

//        builder.append("\"--formatConfig\":");
//        builder.append("[");
//        builder.append("{").append("\"name\":\"-countMethod\"").append("}");
//        builder.append("],");
        builder.append("\"options\":");
        builder.append("[");
        builder.append("{").append("\"name\":\"-manifest\"").append("}");
//        builder.append("{").append("\"name\":\"-xmmanifest\"").append("}");
//        builder.append("{").append("\"name\":\"-fileSize\",\"--min\":\"10\",\"--order\":\"desc\",\"--suffix\":\"png, jpg, jpeg, gif, arsc\"").append("},");
//        builder.append("{").append("\"name\":\"-countMethod\",\"--group\":\"package\"").append("},");
//        builder.append("{").append("\"name\":\"-xmapk\"").append("},");
//        builder.append("{").append("\"name\":\"-xmassets\"").append("},");
//        builder.append("{").append("\"name\":\"-xmclass\"").append("},");
//        builder.append("{").append("\"name\":\"-xmfile\"").append("},");
//        builder.append("{").append("\"name\":\"-xmmanifest\"").append("},");
//        builder.append("{").append("\"name\":\"-xmmethod\"").append("},");
//        if (null != rTxt && rTxt.exists() && rTxt.isFile()) {
//            builder.append("{").append("\"name\":\"-xmresource\", \"--rTxt\":\"" + rTxt.getAbsolutePath() + "\"").append("},");
//        }
//
//        if (null != nmtool && nmtool.exists() && nmtool.isFile()) {
//            builder.append("{").append("\"name\":\"-xmlibrary\", \"--toolnm\":\"" + nmtool.getAbsolutePath() + "\"").append("},");
//        }
//
//        builder.append("{").append("\"name\":\"-xmimage\"").append("}");
        builder.append("]");
        builder.append("}");
        return builder.toString();
    }

    private static File writeContentIntoConfig(File apk, String content) throws IOException, ConfigCreateException {
        File config = new File(apk.getParentFile(), "config.json");
        if (null != config) {
            if (config.exists()) {
                config.delete();
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(config)));
            writer.write(content);
            writer.flush();
            writer.close();
            return config;
        } else {
            throw new ConfigCreateException("Config file is null before write Exception.");
        }
    }
}
