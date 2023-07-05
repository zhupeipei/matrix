/*
 * Tencent is pleased to support the open source community by making wechat-matrix available.
 * Copyright (C) 2018 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencent.matrix.apk.model.job;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jinqiuchen on 17/6/14.
 */

public final class JobConstants {
    public static final String separater = "*********************************";
    public static final String PARAM_CONFIG = "--config";
    public static final String PARAM_INPUT = "--input";
    public static final String PARAM_APK = "--apk";
    public static final String PARAM_UNZIP = "--unzip";
    public static final String PARAM_OUTPUT = "--output";
    public static final String PARAM_FORMAT = "--format";
    public static final String PARAM_FORMAT_JAR = "--formatJar";
    public static final String PARAM_FORMAT_CONFIG = "--formatConfig";
    public static final String PARAM_TOOL_NM = "--toolnm";
    public static final String PARAM_MIN_SIZE_IN_KB = "--min";
    public static final String PARAM_ORDER = "--order";
    public static final String PARAM_GROUP = "--group";
    public static final String PARAM_SUFFIX = "--suffix";
    public static final String PARAM_R_TXT = "--rTxt";
    public static final String PARAM_IGNORE_RESOURCES_LIST = "--ignoreResources";
    public static final String PARAM_MAPPING_TXT = "--mappingTxt";
    public static final String PARAM_RES_MAPPING_TXT = "--resMappingTxt";
    public static final String PARAM_TARGET_URL = "--targetUrl";
    public static final String PARAM_IGNORE_ASSETS_LIST = "--ignoreAssets";
    public static final String PARAM_LOG_LEVEL = "--log";
    public static final String PARAM_BRANCH = "--branch";
    public static final String PARAM_BUILD_NUM = "--buildNumber";
    public static final String PARAM_BUNDLE_VERSION = "--bundleVersion";
    public static final String PARAM_PIPE_LINE_HISTORY_ID = "--pipeLineHistoryId";
    public static final String OPTION_MANIFEST = "-manifest";
    public static final String OPTION_FILE_SIZE = "-fileSize";
    public static final String OPTION_COUNT_METHOD = "-countMethod";
    public static final String OPTION_CHECK_RES_PROGUARD = "-checkResProguard";
    public static final String OPTION_FIND_NON_ALPHA_PNG = "-findNonAlphaPng";
    public static final String OPTION_CHECK_MULTILIB = "-checkMultiLibrary";
    public static final String OPTION_UNCOMPRESSED_FILE = "-uncompressedFile";
    public static final String OPTION_COUNT_R_CLASS = "-countR";
    public static final String OPTION_DUPLICATE_RESOURCES = "-duplicatedFile";
    public static final String OPTION_CHECK_MULTISTL = "-checkMultiSTL";
    public static final String OPTION_UNUSED_RESOURCES = "-unusedResources";
    public static final String OPTION_UNUSED_ASSETS = "-unusedAssets";
    public static final String OPTION_UNSTRIPPED_SO = "-unstrippedSo";
    public static final String OPTION_COUNT_CLASS = "-countClass";
    public static final String OPTION_XM_MAINIFEST = "-xmmanifest";
    public static final String OPTION_XM_APK = "-xmapk";
    public static final String OPTION_XM_FILE = "-xmfile";
    public static final String OPTION_XM_ASSETS = "-xmassets";
    public static final String OPTION_XM_IMAGE = "-xmimage";
    public static final String OPTION_XM_RESOURCE = "-xmresource";
    public static final String OPTION_XM_RFILE = "-xmRfile";
    public static final String OPTION_XM_CLASS = "-xmclass";
    public static final String OPTION_XM_METHOD = "-xmmethod";
    public static final String OPTION_XM_LIBRARY = "-xmlibrary";
    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";
    public static final String GROUP_PACKAGE = "package";
    public static final String GROUP_CLASS = "class";
    public static final String TASK_RESULT_REGISTRY = "TaskResult-Registry";
    public static final String TASK_RESULT_REGISTERY_CLASS = "TaskResult-Registry-Class";
    public static final String OUTPUT_DIRECTORY_NAME = "CheckerReport";
    public static final Set<String> MODULE_NAME = new HashSet();
    public static final String MODULE_UNKNOWN = "UnKnown";
    public static final Set<String> SENSITIVE_PERMISSION = new HashSet();

    public JobConstants() {
    }

    static {
        SENSITIVE_PERMISSION.add("android.permission-group.CONTACTS");
        SENSITIVE_PERMISSION.add("android.permission.WRITE_CONTACTS");
        SENSITIVE_PERMISSION.add("android.permission.GET_ACCOUNTS");
        SENSITIVE_PERMISSION.add("android.permission.READ_CONTACTS");
        SENSITIVE_PERMISSION.add("android.permission-group.PHONE");
        SENSITIVE_PERMISSION.add("android.permission.READ_CALL_LOG");
        SENSITIVE_PERMISSION.add("android.permission.READ_PHONE_STATE");
        SENSITIVE_PERMISSION.add("android.permission.CALL_PHONE");
        SENSITIVE_PERMISSION.add("android.permission.WRITE_CALL_LOG");
        SENSITIVE_PERMISSION.add("android.permission.USE_SIP");
        SENSITIVE_PERMISSION.add("android.permission.PROCESS_OUTGOING_CALLS");
        SENSITIVE_PERMISSION.add("com.android.voicemail.permission.ADD_VOICEMAIL");
        SENSITIVE_PERMISSION.add("android.permission-group.CALENDAR");
        SENSITIVE_PERMISSION.add("android.permission.READ_CALENDAR");
        SENSITIVE_PERMISSION.add("android.permission.WRITE_CALENDAR");
        SENSITIVE_PERMISSION.add("android.permission-group.CAMERA");
        SENSITIVE_PERMISSION.add("android.permission.CAMERA");
        SENSITIVE_PERMISSION.add("android.permission-group.SENSORS");
        SENSITIVE_PERMISSION.add("android.permission.BODY_SENSORS");
        SENSITIVE_PERMISSION.add("android.permission-group.LOCATION");
        SENSITIVE_PERMISSION.add("android.permission.ACCESS_FINE_LOCATION");
        SENSITIVE_PERMISSION.add("android.permission.ACCESS_COARSE_LOCATION");
        SENSITIVE_PERMISSION.add("android.permission-group.STORAGE");
        SENSITIVE_PERMISSION.add("android.permission.READ_EXTERNAL_STORAGE");
        SENSITIVE_PERMISSION.add("android.permission.WRITE_EXTERNAL_STORAGE");
        SENSITIVE_PERMISSION.add("android.permission-group.MICROPHONE");
        SENSITIVE_PERMISSION.add("android.permission.RECORD_AUDIO");
        SENSITIVE_PERMISSION.add("android.permission-group.SMS");
        SENSITIVE_PERMISSION.add("android.permission.READ_SMS");
        SENSITIVE_PERMISSION.add("android.permission.RECEIVE_WAP_PUSH");
        SENSITIVE_PERMISSION.add("android.permission.RECEIVE_MMS");
        SENSITIVE_PERMISSION.add("android.permission.RECEIVE_SMS");
        SENSITIVE_PERMISSION.add("android.permission.SEND_SMS");
        SENSITIVE_PERMISSION.add("android.permission.READ_CELL_BROADCASTS");
    }
}
