package com.cnh.frame.wraps;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/23
 */
public class DownloadWrap {


    /**
     * 下载远程文件到本地，并返回存储文件的相对路径
     *
     * @param fileRemoteUrl     远程文件url地址
     * @param realParentRoot    存储文件的<b>根目录</b>
     * @param relativeParentDir 存储文件的父<b>相对目录</b>
     * @param filePrefix        文件名前缀
     * @return
     * @throws Exception
     */
    public static String downloadRemoteFileToLocalAndReturnRelativePath(String fileRemoteUrl, String realParentRoot, String relativeParentDir, String filePrefix) throws Exception {
        realParentRoot = StringWrap.endBy(realParentRoot, "/");
        relativeParentDir = StringWrap.endBy(relativeParentDir, "/");
        File saveFile = downloadRemoteFileToLocal(fileRemoteUrl, realParentRoot + relativeParentDir, filePrefix);
        return relativeParentDir + saveFile.getName();
    }


    /**
     * 下载远程文件到本地
     *
     * @param fileRemoteUrl 远程文件url地址
     * @param parentDir     存储文件的父<b>物理目录</b>
     * @param filePrefix    文件名前缀
     * @return
     * @throws Exception
     */
    public static File downloadRemoteFileToLocal(String fileRemoteUrl, String parentDir, String filePrefix) throws Exception {
        if (StringUtils.isBlank(fileRemoteUrl)) {
            throw new Exception("remote file url can not be empty");
        }
        int extIndex = fileRemoteUrl.lastIndexOf(".");
        int remoteFileNameIndex = fileRemoteUrl.lastIndexOf("/");
        String ext = "";
        if (extIndex > -1 && extIndex > remoteFileNameIndex) {
            ext = fileRemoteUrl.substring(extIndex + 1);
        }

        InputStream is = null;
        File saveFile = null;
        try {
            URL url = new URL(fileRemoteUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(30000);
            is = urlConnection.getInputStream();
            saveFile = FileWrap.newFile(StringWrap.endBy(parentDir, "/") + filePrefix + "_" + System.currentTimeMillis() + (StringUtils.isBlank(ext) ? "" : ("." + ext)));
            FileWrap.saveFileFromStream(is, saveFile);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return saveFile;
    }
}
