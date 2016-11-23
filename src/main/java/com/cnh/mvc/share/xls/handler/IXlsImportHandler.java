package com.cnh.mvc.share.xls.handler;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/19
 */
public interface IXlsImportHandler {

    /**
     * 导入Excel
     * @param workbook
     * @param paramMap
     * @return
     * @throws IOException
     */
    public Workbook importXls(Workbook workbook, Map<String, Object> paramMap) throws Exception;
}
