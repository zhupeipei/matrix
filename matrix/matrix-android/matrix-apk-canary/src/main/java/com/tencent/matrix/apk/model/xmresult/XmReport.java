

package com.tencent.matrix.apk.model.xmresult;

import com.tencent.matrix.apk.model.xmresult.detail.ApkSizeInfo;
import com.tencent.matrix.apk.model.xmresult.detail.AssetsInfo;
import com.tencent.matrix.apk.model.xmresult.detail.ClassAndMethodInfo;
import com.tencent.matrix.apk.model.xmresult.detail.FileInfo;
import com.tencent.matrix.apk.model.xmresult.detail.ImageInfo;
import com.tencent.matrix.apk.model.xmresult.detail.LibInfo;
import com.tencent.matrix.apk.model.xmresult.detail.ProguardInfo;
import com.tencent.matrix.apk.model.xmresult.detail.ResourceInfo;
import com.tencent.matrix.apk.model.xmresult.detail.SecurityInfo;
import com.tencent.matrix.apk.model.xmresult.detail.Test;

public class XmReport {
    private ApkSizeInfo apkSizeInfo = new ApkSizeInfo();
    private FileInfo fileInfo = new FileInfo();
    private SecurityInfo securityInfo = new SecurityInfo();
    private ProguardInfo proguardInfo = new ProguardInfo();
    private AssetsInfo assetsInfo = new AssetsInfo();
    private ImageInfo imageInfo = new ImageInfo();
    private ResourceInfo resourceInfo = new ResourceInfo();
    private ClassAndMethodInfo classAndMethodInfo = new ClassAndMethodInfo();
    private LibInfo libInfo = new LibInfo();
    private Test test = new Test();

    public XmReport() {
    }

    public ApkSizeInfo getApkSizeInfo() {
        return this.apkSizeInfo;
    }

    public FileInfo getFileInfo() {
        return this.fileInfo;
    }

    public SecurityInfo getSecurityInfo() {
        return this.securityInfo;
    }

    public ProguardInfo getProguardInfo() {
        return this.proguardInfo;
    }

    public AssetsInfo getAssetsInfo() {
        return this.assetsInfo;
    }

    public ImageInfo getImageInfo() {
        return this.imageInfo;
    }

    public ResourceInfo getResourceInfo() {
        return this.resourceInfo;
    }

    public ClassAndMethodInfo getClassAndMethodInfo() {
        return this.classAndMethodInfo;
    }

    public LibInfo getLibInfo() {
        return this.libInfo;
    }

    public Test getTest() {
        return this.test;
    }
}
