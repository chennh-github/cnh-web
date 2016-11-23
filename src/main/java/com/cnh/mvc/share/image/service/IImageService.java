package com.cnh.mvc.share.image.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/22
 */
public interface IImageService {


    /**
     * 图片服务处理函数
     * @return
     * @throws Exception
     */
    public Object handle(MultipartHttpServletRequest multipartRequest, String businessType) throws Exception;

}
