package com.cnh.mvc.share.xls.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/19
 */
public interface IXlsExportHandler {

    /**
     * 导出Excel
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String exportXls(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
