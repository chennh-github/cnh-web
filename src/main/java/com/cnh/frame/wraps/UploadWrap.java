package com.cnh.frame.wraps;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/22
 */
public class UploadWrap {


    /**
     * http多文件上传，并返回文件的相对路径数组
     *
     * @param multipartRequest
     * @param relativeParentDir 文件所在的父目录<b>相对路径</b>，自动拼接成物理路径，若不存在物理目录则自动创建
     * @param filePrefix        文件前缀，如：xls_xxx
     * @return
     * @throws Exception
     */
    public static String[] uploadFilesAndReturnFileRelativePaths(MultipartHttpServletRequest multipartRequest, String realParentRoot, String relativeParentDir, String filePrefix) throws Exception {
        List<File> fileList = uploadFiles(multipartRequest, realParentRoot + relativeParentDir, filePrefix);
        String[] fileRelativePathList = new String[fileList.size()];
        relativeParentDir = StringWrap.endBy(relativeParentDir, "/");
        for (int i = 0, l = fileList.size(); i < l; i++) {
            fileRelativePathList[i] = relativeParentDir + fileList.get(i).getName();
        }
        return fileRelativePathList;
    }

    /**
     * http多文件上传
     *
     * @param multipartRequest
     * @param parentDir        文件所在的父目录<b>物理路径</b>，若不存在物理目录则自动创建
     * @param filePrefix       文件前缀，如：xls_xxx
     * @return
     * @throws Exception
     */
    public static List<File> uploadFiles(MultipartHttpServletRequest multipartRequest, String parentDir, String filePrefix) throws Exception {
        List<File> fileList = new ArrayList<File>();
        for (Iterator<String> fileNameIterator = multipartRequest.getFileNames(); fileNameIterator.hasNext(); ) {
            String fileName = fileNameIterator.next();
            fileList.add(uploadFile(multipartRequest.getFile(fileName), parentDir, filePrefix));
        }
        return fileList;
    }


    /**
     * 存储单个文件，并返回该文件的对象
     *
     * @param multipartFile http上传文件包装对象，由spring提供
     * @param parentDir     文件所在的父目录<b>物理路径</b>，若不存在物理目录则自动创建
     * @param filePrefix    文件前缀，如：xls_xxx
     * @return
     * @throws Exception
     */
    public static File uploadFile(MultipartFile multipartFile, String parentDir, String filePrefix) throws Exception {
        return FileWrap.saveFile(multipartFile, StringWrap.endBy(parentDir, "/"), filePrefix);
    }


}
