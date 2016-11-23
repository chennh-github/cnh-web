package com.cnh.mvc.components.image.controller;

import com.cnh.frame.wraps.FileWrap;
import com.cnh.frame.wraps.ImageWrap;
import com.cnh.frame.wraps.StringWrap;
import com.cnh.mvc.components.ComponentController;
import com.cnh.mvc.components.image.entity.Cropper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/1
 */
@Controller
@RequestMapping(ComponentController.COMPONENT_ROUTE_PATH + "/image/cropper")
public class CropperController extends ComponentController {

    @Value("${custom.imgServerRoot}")
    private String imgServerRoot;               // 图片存储服务器根目录
    @Value("${custom.imgServerParentDir}")
    private String imgServerParentDir;          // 图片存储服务器相对父目录
    @Value("${custom.imgShowRoot}")
    private String imgShowRoot;                 // 图片展示的服务器路径



    @RequestMapping("/index")
    public Object index(@RequestParam(value = "imgUrl", required = false) String imgUrl,
                        @RequestParam(value = "callback", required = false) String callback) {
        ModelAndView view = new ModelAndView("components/image/cropper/index");
        view.getModelMap().put("imgUrl", imgUrl);
        view.getModelMap().put("callback", callback);
        return view;
    }


    @RequestMapping(value = "/crop", method = RequestMethod.POST)
    @ResponseBody
    public Object crop(@RequestBody Cropper cropper) throws Exception {

        double zoom = cropper.getZoom();

        // 载入图片
        BufferedImage bufferedImage = ImageWrap.readImage(cropper.getImgUrl());

        // 缩放到指定比例
        if (zoom != 1) {
            bufferedImage = ImageWrap.zoom(bufferedImage, zoom);
        }

        // 是否水平翻转图片
        if (cropper.getScaleX() == -1) {
            bufferedImage = ImageWrap.flipHorizontal(bufferedImage);
        }

        // 是否垂直翻转图片
        if (cropper.getScaleY() == -1) {
            bufferedImage = ImageWrap.flipVertical(bufferedImage);
        }

        // 旋转图片，必须先翻转再旋转
        if (cropper.getRotate() != 0) {
            bufferedImage = ImageWrap.rotate(bufferedImage, cropper.getRotate(), null);
        }

        // 裁剪图片
        bufferedImage = ImageWrap.cut(bufferedImage, cropper.getX(), cropper.getY(), cropper.getWidth(), cropper.getHeight());

        String ext = cropper.getImgUrl().substring(cropper.getImgUrl().lastIndexOf(".") + 1);
        String relativePath = StringWrap.endBy(imgServerParentDir, "/") + "cropper/" + System.currentTimeMillis() + "." + ext;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(FileWrap.newFile(StringWrap.endBy(imgServerRoot, "/") + relativePath));
            ImageIO.write(bufferedImage, ext, fos);
        } finally {
            if (fos != null) {
                fos.flush();
                fos.close();
            }
        }

        JSONObject data = new JSONObject();
        data.put("paths", relativePath);
        data.put("imgShowRoot", StringWrap.endBy(imgShowRoot, "/"));
        return data;
    }

}
