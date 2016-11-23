package com.cnh.mvc.share.image;

import com.cnh.frame.holders.ApplicationContextHolder;
import com.cnh.mvc.share.image.service.IImageService;

import java.util.HashMap;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/23
 */
public class ImageServiceRegister {

    /**
     * 图片处理服务
     */
    private static Map<String, String> imageServiceMap = new HashMap<String, String>();


   static {

        // 默认的图片处理服务
        imageServiceMap.put("defaultImage", "imageDefaultService");

       // 默认的文件处理服务
       imageServiceMap.put("defaultFile", "fileDefaultService");
    }

    /**
     * 取图片处理服务
     * @param name
     * @return
     * @throws Exception
     */
    public static IImageService getImageService(String name) throws Exception{
        if (imageServiceMap.containsKey(name)) {
            return ApplicationContextHolder.getApplicationContext().getBean(imageServiceMap.get(name), IImageService.class);
        }
        return null;
    }

    /**
     * 取默认的图片处理服务
     * @return
     * @throws Exception
     */
    public static IImageService getDefaultImageService() throws Exception {
        return getImageService("default");
    }

}
