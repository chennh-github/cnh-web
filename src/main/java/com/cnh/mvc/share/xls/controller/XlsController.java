package com.cnh.mvc.share.xls.controller;

import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.wraps.FileWrap;
import com.cnh.frame.wraps.RequestWrap;
import com.cnh.frame.wraps.StringWrap;
import com.cnh.frame.wraps.UploadWrap;
import com.cnh.mvc.share.xls.XlsServiceRegister;
import com.cnh.mvc.share.xls.handler.IXlsExportHandler;
import com.cnh.mvc.share.xls.handler.IXlsImportHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/19
 */
@Controller
@RequestMapping(value = "/xls/")
public class XlsController {

    private final Logger LOGGER = LoggerFactory.getLogger(XlsController.class);

    @Value("${custom.xlsStoreRelativePath}")
    private String xlsStoreRelativePath;


    /**
     * xls文件上传
     *
     * @param multipartRequest
     * @param name
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "importXls", method = RequestMethod.POST)
    @ResponseBody
    public Object importXls(MultipartHttpServletRequest multipartRequest, @RequestParam("xlsName") String name) throws Exception {

        Assist.threw(!FileWrap.isValidExt(multipartRequest, new String[]{"xls", "xlsx"}), "文件格式错误，只允许上传.xls .xlsx格式的Excel文件");

        // 取指定的处理服务
        IXlsImportHandler xlsImportHandler = XlsServiceRegister.getXlsImportHandler(name);
        Assist.notNull(xlsImportHandler, String.format("未指定名为[%s]的处理服务", name));

        String[] xlsFileRelativePaths = UploadWrap.uploadFilesAndReturnFileRelativePaths(multipartRequest, RequestWrap.getRealPath(multipartRequest, "webapp/"), StringWrap.endBy(xlsStoreRelativePath, "/") + "import/", "xls_");

        Assist.threw(xlsFileRelativePaths.length == 0, "保存excel文件错误");

        for (String xlsFileRelativePath : xlsFileRelativePaths) {
            FileInputStream fis = null;
            OutputStream fos = null;
            try {
                String xlsFileRealPath = RequestWrap.getRealPath(multipartRequest, "webapp/" + xlsFileRelativePath);
                fis = new FileInputStream(xlsFileRealPath);
                String ext = xlsFileRealPath.substring(xlsFileRealPath.lastIndexOf(".") + 1);
                Workbook workbook = null;
                if ("xls".equals(ext)) {
                    workbook = xlsImportHandler.importXls(new HSSFWorkbook(fis), RequestWrap.getMap(multipartRequest));
                } else {
//                    workbook = xlsImportHandler.importXls(new XSSFWorkbook(fis), RequestWrap.getMap(multipartRequest));
                }
                fos = new FileOutputStream(xlsFileRealPath);
                workbook.write(fos);
            } finally {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
        }
        // 只返回第一个xls文件的url地址，提供下载
        return RequestWrap.getFullPath(multipartRequest, xlsFileRelativePaths[0]);
    }


    /**
     * xls 文件导出
     *
     * @param request
     * @param response
     * @param name
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "exportXls", method = RequestMethod.POST)
    @ResponseBody
    public Object exportXls(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("xlsName") String name) throws Exception {


        // 取指定的处理服务
        IXlsExportHandler xlsExportHandler = XlsServiceRegister.getXlsExportHandler(name);
        Assist.notNull(xlsExportHandler, String.format("未指定名为[%s]的处理服务", name));

        String xlsHttpUrl = xlsExportHandler.exportXls(request, response);
        Assist.threw(StringUtils.isBlank(xlsHttpUrl), "生成excel文件失败");
        return xlsHttpUrl;
    }

}
