package com.cnh.mvc.share.image.controller;

import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.wraps.StringWrap;
import com.cnh.mvc.share.image.ImageServiceRegister;
import com.cnh.mvc.share.image.service.IImageService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/22
 */
@Controller
@RequestMapping(value = "/image/")
public class ImageController {

    @Value("${custom.imgShowRoot}")
    private String imgShowRoot;                             // 图片展示的服务器路径

    /**
     * 常规图片上传
     * @param multipartRequest
     * @param businessType 业务类型，必须指定
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(MultipartHttpServletRequest multipartRequest,
                         @RequestParam("businessType") String businessType,
                         @RequestParam(value = "name", required = false) String name) throws Exception {
        Assist.threw(StringUtils.isBlank(businessType), "未指定businessType");
        JSONObject data = new JSONObject();
        data.put("paths", getImageService(name).handle(multipartRequest, businessType));
        data.put("imgShowRoot", StringWrap.endBy(imgShowRoot, "/"));
        return data;
    }

    /**
     * CKEDITOR中的图片上传
     * @param multipartRequest
     * @param response
     * @param callback
     * @throws Exception
     */
    @RequestMapping(value = "ckeditorUpload")
    @ResponseBody
    public void ckeditorUpload(MultipartHttpServletRequest multipartRequest,
                               HttpServletResponse response,
                               @RequestParam("CKEditorFuncNum") String callback,
                               @RequestParam(value = "name", required = false) String name) throws Exception{
        String[] imgFilePathList = (String[]) getImageService(name).handle(multipartRequest, "ckeditor");

        String imgRealPath = StringWrap.endBy(imgShowRoot, "/") + imgFilePathList[0];

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=utf-8");
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imgRealPath + "','')");
        out.println("</script>");
    }



    private IImageService getImageService(String name) throws Exception {
        if (StringUtils.isBlank(name)) {
            name = "defaultImage";
        }
        IImageService imageService = ImageServiceRegister.getImageService(name);
        Assist.notNull(imageService, "未指定businessType");
        return imageService;
    }

}
