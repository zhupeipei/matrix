

package com.tencent.matrix.apk.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class RequestUtil {
    private static final String BOUNDARY = UUID.randomUUID().toString().toLowerCase().replaceAll("-", "");
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";

    public RequestUtil() {
    }

    public static String sendPostRequestWithJsonString(String url, String jsonString) {
        if (null != url && null != jsonString) {
            HttpURLConnection conn = null;

            try {
                URL obj = new URL(url);
                conn = (HttpURLConnection) obj.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "Mozilla/4.0");
                conn.setRequestProperty("Accept-Language", "en-US");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setConnectTimeout(2000);
                conn.setReadTimeout(2000);
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();
                byte[] content = jsonString.getBytes(StandardCharsets.UTF_8);
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(content, 0, content.length);
                outputStream.flush();
                outputStream.close();
                InputStream inputStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while (null != (line = reader.readLine())) {
                    stringBuilder.append(line);
                }

                reader.close();
                String var10 = stringBuilder.toString();
                return var10;
            } catch (Exception var14) {
                var14.printStackTrace();
            } finally {
                if (null != conn) {
                    conn.disconnect();
                }

            }

            return null;
        } else {
            return null;
        }
    }

    public static String sendPostRequestWithFile(String url, Map<String, String> param, Map<String, File> files) {
        if (null == url) {
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            DataOutputStream os = null;
            HttpURLConnection conn = null;
            BufferedReader reader = null;

            try {
                URL obj = new URL(url);
                conn = (HttpURLConnection) obj.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept", "*/*");
                conn.setRequestProperty("Connection", "keep-alive");
                conn.setRequestProperty("User-Agent", "Mozilla/4.0");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();
                os = new DataOutputStream(conn.getOutputStream());
                String temp = null;
                temp = writeParam(param, os);
                stringBuilder.append("\n").append("writeParam : ").append("\n");
                if (null != temp) {
                    stringBuilder.append(temp).append("\n");
                }

                stringBuilder.append("\n").append("writeFile : ").append("\n");
                temp = writeFile(files, os);
                if (null != temp) {
                    stringBuilder.append(temp).append("\n");
                }

                String endLine = "--" + BOUNDARY + "--" + "\r\n";
                os.write(endLine.getBytes());
                os.flush();
                int code = conn.getResponseCode();
                stringBuilder.append("\n").append("sendPostRequestWithFile result code is ").append(code).append("\n");
                InputStream inputStream;
                if (200 == code) {
                    inputStream = conn.getInputStream();
                } else {
                    inputStream = conn.getErrorStream();
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while (null != (line = reader.readLine())) {
                    stringBuilder.append(line);
                }

                String var13 = stringBuilder.toString();
                return var13;
            } catch (Exception var23) {
                var23.printStackTrace();
            } finally {
                try {
                    if (null != conn) {
                        conn.disconnect();
                    }

                    if (null != os) {
                        os.close();
                    }

                    if (null != reader) {
                        reader.close();
                    }
                } catch (Exception var22) {
                    var22.printStackTrace();
                }

            }

            return null;
        }
    }

    private static String writeParam(Map<String, String> params, OutputStream os) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("请求参数部分为：");

        try {
            if (null != params && !params.isEmpty()) {
                StringBuilder paramStringBuilder = new StringBuilder();
                Iterator var4 = params.keySet().iterator();

                while (var4.hasNext()) {
                    String key = (String) var4.next();
                    if (null != key) {
                        String value = (String) params.get(key);
                        if (null != value) {
                            paramStringBuilder.append("--").append(BOUNDARY).append("\r\n");
                            paramStringBuilder.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append("\r\n");
                            paramStringBuilder.append("Content-Type: text/plain; charset=utf-8").append("\r\n");
                            paramStringBuilder.append("Content-Transfer-Encoding: 8bit").append("\r\n");
                            paramStringBuilder.append("\r\n");
                            paramStringBuilder.append(value);
                            paramStringBuilder.append("\r\n");
                        }
                    }
                }

                os.write(paramStringBuilder.toString().getBytes());
                os.flush();
                stringBuilder.append(paramStringBuilder.toString());
            } else {
                stringBuilder.append("空");
            }
        } catch (Exception var7) {
            stringBuilder.append("\n").append("writeParam fails").append("\n");
            throw new Exception(var7);
        }

        return stringBuilder.toString();
    }

    private static String writeFile(Map<String, File> fileMap, OutputStream os) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("请求上传文件部分为：");
        FileInputStream is = null;

        try {
            if (null != fileMap && !fileMap.isEmpty()) {
                StringBuilder fileStringBuilder = new StringBuilder();
                Iterator var5 = fileMap.keySet().iterator();

                while (true) {
                    String key;
                    File file;
                    do {
                        do {
                            do {
                                if (!var5.hasNext()) {
                                    return stringBuilder.toString();
                                }

                                key = (String) var5.next();
                            } while (null == key);

                            file = (File) fileMap.get(key);
                        } while (null == file);
                    } while (!file.exists());

                    fileStringBuilder.append("--").append(BOUNDARY).append("\r\n");
                    fileStringBuilder.append("Content-Disposition: form-data; name=\"").append(key).append("\"; filename=\"").append(file.getName()).append("\"").append("\r\n");
                    fileStringBuilder.append("Content-Type: ").append(getContentType(file)).append("\r\n");
                    fileStringBuilder.append("Content-Transfer-Encoding: 8bit").append("\r\n");
                    fileStringBuilder.append("\r\n");
                    os.write(fileStringBuilder.toString().getBytes());
                    stringBuilder.append("\n").append(fileStringBuilder.toString());
                    is = new FileInputStream(file);
                    byte[] temp = new byte[1024 * 1024];

                    long totalSize;
                    int len;
                    for (totalSize = 0L; -1 != (len = is.read(temp)); totalSize += (long) len) {
                        os.write(temp, 0, len);
                    }

                    os.write("\r\n".getBytes());
                    os.flush();
                    stringBuilder.append("\n").append(key).append(" == ").append(file.getName()).append("  total Size : ").append(totalSize);
                }
            } else {
                stringBuilder.append("空");
            }
        } catch (Exception var19) {
            stringBuilder.append("\n").append("writeFile fails").append("\n");
            throw new Exception(var19);
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (Exception var18) {
                var18.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    private static String getContentType(File file) {
        return "application/octet-stream";
    }
}
