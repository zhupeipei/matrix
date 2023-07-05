

package com.tencent.matrix.apk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nonnull;

public class FileUtil {
    public FileUtil() {
    }

    public static void deleteDirectory(File dir) {
        if (null != dir && dir.exists()) {
            if (dir.isDirectory()) {
                File[] var1 = dir.listFiles();
                int var2 = var1.length;

                for (int var3 = 0; var3 < var2; ++var3) {
                    File temp = var1[var3];
                    deleteDirectory(temp);
                }
            }

            dir.delete();
        }
    }

    public static File zipFile(File file) {
        if (null != file && file.exists()) {
            return file.isDirectory() ? zipDirectory(file) : null;
        } else {
            return null;
        }
    }

    public static File zipDirectory(File dir) {
        if (null != dir && dir.exists()) {
            if (dir.isFile()) {
                return zipFile(dir);
            } else {
                File zippedFile = new File(dir.getParentFile(), dir.getName() + ".zip");
                ZipOutputStream zipOutputStream = null;

                try {
                    zipOutputStream = new ZipOutputStream(new FileOutputStream(zippedFile));
                    realZipDirectory(dir, zipOutputStream, "");
                } catch (Exception var12) {
                    var12.printStackTrace();
                } finally {
                    if (null != zipOutputStream) {
                        try {
                            zipOutputStream.close();
                        } catch (Exception var11) {
                            var11.printStackTrace();
                        }
                    }

                }

                return zippedFile;
            }
        } else {
            return null;
        }
    }

    private static void realZipDirectory(File dir, ZipOutputStream zipOutputStream, @Nonnull String path) {
        if (null != dir && dir.exists() && null != zipOutputStream) {
            FileInputStream inputStream = null;

            try {
                if (dir.isFile()) {
                    ZipEntry zipEntry = new ZipEntry(path + dir.getName());
                    inputStream = new FileInputStream(dir);
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] temp = new byte[4096];
                    boolean var6 = true;

                    int len;
                    while (-1 != (len = inputStream.read(temp))) {
                        zipOutputStream.write(temp, 0, len);
                    }

                    zipOutputStream.closeEntry();
                } else {
                    String realPath = path + dir.getName() + File.separator;
                    ZipEntry zipEntry = new ZipEntry(realPath);
                    zipOutputStream.putNextEntry(zipEntry);
                    File[] children = dir.listFiles();
                    if (null != children) {
                        File[] var7 = children;
                        int var8 = children.length;

                        for (int var9 = 0; var9 < var8; ++var9) {
                            File child = var7[var9];
                            if (null != child) {
                                realZipDirectory(child, zipOutputStream, realPath);
                            }
                        }
                    }
                }
            } catch (Exception var19) {
                var19.printStackTrace();
            } finally {
                if (null != inputStream) {
                    try {
                        inputStream.close();
                    } catch (Exception var18) {
                        var18.printStackTrace();
                    }
                }

            }

        }
    }
}
