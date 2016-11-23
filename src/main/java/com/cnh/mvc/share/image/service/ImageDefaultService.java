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
 * @since 2016/8/22
 */
@Service
public class ImageDefaultService implements IImageService{

    @Value("${custom.imgServerRoot}")
    private String imgServerRoot;               // 图片存储服务器根目录

    @Value("${custom.imgServerParentDir:}")
    private String imgServerParentDir;          // 图片存储服务器相对父目录

    @Value("${custom.imgTypes:jpg,jpeg,gif,bmp,png}")
    private String imgTypes;				    // 支持的图片类型

    @Value("${custom.imgSize:2}")
    private Long imgSize;				        // 支持的图片大小(M)


    /**
     * 图片上传处理
     * @param multipartRequest
     * @return
     * @throws Exception
     */
    @Override
    public Object handle(MultipartHttpServletRequest multipartRequest, String businessType) throws Exception {

        // 验证图片类型
        Assist.threw(!FileWrap.isValidExt(multipartRequest, imgTypes.split(",")), String.format("只允许上传[%s]类型的图片", imgTypes));

        // 验证图片尺寸
        long allowedSize = imgSize*1024*1024;
        Assist.threw(!FileWrap.isValidSize(multipartRequest, allowedSize), String.format("图片尺寸不能超过[%s]Byte", allowedSize));

        String[] imgFileList = UploadWrap.uploadFilesAndReturnFileRelativePaths(multipartRequest, StringWrap.endBy(imgServerRoot, "/"), StringWrap.endBy(imgServerParentDir, "/") + StringWrap.endBy(businessType, "/"), "image_");

        Assist.threw(imgFileList.length == 0, "存储图片失败");

        return imgFileList;
    }
}
