package com.cnh.mvc.share.image.service;

import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.wraps.FileWrap;
import com.cnh.frame.wraps.StringWrap;
import com.cnh.frame.wraps.UploadWrap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/10/31
 */
@Service
public class FileDefaultService implements IImageService {

    @Value("${custom.imgServerRoot}")
    private String fileServerRoot;               // 文件存储服务器根目录

    @Value("${custom.imgServerParentDir}")
    private String fileServerParentDir;          // 文件存储服务器相对父目录

    @Value("${custom.imgSize:50}")
    private Long fileSize;				        // 支持的文件大小(M)

    @Override
    public Object handle(MultipartHttpServletRequest multipartRequest, String businessType) throws Exception {

        // 验证文件尺寸
        long allowedSize = fileSize*1024*1024;
        Assist.threw(!FileWrap.isValidSize(multipartRequest, allowedSize), String.format("文件大小不能超过[%s]MB", fileSize));

        String[] fileList = UploadWrap.uploadFilesAndReturnFileRelativePaths(multipartRequest, StringWrap.endBy(fileServerRoot, "/"), StringWrap.endBy(fileServerParentDir, "/") + StringWrap.endBy(businessType, "/"), "file_");

        Assist.threw(fileList.length == 0, "存储文件失败");

        return fileList;
    }
}
